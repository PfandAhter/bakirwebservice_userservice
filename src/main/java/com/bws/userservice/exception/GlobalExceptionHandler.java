package com.bws.userservice.exception;

import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.model.entity.ErrorCodes;
import com.bws.userservice.rest.service.interfaces.ICacheService;
import feign.FeignException;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.AnnotationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.bws.userservice.model.constants.ResponseStatus.FAILED;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ICacheService cacheService;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleException(RuntimeException e) {
        log.error("Error: ",e);
        return ResponseEntity.internalServerError().body(createFailResponseBody(FAILED));
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<BaseResponse> handleException(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createFailResponseBody(e.getMessage()));
    }

    @ExceptionHandler(AnnotationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleException(AnnotationException e) {
        log.error("Error: ",e);
        return ResponseEntity.badRequest().body(createFailResponseBody(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ResponseEntity<BaseResponse> handleException(Exception e) {
        log.error("Error: ",e);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(createFailResponseBody(e.getMessage()));
    }


    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseResponse> handleException(FeignException e){
        log.error("Error: ",e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createFailResponseBody(e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse> handleException (AccessDeniedException e){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(createFailResponseBody(e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BaseResponse> handleException (UsernameNotFoundException e){
        return ResponseEntity.badRequest().body(createFailResponseBody("USERNAME NOT FOUND ! "));
    }

    private BaseResponse createFailResponseBody(String exceptionMessage){
        BaseResponse baseResponse = new BaseResponse();
        ErrorCodes errorCodes = cacheService.getErrorCodesList().get(exceptionMessage);
        baseResponse.setStatus(errorCodes.getId());
        baseResponse.setErrorCode(errorCodes.getError());
        baseResponse.setErrorDescription(errorCodes.getDescription());
        return baseResponse;
    }

}
