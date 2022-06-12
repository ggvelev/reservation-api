package io.reservation.service.api;

import io.reservation.entity.ReservationEntity;
import io.reservation.web.model.UpdateReservationRequest;
import io.reservation.web.model.ReservationInfo;
import io.reservation.web.model.ReservationQuery;
import io.reservation.web.model.ReservationRequest;

import java.util.List;

public interface ReservationService {
    /**
     * Creates and persists a {@link ReservationEntity} based on incoming {@link ReservationRequest}
     *
     * @param request request for making a restaurant reservation
     * @return the persisted reservation info
     */
    ReservationInfo createReservation(ReservationRequest request);

    /**
     * Looks for reservations based on a {@link ReservationQuery}
     * trying to match all reservations based on the provided property values.
     *
     * @param query {@link ReservationQuery}
     * @return all matching {@link ReservationInfo}s
     */
    List<ReservationInfo> getReservations(ReservationQuery query);

    /**
     * Retrieves a single reservation by ID.
     *
     * @param reservationId reservation identifier
     * @return reservation found
     */
    ReservationInfo getById(Long reservationId);

    /**
     * Deletes a reservation by ID.
     *
     * @param reservationId reservation identifier
     */
    void deleteById(Long reservationId);

    /**
     * Updates a reservation entity.
     *
     * @param reservationId existing reservation's ID
     * @param editRequest   {@link UpdateReservationRequest}
     * @return updated reservation info
     */
    ReservationInfo updateReservation(Long reservationId, UpdateReservationRequest editRequest);
}
