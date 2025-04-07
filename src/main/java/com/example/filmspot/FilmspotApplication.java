package com.example.filmspot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FilmspotApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmspotApplication.class, args);
    }


}
