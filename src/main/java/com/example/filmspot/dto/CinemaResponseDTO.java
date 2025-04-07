package com.example.filmspot.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CinemaResponseDTO {

    private Long cinemaId;
    private String cinemaName;
    private List<ShowDTO> shows;

}
