package com.evacuation.algorithm;

import com.evacuation.graph.CityGraph;
import com.evacuation.model.Location;
import com.evacuation.model.Road;
import com.evacuation.model.RouteResult;

import java.util.*;

public class Dijkstra {

    public RouteResult findShortestPath(
            CityGraph graph,
            String startId,
            String destinationId
    ) {
        if (!graph.containsLocation(startId)
                || !graph.containsLocation(destinationId)) {
            return new RouteResult(
                    Collections.emptyList(),
                    Double.POSITIVE_INFINITY,
                    false,
                    0
            );
        }

        if (startId.equals(destinationId)) {
            Location start = graph.getLocation(startId);

            return new RouteResult(
                    List.of(start),
                    0,
                    true,
                    1
            );
        }

        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();

        for (Location location : graph.getAllLocations()) {
            distances.put(
                    location.getId(),
                    Double.POSITIVE_INFINITY
            );
        }

        distances.put(startId, 0.0);

        PriorityQueue<Node> queue = new PriorityQueue<>(
                Comparator.comparingDouble(Node::getDistance)
        );

        queue.add(new Node(startId, 0));

        Set<String> visited = new HashSet<>();

        int nodesExplored = 0;

        while (!queue.isEmpty()) {

            Node current = queue.poll();

            String currentId = current.getLocationId();

            if (visited.contains(currentId)) {
                continue;
            }

            visited.add(currentId);
            nodesExplored++;

            if (currentId.equals(destinationId)) {
                break;
            }

            for (Road road : graph.getRoadsFrom(currentId)) {

                if (!road.isUsable()) {
                    continue;
                }

                String neighborId = road.getTo().getId();

                if (visited.contains(neighborId)) {
                    continue;
                }

                double newDistance =
                        distances.get(currentId)
                                + road.getEffectiveCost();

                if (newDistance < distances.get(neighborId)) {

                    distances.put(
                            neighborId,
                            newDistance
                    );

                    previous.put(
                            neighborId,
                            currentId
                    );

                    queue.add(
                            new Node(
                                    neighborId,
                                    newDistance
                            )
                    );
                }
            }
        }

        if (!visited.contains(destinationId)) {
            return new RouteResult(
                    Collections.emptyList(),
                    Double.POSITIVE_INFINITY,
                    false,
                    nodesExplored
            );
        }

        List<Location> path = new ArrayList<>();

        String currentId = destinationId;

        while (currentId != null) {

            path.add(
                    graph.getLocation(currentId)
            );

            currentId = previous.get(currentId);
        }

        Collections.reverse(path);

        return new RouteResult(
                path,
                distances.get(destinationId),
                true,
                nodesExplored
        );
    }

    private static class Node {

        private final String locationId;
        private final double distance;

        public Node(
                String locationId,
                double distance
        ) {
            this.locationId = locationId;
            this.distance = distance;
        }

        public String getLocationId() {
            return locationId;
        }

        public double getDistance() {
            return distance;
        }
    }
}