package com.bws.userservice.rest.aspect;

import com.bws.userservice.model.entity.ErrorCodes;
import com.bws.userservice.repository.ErrorCodeRepository;
import com.bws.userservice.rest.service.CacheServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class GeneralInterceptorAspect {

    private final ErrorCodeRepository errorCodeRepository;

    private final CacheServiceImpl cacheService;


    @Before(value = "execution(* com.bws.userservice.exception.GlobalExceptionHandler.*(..)) ")
    public void beforeException (JoinPoint joinPoint){
        Object [] parameters = joinPoint.getArgs();
        for(Object param : parameters){
            //if (instanceof Exception exception)
            if(param instanceof Exception){

            }
        }
    }
}