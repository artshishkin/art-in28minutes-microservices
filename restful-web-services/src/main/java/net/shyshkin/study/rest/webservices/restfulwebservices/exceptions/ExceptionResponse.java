package net.shyshkin.study.rest.webservices.restfulwebservices.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ExceptionResponse {

    private final LocalDateTime timestamp;
    private final String message;
    private final String details;

}
