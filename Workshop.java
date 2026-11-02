package com.example.workshopapi;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class Workshop {
    private int id;

    @NotBlank(message = "Workshop title is required")
    private String title;

    private String date;

    @Positive(message = "Total sessions must be greater than zero")
    private int totalSessions;

    public Workshop() {
    }

    public Workshop(int id, String title, String date, int totalSessions) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.totalSessions = totalSessions;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getTotalSessions() { return totalSessions; }
    public void setTotalSessions(int totalSessions) { this.totalSessions = totalSessions; }
}
