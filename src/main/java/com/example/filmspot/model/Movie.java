package com.example.filmspot.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieid")
    private Long id;

    private String title;
    private String description;
    private Integer duration;
    private String genre;
    private String language;
    @Column(name = "releasedate")
    private LocalDate releaseDate;
    private String country;
}