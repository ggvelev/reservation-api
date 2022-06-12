package io.reservation.web.controller;

import io.reservation.service.api.ReservationService;
import io.reservation.web.model.UpdateReservationRequest;
import io.reservation.web.model.ReservationInfo;
import io.reservation.web.model.ReservationQuery;
import io.reservation.web.model.ReservationRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static io.reservation.web.ApiConst.RESERVATIONS;
import static io.reservation.web.ApiConst.RESERVATION_BY_ID;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Defines REST APIs request handlers for managing {@link ReservationInfo}s.
 *
 * @author Georgi Velev
 */
@RestController
public class ReservationController {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private ReservationService reservationService;

    @Operation(summary = "Query for reservations.",
            description = "Retrieves all persisted reservations. Optionally search for reservations matching by name, date and time.")
    @RequestMapping(method = GET, path = RESERVATIONS)
    public ResponseEntity<List<ReservationInfo>> getReservations(@RequestParam Optional<String> name,
                                                                 @RequestParam Optional<LocalDate> date,
                                                                 @RequestParam Optional<LocalTime> time) {
        ReservationQuery query = new ReservationQuery(
                name.orElse(null), date.orElse(null), time.orElse(null));

        List<ReservationInfo> reservations = reservationService.getReservations(query);

        return ResponseEntity.ok(reservations);
    }

    @Operation(summary = "Request new reservation to be created.",
            description = "The newly created resource URI location will be returned in 'Location' header.")
    @RequestMapping(method = POST, path = RESERVATIONS)
    public ResponseEntity<ReservationInfo> createReservation(@NotNull @RequestBody ReservationRequest request) {
        ReservationInfo reservation = reservationService.createReservation(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{reservation-id}")
                .buildAndExpand(reservation.id())
                .toUri();

        return ResponseEntity.created(location).body(reservation);
    }

    @Operation(summary = "Retrieve existing reservation details by reservation ID.")
    @RequestMapping(method = GET, path = RESERVATION_BY_ID)
    public ResponseEntity<ReservationInfo> getReservationById(@PathVariable Long reservationId) {
        ReservationInfo reservation = reservationService.getById(reservationId);
        return ResponseEntity.ok(reservation);
    }

    @Operation(summary = "Update existing reservation details.",
            description = "Only non-null values will be taken into account when updating the reservation model")
    @RequestMapping(method = PUT, path = RESERVATION_BY_ID)
    public ResponseEntity<ReservationInfo> updateReservation(@PathVariable Long reservationId,
                                                             @RequestBody UpdateReservationRequest request) {
        ReservationInfo edited = reservationService.updateReservation(reservationId, request);
        return ResponseEntity.ok(edited);
    }

    @Operation(summary = "Deletes a reservation by ID.")
    @RequestMapping(method = DELETE, path = RESERVATION_BY_ID)
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteById(reservationId);
        return ResponseEntity.noContent().build();
    }
}
