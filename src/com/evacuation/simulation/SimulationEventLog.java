package com.evacuation.simulation;

import java.util.ArrayList;
import java.util.List;

public class SimulationEventLog {

    private final List<SimulationEvent> events;

    public SimulationEventLog() {
        events = new ArrayList<>();
    }

    public void addEvent(
            SimulationEventType type,
            String message
    ) {
        events.add(
                new SimulationEvent(
                        type,
                        message
                )
        );
    }

    public List<SimulationEvent> getEvents() {
        return List.copyOf(events);
    }

    public int getEventCount() {
        return events.size();
    }

    public void clear() {
        events.clear();
    }
}