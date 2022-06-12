package io.reservation.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Restaurant reservation representation.
 *
 * @param id     unique identifier
 * @param name   reservation name
 * @param date   date for the reservation
 * @param time   time for the reservation
 * @param people number of people
 */
public record ReservationInfo(
        Long id,
        String name,
        LocalDate date,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm") LocalTime time,
        Integer people
) {
    public ReservationInfo(Long id, String name, LocalDate date, LocalTime time, Integer people) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time == null ? null : time.truncatedTo(ChronoUnit.MINUTES);
        this.people = people;
    }
}
