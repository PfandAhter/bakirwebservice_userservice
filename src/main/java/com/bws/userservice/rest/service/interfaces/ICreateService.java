package com.bws.userservice.rest.service.interfaces;

import com.bws.userservice.api.request.UserAddRequest;
import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.exception.CreateFailedException;

public interface ICreateService {

    BaseResponse createUser(UserAddRequest request) throws CreateFailedException;


}
