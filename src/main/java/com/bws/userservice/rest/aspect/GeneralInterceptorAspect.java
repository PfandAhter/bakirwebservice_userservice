package com.bws.userservice.rest.aspect;

import com.bws.userservice.model.entity.ErrorCodes;
import com.bws.userservice.repository.ErrorCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class GeneralInterceptorAspect {

    private final ErrorCodeRepository errorCodeRepository;

    @Before(value = "execution(* com.bws.userservice.exception.GlobalExceptionHandler.*(..)) ")
    public void beforeException (JoinPoint joinPoint){
        Object [] parameters = joinPoint.getArgs();
        for(Object param : parameters){
            //if (instanceof Exception exception)
            if(param instanceof Exception){
                if(errorCodeRepository.findErrorByError(param.getClass().getName()) == null){
                    ErrorCodes errorCodes = new ErrorCodes();
                    errorCodes.setError(param.getClass().getName());
                    errorCodes.setError_description(((Exception) param).getMessage());
                    errorCodeRepository.save(errorCodes);
                    Long localCode = 6000L;
                    errorCodes.setError_code(errorCodes.getId()+localCode);
                    errorCodeRepository.save(errorCodes);
                }
            }
        }
    }
}