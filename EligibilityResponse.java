package com.example.workshopapi;

// Response of GET /participants/{id}/eligibility
public class EligibilityResponse {
    private int participantId;
    private String participantName;
    private String workshopTitle;
    private int totalSessions;
    private int sessionsAttended;
    private double attendancePercentage;
    private double requiredPercentage;
    private boolean eligible;
    private String status;

    public EligibilityResponse(int participantId, String participantName, String workshopTitle,
                               int totalSessions, int sessionsAttended,
                               double attendancePercentage, double requiredPercentage,
                               boolean eligible) {
        this.participantId = participantId;
        this.participantName = participantName;
        this.workshopTitle = workshopTitle;
        this.totalSessions = totalSessions;
        this.sessionsAttended = sessionsAttended;
        this.attendancePercentage = attendancePercentage;
        this.requiredPercentage = requiredPercentage;
        this.eligible = eligible;
        this.status = eligible ? "ELIGIBLE for certificate" : "NOT ELIGIBLE for certificate";
    }

    public int getParticipantId() { return participantId; }
    public String getParticipantName() { return participantName; }
    public String getWorkshopTitle() { return workshopTitle; }
    public int getTotalSessions() { return totalSessions; }
    public int getSessionsAttended() { return sessionsAttended; }
    public double getAttendancePercentage() { return attendancePercentage; }
    public double getRequiredPercentage() { return requiredPercentage; }
    public boolean isEligible() { return eligible; }
    public String getStatus() { return status; }
}
