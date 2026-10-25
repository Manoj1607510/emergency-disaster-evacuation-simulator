package com.evacuation.disaster;

import com.evacuation.graph.CityGraph;
import com.evacuation.model.Location;
import com.evacuation.model.Road;
import com.evacuation.model.RoadCondition;
import com.evacuation.model.TrafficLevel;
import com.evacuation.simulation.SimulationEventLog;
import com.evacuation.simulation.SimulationEventType;

import java.util.ArrayList;
import java.util.List;

public class DisasterManager {

    private final List<Disaster> activeDisasters;
    private SimulationEventLog eventLog;

    public DisasterManager() {
        activeDisasters = new ArrayList<>();
        eventLog = new SimulationEventLog();
    }

    public void addDisaster(
            Disaster disaster,
            CityGraph graph
    ) {
        if (disaster == null) {
            throw new IllegalArgumentException(
                    "Disaster cannot be null"
            );
        }

        if (graph == null) {
            throw new IllegalArgumentException(
                    "City graph cannot be null"
            );
        }

        activeDisasters.add(disaster);
        applyDisasterImpact(
                disaster,
                graph
        );
    }

    private void applyDisasterImpact(
            Disaster disaster,
            CityGraph graph
    ) {
        Location disasterLocation =
                disaster.getLocation();

        for (Location location :
                graph.getAllLocations()) {

            double distance =
                    calculateDistance(
                            disasterLocation,
                            location
                    );

            if (distance > disaster.getRadius()) {
                continue;
            }

            double impactRatio =
                    1.0
                            - (distance
                            / disaster.getRadius());

            for (Road road :
                    graph.getRoadsFrom(
                            location.getId()
                    )) {

                applyRoadImpact(
                        road,
                        impactRatio,
                        disaster.getSeverity()
                );
            }
        }
    }

    private void applyRoadImpact(
            Road road,
            double impactRatio,
            int severity
    ) {
        if (impactRatio >= 0.75
                && severity >= 8) {

            if (!road.isBlocked()) {

                road.setCondition(
                        RoadCondition.BLOCKED
                );

                eventLog.addEvent(
                        SimulationEventType.ROAD_BLOCKED,
                        road.getId()
                                + ": "
                                + road.getFrom().getName()
                                + " → "
                                + road.getTo().getName()
                );
            }

            return;
        }

        if (impactRatio >= 0.5
                && severity >= 6) {

            if (road.getCondition()
                    != RoadCondition.DAMAGED) {

                road.setCondition(
                        RoadCondition.DAMAGED
                );

                road.setTrafficLevel(
                        TrafficLevel.SEVERE
                );

                eventLog.addEvent(
                        SimulationEventType.ROAD_DAMAGED,
                        road.getId()
                                + ": "
                                + road.getFrom().getName()
                                + " → "
                                + road.getTo().getName()
                );
            }

            return;
        }

        if (impactRatio >= 0.25) {

            if (road.getTrafficLevel()
                    != TrafficLevel.HIGH
                    && road.getTrafficLevel()
                    != TrafficLevel.SEVERE) {

                road.setTrafficLevel(
                        TrafficLevel.HIGH
                );
            }
        }
    }

    private double calculateDistance(
            Location first,
            Location second
    ) {
        double dx =
                first.getX() - second.getX();

        double dy =
                first.getY() - second.getY();

        return Math.sqrt(
                dx * dx + dy * dy
        );
    }

    public List<Disaster> getActiveDisasters() {
        return List.copyOf(
                activeDisasters
        );
    }

    public int getActiveDisasterCount() {
        return activeDisasters.size();
    }

    public SimulationEventLog getEventLog() {
        return eventLog;
    }

    public void setEventLog(
            SimulationEventLog eventLog
    ) {
        if (eventLog == null) {
            throw new IllegalArgumentException(
                    "Event log cannot be null"
            );
        }

        this.eventLog = eventLog;
    }

    public void clearDisasters(
            CityGraph graph
    ) {
        if (graph == null) {
            throw new IllegalArgumentException(
                    "City graph cannot be null"
            );
        }

        activeDisasters.clear();
        graph.resetRoads();
        eventLog.clear();
    }
}