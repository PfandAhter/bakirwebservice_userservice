package com.bws.userservice.exception;

import com.bws.userservice.api.response.BaseResponse;
import com.bws.userservice.model.dto.ErrorCodesDTO;
import com.bws.userservice.model.entity.ErrorCodes;
import com.bws.userservice.rest.service.interfaces.ICacheService;
import com.bws.userservice.rest.service.interfaces.MapperService;
import feign.FeignException;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.AnnotationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private final MapperService mapperService;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleException(RuntimeException e) {
        log.error("Error: ",e);
        return ResponseEntity.internalServerError().body(createFailResponse(FAILED));
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<BaseResponse> handleException(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createFailResponse(e.getMessage()));
    }

    @ExceptionHandler(AnnotationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleException(AnnotationException e) {
        log.error("Error: ",e);
        return ResponseEntity.badRequest().body(createFailResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ResponseEntity<BaseResponse> handleException(Exception e) {
        log.error("Error: ",e);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(createFailResponse(e.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseResponse> handleException(FeignException e){
        log.error("Error: ",e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createFailResponse(e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse> handleException (AccessDeniedException e){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(createFailResponse(e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BaseResponse> handleException (UsernameNotFoundException e){
        return ResponseEntity.badRequest().body(createFailResponse("USERNAME NOT FOUND ! "));
    }

    private BaseResponse createFailResponse(String exceptionMessage){
        ErrorCodesDTO errorCodesDTO = findErrorCode(exceptionMessage);
        return new BaseResponse(errorCodesDTO.getId(),errorCodesDTO.getError(),errorCodesDTO.getDescription());
    }

    private ErrorCodesDTO findErrorCode(String errorKey) {
        ErrorCodes errorCodes = cacheService.getErrorCodesList().get(errorKey);
        if (errorCodes == null) {
            errorCodes = new ErrorCodes();
            errorCodes.setId(FAILED);
            errorCodes.setError(errorKey);
            errorCodes.setDescription(errorKey);
        }
        return mapperService.map(errorCodes, ErrorCodesDTO.class);
    }

}
