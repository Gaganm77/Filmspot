package com.example.filmspot.service;

import com.example.filmspot.dto.WaitingUser;
import com.example.filmspot.model.ShowSeat;
import com.example.filmspot.repository.BookingRepository;
import com.example.filmspot.repository.SeatReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class WaitingUsersService {
    private final SeatReservationRepository seatReservationRepository;
    private final BookingRepository bookingRepository;
    private final Map<Long, LinkedHashMap<Long, WaitingUser>> waitingUsers = new ConcurrentHashMap<>();

    public void addToWaitingList(Long showId, Long userId, List<Long> seatIds) {
        waitingUsers.computeIfAbsent(showId, k -> new LinkedHashMap<>()).put(userId, new WaitingUser(userId, seatIds));
    }

    public void checkAndNotifyUsers(Long showId) {
        List<ShowSeat> freeSeats = seatReservationRepository.findAvailableSeats(showId, null);

        int avilableSeats = freeSeats.size();

        System.out.println("available seats" + freeSeats.size());

        if (freeSeats.isEmpty()) {
//            sendNotification(showId, "All seats are booked. Sorry!");
            System.out.println("all seats are booked");
            waitingUsers.remove(showId);
            return;
        }

        Iterator<Map.Entry<Long, WaitingUser>> iterator =
                waitingUsers.getOrDefault(showId, new LinkedHashMap<>()).entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Long, WaitingUser> entry = iterator.next();
            WaitingUser waitingUser = entry.getValue();
            int waitingUserRequiredSeats = waitingUser.getSeatIds().size();
            if (avilableSeats >= waitingUserRequiredSeats) {
                sendNotification(waitingUser.getUserId(), "Seats are now available! Redirect to booking");
                avilableSeats = avilableSeats - waitingUserRequiredSeats;
                iterator.remove();
            }
        }
    }

    @Scheduled(fixedRate = 300000) // Runs every 5 minutes
    @Transactional
    public void notifyLongWaitingUsers() {
        Duration maxWaitTime = Duration.ofHours(2);

        for (Map.Entry<Long, LinkedHashMap<Long, WaitingUser>> entry : waitingUsers.entrySet()) {
            Long showId = entry.getKey();
            Iterator<Map.Entry<Long, WaitingUser>> iterator = entry.getValue().entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<Long, WaitingUser> waitingEntry = iterator.next();
                WaitingUser user = waitingEntry.getValue();

                if (user.hasWaitedTooLong(maxWaitTime)) {
                    sendNotification(user.getUserId(), "You have been waiting for too long. Consider choosing another show.");
                    iterator.remove();
                }
            }
        }
    }


    private void sendNotification(Long userId, String message) {
        System.out.println("User " + userId + message);
    }
}
