package com.example.filmspot.dto;

import lombok.Data;

@Data
public class SearchFilterDTO {
    private String city;
    private String movieTitle;
    private String language;
    private String genre;
    private String format;
    private String date;
    private String startTime;
    private Double priceMin;
    private Double priceMax;
    private Double latitude;
    private Double longitude;
}