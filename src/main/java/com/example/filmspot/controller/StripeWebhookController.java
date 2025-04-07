package com.example.filmspot.controller;

import com.example.filmspot.service.ActiveReservationService;
import com.example.filmspot.service.SeatReservationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class StripeWebhookController {

    //    private final BookingRepository bookingRepository;
    private final SeatReservationService seatReservationService;
    private final ActiveReservationService activeReservationService;
    @Value("${stripe.webhook.secret}")
    private String endpointSecret;


    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            // Verify Stripe Signature
            System.out.println("inside");
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            System.out.println("inside");
            if ("checkout.session.completed".equals(event.getType())) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(payload);
                JsonNode sessionNode = jsonNode.get("data").get("object");
                if (sessionNode != null) {
                    String sessionId = sessionNode.get("id").asText();
                    System.out.println("session" + sessionId);
                    String bookingIdStr = sessionNode.get("metadata").get("bookingId").asText();  // Extract metadata
                    System.out.println(bookingIdStr);
                    if (bookingIdStr != null) {
                        Long bookingId = Long.parseLong(bookingIdStr);
                        seatReservationService.confirmPayment(bookingId);
                    }
                }
            }

            return ResponseEntity.ok("Received");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Webhook Handling Failed: " + e.getMessage());
        }
    }


}

