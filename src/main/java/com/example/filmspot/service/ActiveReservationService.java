package com.example.filmspot.service;

import com.example.filmspot.repository.BookingRepository;
import com.example.filmspot.repository.SeatReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ActiveReservationService {
    private final BookingRepository bookingRepository;
    private final SeatReservationRepository seatReservationRepository;
    private final WaitingUsersService waitingUsersService;

    private final Map<Long, LinkedHashMap<Long, LocalDateTime>> activeReservations = new ConcurrentHashMap<>();

    public void addReservation(Long showId, Long bookingId, LocalDateTime timestamp) {
        activeReservations.computeIfAbsent(showId, k -> new LinkedHashMap<>()).put(bookingId, timestamp);
    }

    public void removeReservation(Long bookingId) {
        activeReservations.forEach((showId, reservations) -> reservations.remove(bookingId));
    }

    @Scheduled(fixedRate = 60000) // Runs every minute
    @Transactional
    public void expireReservations() {
        LocalDateTime now = LocalDateTime.now();

        activeReservations.forEach((showId, reservations) -> {
            List<Long> expiredBookings = new ArrayList<>();

            reservations.forEach((bookingId, timestamp) -> {
                if (timestamp.plusMinutes(10).isBefore(now)) { // Reservation expired
                    bookingRepository.updateStatus(bookingId, 3); // Mark as Expired
                    seatReservationRepository.freeExpiredSeats(bookingId);
                    expiredBookings.add(bookingId);
                }
            });

            // Remove expired reservations from memory
            expiredBookings.forEach(reservations::remove);

            // Notify waiting users
            waitingUsersService.checkAndNotifyUsers(showId);
        });
    }
}
