package com.example.filmspot.controller;

import com.example.filmspot.dto.SeatReservationRequest;
import com.example.filmspot.service.SeatReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/show-seats")
@RequiredArgsConstructor
public class SeatReservationController {

    private final SeatReservationService seatReservationService;

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveSeats(@RequestBody SeatReservationRequest request) {
        return seatReservationService.reserveSeats(request);
    }
}