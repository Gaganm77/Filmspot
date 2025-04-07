package com.example.filmspot.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
public class SeatResponseDTO {
    private String type;
    private double price;
    private String status;
}