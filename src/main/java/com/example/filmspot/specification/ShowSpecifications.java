package com.example.filmspot.specification;


import com.example.filmspot.model.Show;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ShowSpecifications {

    public static Specification<Show> filterByCity(String city) {
        return (root, query, criteriaBuilder) -> city == null ? null :
                criteriaBuilder.equal(root.get("cinemaHall").get("cinema").get("city").get("name"), city);
    }

    public static Specification<Show> filterByMovieTitle(String movieTitle) {
        return (root, query, criteriaBuilder) -> movieTitle == null ? null :
                criteriaBuilder.like(root.get("movie").get("title"), "%%" + movieTitle + "%%");
    }

    public static Specification<Show> filterByLanguage(String language) {
        return (root, query, criteriaBuilder) -> language == null ? null :
                criteriaBuilder.equal(root.get("movie").get("language"), language);
    }

    public static Specification<Show> filterByGenre(String genre) {
        return (root, query, criteriaBuilder) -> genre == null ? null :
                criteriaBuilder.like(root.get("movie").get("genre"), "%%" + genre + "%%");
    }

    public static Specification<Show> filterByFormat(String format) {
        return (root, query, criteriaBuilder) -> format == null ? null :
                criteriaBuilder.equal(root.get("format"), format);
    }

    public static Specification<Show> filterByDate(String date) {
        return (root, query, criteriaBuilder) -> {
            if (date == null) return null;
            LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            System.out.println(date);
            return criteriaBuilder.equal(root.get("date"), parsedDate);
        };
    }

    public static Specification<Show> filterByStartTime(String startTime) {
        return (root, query, criteriaBuilder) -> {
            if (startTime == null) return null;
            LocalTime parsedTime = LocalTime.parse(startTime, DateTimeFormatter.ISO_TIME);
            return criteriaBuilder.greaterThanOrEqualTo(root.get("startTime"), parsedTime);
        };
    }

    public static Specification<Show> filterByPriceRange(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) return null;
            Join<Object, Object> showSeats = root.join("showSeats");
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(showSeats.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(showSeats.get("price"), minPrice);
            } else {
                return criteriaBuilder.lessThanOrEqualTo(showSeats.get("price"), maxPrice);
            }
        };
    }
}
