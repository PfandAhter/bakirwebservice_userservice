package com.bws.userservice.rest.service;


import com.bws.userservice.api.client.EmailServiceClient;
import com.bws.userservice.api.client.TokenServiceClient;
import com.bws.userservice.api.request.*;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.api.response.SellerGetResponse;
import com.bws.userservice.exception.AccessDeniedException;
import com.bws.userservice.exception.ActivateCodeNotMatchedException;
import com.bws.userservice.model.Role;
import com.bws.userservice.model.dto.SellerDTO;
import com.bws.userservice.model.entity.*;
import com.bws.userservice.repository.*;
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

@RequiredArgsConstructor
@Slf4j
@Service

public class UserServiceImpl implements IUserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final BalanceServiceImpl balanceService;

    private final AddressRepository addressRepository;

    private final CountryRepository countryRepository;

    private final CityRepository cityRepository;

    private final EmailServiceClient emailServiceClient;

    private final TokenServiceClient tokenServiceClient;

    private final SellerRepository sellerRepository;

    private final MapperServiceImpl mapperService;

    @Override
    public User findUserByUsername(FindUserByUsernameRequest request) {
        return userRepository.findByUsername(request.getUsername());
    }

    @Transactional
    @Override
    public BaseResponse createUser(UserAddRequest request) {
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(2);

        if (request.getUsername().equals("Pfand") && request.getFirstName().equals("Ataberk") && request.getLastName().equals("Bakir")) {
            user.setRole(Role.ADMIN);
            user.setActive(1);
        } else {
            user.setRole(Role.CUSTOMER);
        }

        CreateBalanceRequest createBalanceRequest = new CreateBalanceRequest();
        createBalanceRequest.setUser_id(user.getUserId());
        createBalanceRequest.setUsername(user.getUsername());

        Timestamp timestamp1 = Timestamp.from(Instant.now());
        Timestamp timestamp2 = Timestamp.from(Instant.now());
        user.setAccountCreateDate(timestamp1);
        user.setPasswordLastChangedDate(timestamp1);
        timestamp2.setMonth(timestamp2.getMonth() + 2);
        user.setPasswordExpireDate(timestamp2);

        EmailValidatorRequest emailValidatorRequest = new EmailValidatorRequest();
        emailValidatorRequest.setEmail(request.getEmail());
        emailValidatorRequest.setUsername(request.getUsername());
//        emailServiceClient.validateEmail(emailValidatorRequest);

        this.userRepository.save(user);

        balanceService.createBalance(createBalanceRequest, user);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setErrorDescription("ACTIVATE USER FROM EMAIL");

//        log.info("User registered , user email : " + user.getEmail() + " user first name : " + user.getFirstName());
        return baseResponse;
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

    public BaseResponse changePassword(PasswordChangeRequest request) {
        Timestamp timestamp = Timestamp.from(Instant.now());

        String localUsername = tokenServiceClient.extractedUsername(request);
        User localUser = userRepository.findByUsername(localUsername);
        localUser.setPasswordLastChangedDate(timestamp);

        timestamp.setMonth(timestamp.getMonth() + 2);

        localUser.setPasswordExpireDate(timestamp);

        localUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(localUser);

        return new BaseResponse();
    }

    public String extractRole(BaseRequest request) {
        String localUsername = tokenServiceClient.extractedUsername(request);
        return userRepository.findByUsername(localUsername).getRole().toString();
    }

    public BaseResponse activateUser(UserActivateRequest request) throws ActivateCodeNotMatchedException {
        User localUser = userRepository.findByUsername(request.getUsername());
        if (Boolean.TRUE.equals(emailServiceClient.activateCodeMatched(request))) {
            localUser.setActive(1);
            userRepository.save(localUser);
            return new BaseResponse();
        } else {
            throw new ActivateCodeNotMatchedException("ACTIVATE CODE NOT MATCHED");
        }
    }

    @Transactional
    public BaseResponse registerSeller(SellerAddRequest request) {
        createUser(modelMapper.map(request, UserAddRequest.class));
        sellerRepository.save(Seller.builder().sellerName(request.getSellerName()).sellerUsername(request.getUsername()).build());
        return new BaseResponse();
    }

    public BaseResponse activateSellerByAdmin(String sellerId, BaseRequest baseRequest) throws AccessDeniedException {

        if (extractRole(baseRequest).equals("ADMIN")) {
            String localUsername = sellerRepository.findSellerBySellerId(sellerId).getSellerUsername();
            User user = userRepository.findByUsername(localUsername);
            user.setActive(1);
            userRepository.save(user);
            sellerRepository.delete(sellerRepository.findSellerBySellerId(sellerId));

            return new BaseResponse();
        } else {
            throw new AccessDeniedException("THIS SERVICE ONLY FOR ADMIN");
        }
    }

    public SellerGetResponse sellerGetResponse(String sellers, BaseRequest baseRequest) throws AccessDeniedException {
        if (extractRole(baseRequest).equals("ADMIN")) {
            return new SellerGetResponse(mapperService.map(sellerRepository.findSellersBySellerId(sellers), SellerDTO.class));
        } else {
            throw new AccessDeniedException("THIS SERVICE ONLY FOR ADMIN");
        }
    }

    public String extractSellerName(BaseRequest request) {
        return sellerRepository.findSellerBySellerUsername(tokenServiceClient.extractedUsername(request)).getSellerUsername();
    }

    public BaseResponse forgetPassword(ForgetPasswordRequest request) {
        return emailServiceClient.forgetPasswordCreateCode(request);
    }

    public BaseResponse addPhoto (String localUsername ,MultipartFile image) throws IOException {
//        String localUsername = tokenServiceClient.extractedUsername(baseRequest);
        User localUser = userRepository.findByUsername(localUsername);
        localUser.setPhoto(Base64.getEncoder().encodeToString(image.getBytes()));
        userRepository.save(localUser);

        return new BaseResponse();
    }

    public byte[] getPhoto (BaseRequest baseRequest) throws IOException{
        String localUsername = tokenServiceClient.extractedUsername(baseRequest);
        userRepository.findByUsername(localUsername);
        byte[] imageBytes = java.util.Base64.getDecoder().decode(userRepository.findByUsername(localUsername).getPhoto());

//        byte[] imageBytes = userRepository.findByUsername(localUsername).getPhoto().getBytes();

        return imageBytes;
    }



    public BaseResponse changePwByCode(ChangePwByCodeRequest request) throws ActivateCodeNotMatchedException {
        User localUser = userRepository.findByEmail(request.getEmail());
        if (emailServiceClient.passwordChangeCodeMatched(request) && request.getNewPassword().equals(request.getNewPasswordAgain())) {
            localUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
            Timestamp timestamp = Timestamp.from(Instant.now());
            localUser.setPasswordLastChangedDate(timestamp);
            timestamp.setMonth(timestamp.getMonth() + 2);
            localUser.setPasswordExpireDate(timestamp);

            userRepository.save(localUser);
            return new BaseResponse();
        }else{
            throw new ActivateCodeNotMatchedException("EMAIL CODE NOT MATCHED");
        }
    }

}
