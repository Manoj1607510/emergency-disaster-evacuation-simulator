package com.evacuation.model;

public class Person {

    private final String id;
    private final String name;

    private Location currentLocation;
    private final Location startingLocation;
    private Location destination;

    private PersonStatus status;

    private final double walkingSpeed;

    public Person(
            String id,
            String name,
            Location startingLocation,
            double walkingSpeed
    ) {
        this.id = id;
        this.name = name;
        this.startingLocation = startingLocation;
        this.currentLocation = startingLocation;
        this.walkingSpeed = walkingSpeed;
        this.status = PersonStatus.SAFE;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public Location getStartingLocation() {
        return startingLocation;
    }

    public Location getDestination() {
        return destination;
    }

    public PersonStatus getStatus() {
        return status;
    }

    public double getWalkingSpeed() {
        return walkingSpeed;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public void setStatus(PersonStatus status) {
        this.status = status;
    }

    public boolean isEvacuated() {
        return status == PersonStatus.EVACUATED;
    }

    public boolean isAtRisk() {
        return status == PersonStatus.EVACUATING
                || status == PersonStatus.TRAPPED
                || status == PersonStatus.INJURED;
    }

    @Override
    public String toString() {
        return id + " - " + name
                + " [" + status + "]";
    }
}