package com.bws.userservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FindUserByUsernameRequest extends BaseRequest{
    private String username;
}
