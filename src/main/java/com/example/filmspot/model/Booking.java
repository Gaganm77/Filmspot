package com.example.filmspot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingid")
    private Long bookingId; // Primary Key

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user; // Foreign Key to User table

    @ManyToOne
    @JoinColumn(name = "showid", nullable = false)
    private Show show; // Foreign Key to Show table

    @Column(name = "numberofseats", nullable = false)
    private Integer numberOfSeats; // Total seats booked

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp; // When booking was made
    
    @Column(name = "status", nullable = false)
    private Integer status;
}