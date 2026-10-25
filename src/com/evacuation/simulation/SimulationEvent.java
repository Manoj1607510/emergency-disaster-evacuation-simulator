package com.evacuation.simulation;

import java.time.LocalDateTime;

public class SimulationEvent {

    private final LocalDateTime timestamp;
    private final SimulationEventType type;
    private final String message;

    public SimulationEvent(
            SimulationEventType type,
            String message
    ) {
        if (type == null) {
            throw new IllegalArgumentException(
                    "Event type cannot be null"
            );
        }

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException(
                    "Event message cannot be empty"
            );
        }

        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public SimulationEventType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "["
                + type
                + "] "
                + message;
    }
}