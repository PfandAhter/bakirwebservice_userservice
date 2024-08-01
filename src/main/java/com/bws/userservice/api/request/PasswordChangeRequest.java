package com.bws.userservice.api.request;

import com.bws.userservice.model.constants.ErrorCodeConstants;
import com.bws.userservice.rest.validator.annotations.ValidPassword;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PasswordChangeRequest extends ValidateCodeRequest{

    private String oldPassword;

    @NotEmpty(message = ErrorCodeConstants.PASSWORD_NOT_NULL)
    @ValidPassword(message = ErrorCodeConstants.PASSWORD_NOT_VALID)
    private String newPassword;

    @NotEmpty(message = ErrorCodeConstants.PASSWORD_NOT_NULL)
    @ValidPassword(message = ErrorCodeConstants.PASSWORD_NOT_VALID)
    private String newPasswordAgain;

}
