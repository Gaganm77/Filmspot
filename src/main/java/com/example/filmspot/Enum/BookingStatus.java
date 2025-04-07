package com.example.filmspot.Enum;

public enum BookingStatus {
    RESERVED(1),
    BOOKED(2),
    EXPIRED(3);

    private final int value;

    BookingStatus(int value) {
        this.value = value;
    }

    public static BookingStatus fromValue(int value) {
        for (BookingStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid BookingStatus value: " + value);
    }

    public int getValue() {
        return value;
    }
}
