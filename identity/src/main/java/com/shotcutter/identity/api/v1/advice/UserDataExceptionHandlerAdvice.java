package com.shotcutter.identity.api.v1.advice;

import com.shotcutter.identity.user.InvalidUserDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class UserDataExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUserDataException.class)
    @ResponseBody
    Map<String, String> onUserDataInvalid(InvalidUserDataException exception) {
        return Collections.singletonMap("message", exception.getMessage());
    }

}
