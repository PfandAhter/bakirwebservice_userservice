package com.bws.userservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ChangePwByCodeRequest extends ValidateCodeRequest {

    private String email;
    private String emailCode;
    private String newPassword;
    private String newPasswordAgain;
}
