package com.evacuation.model;

public class RescueRequest {

    private final String id;
    private final Person person;
    private final Location location;
    private final RescuePriority priority;
    private final String reason;

    public RescueRequest(
            String id,
            Person person,
            Location location,
            RescuePriority priority,
            String reason
    ) {
        this.id = id;
        this.person = person;
        this.location = location;
        this.priority = priority;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public Location getLocation() {
        return location;
    }

    public RescuePriority getPriority() {
        return priority;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return id + " - "
                + person.getName()
                + " - "
                + priority
                + " - "
                + reason;
    }
}