# 🚖 QuickRide – Scalable Ride Booking Backend

## 📌 Project Overview

**QuickRide** is a scalable ride-booking backend system built using **Spring Boot Microservices Architecture**.
The system simulates real-world ride-hailing platforms like Uber/Ola and includes user management, driver management, ride booking, live location tracking, payments, and notifications.

This project is designed to demonstrate **backend engineering, system design, and scalable architecture** for software engineering job roles.

---

## 🛠️ Tech Stack

🟡 **Currently in Development**
✅ User Register API Completed
✅ User Login API Completed

**Technologies Used:**

* Java 21+
* Spring Boot
* Spring Data JPA
* Spring Security + JWT
* PostgreSQL / MySQL
* Redis (Caching & Nearby Drivers)
* Apache Kafka (Event Streaming & Notifications)
* WebSocket (Live Driver Location)
* Docker
* Spring Cloud Gateway (API Gateway)
* GraphQL
* Swagger / OpenAPI
* JUnit & Testcontainers

---

## 🏗️ System Architecture

```
Client (Mobile/Web)
        |
        v
API Gateway (Spring Cloud Gateway)
        |
------------------------------------------------
|        |        |        |        |           |
User   Driver    Ride   Payment  Location  Notification
Service Service Service Service Service     Service
        |
     Database (PostgreSQL)
        |
      Redis (Cache)
        |
      Kafka (Event Streaming)
```

---

## 📂 Project Modules

| Service              | Description                        |
| -------------------- | ---------------------------------- |
| User Service         | User registration & authentication |
| Driver Service       | Driver profile & availability      |
| Ride Service         | Ride booking & ride lifecycle      |
| Location Service     | Live driver location tracking      |
| Payment Service      | Ride payment processing            |
| Notification Service | Email/SMS/Push notifications       |

---

## 🗄️ Database Schema

**Main Tables:**

* users
* drivers
* ride_requests
* rides
* payments
* locations

---

## 🔑 Features

* User Registration & Login (JWT Authentication)
* Driver Availability Toggle
* Ride Request System
* Driver Matching Algorithm (Nearest Driver)
* Ride Lifecycle:

  * REQUESTED
  * ACCEPTED
  * STARTED
  * COMPLETED
  * CANCELLED
* Live Driver Location Tracking (WebSocket)
* Payment Integration
* Kafka Event-based Notifications
* Redis Cache for Nearby Drivers
* API Gateway Routing
* Dockerized Deployment

---

## 🚀 API Endpoints (Sample)

### User APIs

| Method | Endpoint            | Description   |
| ------ | ------------------- | ------------- |
| POST   | /api/users/register | Register user |
| GET    | /api/users/login    | Login user    |

### Ride APIs

| Method | Endpoint            | Description         |
| ------ | ------------------- | ------------------- |
| POST   | /api/rides/request  | Request ride        |
| POST   | /api/rides/accept   | Driver accepts ride |
| POST   | /api/rides/start    | Start ride          |
| POST   | /api/rides/complete | Complete ride       |

---

## 🐳 Docker Run

```bash
docker-compose up --build
```

---

## 📊 Future Improvements

* Surge Pricing Algorithm
* ETA Calculation
* Google Maps Integration
* Online Payments (Stripe/Razorpay)
* Kubernetes Deployment
* CI/CD Pipeline (GitHub Actions)
* Rate Limiting
* Distributed Logging (ELK Stack)

---

## 👨‍💻 Author

**Sunny Bharti**

---

## ⭐ Resume Project Description

**QuickRide – Scalable Ride Booking System**
Developing a scalable ride booking backend using Spring Boot microservices architecture with Kafka, Redis, WebSocket, and Docker. Implemented user registration and login and currently building driver, ride, and payment modules with planned JWT authentication and event-driven architecture.

---
