# 🎬 FilmSpot - Backend

**FilmSpot** is a backend system for a movie ticket booking application, similar to BookMyShow. It provides secure and optimized REST APIs for searching movie shows, booking seats, processing payments, and managing real-time reservations.

---

## 📦 Features

### 🔍 Search API
- Filter shows by:
  - City
  - Movie title
  - Language
  - Genre
  - Date & time
  - Format (e.g., 3D, IMAX)
  - Price range
- Location-based filtering using OpenStreetMap (via lat/lng to city)
- Pagination support

### 🎟️ Seat Booking
- Active seat reservation with timeout (to prevent double bookings)
- Waiting users service for high-demand shows

### 💳 Payments
- Stripe integration for secure payment handling
- Booking is confirmed only after successful payment

### 🔐 Security
- Secure user signup/login with encrypted passwords
- JWT-based authentication

---

## 🛠️ Tech Stack

| Component        | Technology                      |
|------------------|----------------------------------|
| Framework        | Spring Boot                     |
| Persistence      | Spring Data JPA, Hibernate      |
| Database         | PostgreSQL                      |
| Authentication   | Spring Security, JWT            |
| Payment Gateway  | Stripe API                      |
| Geolocation API  | OpenStreetMap (Nominatim)       |

---

## 🗃️ Database Schema (Simplified)

- `users` – stores user accounts
- `movies`, `cinemas`, `cinema_halls`
- `shows` – contains movie showtime info
- `seats` – seating plan per cinema hall
- `bookings` – stores seat reservations
- `payments` – logs payment transactions

Includes views and queries optimized with `RANK()` and indexes for latest show data.

---

## 🚀 Getting Started

### 📋 Prerequisites
- Java 17+
- Maven
- PostgreSQL

### ⚙️ Setup

1. Clone the repository:
```bash
git clone git clone [https://github.com/your-username/filmspot.git](https://github.com/Gaganm77/Filmspot.git)


