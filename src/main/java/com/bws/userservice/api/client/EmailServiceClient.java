package com.bws.userservice.api.client;

import com.bws.userservice.api.request.ChangePwByCodeRequest;
import com.bws.userservice.api.request.EmailValidatorRequest;
import com.bws.userservice.api.request.ForgetPasswordRequest;
import com.bws.userservice.api.request.UserActivateRequest;
import com.bws.userservice.api.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "EmailService" , url = "${client.feign.email-service.path}")
public interface EmailServiceClient {

    @PostMapping("${client.feign.email-service.emailValidator}")
    BaseResponse validateEmail (@RequestBody EmailValidatorRequest emailValidatorRequest);

    @PostMapping("${client.feign.email-service.activateUser}")
    Boolean activateCodeMatched(@RequestBody UserActivateRequest userActivateRequest);

    @PostMapping("${client.feign.email-service.passwordChangeCode}")
    Boolean passwordChangeCodeMatched(@RequestBody ChangePwByCodeRequest changePwByCodeRequest);

    @PostMapping("${client.feign.email-service.passwordForget}")
    BaseResponse forgetPasswordCreateCode (@RequestBody ForgetPasswordRequest forgetPasswordRequest);
}
