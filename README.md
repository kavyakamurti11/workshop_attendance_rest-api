# College Workshop Attendance and Certificate Eligibility REST API

Project 10 - HNCC Java-Based Spring Boot Development.
Data is stored in an in-memory Java list, so it resets when the application restarts.

## Requirements
- JDK 21
- Eclipse with Spring Tools, Maven
- REST Client Chrome extension

## Import and run in Eclipse
1. Extract the ZIP file.
2. File -> Import -> Maven -> Existing Maven Projects -> select the `workshop-attendance-rest-api` folder -> Finish.
3. Wait for Maven dependencies to download (right-click project -> Maven -> Update Project if needed).
4. Run `WorkshopAttendanceApplication.java` as a Java Application.
5. The API runs at `http://localhost:8080`. If port 8080 is busy, change `server.port` in `application.properties`.

## Files
| File | Purpose |
|---|---|
| `Workshop.java`, `Participant.java` | Model classes with validation |
| `AttendanceRequest.java` | Request body for updating attendance |
| `EligibilityResponse.java`, `WorkshopSummary.java` | Response classes (eligibility result, optional summary) |
| `WorkshopService.java` | Business logic, in-memory lists, attendance % and eligibility |
| `WorkshopController.java` | REST endpoints |
| `GlobalExceptionHandler.java` | Error responses ({"message": "..."}) |
| `ResourceNotFoundException.java`, `InvalidAttendanceException.java` | Custom exceptions |
| `WorkshopAttendanceApplication.java` | Main class |

## API endpoints
| Method | Endpoint | Purpose | Success | Errors |
|---|---|---|---|---|
| GET | `/workshops` | List workshops | 200 | |
| POST | `/workshops` | Create workshop | 201 | 400 |
| GET | `/workshops/{id}` | Get workshop | 200 | 404 |
| POST | `/workshops/{id}/participants` | Register participant | 201 | 400, 404 |
| GET | `/workshops/{id}/participants` | List participants of a workshop | 200 | 404 |
| GET | `/participants/{id}` | Get participant details | 200 | 404 |
| PUT | `/participants/{id}/attendance` | Update sessions attended | 200 | 400, 404 |
| GET | `/participants/{id}/eligibility` | Attendance % and certificate eligibility | 200 | 404 |
| GET | `/workshops/{id}/summary` | Optional extension: attendance summary | 200 | 404 |

Eligibility rule: `sessionsAttended / totalSessions x 100 >= 75`

## Test order in REST Client
IDs start from 1 and the data is empty at startup, so run in this order.
For POST/PUT use header `Content-Type: application/json` and a raw JSON body.

1. POST `http://localhost:8080/workshops`
   `{ "title": "Spring Boot Workshop", "date": "2026-11-20", "totalSessions": 4 }`  -> 201
2. POST `http://localhost:8080/workshops/1/participants`
   `{ "name": "Rahul", "email": "rahul@college.edu" }`  -> 201 (sessionsAttended = 0)
3. GET `/workshops`, `/workshops/1`, `/workshops/1/participants`, `/participants/1` -> 200
4. PUT `http://localhost:8080/participants/1/attendance`  `{ "sessionsAttended": 2 }` -> 200
   GET `/participants/1/eligibility` -> attendancePercentage 50.0, eligible false, "NOT ELIGIBLE for certificate"
5. PUT same URL `{ "sessionsAttended": 3 }` -> GET eligibility -> 75.0, eligible true, "ELIGIBLE for certificate"
6. PUT same URL `{ "sessionsAttended": 10 }` -> 400 "sessionsAttended must be between 0 and 4"
7. GET `/participants/999/eligibility` -> 404 "Participant not found with ID: 999"
8. GET `/workshops/1/summary` -> totalParticipants 1, eligibleCount 1, averageAttendancePercentage 75.0

Validation (expect 400):
- POST `/workshops` with `{ "title": "", "date": "2026-11-20", "totalSessions": 0 }`
- POST `/workshops/1/participants` with `{ "name": "", "email": "" }`
- POST `/workshops/1/participants` with `{ "name": "Amit", "email": "abc" }` (invalid email)
- PUT `/participants/1/attendance` with `{ }` (sessionsAttended required) or `{ "sessionsAttended": -1 }`

Missing record (expect 404):
- GET `/workshops/99`
- POST `/workshops/99/participants` with a valid body
