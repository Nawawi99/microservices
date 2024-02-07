package dev.awn.apigateway.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class MissingAuthorizationHeaderException extends RuntimeException {

    public MissingAuthorizationHeaderException() {
        super();
    }

    public MissingAuthorizationHeaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingAuthorizationHeaderException(String message) {
        super(message);
    }

    public MissingAuthorizationHeaderException(Throwable cause) {
        super(cause);
    }
}
