package com.bws.userservice.rest.controller;

import com.bws.userservice.api.request.BaseRequest;
import com.bws.userservice.api.request.SellerAddRequest;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.api.response.SellerGetResponse;
import com.bws.userservice.exception.AccessDeniedException;
import com.bws.userservice.exception.CreateFailedException;
import com.bws.userservice.exception.NotFoundException;
import com.bws.userservice.rest.controller.api.SellerRestServiceApi;
import com.bws.userservice.rest.service.interfaces.ISellerService;
import com.bws.userservice.rest.validator.annotations.GeneralValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user/seller") //TODO refactor...
@CrossOrigin
@RequiredArgsConstructor

public class SellerServiceController implements SellerRestServiceApi {

    private final ISellerService sellerService;

    private final GeneralValidator generalvalidator;

    @Override
    public ResponseEntity<SellerGetResponse> sellerGet(String sellers, BaseRequest baseRequest) throws AccessDeniedException, NotFoundException {
        generalvalidator.hasAuthority(baseRequest);
        return ResponseEntity.ok(sellerService.sellerGetResponse(sellers, baseRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> registerSeller(SellerAddRequest sellerAddRequest, HttpServletRequest request) throws CreateFailedException {
        return ResponseEntity.ok(sellerService.registerSeller(sellerAddRequest));
    }

    @Override
    public ResponseEntity<String> extractSellerName(BaseRequest baseRequest, HttpServletRequest request) throws NotFoundException {
        return ResponseEntity.ok(sellerService.extractSellerName(baseRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> activateSellerByAdmin(String sellerid, BaseRequest baseRequest) throws AccessDeniedException, NotFoundException {
        generalvalidator.hasAuthority(baseRequest);
        return ResponseEntity.ok(sellerService.activateSellerByAdmin(sellerid, baseRequest));
    }


}
