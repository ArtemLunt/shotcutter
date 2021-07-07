package com.shotcutter.identity.api.v1.advice;

import com.shotcutter.identity.user.InvalidUserDataException;
import com.shotcutter.identity.user.InvalidUserDataExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserDataExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUserDataException.class)
    @ResponseBody
    InvalidUserDataExceptionDTO onUserDataInvalid(InvalidUserDataException exception) {
        return InvalidUserDataExceptionDTO.fromException(exception);
    }

}
