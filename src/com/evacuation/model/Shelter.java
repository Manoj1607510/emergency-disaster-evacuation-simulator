package com.evacuation.model;

public class Shelter {

    private final String id;
    private final String name;
    private final Location location;

    private final int capacity;
    private int currentOccupancy;

    private boolean operational;

    public Shelter(
            String id,
            String name,
            Location location,
            int capacity
    ) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.currentOccupancy = 0;
        this.operational = true;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    public boolean isOperational() {
        return operational;
    }

    public int getAvailableCapacity() {
        return capacity - currentOccupancy;
    }

    public boolean hasSpace() {
        return operational && currentOccupancy < capacity;
    }

    public boolean admitPerson() {
        if (!hasSpace()) {
            return false;
        }

        currentOccupancy++;
        return true;
    }

    public void releasePerson() {
        if (currentOccupancy > 0) {
            currentOccupancy--;
        }
    }

    public void setOperational(boolean operational) {
        this.operational = operational;
    }

    @Override
    public String toString() {
        return name
                + " [" + currentOccupancy
                + "/" + capacity + "]";
    }
}