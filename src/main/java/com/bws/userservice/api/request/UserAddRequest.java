package com.bws.userservice.api.request;

import com.bws.userservice.model.constants.ErrorCodeConstants;
import com.bws.userservice.rest.validator.annotations.UniqueEmail;
import com.bws.userservice.rest.validator.annotations.UniqueUsername;
import com.bws.userservice.rest.validator.annotations.ValidEmail;
import com.bws.userservice.rest.validator.annotations.ValidPassword;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddRequest {
    @UniqueUsername(message = ErrorCodeConstants.USERNAME_IN_USE)
    @NotEmpty(message = ErrorCodeConstants.USERNAME_NOT_NULL)
    private String username;

    @UniqueEmail(message = ErrorCodeConstants.EMAIL_IN_USE)
    @ValidEmail(message = ErrorCodeConstants.EMAIL_NOT_VALID)
    @NotEmpty(message = ErrorCodeConstants.EMAIL_NOT_NULL)
    private String email;

    @NotEmpty(message = ErrorCodeConstants.PASSWORD_NOT_NULL)
    @ValidPassword(message = ErrorCodeConstants.PASSWORD_NOT_VALID)
    private String password;

    private String firstName;

    private String lastName;
}
