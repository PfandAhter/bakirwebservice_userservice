package com.bws.userservice.rest.service.interfaces;


import com.bws.userservice.api.request.AddAddressInfoRequest;
import com.bws.userservice.api.request.FindUserByUsernameRequest;
import com.bws.userservice.api.request.UserAddRequest;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.model.entity.User;

public interface IUserService {

    User findUserByUsername(FindUserByUsernameRequest request);

    BaseResponse createUser(UserAddRequest request);

    BaseResponse addAddressInfo(AddAddressInfoRequest request);
}
