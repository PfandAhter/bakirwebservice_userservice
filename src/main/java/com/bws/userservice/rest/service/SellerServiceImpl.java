package com.bws.userservice.rest.service;

import com.bws.userservice.api.client.TokenServiceClient;
import com.bws.userservice.api.request.*;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.api.response.SellerGetResponse;
import com.bws.userservice.exception.AccessDeniedException;
import com.bws.userservice.exception.CreateFailedException;
import com.bws.userservice.exception.NotFoundException;
import com.bws.userservice.model.Role;
import com.bws.userservice.model.dto.SellerDTO;
import com.bws.userservice.model.entity.Seller;
import com.bws.userservice.model.entity.User;
import com.bws.userservice.repository.SellerRepository;
import com.bws.userservice.repository.UserRepository;
import com.bws.userservice.rest.service.interfaces.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bws.userservice.model.constants.ErrorCodeConstants.NO_AUTHORITY;
import static com.bws.userservice.model.constants.ErrorCodeConstants.SELLER_CREATE_FAILED;
import static com.bws.userservice.model.constants.ErrorCodeConstants.SELLER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j

public class SellerServiceImpl implements ISellerService {

    private final SellerRepository sellerRepository;

    private final MapperService mapperService;

    private final IUserService userService;

    private final ICreateService createService;

    private final TokenServiceClient tokenServiceClient;

    private final UserRepository userRepository;

    @Transactional
    public BaseResponse registerSeller(SellerAddRequest request) throws CreateFailedException {
        try {
            createService.createUser(mapperService.map(request, UserAddRequest.class));
            sellerRepository.save(Seller.builder().sellerName(request.getSellerName()).sellerUsername(request.getUsername()).build());
            return new BaseResponse();
        } catch (Exception e) {
            log.error("Register seller error: " + e);
            throw new CreateFailedException(SELLER_CREATE_FAILED);
        }
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


}
