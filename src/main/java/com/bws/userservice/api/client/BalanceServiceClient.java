package com.bws.userservice.api.client;

import com.bws.userservice.api.request.BaseRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(value = "BalanceService" , url = "${client.feign.token-service.path}")
public interface BalanceServiceClient {

}
