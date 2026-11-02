package com.example.workshopapi;

// Response of GET /workshops/{id}/summary  (optional extension)
public class WorkshopSummary {
    private int workshopId;
    private String title;
    private int totalSessions;
    private int totalParticipants;
    private int eligibleCount;
    private int notEligibleCount;
    private double averageAttendancePercentage;

    public WorkshopSummary(int workshopId, String title, int totalSessions,
                           int totalParticipants, int eligibleCount,
                           int notEligibleCount, double averageAttendancePercentage) {
        this.workshopId = workshopId;
        this.title = title;
        this.totalSessions = totalSessions;
        this.totalParticipants = totalParticipants;
        this.eligibleCount = eligibleCount;
        this.notEligibleCount = notEligibleCount;
        this.averageAttendancePercentage = averageAttendancePercentage;
    }

    public int getWorkshopId() { return workshopId; }
    public String getTitle() { return title; }
    public int getTotalSessions() { return totalSessions; }
    public int getTotalParticipants() { return totalParticipants; }
    public int getEligibleCount() { return eligibleCount; }
    public int getNotEligibleCount() { return notEligibleCount; }
    public double getAverageAttendancePercentage() { return averageAttendancePercentage; }
}
