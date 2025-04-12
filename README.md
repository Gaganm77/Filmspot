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

## 🚢 Containerized with Docker & Deployed on Render

- Containerized using Docker and deployed on Render for seamless scaling and management.

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
git clone git clone https://github.com/Gaganm77/Filmspot.git
```

2. Configure application.properties
```bash
spring.datasource.url=jdbc:postgresql://aws-0-ap-south-1.pooler.supabase.com:6543/postgres?currentSchema=filmspot
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.show-sql=true
stripe.secret.key=${STRIPE_SECRET_KEY}
stripe.public.key=pk_test_51QacDiDGOz76PyI8QARSCS9OcHEg3HW9bvZ0NWzhxSoiSlVJasfxKbjLuDo0bl2ODFE88x9hdxNINo020TlMmY9W00f2xmKfmP
stripe.webhook.secret=${STRIPE_WEBHOOK_SECRET}
```

3. Run the app
 ```bash
./mvnw clean install
./mvnw spring-boot:run
```

## 🔌 API Quick Reference – FilmSpot (Deployed)

Base URL: `https://filmspot.onrender.com`

---

### 🧑‍💼 User APIs

#### 🔐 Register
`POST /register`

```json
{
  "name": "user2",
  "password": "user2@123",
  "email": "user2@gmail.com",
  "phone": "9134023343"
}
```
#### 🔐 Login
POST /login

```json
{
  "email": "user@gmail.com",
  "password": "user1@123"
}
```
🔁 Returns JWT token used for authenticated endpoints.

#### 🎟️ Show & Seat APIs
🎫 Get Show Seats by Show ID
GET /api/shows/show-seats/{showId}

Example:

GET /api/shows/show-seats/1
Returns seat availability (reserved, available) for that show.

#### ⏳ Reserve Seats (Temporary Hold)
POST /show-seats/reserve

```json
{
  "showId": 1,
  "seatIds": [308, 364],
  "userId": 1
}
```
⚠️ Starts a 10-minute reservation timer. Must confirm via payment.

#### 💳 Payment API
✅ Create Payment Intent (Confirm Booking)
POST /api/payment/create/{bookingId}?amount={amount}

```json
{
  "showId": 1,
  "seatIds": [280, 252],
  "userId": 1
}
```
Example:

POST /api/payment/create/28?amount=350
🏦 Integrates with Stripe. Booking is only confirmed after successful payment.

#### 🔍 Search API
📅 Search Shows with Filters
GET /api/shows/search

Supports filters: city, date, movieTitle, language, genre, etc.

Example:


GET /api/shows/search?page=0&size=5&city=Bengaluru&date=2025-02-03
Returns paginated list of matching shows with movie and seat info.


## 🎯 Reservation System with Timeouts & Notifications

FilmSpot uses in-memory maps and scheduled tasks to implement real-time seat management for high-demand shows, handling scenarios like user inactivity, long waits, and automatic seat releases.

### 🕓 ActiveReservationService

This service temporarily holds user seat reservations during the booking process:

- Reservations are stored in-memory:  
  `Map<showId, Map<bookingId, timestamp>>`
- Every reservation has a **10-minute expiration**.
- A **scheduled task** (runs every 1 minute) checks for expired reservations:
  - If a reservation is older than 10 minutes, it is:
    - Marked as expired in the database.
    - Released via `seatReservationRepository.freeExpiredSeats(...)`
    - Removed from memory.
    - Triggers notification for users in the waiting list (`WaitingUsersService`).

### 👥 WaitingUsersService

The `WaitingUsersService` manages users who are attempting to book seats that are currently held by other users.

### 🧠 How It Works

- Waiting users are tracked per show:`Map<showId, Map<userId, WaitingUser>>`
- When seats are **released** (either due to timeout or cancellation), the system:
- Checks for available seats.
- Matches waiting users whose seat requirements can be fulfilled.
- Sends notifications to those users so they can retry booking.


## 🚀 Contributing

We welcome contributions to FilmSpot! If you'd like to contribute, feel free to fork the repository, make changes, and create a pull request. Here’s how you can contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature-name`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature-name`)
5. Create a new Pull Request

Please make sure your code follows the project's coding style

## 🎉 Acknowledgements

Thank you for checking out FilmSpot! Enjoy the experience and feel free to share feedback!









