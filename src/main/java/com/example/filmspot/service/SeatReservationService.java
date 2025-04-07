package com.example.filmspot.service;

import com.example.filmspot.Enum.BookingStatus;
import com.example.filmspot.dto.SeatReservationRequest;
import com.example.filmspot.model.*;
import com.example.filmspot.repository.BookingRepository;
import com.example.filmspot.repository.SeatReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SeatReservationService {
    private final SeatReservationRepository seatReservationRepository;
    private final BookingRepository bookingRepository;
    private final ActiveReservationService activeReservationService;
    private final WaitingUsersService waitingUsersService;

    @Transactional
    public ResponseEntity<?> reserveSeats(SeatReservationRequest request) {
        // Check if seats are available
        List<ShowSeat> availableSeats = seatReservationRepository.findAvailableSeats(request.getShowId(), request.getSeatIds());

        if (availableSeats.size() < request.getSeatIds().size()) {
            // Some or all seats are already reserved → Add user to waiting list
            waitingUsersService.addToWaitingList(request.getShowId(), request.getUserId(), request.getSeatIds());
            return ResponseEntity.ok("Seats unavailable. Added to waiting list.");
        }

        // Create a new booking
        Booking booking = new Booking();
        booking.setBookingId(booking.getBookingId());
        Show show = new Show();
        show.setId(request.getShowId());
        booking.setShow(show);
        User user = new User();
        user.setUserId(request.getUserId());
        booking.setUser(user);
        booking.setNumberOfSeats(request.getSeatIds().size());
        booking.setStatus(BookingStatus.RESERVED.getValue()); // Reserved!!!
        booking.setTimestamp(LocalDateTime.now());
        bookingRepository.save(booking);

        // Update seats as reserved
        seatReservationRepository.updateSeatsAsReserved(booking, request.getSeatIds());


        // Add reservation to ActiveReservationService
        activeReservationService.addReservation(request.getShowId(), booking.getBookingId(), LocalDateTime.now());

        double amount = calculateTotalAmount(availableSeats);

        return ResponseEntity.ok(Map.of("message", "Seats reserved successfully", "bookingId", booking.getBookingId(), "amount", amount));
    }

    @Transactional
    public void confirmPayment(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(2); // Mark as "Booked"
        bookingRepository.save(booking);

        // Remove from Active Reservations
        activeReservationService.removeReservation(bookingId);
    }

    public double calculateTotalAmount(List<ShowSeat> showSeats) {
        return showSeats
                .stream()
                .mapToDouble(ShowSeat::getPrice)  // Extract price from Seat entity
                .sum();
    }

}
