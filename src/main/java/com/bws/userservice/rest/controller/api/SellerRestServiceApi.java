package com.bws.userservice.rest.controller.api;

import com.bws.userservice.api.request.BaseRequest;
import com.bws.userservice.api.request.SellerAddRequest;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.api.response.SellerGetResponse;
import com.bws.userservice.exception.AccessDeniedException;
import com.bws.userservice.exception.CreateFailedException;
import com.bws.userservice.exception.NotFoundException;
import com.bws.userservice.model.constants.PropertyConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface SellerRestServiceApi {

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_GET_SELLERS)
    ResponseEntity<SellerGetResponse> sellerGet (@RequestParam("sellers") String sellers , @RequestBody BaseRequest baseRequest)throws AccessDeniedException, NotFoundException;

    @PostMapping(path = PropertyConstants.REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_SELLER_REGISTER, produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> registerSeller (@Valid @RequestBody SellerAddRequest sellerAddRequest , HttpServletRequest request) throws CreateFailedException;

    @PostMapping(path = PropertyConstants.REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_EXTRACT_SELLER_NAME , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> extractSellerName (@Valid @RequestBody BaseRequest baseRequest , HttpServletRequest request) throws NotFoundException;

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_SELLER_ACTIVATE_BY_ADMIN)
    ResponseEntity<BaseResponse> activateSellerByAdmin (@RequestParam("sellerid") String sellerid , @RequestBody BaseRequest baseRequest) throws AccessDeniedException, NotFoundException;

}
