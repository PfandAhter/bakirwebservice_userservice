package com.bws.userservice.rest.controller;

import com.bws.userservice.api.request.FindUserByUsernameRequest;
import com.bws.userservice.api.request.UserAddRequest;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.exception.AuthException;
import com.bws.userservice.exception.UserNotFoundException;
import com.bws.userservice.model.constants.PropertyConstants;
import com.bws.userservice.model.entity.User;
import com.bws.userservice.rest.controller.api.UserRestServiceApi;
import com.bws.userservice.rest.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = PropertyConstants.REQUEST_USERSERVICE)
@CrossOrigin
@RequiredArgsConstructor
public class UserServiceController implements UserRestServiceApi {

    private final UserServiceImpl userService;

    @Override
    public ResponseEntity<BaseResponse> registerUser(UserAddRequest userAddRequest, HttpServletRequest request, BindingResult bindingResult) throws AuthException {
        return ResponseEntity.ok(userService.createUser(userAddRequest));
    }

    @Override
    public ResponseEntity<User> findUserByUsername(FindUserByUsernameRequest findUserByUsernameRequest, HttpServletRequest request, BindingResult bindingResult) throws UserNotFoundException {
        return ResponseEntity.ok(userService.findUserByUsername(findUserByUsernameRequest));
    }

}

