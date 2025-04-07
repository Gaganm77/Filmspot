package com.example.filmspot.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class WaitingUser {
    private Long userId;
    private List<Long> seatIds;
    private LocalDateTime joinedAt;

    public WaitingUser(Long userId, List<Long> seatIds) {
        this.userId = userId;
        this.seatIds = seatIds;
        this.joinedAt = LocalDateTime.now();
    }

    public boolean hasWaitedTooLong(Duration maxWaitTime) {
        return Duration.between(joinedAt, LocalDateTime.now()).compareTo(maxWaitTime) > 0;
    }

    public Long getUserId() {
        return userId;
    }
}
