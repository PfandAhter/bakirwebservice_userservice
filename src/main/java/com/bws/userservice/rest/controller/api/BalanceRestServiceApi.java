package com.bws.userservice.rest.controller.api;

import com.bws.userservice.api.request.AddBalanceRequest;
import com.bws.userservice.api.request.BaseRequest;
import com.bws.userservice.api.response.AddBalanceResponse;
import com.bws.userservice.api.response.GetBalanceResponse;
import com.bws.userservice.model.constants.PropertyConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BalanceRestServiceApi {

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_BALANCE_SERVICE_BALANCE_ADD, produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AddBalanceResponse> addBalanceRequest (@Valid @RequestBody AddBalanceRequest addBalanceRequest, HttpServletRequest request, BindingResult bindingResult);

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_BALANCE_SERVICE_BALANCE_GET, produces = MediaType.APPLICATION_JSON_VALUE,  consumes =  MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GetBalanceResponse> getBalanceResponse (@Valid @RequestBody BaseRequest baseRequest , HttpServletRequest request, BindingResult bindingResult);

}
