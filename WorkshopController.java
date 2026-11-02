package com.example.workshopapi;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkshopController {
    private final WorkshopService workshopService;

    public WorkshopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    // ---------------- Workshops ----------------

    // GET /workshops
    @GetMapping("/workshops")
    public List<Workshop> getAllWorkshops() {
        return workshopService.getAllWorkshops();
    }

    // POST /workshops
    @PostMapping("/workshops")
    public ResponseEntity<Workshop> addWorkshop(@Valid @RequestBody Workshop workshop) {
        Workshop saved = workshopService.addWorkshop(workshop);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET /workshops/1
    @GetMapping("/workshops/{id}")
    public Workshop getWorkshopById(@PathVariable int id) {
        return workshopService.getWorkshopById(id);
    }

    // ---------------- Participants ----------------

    // POST /workshops/1/participants
    @PostMapping("/workshops/{id}/participants")
    public ResponseEntity<Participant> addParticipant(
            @PathVariable int id,
            @Valid @RequestBody Participant participant) {
        Participant saved = workshopService.addParticipant(id, participant);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET /workshops/1/participants
    @GetMapping("/workshops/{id}/participants")
    public List<Participant> getParticipants(@PathVariable int id) {
        return workshopService.getParticipantsByWorkshop(id);
    }

    // GET /participants/1   (participant details)
    @GetMapping("/participants/{id}")
    public Participant getParticipantById(@PathVariable int id) {
        return workshopService.getParticipantById(id);
    }

    // ---------------- Attendance and eligibility ----------------

    // PUT /participants/1/attendance
    @PutMapping("/participants/{id}/attendance")
    public Participant updateAttendance(
            @PathVariable int id,
            @Valid @RequestBody AttendanceRequest request) {
        return workshopService.updateAttendance(id, request.getSessionsAttended());
    }

    // GET /participants/1/eligibility
    @GetMapping("/participants/{id}/eligibility")
    public EligibilityResponse checkEligibility(@PathVariable int id) {
        return workshopService.checkEligibility(id);
    }

    // ---------------- Optional extension ----------------

    // GET /workshops/1/summary
    @GetMapping("/workshops/{id}/summary")
    public WorkshopSummary getWorkshopSummary(@PathVariable int id) {
        return workshopService.getWorkshopSummary(id);
    }
}
