package com.bws.userservice.rest.controller;

import com.bws.userservice.api.request.AddBalanceRequest;
import com.bws.userservice.api.request.BaseRequest;
import com.bws.userservice.api.response.AddBalanceResponse;
import com.bws.userservice.api.response.GetBalanceResponse;
import com.bws.userservice.model.constants.PropertyConstants;
import com.bws.userservice.rest.controller.api.BalanceRestServiceApi;
import com.bws.userservice.rest.service.BalanceServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = PropertyConstants.REQUEST_SERVICE_BALANCE_CONTROLLER)
@CrossOrigin
@RequiredArgsConstructor

public class BalanceServiceController implements BalanceRestServiceApi {

    private final BalanceServiceImpl balanceService;

    @Override
    public ResponseEntity<AddBalanceResponse> addBalanceRequest(AddBalanceRequest addBalanceRequest, HttpServletRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(balanceService.addBalance(addBalanceRequest));
    }

    // MAYBE GET METHOD WOULD BE GREAT
    @Override
    public ResponseEntity<GetBalanceResponse> getBalanceResponse(BaseRequest baseRequest, HttpServletRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(balanceService.getBalance(baseRequest));
    }
}
