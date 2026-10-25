package com.evacuation.graph;

import com.evacuation.model.Location;
import com.evacuation.model.Road;

import java.util.*;

public class CityGraph {

    private final Map<String, Location> locations;
    private final Map<String, List<Road>> adjacencyList;

    public CityGraph() {
        locations = new HashMap<>();
        adjacencyList = new HashMap<>();
    }

    public void addLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException(
                    "Location cannot be null"
            );
        }

        if (locations.containsKey(location.getId())) {
            throw new IllegalArgumentException(
                    "Location already exists: "
                            + location.getId()
            );
        }

        locations.put(
                location.getId(),
                location
        );

        adjacencyList.put(
                location.getId(),
                new ArrayList<>()
        );
    }

    public void addRoad(Road road) {
        if (road == null) {
            throw new IllegalArgumentException(
                    "Road cannot be null"
            );
        }

        Location from = road.getFrom();
        Location to = road.getTo();

        if (!locations.containsKey(from.getId())
                || !locations.containsKey(to.getId())) {

            throw new IllegalArgumentException(
                    "Both locations must be added before adding a road"
            );
        }

        adjacencyList
                .get(from.getId())
                .add(road);

        if (road.isBidirectional()) {
            adjacencyList
                    .get(to.getId())
                    .add(road);
        }
    }

    public Location getLocation(String id) {
        return locations.get(id);
    }

    public List<Road> getRoadsFrom(
            String locationId
    ) {
        return Collections.unmodifiableList(
                adjacencyList.getOrDefault(
                        locationId,
                        Collections.emptyList()
                )
        );
    }

    public Collection<Location> getAllLocations() {
        return Collections.unmodifiableCollection(
                locations.values()
        );
    }

    public int getLocationCount() {
        return locations.size();
    }

    public int getRoadCount() {

        Set<String> roadIds =
                new HashSet<>();

        for (List<Road> roads :
                adjacencyList.values()) {

            for (Road road : roads) {
                roadIds.add(
                        road.getId()
                );
            }
        }

        return roadIds.size();
    }

    public boolean containsLocation(
            String locationId
    ) {
        return locations.containsKey(locationId);
    }

    public boolean hasRoad(
            String fromId,
            String toId
    ) {
        return getRoadsFrom(fromId)
                .stream()
                .anyMatch(
                        road -> road.getTo()
                                .getId()
                                .equals(toId)
                );
    }

    public void resetRoads() {

        Set<String> roadIds =
                new HashSet<>();

        for (List<Road> roads :
                adjacencyList.values()) {

            for (Road road : roads) {

                if (roadIds.add(
                        road.getId()
                )) {
                    road.reset();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "CityGraph{" +
                "locations=" + locations.size() +
                ", roads=" + getRoadCount() +
                '}';
    }
}