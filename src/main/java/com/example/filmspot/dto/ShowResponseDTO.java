package com.example.filmspot.dto;


import com.example.filmspot.model.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class ShowResponseDTO {


    private Long movieID;
    private String title;
    private String description;
    private Integer duration;
    private String genre;
    private String language;
    private LocalDate releaseDate;
    private String country;
    private List<CinemaResponseDTO> cinemas;

    public static Page<ShowResponseDTO> fromEntity(Page<Show> showPage) {


        Map<Movie, List<Show>> movieShowsMap = showPage.getContent()
                .stream()
                .collect(Collectors.groupingBy(Show::getMovie));

        // Convert each movie and its associated shows into a ShowResponseDTO
        List<ShowResponseDTO> responseList = movieShowsMap.entrySet().stream()
                .map(entry -> {
                    Movie movie = entry.getKey();
                    List<Show> shows = entry.getValue();

                    // Group shows by Cinema
                    Map<Cinema, List<Show>> cinemaShowsMap = shows.stream()
                            .collect(Collectors.groupingBy(show -> show.getCinemaHall().getCinema()));

                    List<CinemaResponseDTO> cinemaList = cinemaShowsMap.entrySet().stream()
                            .map(cinemaEntry -> {
                                Cinema cinema = cinemaEntry.getKey();
                                List<ShowDTO> showDTOs = cinemaEntry.getValue().stream()
                                        .map(show -> ShowDTO.builder()
                                                .showID(show.getId())
                                                .cinemaHallName(show.getCinemaHall().getName())
                                                .date(show.getDate())
                                                .startTime(show.getStartTime())
                                                .endTime(show.getEndTime())
                                                .seats(getListSeatDTO(show))
                                                .build())
                                        .collect(Collectors.toList());
                                return new CinemaResponseDTO(cinema.getId(), cinema.getName(), showDTOs);
                            })
                            .collect(Collectors.toList());
//
//        ShowResponseDTO dto = new ShowResponseDTO();
//        dto.setMovieID(show.getMovie().getId());
//        dto.setCinemaId(show.getCinemaHall().getCinema().getId());
//        dto.setTitle(show.getMovie().getTitle());
//        dto.setDescription(show.getMovie().getDescription());
//        dto.setDuration(show.getMovie().getDuration());
//        dto.setGenre(show.getMovie().getGenre());
//        dto.setLanguage(show.getMovie().getLanguage());
//        dto.setReleaseDate(show.getMovie().getReleaseDate());
//        dto.setCountry(show.getMovie().getCountry());

//        List<CinemaResponseDTO> cinemaList = showpage.getContent().stream()
//                .collect(Collectors.groupingBy(show -> show.getCinemaHall().getCinema())) // Group by Cinema
//                .entrySet().stream()
//                .map(entry -> {
//                    Cinema cinema = entry.getKey();
//                    List<ShowDTO> showDTOs = entry.getValue().stream()
//                            .map(show -> ShowDTO.builder()
//                                    .showID(show.getId())
//                                    .date(show.getDate())
//                                    .startTime(show.getStartTime())
//                                    .endTime(show.getEndTime())
//                                    .seats(getListSeatDTO(show))
//                                    .build())
//                            .collect(Collectors.toList());
//
//                    return new CinemaResponseDTO(cinema.getId(), cinema.getName(), showDTOs);
//                })
//                .collect(Collectors.toList());
//

//        Map<Long, List<ShowSeat>> cinemagroup = show.getShowSeats().stream().collect(Collectors.groupingBy((s) -> s.getShow().getCinemaHall().getCinema().getId()));
//
//        List<CinemaResponseDTO> cinemaResponseDTOS = cinemagroup.entrySet().stream()
//                .map(entry ->
//                        dto.
//                                CinemaResponseDTO.fromEntity(entry.getValue()))
//                .collect(Collectors.toList());


//        // Grouping seats by CinemaSeat (type) and determining status
//        Map<String, List<ShowSeat>> seatGroups = show.getShowSeats().stream()
//                .collect(Collectors.groupingBy((s) -> s.getCinemaSeat().getType()));
//
//
//        List<SeatResponseDTO> seatResponses = seatGroups.entrySet().stream()
//                .map(entry -> {
//                    String type = entry.getKey();
//                    List<ShowSeat> seats = entry.getValue();
//                    double price = seats.get(0).getPrice(); // Assuming same price for same type
//                    long availableSeats = seats.stream().filter(s -> s.getStatus() == 0).count();  // Assuming status 0 means "Available"
//                    String status = determineSeatStatus(availableSeats, seats.size());
//
//                    return new SeatResponseDTO(type, price, status);
//                })
//                .collect(Collectors.toList());
//
//        dto.setSeats(seatResponses);
//        Page<ShowResponseDTO> responsePage = showPage.map(show -> {
//            // Get movie details from the Show object
//            List<CinemaHall> cinemaHall = show.getCinemaHall(); // Assuming Show has a Movie field
////            Map<Long, List<CinemaHall>> cinemas = cinemaHall.stream().collect(Collectors.groupingBy(cine -> cine.getId()));
//
//
//            List<CinemaResponseDTO> cinemaList = cinemaHall.stream()
//                    .map(entry -> {
//                        Show show1 = entry.getShow();
//                        ShowDTO showDTO = new ShowDTO(show1.getId(), entry.getName(), show1.getDate(), show1.getStartTime(), show1.getEndTime(), getListSeatDTO(show1));
//                        return showDTO;
//                    })
//                                .collect(Collectors.toList());
//                        return new CinemaResponseDTO(entry.getKey(), show.getName(), showDTOs);
//                    })
//                    .collect(Collectors.toList());


//        Page<ShowResponseDTO> responsePage = showPage.map(show -> {
//            // Get movie details from the Show object
//            Movie movie = show.getMovie(); // Assuming Show has a Movie field
//
//            List<CinemaResponseDTO> cinemaList = show.getShowSeats().stream()
//                    .collect(Collectors.groupingBy(seat -> seat.getCinemaSeat().getCinemaHall().getCinema())) // Group by Cinema
//                    .entrySet().stream()
//                    .map(entry -> {
//                        Cinema cinema = entry.getKey();
//                        List<ShowDTO> showDTOS = new ArrayList<>();
//                        entry.getValue().stream().map(seat->seat.getShow().getShowSeats().)
//                        showDTOS.add(ShowDTO.builder()
//                                .showID(show.getId()) // Now fetched from Show table
//                                .date(show.getDate())
//                                .startTime(show.getStartTime())
//                                .endTime(show.getEndTime())
//                                .seats(getListSeatDTO(show))
//                                .build());
//
//                        return new CinemaResponseDTO(cinema.getId(), cinema.getName(), showDTOS);
//                    })
//                    .collect(Collectors.toList());

                    // Return ShowResponseDTO with movie details from the Show object
                    return ShowResponseDTO.builder()
                            .movieID(movie.getId())
                            .title(movie.getTitle())
                            .description(movie.getDescription())
                            .duration(movie.getDuration())
                            .genre(movie.getGenre())
                            .language(movie.getLanguage())
                            .releaseDate(movie.getReleaseDate())
                            .country(movie.getCountry())
                            .cinemas(cinemaList)
                            .build();
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, showPage.getPageable(), showPage.getTotalElements());

    }


    public static List<SeatResponseDTO> getListSeatDTO(Show show) {
        Map<String, List<ShowSeat>> seatGroups = show.getShowSeats().stream()
                .collect(Collectors.groupingBy((s) -> s.getCinemaSeat().getType()));


        List<SeatResponseDTO> seatResponses = seatGroups.entrySet().stream()
                .map(entry -> {
                    String type = entry.getKey();
                    List<ShowSeat> seats = entry.getValue();
                    double price = seats.get(0).getPrice(); // Assuming same price for same type
                    long availableSeats = seats.stream().filter(s -> s.getStatus() == 0).count();  // Assuming status 0 means "Available"
                    String status = determineSeatStatus(availableSeats, seats.size());

                    return new SeatResponseDTO(type, price, status);
                })
                .collect(Collectors.toList());
        return seatResponses;

    }

    private static String determineSeatStatus(long availableSeats, long totalSeats) {
        if (availableSeats == 0) {
            return "Full";
        } else if (availableSeats < totalSeats * 0.2) {
            return "Almost Full";
        } else {
            return "Available";
        }
    }


}