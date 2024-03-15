package com.bws.userservice.rest.controller.api;

import com.bws.userservice.api.request.FindUserByUsernameRequest;
import com.bws.userservice.api.request.UserAddRequest;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.exception.AuthException;
import com.bws.userservice.exception.UserNotFoundException;
import com.bws.userservice.model.constants.PropertyConstants;
import com.bws.userservice.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserRestServiceApi {

    @PostMapping(path = PropertyConstants.REQUEST_LOCKED_USER_REGISTER, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> registerUser (@Valid @RequestBody UserAddRequest userAddRequest, HttpServletRequest request, BindingResult bindingResult) throws AuthException;

    @PostMapping(path = PropertyConstants.REQUEST_LOCKED_USER_FINDUSERBYUSERNAME, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> findUserByUsername (@Valid @RequestBody FindUserByUsernameRequest findUserByUsernameRequest, HttpServletRequest request, BindingResult bindingResult) throws UserNotFoundException;

}
