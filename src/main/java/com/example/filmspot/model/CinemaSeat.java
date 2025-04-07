package com.example.filmspot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CinemaSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinemaseatid")
    private Long id;

    @Column(name = "seatnumber")
    private String seatNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinemahallid", nullable = false)
    private CinemaHall cinemaHall;

    private String type; // "Regular" or "Premium"
}