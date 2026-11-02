package com.example.workshopapi;

// Used when a workshop or participant ID is not found (404)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
