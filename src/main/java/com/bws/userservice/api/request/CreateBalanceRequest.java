package com.bws.userservice.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class CreateBalanceRequest{
    private String username;
    private String userId;
}

