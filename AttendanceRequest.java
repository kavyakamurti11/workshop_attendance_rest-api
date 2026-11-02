package com.example.workshopapi;

import jakarta.validation.constraints.NotNull;

// Request body for PUT /participants/{id}/attendance
// Example: { "sessionsAttended": 3 }
public class AttendanceRequest {

    @NotNull(message = "sessionsAttended is required")
    private Integer sessionsAttended;

    public AttendanceRequest() {
    }

    public Integer getSessionsAttended() { return sessionsAttended; }
    public void setSessionsAttended(Integer sessionsAttended) { this.sessionsAttended = sessionsAttended; }
}
