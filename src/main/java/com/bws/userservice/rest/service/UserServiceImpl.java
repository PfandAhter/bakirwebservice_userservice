package com.bws.userservice.rest.service;


import com.bws.userservice.api.client.EmailServiceClient;
import com.bws.userservice.api.client.TokenServiceClient;
import com.bws.userservice.api.request.*;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.exception.CreateFailedException;
import com.bws.userservice.exception.NotFoundException;
import com.bws.userservice.model.Role;
import com.bws.userservice.model.entity.*;
import com.bws.userservice.repository.*;
import com.bws.userservice.rest.service.interfaces.ICreateService;
import com.bws.userservice.rest.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;

import static com.bws.userservice.model.constants.ErrorCodeConstants.USER_CREATE_FAILED;
import static com.bws.userservice.model.constants.ErrorCodeConstants.USER_NOT_FOUND;

@RequiredArgsConstructor
@Slf4j
@Service

public class UserServiceImpl implements IUserService, ICreateService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final BalanceServiceImpl balanceService;

    private final AddressRepository addressRepository;

    private final CountryRepository countryRepository;

    private final CityRepository cityRepository;

    private final EmailServiceClient emailServiceClient;

    private final TokenServiceClient tokenServiceClient;


    @Override
    public User findUserByUsername(FindUserByUsernameRequest request) {
        return userRepository.findByUsername(request.getUsername());
    }

    @Transactional
    @Override
    public BaseResponse createUser(UserAddRequest request) throws CreateFailedException {
        try {
            User user = modelMapper.map(request, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(2); //TODO use enum instead of constant int value

            if (request.getUsername().equals("Pfand") && request.getFirstName().equals("Ataberk") && request.getLastName().equals("Bakir")) {
                user.setRole(Role.ADMIN);
                user.setActive(1); //TODO use enum instead of constant int value
            } else {
                user.setRole(Role.CUSTOMER);
            }

            Timestamp timestamp1 = Timestamp.from(Instant.now());
            Timestamp timestamp2 = Timestamp.from(Instant.now());
            user.setAccountCreateDate(timestamp1);
            user.setPasswordLastChangedDate(timestamp1);
            timestamp2.setMonth(timestamp2.getMonth() + 2);
            user.setPasswordExpireDate(timestamp2);

//            emailServiceClient.validateEmail(EmailValidatorRequest.builder()
//                    .email(request.getEmail())
//                    .username(request.getUsername()).build()); //TODO activate this line when email service is ready

            this.userRepository.save(user);

            balanceService.createBalance(CreateBalanceRequest.builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .build(), user);

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setErrorDescription("ACTIVATE USER FROM EMAIL");

            return baseResponse;
        } catch (Exception e) {
            log.error("Create user error: " + e);
            throw new CreateFailedException(USER_CREATE_FAILED);
        }
    }

    @Transactional
    @Override
    public BaseResponse addAddressInfo(AddAddressInfoRequest request) {
        String localUsername = tokenServiceClient.extractedUsername(request);

        if (addressRepository.findAddressByUsername(localUsername) == null) {
            Address localAddress = modelMapper.map(request, Address.class);
            localAddress.setUsername(localUsername);
            Country localCountry;

            if (countryRepository.findCountryByCountryName(request.getCountryName()) == null) {
                localCountry = modelMapper.map(request, Country.class);
                localCountry.setCountryName(localCountry.getCountryName().toUpperCase());
                countryRepository.save(localCountry);
            } else {
                localCountry = countryRepository.findCountryByCountryName(request.getCountryName());
            }

            if (cityRepository.findCityByCityName(request.getCityName()) == null) {
                City localCity = modelMapper.map(request, City.class);
                localCity.setCountry_id(localCountry.getCountryId());
                localCity.setCityName(localCity.getCityName().toUpperCase());
                cityRepository.save(localCity);
                localAddress.setCity_id(localCity.getCityId());
            } else {
                City localCity = cityRepository.findCityByCityName(request.getCityName());
                localAddress.setCity_id(localCity.getCityId());
            }

            localAddress.setDistrict(localAddress.getDistrict().toUpperCase());

            addressRepository.save(localAddress);

            return new BaseResponse();
        } else {
            Address localAddress = addressRepository.findAddressByUsername(localUsername);
            Country localCountry;

            if (countryRepository.findCountryByCountryName(request.getCountryName()) == null) {
                localCountry = modelMapper.map(request, Country.class);
                localCountry.setCountryName(localCountry.getCountryName().toUpperCase());
                countryRepository.save(localCountry);
            } else {
                localCountry = countryRepository.findCountryByCountryName(request.getCountryName());
            }

            if (cityRepository.findCityByCityName(request.getCityName()) == null) {
                City localCity = modelMapper.map(request, City.class);
                localCity.setCountry_id(localCountry.getCountryId());
                localCity.setCityName(localCity.getCityName().toUpperCase());
                cityRepository.save(localCity);
                localAddress.setCity_id(localCity.getCityId());
            } else {
                City localCity = cityRepository.findCityByCityName(request.getCityName());
                localAddress.setCity_id(localCity.getCityId());
            }

            localAddress.setDistrict(localAddress.getDistrict().toUpperCase());
            addressRepository.save(localAddress);

            return new BaseResponse();
        }
    }

    public BaseResponse changePassword(PasswordChangeRequest request) throws NotFoundException {
        try {
            Timestamp timestamp = Timestamp.from(Instant.now());

            String localUsername = tokenServiceClient.extractedUsername(request);
            User localUser = userRepository.findByUsername(localUsername);
            localUser.setPasswordLastChangedDate(timestamp);

            timestamp.setMonth(timestamp.getMonth() + 2);
            localUser.setPasswordExpireDate(timestamp);

            localUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(localUser);

            return new BaseResponse();
        } catch (Exception e) {
            log.error("Change password error: " + e);
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

    public String extractRole(BaseRequest request) throws NotFoundException {
        try {
            String localUsername = tokenServiceClient.extractedUsername(request);
            return userRepository.findByUsername(localUsername).getRole().toString();
        } catch (Exception e) {
            log.error("Extract role error: " + e);
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

    public BaseResponse activateUser(UserActivateRequest request) throws NotFoundException {
        try {
            User localUser = userRepository.findByUsername(request.getUsername());
            localUser.setActive(1);
            userRepository.save(localUser);
            return new BaseResponse();
        } catch (Exception e) {
            log.error("Activate user error: " + e);
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

    public BaseResponse forgetPassword(ForgetPasswordRequest request) throws NotFoundException {
        try {
            return emailServiceClient.forgetPasswordCreateCode(request);
        } catch (Exception e) {
            log.error("Forget password error: " + e);
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

    //TODO refactor use token service client instead localUsername for username
    public BaseResponse addPhoto(String localUsername, MultipartFile image) throws IOException {
//        String localUsername = tokenServiceClient.extractedUsername(baseRequest);
        User localUser = userRepository.findByUsername(localUsername);
        localUser.setPhoto(Base64.getEncoder().encodeToString(image.getBytes()));
        userRepository.save(localUser);

        return new BaseResponse();
    }


    public byte[] getPhoto(BaseRequest baseRequest) {
        String localUsername = tokenServiceClient.extractedUsername(baseRequest);
        userRepository.findByUsername(localUsername);
        byte[] imageBytes = java.util.Base64.getDecoder().decode(userRepository.findByUsername(localUsername).getPhoto());

//        byte[] imageBytes = userRepository.findByUsername(localUsername).getPhoto().getBytes();

        return imageBytes;
    }


    //TODO REFACTOR...
    public BaseResponse changePasswordByCode(ChangePwByCodeRequest request) throws NotFoundException {
        try {
            User localUser = userRepository.findByEmail(request.getEmail());
            localUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
            Timestamp timestamp = Timestamp.from(Instant.now());
            localUser.setPasswordLastChangedDate(timestamp);
            timestamp.setMonth(timestamp.getMonth() + 2);
            localUser.setPasswordExpireDate(timestamp);

            userRepository.save(localUser);
            return new BaseResponse();
        }catch (Exception e){
            log.error("Change password by code error: " + e);
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

}
