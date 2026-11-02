package com.example.workshopapi;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Participant {
    private int id;

    @NotBlank(message = "Participant name is required")
    private String name;

    @NotBlank(message = "Participant email is required")
    @Email(message = "Participant email must be a valid email address")
    private String email;

    private int workshopId;
    private int sessionsAttended;

    public Participant() {
    }

    public Participant(int id, String name, String email,
                       int workshopId, int sessionsAttended) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.workshopId = workshopId;
        this.sessionsAttended = sessionsAttended;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getWorkshopId() { return workshopId; }
    public void setWorkshopId(int workshopId) { this.workshopId = workshopId; }

    public int getSessionsAttended() { return sessionsAttended; }
    public void setSessionsAttended(int sessionsAttended) { this.sessionsAttended = sessionsAttended; }
}
