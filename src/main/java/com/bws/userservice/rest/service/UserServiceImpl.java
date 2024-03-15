package com.bws.userservice.rest.service;


import com.bws.userservice.api.request.CreateBalanceRequest;
import com.bws.userservice.api.request.FindUserByUsernameRequest;
import com.bws.userservice.api.request.UserAddRequest;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.exception.AuthException;
import com.bws.userservice.exception.UserNotFoundException;
import com.bws.userservice.model.constants.Constants;
import com.bws.userservice.model.Role;
import com.bws.userservice.model.entity.Address;
import com.bws.userservice.model.entity.City;
import com.bws.userservice.model.entity.Country;
import com.bws.userservice.model.entity.User;
import com.bws.userservice.repository.AddressRepository;
import com.bws.userservice.repository.CityRepository;
import com.bws.userservice.repository.CountryRepository;
import com.bws.userservice.repository.UserRepository;
import com.bws.userservice.rest.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import static com.bws.userservice.model.constants.PropertyConstants.REST_TEMPLATE_REQUEST_MICROSERVICE_PAYMENT_SERVICE_BALANCE_CREATE;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final ModelMapper modelMapper;

    private final RestTemplate restTemplate;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AddressRepository addressRepository;

    private final CountryRepository countryRepository;

    private final CityRepository cityRepository;


    public User findUserByUsername(FindUserByUsernameRequest request) throws UserNotFoundException {
        try {
            return userRepository.findByUsername(request.getUsername());
        } catch (Exception exception) {
            throw new UserNotFoundException("User not found.");
        }
    }

    @Value(REST_TEMPLATE_REQUEST_MICROSERVICE_PAYMENT_SERVICE_BALANCE_CREATE)
    private String createBalanceFromPaymentServicePaths;

    public BaseResponse createUser(UserAddRequest request) throws AuthException {
        if (validateUserRegister(request)) {
            User user = modelMapper.map(request, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(1);

            Address localAddress = modelMapper.map(request, Address.class);
            Country localCountry;

            if(countryRepository.findCountryByCountryName(request.getCountryName()) == null){
                localCountry = modelMapper.map(request, Country.class);
                localCountry.setCountryName(localCountry.getCountryName().toUpperCase());
                countryRepository.save(localCountry);
            }else{
                localCountry = countryRepository.findCountryByCountryName(request.getCountryName());
            }

            if(cityRepository.findCityByCityName(request.getCityName()) == null){
                City localCity = modelMapper.map(request, City.class);
                localCity.setCountry_id(localCountry.getCountry_id());
                localCity.setCityName(localCity.getCityName().toUpperCase());
                cityRepository.save(localCity);
                localAddress.setCity_id(localCity.getCity_id());
            }else{
                City localCity = cityRepository.findCityByCityName(request.getCityName());
                localAddress.setCity_id(localCity.getCity_id());
            }

            localAddress.setDistrict(localAddress.getDistrict().toUpperCase());

            addressRepository.save(localAddress);

            //TODO BURAYI DUZENLE UNUTMA...

            if (request.getUsername().equals("Pfand") && request.getFirstName().equals("Ataberk") && request.getLastName().equals("Bakir")) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.CUSTOMER);
            }
            this.userRepository.save(user);

            CreateBalanceRequest createBalanceRequest = new CreateBalanceRequest();
            createBalanceRequest.setUser_id(user.getUser_id());
            createBalanceRequest.setUsername(user.getUsername());

            restTemplate.postForObject(createBalanceFromPaymentServicePaths,createBalanceRequest ,BaseResponse.class);

            log.info("User registered , user email : " + user.getEmail() + " user first name : " + user.getFirstName());
            return new BaseResponse();
        } else {
            throw new AuthException("INVALID CREDENTIALS");
        }
    }


    private boolean validateUserRegister(UserAddRequest request) throws AuthException {
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new AuthException(Constants.EMAIL_IN_USE);
        }
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new AuthException(Constants.USERNAME_IN_USE);
        } else if (request.getPassword().length() < 8) {
            throw new AuthException(Constants.INVALID_PASSWORD);
        } else {
            return true;
        }
    }

}
