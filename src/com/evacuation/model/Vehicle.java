package com.evacuation.model;

public class Vehicle {

    private final String id;
    private final VehicleType type;
    private final Location baseLocation;

    private Location currentLocation;

    private boolean available;

    public Vehicle(
            String id,
            VehicleType type,
            Location baseLocation
    ) {
        this.id = id;
        this.type = type;
        this.baseLocation = baseLocation;
        this.currentLocation = baseLocation;
        this.available = true;
    }

    public String getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }

    public Location getBaseLocation() {
        return baseLocation;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return id + " - " + type
                + " [" + (available ? "AVAILABLE" : "BUSY") + "]";
    }
}
