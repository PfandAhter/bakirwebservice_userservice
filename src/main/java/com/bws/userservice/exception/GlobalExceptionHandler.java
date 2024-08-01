package com.bws.userservice.exception;

import com.bws.userservice.api.response.BaseResponse;
import feign.FeignException;
import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.bws.userservice.model.constants.ResponseStatus.FAILED;

@RestControllerAdvice
@Slf4j

public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleException(RuntimeException e) {
        log.error("Error: ",e);
        return ResponseEntity.internalServerError().body(createFailResponse(FAILED));

    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<BaseResponse> handleException(AuthException e) {
        return ResponseEntity.badRequest().body(createFailResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(Exception e) {
        return ResponseEntity.badRequest().body(createFailResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleException(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body(createFailResponse("CHECK USERNAME & CHECK EMAIL & CHECK PASSWORD IS VALID"));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<BaseResponse> handleException(FeignException e){
        return ResponseEntity.badRequest().body(createFailResponse("ACCESS DENIED"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse> handleException (AccessDeniedException e){
        return ResponseEntity.badRequest().body(createFailResponse("ACCESS DENIED"));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BaseResponse> handleException (UsernameNotFoundException e){
        return ResponseEntity.badRequest().body(createFailResponse("USERNAME NOT FOUND ! "));
    }


    private BaseResponse createFailResponse(String message) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setErrorCode(message);
        baseResponse.setErrorDescription(message);
        return baseResponse;
    }
}
