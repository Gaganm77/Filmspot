package com.example.filmspot.dto;

import com.example.filmspot.model.Show;
import com.example.filmspot.model.ShowSeat;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ShowSeatDTO {


    private Long showSeatId;
    private String seatNumber;
    private String type;

    private Integer status;

    private Double price;

    public static List<ShowSeatDTO> fromEntity(Show showId) {

        List<ShowSeat> showSeats = showId.getShowSeats();

        List<ShowSeatDTO> showSeatDTOS = showSeats.stream().map(showSeat -> {
            return ShowSeatDTO.builder()
                    .showSeatId(showSeat.getId())
                    .seatNumber(showSeat.getCinemaSeat().getSeatNumber())
                    .type(showSeat.getCinemaSeat().getType())
                    .status(showSeat.getStatus())
                    .price(showSeat.getPrice())
                    .build();
        }).collect(Collectors.toList());

        return showSeatDTOS;

    }
}
