package com.evacuation.model;

public class Location {

    private final String id;
    private final String name;
    private final LocationType type;

    private final double x;
    private final double y;

    public Location(String id, String name, LocationType type, double x, double y) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocationType getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}