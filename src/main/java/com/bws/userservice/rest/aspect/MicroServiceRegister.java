package com.bws.userservice.rest.aspect;

import com.bws.userservice.api.client.MicroServiceRegisterClient;
import com.bws.userservice.api.request.MicroServiceReadyRequest;
import com.bws.userservice.api.request.MicroServiceStoppedRequest;
import com.bws.userservice.rest.service.CacheServiceImpl;
import com.bws.userservice.rest.util.Util;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@Component
@Slf4j
@RequiredArgsConstructor
public class MicroServiceRegister {

    private final MicroServiceRegisterClient microServiceRegisterClient;

    private static final String microServiceCode = "US857661488a18452ab5516a52573950e2";

    private static final String microServiceName = "USER-SERVICE";

    private final CacheServiceImpl cacheService;

    @EventListener(ApplicationReadyEvent.class)
    public void logToDataBaseServiceReady(){
//        MicroServiceReadyRequest microServiceReadyRequest = new MicroServiceReadyRequest();
//        microServiceReadyRequest.setServiceCode(microServiceCode);
//        microServiceReadyRequest.setServiceStatus("UP");
//        microServiceReadyRequest.setErrorCode("6000");
//        microServiceReadyRequest.setServiceReadyDate(Timestamp.from(Instant.now()));
//        microServiceReadyRequest.setServiceName(microServiceName);
//
        cacheService.getErrorCodes();
//
//        microServiceRegisterClient.microServiceReady(microServiceReadyRequest);
    }

    @PreDestroy
    public void testLogToDatabaseStopped(){
//        MicroServiceStoppedRequest microServiceStoppedRequest = new MicroServiceStoppedRequest();
//        microServiceStoppedRequest.setServiceStoppedDate(Timestamp.from(Instant.now()));
//        microServiceStoppedRequest.setServiceName(microServiceName);
//        microServiceStoppedRequest.setErrorCode("6000");
//        microServiceStoppedRequest.setServiceStatus("DOWN");
//        microServiceStoppedRequest.setServiceCode(microServiceCode);
//
//        microServiceRegisterClient.microServiceStopped(microServiceStoppedRequest);
    }
}