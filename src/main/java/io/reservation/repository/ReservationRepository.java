package io.reservation.repository;

import io.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A {@link JpaRepository} providing CRUD operations on {@link ReservationEntity}.
 */
@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    // pass
}
