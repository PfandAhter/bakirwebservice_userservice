package com.bws.userservice.rest.controller;

import com.bws.userservice.api.request.*;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.exception.*;
import com.bws.userservice.model.constants.PropertyConstants;
import com.bws.userservice.model.entity.User;
import com.bws.userservice.rest.controller.api.UserRestServiceApi;
import com.bws.userservice.rest.service.UserServiceImpl;
import com.bws.userservice.rest.validator.annotations.GeneralValidator;
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

    private final GeneralValidator generalvalidator;

    @Override
    public ResponseEntity<BaseResponse> registerUser(UserAddRequest userAddRequest, HttpServletRequest request,BindingResult bindingResult) throws CreateFailedException {
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
    public ResponseEntity<BaseResponse> changePassword(PasswordChangeRequest passwordChangeRequest, HttpServletRequest request) throws NotFoundException {
        return ResponseEntity.ok(userService.changePassword(passwordChangeRequest));
    }

    @Override
    public ResponseEntity<String> extractRole(BaseRequest baseRequest, HttpServletRequest request) throws NotFoundException {
        return ResponseEntity.ok(userService.extractRole(baseRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> activateUser(UserActivateRequest userActivateRequest, HttpServletRequest request) throws NotMatchedException, NotFoundException {
        generalvalidator.activateCodeMatch(userActivateRequest);
        return ResponseEntity.ok(userService.activateUser(userActivateRequest));
    }


    @Override
    public ResponseEntity<BaseResponse> forgetPassword(ForgetPasswordRequest forgetPasswordRequest, HttpServletRequest request) throws NotFoundException {
        return ResponseEntity.ok(userService.forgetPassword(forgetPasswordRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> changePasswordByCode(ChangePwByCodeRequest changePwByCodeRequest, HttpServletRequest request) throws NotFoundException, NotMatchedException{
        generalvalidator.changePasswordByEmailCode(changePwByCodeRequest);
        return ResponseEntity.ok(userService.changePasswordByCode(changePwByCodeRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> addPhoto(String username, MultipartFile image, HttpServletRequest request) throws IOException {
        return ResponseEntity.ok(userService.addPhoto(username,image));
    }

    @Override
    public ResponseEntity<byte[]> getPhoto(BaseRequest baseRequest, HttpServletRequest request) {
        byte[] image = userService.getPhoto(baseRequest);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);

//        return ResponseEntity.ok(image);
        return new ResponseEntity<>(image,httpHeaders, HttpStatus.OK);
    }


}

