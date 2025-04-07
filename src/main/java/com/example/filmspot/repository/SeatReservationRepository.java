package com.example.filmspot.repository;

import com.example.filmspot.model.Booking;
import com.example.filmspot.model.ShowSeat;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatReservationRepository extends JpaRepository<ShowSeat, Long> {
    @Query("SELECT s FROM ShowSeat s WHERE s.show.id = :showId AND (COALESCE(:seatIds, NULL) IS NULL OR s.id IN :seatIds) AND s.status = 0")
    List<ShowSeat> findAvailableSeats(@Param("showId") Long showId, @Param("seatIds") List<Long> seatIds);

    @Modifying
    @Query("UPDATE ShowSeat s SET s.status = 1, s.booking = :booking WHERE s.id IN :seatIds")
    void updateSeatsAsReserved(@Param("booking") Booking booking, @Param("seatIds") List<Long> seatIds);

    @Modifying
    @Query("UPDATE ShowSeat s SET s.status = 0, s.booking = NULL WHERE s.booking.bookingId = :bookingId")
    void freeExpiredSeats(@Param("bookingId") Long bookingId);


}
