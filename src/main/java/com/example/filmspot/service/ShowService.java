package com.example.filmspot.service;


import com.example.filmspot.dto.*;
import com.example.filmspot.model.Show;
import com.example.filmspot.repository.ShowRepository;
import com.example.filmspot.specification.ShowSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final GeoLocationService geoLocationService;

    public Page<ShowResponseDTO> searchShows(SearchFilterDTO filter, Pageable pageable) {
        if (filter.getLatitude() != null && filter.getLongitude() != null) {
            String city = geoLocationService.getCityFromCoordinates(filter.getLatitude(), filter.getLongitude());
            filter.setCity(city);
        }

        Specification<Show> spec = Specification.where(ShowSpecifications.filterByCity(filter.getCity()))
                .and(ShowSpecifications.filterByMovieTitle(filter.getMovieTitle()))
                .and(ShowSpecifications.filterByLanguage(filter.getLanguage()))
                .and(ShowSpecifications.filterByGenre(filter.getGenre()))
                .and(ShowSpecifications.filterByFormat(filter.getFormat()))
                .and(ShowSpecifications.filterByDate(filter.getDate()))
                .and(ShowSpecifications.filterByStartTime(filter.getStartTime()))
                .and(ShowSpecifications.filterByPriceRange(filter.getPriceMin(), filter.getPriceMax()));


        return ShowResponseDTO.fromEntity(showRepository.findAll(spec, pageable));
    }

    public List<ShowSeatDTO> getShowSeats(Long showId) {

        Show show = showRepository.findById(showId).orElseThrow(() -> new RuntimeException("Show not found with id: " + showId));
        return ShowSeatDTO.fromEntity(show);

    }
}