package com.bws.userservice.rest.service;

import com.bws.userservice.api.client.TokenServiceClient;
import com.bws.userservice.api.request.*;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.api.response.CompanyIdResponse;
import com.bws.userservice.api.response.SellerGetResponse;
import com.bws.userservice.exception.AccessDeniedException;
import com.bws.userservice.exception.CreateFailedException;
import com.bws.userservice.exception.NotFoundException;
import com.bws.userservice.exception.ProcessFailedException;
import com.bws.userservice.model.Role;
import com.bws.userservice.model.dto.SellerDTO;
import com.bws.userservice.model.entity.Seller;
import com.bws.userservice.model.entity.User;
import com.bws.userservice.repository.SellerRepository;
import com.bws.userservice.repository.UserRepository;
import com.bws.userservice.rest.service.interfaces.*;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

import static com.bws.userservice.model.constants.ErrorCodeConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j

public class SellerServiceImpl implements ISellerService {

    private final SellerRepository sellerRepository;

    private final MapperService mapperService;

    private final IUserService userService;

    private final BalanceServiceImpl balanceService;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final TokenServiceClient tokenServiceClient;

    private final UserRepository userRepository;

    @Transactional
    public BaseResponse createSeller(SellerAddRequest request) throws CreateFailedException {
        try {
            User user = modelMapper.map(request, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            user.setActive(2); //TODO use enum instead of constant int value


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
            //sellerRepository.save(Seller.builder().sellerName(request.getUsername()).sellerUsername(request.getUsername()).companyId().build());

            balanceService.createBalance(CreateBalanceRequest.builder()
                    .userId(user.getUserId())
                    .username(user.getUsername())
                    .build(), user);

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setDescription("ACTIVATE USER FROM EMAIL");

            return baseResponse;
        } catch (Exception e) {
            log.error("Create user error: " + e);
            throw new CreateFailedException(USER_CREATE_FAILED);
        }
    }

    @Transactional
    public BaseResponse registerSeller(SellerAddRequest request) throws CreateFailedException {
        /*try {
            createService.createUser(mapperService.map(request, UserAddRequest.class));
            sellerRepository.save(Seller.builder().sellerName(request.getSellerName()).sellerUsername(request.getUsername()).build());
            return new BaseResponse();
        } catch (Exception e) {
            log.error("Register seller error: " + e);
            throw new CreateFailedException(SELLER_CREATE_FAILED);
        }*/
        return null;
    }


    @Override
    public SellerGetResponse sellerGetResponse(String sellers, BaseRequest baseRequest) throws NotFoundException {
        try {
            return new SellerGetResponse(mapperService.map(sellerRepository.findSellersBySellerId(sellers), SellerDTO.class));
        } catch (Exception e) {
            log.error("Seller get error: " + e);
            throw new NotFoundException(SELLER_NOT_FOUND);
        }
    }


    @Override
    public String extractSellerName(BaseRequest request) throws NotFoundException {
        try {
            return sellerRepository.findSellerBySellerUsername(tokenServiceClient.extractedUsername(request)).getSellerUsername();
        } catch (Exception e) {
            log.error("Extract seller name error: " + e);
            throw new NotFoundException(SELLER_NOT_FOUND);
        }
    }


    @Override
    public BaseResponse activateSellerByAdmin(String sellerId, BaseRequest baseRequest) throws AccessDeniedException, NotFoundException {
        try {
            String localUsername = sellerRepository.findSellerBySellerId(sellerId).getSellerUsername();
            User user = userRepository.findByUsername(localUsername);
            user.setActive(1); //TODO use enum instead of constant int value
            userRepository.save(user);
            sellerRepository.delete(sellerRepository.findSellerBySellerId(sellerId));

            return new BaseResponse();
        } catch (Exception e) {
            log.error("Activate seller error: " + e);
            throw new NotFoundException(SELLER_NOT_FOUND);
        }
    }

    @Override
    public CompanyIdResponse extractCompanyId(BaseRequest request) throws NotFoundException,ProcessFailedException {
        try {
            String username = tokenServiceClient.extractedUsername(request);
            User user = userRepository.findByUsername(username);
            Seller seller = sellerRepository.findSellerBySellerName(username);

            if ((seller.getCompanyId() == null) && !(user.getRole().equals(Role.ADMIN.toString()))) {
                throw new NotFoundException(SELLER_NOT_FOUND);
            }

            if (user.getRole().equals(Role.ADMIN.toString())) {
                return new CompanyIdResponse("bakirwebservice");
            }
            return new CompanyIdResponse(seller.getCompanyId());

        } catch (NotFoundException e) {
            throw new NotFoundException(SELLER_NOT_FOUND);
        }
        catch (Exception e) {
            throw new ProcessFailedException("COMPANY ID EXTRACTION FAILED");
        }

    }

}
