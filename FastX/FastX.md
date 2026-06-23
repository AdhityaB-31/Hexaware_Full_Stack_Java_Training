# FastX - Online Bus Ticket Booking System

## Project Overview

FastX is a Spring Boot REST API-based Online Bus Ticket Booking System that enables passengers to search buses, view seat availability, book tickets, make payments, cancel bookings, and view booking history. The system also provides Bus Operator and Admin functionalities for managing buses, routes, schedules, users, and bookings.

---

# Technology Stack

## Backend

* Java 21
* Spring Boot 3.x
* Spring Data JPA
* Spring Security
* Hibernate ORM
* MySQL Database
* JWT Authentication
* Lombok
* Log4j2
* Swagger OpenAPI
* Maven

## Testing

* JUnit 5
* Mockito

---

# Project Structure

```text
com.fastx

├── controller
├── service
│   └── impl
├── repository
├── entity
├── dto
├── security
├── config
├── exception
├── specification
├── util
├── constants
├── enums
├── mapper
├── scheduler
└── FastxApplication
```

---

# Database Configuration

application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fastx_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

logging.level.org.hibernate.SQL=DEBUG
```

---

# Base Entity

All entities should extend BaseEntity.

```java
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
```

---

# Entity Classes

## Role

```text
roleId
roleName
```

## User

```text
userId
fullName
email
password
phoneNumber
gender
address
isActive
role
```

## BusOperator

```text
operatorId
companyName
email
password
phoneNumber
address
isActive
```

## Bus

```text
busId
busName
busNumber
busType
origin
destination
journeyDate
departureTime
arrivalTime
fare
totalSeats
operator
```

## Amenity

```text
amenityId
amenityName
```

Examples:

* Water Bottle
* Blanket
* Charging Point
* TV

## Seat

```text
seatId
seatNumber
seatType
seatStatus
bus
```

## Booking

```text
bookingId
bookingDate
bookingStatus
totalAmount
user
bus
```

## Passenger

```text
passengerId
name
age
gender
seat
booking
```

## Payment

```text
paymentId
amount
paymentMethod
transactionId
paymentStatus
paymentDate
booking
```

## Refund

```text
refundId
refundAmount
refundDate
refundStatus
booking
```

---

# Entity Relationships

## Role ↔ User

```text
One Role → Many Users
Many Users → One Role
```

```java
@OneToMany(mappedBy = "role")
private List<User> users;

@ManyToOne
@JoinColumn(name = "role_id")
private Role role;
```

---

## BusOperator ↔ Bus

```text
One Operator → Many Buses
```

```java
@OneToMany(mappedBy = "busOperator")
private List<Bus> buses;

@ManyToOne
@JoinColumn(name = "operator_id")
private BusOperator busOperator;
```

---

## Bus ↔ Amenity

```text
Many To Many
```

```java
@ManyToMany
@JoinTable(
    name = "bus_amenities",
    joinColumns = @JoinColumn(name = "bus_id"),
    inverseJoinColumns = @JoinColumn(name = "amenity_id")
)
private List<Amenity> amenities;
```

---

## Bus ↔ Seat

```text
One Bus → Many Seats
```

```java
@OneToMany(mappedBy = "bus")
private List<Seat> seats;

@ManyToOne
@JoinColumn(name = "bus_id")
private Bus bus;
```

---

## User ↔ Booking

```text
One User → Many Bookings
```

```java
@OneToMany(mappedBy = "user")
private List<Booking> bookings;

@ManyToOne
@JoinColumn(name = "user_id")
private User user;
```

---

## Bus ↔ Booking

```text
One Bus → Many Bookings
```

```java
@OneToMany(mappedBy = "bus")
private List<Booking> bookings;

@ManyToOne
@JoinColumn(name = "bus_id")
private Bus bus;
```

---

## Booking ↔ Passenger

```text
One Booking → Many Passengers
```

```java
@OneToMany(mappedBy = "booking",
           cascade = CascadeType.ALL)
private List<Passenger> passengers;

@ManyToOne
@JoinColumn(name = "booking_id")
private Booking booking;
```

---

## Passenger ↔ Seat

```text
One Passenger → One Seat
```

```java
@OneToOne
@JoinColumn(name = "seat_id")
private Seat seat;
```

---

## Booking ↔ Payment

```text
One Booking → One Payment
```

```java
@OneToOne(mappedBy = "booking",
          cascade = CascadeType.ALL)
private Payment payment;
```

---

## Booking ↔ Refund

```text
One Booking → One Refund
```

```java
@OneToOne(mappedBy = "booking",
          cascade = CascadeType.ALL)
private Refund refund;
```

---

# DTO Classes

Single DTO per Entity

```text
RegisterDto
LoginDto
JwtDto

UserDto
BusOperatorDto
BusDto
AmenityDto
SeatDto
BookingDto
PassengerDto
PaymentDto
RefundDto
```

DTOs are used for both Request and Response.

---

# Repository Layer

```text
RoleRepository
UserRepository
BusOperatorRepository
BusRepository
AmenityRepository
SeatRepository
BookingRepository
PassengerRepository
PaymentRepository
RefundRepository
```

All repositories extend:

```java
JpaRepository<Entity, Long>
```

---

# Advanced Repository Methods

## UserRepository

```java
findByEmail()

existsByEmail()

findByFullNameContainingIgnoreCase()

findByIsActiveTrue()

countByRoleRoleName()
```

---

## BusRepository

```java
findByOriginAndDestinationAndJourneyDate()

findByBusType()

findByFareBetween()

findByBusNameContainingIgnoreCase()

findTop5ByOrderByFareAsc()
```

---

## SeatRepository

```java
findByBusBusId()

findByBusBusIdAndSeatStatus()

countByBusBusIdAndSeatStatus()
```

---

## BookingRepository

```java
findByUserUserId()

findByBookingStatus()

findByBookingDateBetween()

countByBookingStatus()
```

---

# JPQL Queries

## Calculate Bus Revenue

```java
@Query("""
SELECT SUM(b.totalAmount)
FROM Booking b
WHERE b.bus.busId = :busId
AND b.bookingStatus='CONFIRMED'
""")
Double calculateBusRevenue(Long busId);
```

## User Booking Count

```java
@Query("""
SELECT COUNT(b)
FROM Booking b
WHERE b.user.userId=:userId
""")
Long getUserBookingCount(Long userId);
```

## Popular Routes

```java
@Query("""
SELECT b.bus.origin,
       b.bus.destination,
       COUNT(b.bookingId)
FROM Booking b
GROUP BY b.bus.origin,
         b.bus.destination
ORDER BY COUNT(b.bookingId) DESC
""")
List<Object[]> findPopularRoutes();
```

---

# Native Queries

## Top Revenue Buses

```java
@Query(value = """
SELECT bus_id,
SUM(total_amount)
FROM bookings
GROUP BY bus_id
ORDER BY SUM(total_amount) DESC
LIMIT 5
""", nativeQuery = true)
List<Object[]> getTopRevenueBuses();
```

---

# Dynamic Search

Use:

```java
JpaSpecificationExecutor<Bus>
```

Search Filters

```text
Origin
Destination
Journey Date
Bus Type
Min Fare
Max Fare
Amenities
```

---

# Service Layer

## AuthService

```java
registerUser()
registerOperator()
login()
generateToken()
validateToken()
```

## UserService

```java
createUser()
updateUser()
deleteUser()
getUserById()
getAllUsers()
getBookingHistory()
```

## BusService

```java
addBus()
updateBus()
deleteBus()
searchBus()
getBusDetails()
getAvailableSeats()
getBusesByOperator()
```

## SeatService

```java
reserveSeats()
releaseSeats()
getSeatsByBus()
getAvailableSeats()
```

## BookingService

```java
createBooking()
confirmBooking()
cancelBooking()
getBookingById()
getBookingsByUser()
calculateFare()
```

## PaymentService

```java
processPayment()
verifyPayment()
refundPayment()
```

## RefundService

```java
createRefund()
approveRefund()
getRefundStatus()
```

## AdminService

```java
getDashboardStatistics()
manageUsers()
manageOperators()
manageBookings()
manageRoutes()
```

---

# Seat Booking Flow

### During Booking

```text
AVAILABLE → BOOKED
```

### During Cancellation

```text
BOOKED → AVAILABLE
```

SeatService Methods

```java
reserveSeats(List<Long> seatIds)

releaseSeats(List<Long> seatIds)
```

BookingService calls SeatService during booking confirmation and cancellation.

---

# Security

## JWT Components

```text
JwtService
JwtFilter
SecurityConfig
CustomUserDetailsService
```

Roles

```text
ROLE_USER
ROLE_OPERATOR
ROLE_ADMIN
```

---

# Dashboard APIs

## Admin Dashboard

```java
getTotalUsers()
getTotalOperators()
getTotalBookings()
getTotalRevenue()
getTodayBookings()
getTopRoutes()
getMostBookedBus()
```

## Operator Dashboard

```java
getOperatorRevenue()
getSeatOccupancy()
getBusPerformance()
```

---

# Design Patterns

## Strategy Pattern

Payment Processing

```text
PaymentStrategy

UpiPaymentStrategy
CardPaymentStrategy
NetBankingPaymentStrategy
```

---

# Exception Handling

Custom Exceptions

```text
ResourceNotFoundException
UserAlreadyExistsException
BusNotFoundException
SeatNotAvailableException
BookingException
PaymentException
UnauthorizedException
```

Global Handler

```java
@RestControllerAdvice
```

---

# Logging

Using Log4j2

```java
log.info("Booking Created");

log.info("Payment Success");

log.warn("Seat Already Booked");

log.error("Payment Failed");
```

---

# Swagger

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Unit Testing

```text
AuthServiceTest
UserServiceTest
BusServiceTest
SeatServiceTest
BookingServiceTest
PaymentServiceTest
AdminServiceTest
```

Mockito

```java
@ExtendWith(MockitoExtension.class)
@Mock
@InjectMocks
```

---

# Development Order

1. Create Spring Boot Project
2. Add Dependencies
3. Configure MySQL
4. Create Enums
5. Create BaseEntity
6. Create Entities & Mappings
7. Create DTOs
8. Create Repositories
9. Create Service Interfaces
10. Create Service Implementations
11. Implement JWT Security
12. Create Controllers
13. Implement Exception Handling
14. Implement Logging
15. Add Swagger
16. Create Unit Tests
17. Implement Dashboard APIs
18. Implement Dynamic Search APIs
19. Integrate React Frontend

---

# Expected Features

✔ User Registration

✔ User Login

✔ JWT Authentication

✔ Search Bus

✔ Seat Availability

✔ Seat Booking

✔ Booking History

✔ Ticket Cancellation

✔ Refund Processing

✔ Operator Dashboard

✔ Admin Dashboard

✔ Dynamic Bus Search

✔ Revenue Analytics

✔ Swagger Documentation

✔ Unit Testing

✔ Log4j2 Logging

✔ Hibernate Auto Table Creation
