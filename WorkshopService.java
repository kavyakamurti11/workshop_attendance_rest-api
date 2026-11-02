package com.example.workshopapi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

// Service class: all the logic is written here
@Service
public class WorkshopService {

    // Sample rule: 75% attendance is required for the certificate
    private static final double REQUIRED_PERCENTAGE = 75.0;

    private final List<Workshop> workshops = new ArrayList<>();
    private final List<Participant> participants = new ArrayList<>();

    private int nextWorkshopId = 1;
    private int nextParticipantId = 1;

    // ---------------- Workshops ----------------

    // Create a workshop
    public Workshop addWorkshop(Workshop workshop) {
        workshop.setId(nextWorkshopId);
        nextWorkshopId++;
        workshops.add(workshop);
        return workshop;
    }

    // List all workshops
    public List<Workshop> getAllWorkshops() {
        return workshops;
    }

    // Find a workshop by ID
    public Workshop getWorkshopById(int id) {
        for (Workshop workshop : workshops) {
            if (workshop.getId() == id) {
                return workshop;
            }
        }
        throw new ResourceNotFoundException("Workshop not found with ID: " + id);
    }

    // ---------------- Participants ----------------

    // Register a participant in a workshop
    public Participant addParticipant(int workshopId, Participant participant) {
        getWorkshopById(workshopId);   // gives 404 if the workshop does not exist

        participant.setId(nextParticipantId);
        nextParticipantId++;
        participant.setWorkshopId(workshopId);
        participant.setSessionsAttended(0);   // attendance always starts from 0
        participants.add(participant);
        return participant;
    }

    // List the participants of one workshop
    public List<Participant> getParticipantsByWorkshop(int workshopId) {
        getWorkshopById(workshopId);   // gives 404 if the workshop does not exist

        List<Participant> result = new ArrayList<>();
        for (Participant participant : participants) {
            if (participant.getWorkshopId() == workshopId) {
                result.add(participant);
            }
        }
        return result;
    }

    // Find a participant by ID
    public Participant getParticipantById(int id) {
        for (Participant participant : participants) {
            if (participant.getId() == id) {
                return participant;
            }
        }
        throw new ResourceNotFoundException("Participant not found with ID: " + id);
    }

    // ---------------- Attendance ----------------

    // Update the number of sessions attended
    public Participant updateAttendance(int participantId, int sessionsAttended) {
        Participant participant = getParticipantById(participantId);
        Workshop workshop = getWorkshopById(participant.getWorkshopId());

        if (sessionsAttended < 0 || sessionsAttended > workshop.getTotalSessions()) {
            throw new InvalidAttendanceException(
                "sessionsAttended must be between 0 and " + workshop.getTotalSessions());
        }

        participant.setSessionsAttended(sessionsAttended);
        return participant;
    }

    // percentage = sessionsAttended / totalSessions x 100
    private double calculatePercentage(Participant participant, Workshop workshop) {
        return participant.getSessionsAttended() * 100.0 / workshop.getTotalSessions();
    }

    // Round to 2 decimal places for display only
    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    // Calculate attendance percentage and check certificate eligibility
    public EligibilityResponse checkEligibility(int participantId) {
        Participant participant = getParticipantById(participantId);
        Workshop workshop = getWorkshopById(participant.getWorkshopId());

        double percentage = calculatePercentage(participant, workshop);
        boolean eligible = percentage >= REQUIRED_PERCENTAGE;   // compare the exact value

        return new EligibilityResponse(
                participant.getId(),
                participant.getName(),
                workshop.getTitle(),
                workshop.getTotalSessions(),
                participant.getSessionsAttended(),
                round2(percentage),
                REQUIRED_PERCENTAGE,
                eligible);
    }

    // ---------------- Optional extension ----------------

    // Attendance summary of one workshop
    public WorkshopSummary getWorkshopSummary(int workshopId) {
        Workshop workshop = getWorkshopById(workshopId);

        int totalParticipants = 0;
        int eligibleCount = 0;
        double percentageSum = 0;

        for (Participant participant : participants) {
            if (participant.getWorkshopId() == workshopId) {
                double percentage = calculatePercentage(participant, workshop);
                totalParticipants++;
                percentageSum += percentage;
                if (percentage >= REQUIRED_PERCENTAGE) {
                    eligibleCount++;
                }
            }
        }

        double average = 0;
        if (totalParticipants > 0) {
            average = percentageSum / totalParticipants;
        }

        return new WorkshopSummary(
                workshop.getId(),
                workshop.getTitle(),
                workshop.getTotalSessions(),
                totalParticipants,
                eligibleCount,
                totalParticipants - eligibleCount,
                round2(average));
    }
}
