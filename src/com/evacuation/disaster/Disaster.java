package com.evacuation.disaster;

import com.evacuation.model.Location;

public class Disaster {

    private final String id;
    private final String name;
    private final DisasterType type;
    private final Location location;
    private final double radius;
    private final int severity;

    public Disaster(
            String id,
            String name,
            DisasterType type,
            Location location,
            double radius,
            int severity
    ) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Disaster ID cannot be empty");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Disaster name cannot be empty");
        }

        if (type == null) {
            throw new IllegalArgumentException("Disaster type cannot be null");
        }

        if (location == null) {
            throw new IllegalArgumentException("Disaster location cannot be null");
        }

        if (radius <= 0) {
            throw new IllegalArgumentException("Disaster radius must be greater than zero");
        }

        if (severity < 1 || severity > 10) {
            throw new IllegalArgumentException("Severity must be between 1 and 10");
        }

        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.radius = radius;
        this.severity = severity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DisasterType getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public double getRadius() {
        return radius;
    }

    public int getSeverity() {
        return severity;
    }

    @Override
    public String toString() {
        return name
                + " | "
                + type
                + " | Severity: "
                + severity
                + " | Radius: "
                + radius;
    }
}