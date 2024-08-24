package com.bws.userservice.rest.validator;

import com.bws.userservice.api.client.EmailServiceClient;
import com.bws.userservice.api.request.BaseRequest;
import com.bws.userservice.api.request.ChangePwByCodeRequest;
import com.bws.userservice.api.request.UserActivateRequest;
import com.bws.userservice.exception.AccessDeniedException;
import com.bws.userservice.exception.NotFoundException;
import com.bws.userservice.exception.NotMatchedException;
import com.bws.userservice.model.Role;
import com.bws.userservice.rest.service.interfaces.IUserService;
import com.bws.userservice.rest.validator.annotations.GeneralValidator;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bws.userservice.model.constants.ErrorCodeConstants.ACTIVATE_CODE_NOT_MATCHED;
import static com.bws.userservice.model.constants.ErrorCodeConstants.NO_AUTHORITY;

@Service
@RequiredArgsConstructor
@Slf4j

public class GeneralValidatorImpl implements GeneralValidator {

    private final IUserService userService;

    private final EmailServiceClient emailServiceClient;

    public void hasAuthority(BaseRequest baseRequest) throws AccessDeniedException, NotFoundException {
        if (!userService.extractRole(baseRequest).equals(Role.ADMIN.toString())) {
            log.error("User has no authority");
            throw new AccessDeniedException(NO_AUTHORITY);
        }
    }

    public void activateCodeMatch(UserActivateRequest userActivateRequest) throws NotMatchedException {
        if (emailServiceClient.activateCodeMatched(userActivateRequest).equals(Boolean.FALSE)) {
            log.error("Activate code not matched");
            throw new NotMatchedException(ACTIVATE_CODE_NOT_MATCHED);
        }
    }

    public void changePasswordByEmailCode(ChangePwByCodeRequest changePwByCodeRequest) throws NotMatchedException {
        if (emailServiceClient.passwordChangeCodeMatched(changePwByCodeRequest).equals(Boolean.FALSE) && !(changePwByCodeRequest.getNewPassword().equals(changePwByCodeRequest.getNewPasswordAgain()))) {
            log.error("Password change code not matched");
            throw new NotMatchedException(ACTIVATE_CODE_NOT_MATCHED);
        }
    }


}
