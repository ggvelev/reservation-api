package io.reservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a request for reservation is invalid.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidReservationRequestException extends BaseException {

    private final String property;

    private final String value;

    public InvalidReservationRequestException(String property, String value) {
        super("Invalid reservation request: '%s' -> '%s'".formatted(property, value));
        this.property = property;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }
}
