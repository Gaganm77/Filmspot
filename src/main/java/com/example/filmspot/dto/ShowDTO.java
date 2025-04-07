package com.example.filmspot.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class ShowDTO {
    private Long showID;
    private String cinemaHallName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<SeatResponseDTO> seats;
}
