package com.danielpm1982.springboot3_profile_mng_no_comments.exception;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.ObjectError;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.util.logging.Level;

@ControllerAdvice
@Log
public class GlobalExceptionHandler {
    @ExceptionHandler(PersonDeleteByIdFailedException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    ErrorResponse handlePersonDeleteByIdFailedException(PersonDeleteByIdFailedException e){
        log.log(Level.WARNING, e::toString);
        return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), HttpStatus.SERVICE_UNAVAILABLE.name(), e.getMessage());
    }

    @ExceptionHandler(PersonSaveOrUpdateByIdFailedException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    ErrorResponse handlePersonSaveOrUpdateByIdFailedException(PersonSaveOrUpdateByIdFailedException e){
        log.log(Level.WARNING, e::toString);
        return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), HttpStatus.SERVICE_UNAVAILABLE.name(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.log(Level.WARNING, e::toString);
        String errorDetailMessage = e.getAllErrors().stream().map(ObjectError::getDefaultMessage).reduce((a,b)->a+". "+b).orElseGet(()->"Unknown errors")+".";
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
                "Bad Request: "+e.getClass().getSimpleName()+" ! Errors found at the content of your request body. "+errorDetailMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.log(Level.WARNING, e::toString);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
                "Bad Request: "+e.getClass().getSimpleName()+" ! "+e.getMessage()+". "+
                        "Request Body content incompatible with field 'accept' at Http request header, " +
                        "Media (MIME) Type. Cannot unmarshall request body content.");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleGeneralException(Exception e) {
        log.log(Level.WARNING, e::toString);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(),
                "Internal Server Error: "+e.getClass().getSimpleName()+" !");
    }
}
