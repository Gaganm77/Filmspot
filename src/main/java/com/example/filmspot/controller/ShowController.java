package com.example.filmspot.controller;

import com.example.filmspot.dto.*;
import com.example.filmspot.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @GetMapping("/search")
    public ResponseEntity<Page<ShowResponseDTO>> searchShows(
            @ModelAttribute SearchFilterDTO filter, Pageable pageable) {
        Page<ShowResponseDTO> shows = showService.searchShows(filter, pageable);
        return ResponseEntity.ok(shows);
    }

    @GetMapping("/show-seats/{showId}")
    public ResponseEntity<List<ShowSeatDTO>> getShowSeats(@PathVariable Long showId) {
        List<ShowSeatDTO> showSeatDTOS = showService.getShowSeats(showId);
        return ResponseEntity.ok(showSeatDTOS);

    }


//    @GetMapping("/search")
//    public ResponseEntity<Page<Integer>> searchShows(Pageable pageable) {
//
//        List<Integer> list = IntStream.range(0, 100).boxed().collect(Collectors.toList());
//
//        int start = (int) pageable.getOffset();
//        int end = Math.min(start + pageable.getPageSize(), list.size());
//
//        List<Integer> pageData = list.subList(start, end);
//
//        Page<Integer> shows = new PageImpl<>(pageData, pageable, list.size());
//        return ResponseEntity.ok(shows);
//    }
}
