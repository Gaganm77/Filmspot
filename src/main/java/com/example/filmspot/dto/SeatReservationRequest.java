package com.example.filmspot.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatReservationRequest {
    private Long showId;
    private List<Long> seatIds;
    private Long userId;
}
