package com.example.filmspot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showseatid")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "showid", nullable = false)
    private Show show;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinemaseatid", nullable = false)
    private CinemaSeat cinemaSeat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bookingid", nullable = false)
    private Booking booking;

    //    private String type;
    private Double price;
    private Integer status;  // Example values: "Available", "Full", "Almost Full"
}