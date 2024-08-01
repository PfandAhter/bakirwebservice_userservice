package com.bws.userservice.rest.controller.api;

import com.bws.userservice.api.request.*;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.api.response.SellerGetResponse;
import com.bws.userservice.exception.AccessDeniedException;
import com.bws.userservice.exception.ActivateCodeNotMatchedException;
import com.bws.userservice.model.constants.PropertyConstants;
import com.bws.userservice.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserRestServiceApi {

    @PostMapping(path = PropertyConstants.REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_USER_REGISTER, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> registerUser (@Valid @RequestBody UserAddRequest userAddRequest, HttpServletRequest request,BindingResult bindingResult) ;

    @PostMapping(path = PropertyConstants.REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_FIND_BY_USER_NAME, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> findUserByUsername (@Valid @RequestBody FindUserByUsernameRequest findUserByUsernameRequest, HttpServletRequest request , BindingResult bindingResult);

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_ADD_USER_ADDRESS_INFO , produces = MediaType.APPLICATION_JSON_VALUE ,consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> addAddressInfo (@Valid @RequestBody AddAddressInfoRequest addAddressInfoRequest, HttpServletRequest request);

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_CHANGE_PASSWORD , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> changePassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest , HttpServletRequest request);

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_EXTRACT_ROLE , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> extractRole (@Valid @RequestBody BaseRequest baseRequest , HttpServletRequest request);

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_USER_VALIDATE_EMAIL , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> validateUserEmail (@Valid @RequestBody UserActivateRequest userActivateRequest, HttpServletRequest request)throws ActivateCodeNotMatchedException;

    @PostMapping(path = PropertyConstants.REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_SELLER_REGISTER, produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> registerSeller (@Valid @RequestBody SellerAddRequest sellerAddRequest , HttpServletRequest request);

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_SELLER_ACTIVATE_BY_ADMIN)
    ResponseEntity<BaseResponse> activateSellerByAdmin (@RequestParam("sellerid") String sellerid , @RequestBody BaseRequest baseRequest) throws AccessDeniedException;

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_GET_SELLERS)
    ResponseEntity<SellerGetResponse> sellerGet (@RequestParam("sellers") String sellers , @RequestBody BaseRequest baseRequest)throws AccessDeniedException;

    @PostMapping(path = PropertyConstants.REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_EXTRACT_SELLER_NAME , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> extractSellerName (@Valid @RequestBody BaseRequest baseRequest , HttpServletRequest request);

    @PostMapping(path = PropertyConstants.REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_FORGET_PASSWORD , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> forgetPassword (@Valid @RequestBody ForgetPasswordRequest forgetPasswordRequest, HttpServletRequest request);

    @PostMapping(path = PropertyConstants.REQUEST_NOT_SECURE_REST_CONTROLLER_USER_SERVICE_CHANGE_PW_BY_CODE , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> changePwByCode (@Valid @RequestBody ChangePwByCodeRequest changePwByCodeRequest ,HttpServletRequest request) throws ActivateCodeNotMatchedException;

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_USER_PHOTO_ADD , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BaseResponse> addPhoto (@RequestPart("user") String username, @RequestPart("image") MultipartFile image, HttpServletRequest request) throws IOException;

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_USER_SERVICE_USER_PHOTO_GET)
    ResponseEntity<byte[]> getPhoto (@RequestBody BaseRequest baseRequest ,HttpServletRequest request) throws IOException;
}
