package com.evacuation.simulation;

import com.evacuation.model.Location;
import com.evacuation.model.Person;
import com.evacuation.model.RouteResult;

public class PersonRoute {

    private final Person person;

    private RouteResult route;

    private int currentIndex;
    private double progress;
    private EvacuationState state;

    public PersonRoute(
            Person person,
            RouteResult route
    ) {
        this.person = person;
        this.route = route;
        this.currentIndex = 0;
        this.progress = 0.0;
        this.state = EvacuationState.WAITING;
    }

    public Person getPerson() {
        return person;
    }

    public RouteResult getRoute() {
        return route;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public double getProgress() {
        return progress;
    }

    public EvacuationState getState() {
        return state;
    }

    public Location getCurrentLocation() {
        return route.getPath().get(currentIndex);
    }

    public Location getNextLocation() {

        if (currentIndex >= route.getPath().size() - 1) {
            return getCurrentLocation();
        }

        return route.getPath().get(currentIndex + 1);
    }

    public boolean hasNextLocation() {
        return currentIndex < route.getPath().size() - 1;
    }

    public boolean hasArrived() {
        return state == EvacuationState.ARRIVED;
    }

    public void startMoving() {
        if (state == EvacuationState.WAITING
                || state == EvacuationState.STRANDED) {
            state = EvacuationState.MOVING;
        }
    }

    public void addProgress(double amount) {
        progress += amount;
    }

    public void moveToNextLocation() {

        currentIndex++;
        progress = 0.0;

        if (!hasNextLocation()) {
            state = EvacuationState.ARRIVED;
        }
    }

    public void replaceRoute(RouteResult newRoute) {

        if (newRoute == null || !newRoute.isReachable()) {
            throw new IllegalArgumentException(
                    "New route must be reachable"
            );
        }

        this.route = newRoute;
        this.currentIndex = 0;
        this.progress = 0.0;
        this.state = EvacuationState.WAITING;
    }

    public void setState(EvacuationState state) {
        this.state = state;
    }
}