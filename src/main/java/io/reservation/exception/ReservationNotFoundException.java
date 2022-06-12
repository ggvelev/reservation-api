package io.reservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a reservation is not found by given property and value.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReservationNotFoundException extends BaseException {

    private final String property;
    private final String value;

    public ReservationNotFoundException(String property, String value) {
        super("Reservation with '%s' -> '%s' was not found".formatted(property, value));
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
