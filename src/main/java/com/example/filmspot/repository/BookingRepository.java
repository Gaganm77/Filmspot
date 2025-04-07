package com.example.filmspot.repository;

import com.example.filmspot.model.Booking;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Modifying
    @Query("UPDATE Booking b SET b.status = :status WHERE b.bookingId = :bookingId")
    void updateStatus(@Param("bookingId") Long bookingId, @Param("status") int status);

}
