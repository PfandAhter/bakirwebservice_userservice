package com.bws.userservice.rest.validator.annotations;

import com.bws.userservice.api.request.BaseRequest;
import com.bws.userservice.api.request.ChangePwByCodeRequest;
import com.bws.userservice.api.request.UserActivateRequest;
import com.bws.userservice.exception.AccessDeniedException;
import com.bws.userservice.exception.NotFoundException;
import com.bws.userservice.exception.NotMatchedException;

public interface GeneralValidator {

    void hasAuthority(BaseRequest baseRequest)  throws AccessDeniedException, NotFoundException ;

    void activateCodeMatch (UserActivateRequest userActivateRequest) throws NotMatchedException;

    void changePasswordByEmailCode(ChangePwByCodeRequest changePwByCodeRequest) throws NotMatchedException;
}
