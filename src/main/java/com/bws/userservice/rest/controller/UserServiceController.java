package com.bws.userservice.rest.controller;

import com.bws.userservice.api.request.*;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.api.response.SellerGetResponse;
import com.bws.userservice.exception.AccessDeniedException;
import com.bws.userservice.exception.ActivateCodeNotMatchedException;
import com.bws.userservice.model.constants.PropertyConstants;
import com.bws.userservice.model.entity.User;
import com.bws.userservice.rest.controller.api.UserRestServiceApi;
import com.bws.userservice.rest.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(path = PropertyConstants.REQUEST_SERVICE_USER_CONTROLLER)
@CrossOrigin
@RequiredArgsConstructor

public class UserServiceController implements UserRestServiceApi {

    private final UserServiceImpl userService;

    @Override
    public ResponseEntity<BaseResponse> registerUser(UserAddRequest userAddRequest, HttpServletRequest request,BindingResult bindingResult)  {
        return ResponseEntity.ok(userService.createUser(userAddRequest));
    }

    @Override
    public ResponseEntity<User> findUserByUsername(FindUserByUsernameRequest findUserByUsernameRequest, HttpServletRequest request, BindingResult bindingResult)  {
        return ResponseEntity.ok(userService.findUserByUsername(findUserByUsernameRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> addAddressInfo(AddAddressInfoRequest addAddressInfoRequest, HttpServletRequest request) {
        return ResponseEntity.ok(userService.addAddressInfo(addAddressInfoRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> changePassword(PasswordChangeRequest passwordChangeRequest, HttpServletRequest request) {
        return ResponseEntity.ok(userService.changePassword(passwordChangeRequest));
    }

    @Override
    public ResponseEntity<String> extractRole(BaseRequest baseRequest, HttpServletRequest request) {
        return ResponseEntity.ok(userService.extractRole(baseRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> validateUserEmail(UserActivateRequest userActivateRequest, HttpServletRequest request)throws ActivateCodeNotMatchedException {
        return ResponseEntity.ok(userService.activateUser(userActivateRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> registerSeller(SellerAddRequest sellerAddRequest, HttpServletRequest request) {
        return ResponseEntity.ok(userService.registerSeller(sellerAddRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> activateSellerByAdmin(String sellerid, BaseRequest baseRequest) throws AccessDeniedException {
        return ResponseEntity.ok(userService.activateSellerByAdmin(sellerid,baseRequest));
    }

    @Override
    public ResponseEntity<SellerGetResponse> sellerGet(String sellers, BaseRequest baseRequest) throws AccessDeniedException {
        return ResponseEntity.ok(userService.sellerGetResponse(sellers,baseRequest));
    }

    @Override
    public ResponseEntity<String> extractSellerName(BaseRequest baseRequest, HttpServletRequest request) {
        return ResponseEntity.ok(userService.extractSellerName(baseRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> forgetPassword(ForgetPasswordRequest forgetPasswordRequest, HttpServletRequest request) {
        return ResponseEntity.ok(userService.forgetPassword(forgetPasswordRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> changePwByCode(ChangePwByCodeRequest changePwByCodeRequest, HttpServletRequest request) throws ActivateCodeNotMatchedException {
        return ResponseEntity.ok(userService.changePwByCode(changePwByCodeRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> addPhoto(String username, MultipartFile image, HttpServletRequest request) throws IOException {
        return ResponseEntity.ok(userService.addPhoto(username,image));
    }

    @Override
    public ResponseEntity<byte[]> getPhoto(BaseRequest baseRequest, HttpServletRequest request) throws IOException {
        byte[] image = userService.getPhoto(baseRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);

//        return ResponseEntity.ok(image);
        return new ResponseEntity<>(image,httpHeaders, HttpStatus.OK);
    }


}

