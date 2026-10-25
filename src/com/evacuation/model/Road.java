package com.evacuation.model;

public class Road {

    private final String id;
    private final Location from;
    private final Location to;

    private final double distance;
    private final RoadType type;

    private final TrafficLevel originalTrafficLevel;
    private final RoadCondition originalCondition;

    private TrafficLevel trafficLevel;
    private RoadCondition condition;

    private final boolean bidirectional;

    public Road(
            String id,
            Location from,
            Location to,
            double distance,
            RoadType type,
            TrafficLevel trafficLevel,
            RoadCondition condition,
            boolean bidirectional
    ) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Road ID cannot be empty"
            );
        }

        if (from == null || to == null) {
            throw new IllegalArgumentException(
                    "Road locations cannot be null"
            );
        }

        if (distance <= 0) {
            throw new IllegalArgumentException(
                    "Road distance must be greater than zero"
            );
        }

        if (type == null) {
            throw new IllegalArgumentException(
                    "Road type cannot be null"
            );
        }

        if (trafficLevel == null) {
            throw new IllegalArgumentException(
                    "Traffic level cannot be null"
            );
        }

        if (condition == null) {
            throw new IllegalArgumentException(
                    "Road condition cannot be null"
            );
        }

        this.id = id;
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.type = type;
        this.originalTrafficLevel = trafficLevel;
        this.originalCondition = condition;
        this.trafficLevel = trafficLevel;
        this.condition = condition;
        this.bidirectional = bidirectional;
    }

    public String getId() {
        return id;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public double getDistance() {
        return distance;
    }

    public RoadType getType() {
        return type;
    }

    public TrafficLevel getTrafficLevel() {
        return trafficLevel;
    }

    public RoadCondition getCondition() {
        return condition;
    }

    public TrafficLevel getOriginalTrafficLevel() {
        return originalTrafficLevel;
    }

    public RoadCondition getOriginalCondition() {
        return originalCondition;
    }

    public boolean isBidirectional() {
        return bidirectional;
    }

    public void setTrafficLevel(
            TrafficLevel trafficLevel
    ) {
        if (trafficLevel == null) {
            throw new IllegalArgumentException(
                    "Traffic level cannot be null"
            );
        }

        this.trafficLevel = trafficLevel;
    }

    public void setCondition(
            RoadCondition condition
    ) {
        if (condition == null) {
            throw new IllegalArgumentException(
                    "Road condition cannot be null"
            );
        }

        this.condition = condition;
    }

    public void reset() {
        trafficLevel = originalTrafficLevel;
        condition = originalCondition;
    }

    public boolean isBlocked() {
        return condition == RoadCondition.BLOCKED;
    }

    public boolean isUsable() {
        return condition != RoadCondition.BLOCKED;
    }

    public double getTrafficMultiplier() {
        return switch (trafficLevel) {
            case LOW -> 1.0;
            case MEDIUM -> 1.25;
            case HIGH -> 1.75;
            case SEVERE -> 2.5;
        };
    }

    public double getEffectiveCost() {

        double cost =
                distance * getTrafficMultiplier();

        if (condition == RoadCondition.DAMAGED) {
            cost *= 2.0;
        }

        if (condition == RoadCondition.BLOCKED) {
            return Double.POSITIVE_INFINITY;
        }

        return cost;
    }

    @Override
    public String toString() {
        return from.getName()
                + " → "
                + to.getName()
                + " | "
                + distance
                + " km | "
                + type
                + " | "
                + trafficLevel
                + " | "
                + condition;
    }
}