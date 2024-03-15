package com.bws.userservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBalanceRequest{
    private String username;
    private Long user_id;
}

