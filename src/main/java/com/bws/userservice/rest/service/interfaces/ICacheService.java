package com.bws.userservice.rest.service.interfaces;

import com.bws.userservice.model.entity.ErrorCodes;

import java.util.HashMap;

public interface ICacheService {

    HashMap<String,ErrorCodes> getErrorCodesList();
}
