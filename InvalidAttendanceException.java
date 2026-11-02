package com.example.workshopapi;

// Used when sessionsAttended is less than 0 or more than totalSessions (400)
public class InvalidAttendanceException extends RuntimeException {

    public InvalidAttendanceException(String message) {
        super(message);
    }
}
