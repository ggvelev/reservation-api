package io.reservation;

import io.reservation.entity.ReservationEntity;
import io.reservation.repository.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Random;

@SpringBootApplication
public class ReservationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(ReservationRepository repo) {
        Random rnd = new Random();
        LocalDateTime now = LocalDateTime.now();

        return args -> {
            repo.save(new ReservationEntity("For Ivan's colleagues", now.plusDays(5).toLocalDate(), now.plusHours(1).toLocalTime(), rnd.nextInt(30)));
            repo.save(new ReservationEntity("For Dragan's colleagues", now.plusDays(3).toLocalDate(), now.plusHours(14).toLocalTime(), rnd.nextInt(30)));
            repo.save(new ReservationEntity("For Robert's colleagues", now.plusDays(15).toLocalDate(), now.plusHours(12).toLocalTime(), rnd.nextInt(30)));
            repo.save(new ReservationEntity("For Tzvetelina's colleagues", now.plusDays(44).toLocalDate(), now.plusHours(18).toLocalTime(), rnd.nextInt(30)));
            repo.save(new ReservationEntity("For Hristo's colleagues", now.plusDays(2).toLocalDate(), now.plusHours(1).toLocalTime(), rnd.nextInt(30)));
            repo.save(new ReservationEntity("For Georgi's colleagues", now.plusDays(1).toLocalDate(), now.plusHours(5).toLocalTime(), rnd.nextInt(30)));
        };
    }


}
