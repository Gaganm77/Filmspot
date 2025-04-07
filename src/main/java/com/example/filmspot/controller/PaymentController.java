package com.example.filmspot.controller;

import com.example.filmspot.repository.BookingRepository;
import com.example.filmspot.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private final BookingRepository bookingRepository;


    @PostMapping("/create/{bookingId}")
    public ResponseEntity<Map<String, String>> createPayment(@PathVariable Long bookingId, @RequestParam Double amount) {
        try {
            String sessionUrl = paymentService.createCheckoutSession(bookingId, amount);
            Map<String, String> response = new HashMap<>();
            response.put("url", sessionUrl);
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}
