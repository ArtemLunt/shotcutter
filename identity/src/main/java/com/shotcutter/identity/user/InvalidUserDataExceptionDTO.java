package com.shotcutter.identity.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class InvalidUserDataExceptionDTO {

    private final String message;

    public static InvalidUserDataExceptionDTO fromException(InvalidUserDataException exception) {
        return new InvalidUserDataExceptionDTO(exception.getMessage());
    }

}
