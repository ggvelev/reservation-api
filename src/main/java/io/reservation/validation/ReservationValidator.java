package io.reservation.validation;

import io.reservation.exception.InvalidReservationRequestException;
import io.reservation.web.model.UpdateReservationRequest;
import io.reservation.web.model.ReservationInfo;
import io.reservation.web.model.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Component that helps to validate reservation related requests.
 *
 * @author Georgi Velev
 */
@Component
public class ReservationValidator {

    private static final Logger log = LoggerFactory.getLogger(ReservationValidator.class);

    /**
     * Asserts that a reservation request is valid and has valid properties.
     *
     * @param request request to validate
     */
    public void validateReservationRequest(ReservationRequest request) {
        // Creating reservation in the past is not allowed:
        if (request.date() == null || request.date().isBefore(LocalDate.now())) {
            throw new InvalidReservationRequestException("date", String.valueOf(request.date()));
        }

        if (request.name() == null || request.name().isBlank()) {
            throw new InvalidReservationRequestException("name", request.name());
        }

        if (request.people() == null || request.people() <= 0) {
            throw new InvalidReservationRequestException("people", String.valueOf(request.people()));
        }
    }

    /**
     * Validates whether the reservation update request is valid and
     * can be proceeded to update the existing reservation info.
     *
     * @param editRequest data to update the existing reservation info
     * @param edited      the existing reservation to update
     */
    public void validateUpdateRequest(UpdateReservationRequest editRequest, ReservationInfo edited) {
        // TODO: to be implemented later...
    }

}
