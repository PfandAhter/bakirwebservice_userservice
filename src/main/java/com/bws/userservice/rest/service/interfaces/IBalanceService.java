package com.bws.userservice.rest.service.interfaces;

import com.bws.userservice.api.request.AddBalanceRequest;
import com.bws.userservice.api.request.BaseRequest;
import com.bws.userservice.api.request.CreateBalanceRequest;
import com.bws.userservice.api.response.AddBalanceResponse;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.api.response.GetBalanceResponse;
import com.bws.userservice.model.entity.User;

public interface IBalanceService {

    AddBalanceResponse addBalance(AddBalanceRequest request);

    GetBalanceResponse getBalance(BaseRequest request);

    BaseResponse createBalance(CreateBalanceRequest request, User user);


}
