package io.reservation.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Represents a request for restaurant reservation creation.
 */
public record ReservationRequest(
        String name,
        LocalDate date,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime time,
        Integer people
) {
    public ReservationRequest(String name, LocalDate date, LocalTime time, Integer people) {
        this.name = name;
        this.date = date;
        this.time = time == null ? null : time.truncatedTo(ChronoUnit.MINUTES);
        this.people = people;
    }
}
