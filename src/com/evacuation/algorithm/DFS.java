package com.evacuation.algorithm;

import com.evacuation.graph.CityGraph;
import com.evacuation.model.Location;
import com.evacuation.model.Road;
import com.evacuation.model.RouteResult;

import java.util.*;

public class DFS {

    public RouteResult findPath(
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
            return new RouteResult(
                    List.of(graph.getLocation(startId)),
                    0,
                    true,
                    1
            );
        }

        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();

        int[] nodesExplored = {0};

        boolean found = search(
                graph,
                startId,
                destinationId,
                visited,
                previous,
                nodesExplored
        );

        if (!found) {
            return new RouteResult(
                    Collections.emptyList(),
                    Double.POSITIVE_INFINITY,
                    false,
                    nodesExplored[0]
            );
        }

        List<Location> path = new ArrayList<>();

        String currentId = destinationId;

        while (currentId != null) {
            path.add(graph.getLocation(currentId));
            currentId = previous.get(currentId);
        }

        Collections.reverse(path);

        double totalCost = 0;

        for (int i = 0; i < path.size() - 1; i++) {

            String fromId = path.get(i).getId();
            String toId = path.get(i + 1).getId();

            for (Road road : graph.getRoadsFrom(fromId)) {

                if (road.getTo().getId().equals(toId)) {
                    totalCost += road.getEffectiveCost();
                    break;
                }
            }
        }

        return new RouteResult(
                path,
                totalCost,
                true,
                nodesExplored[0]
        );
    }

    private boolean search(
            CityGraph graph,
            String currentId,
            String destinationId,
            Set<String> visited,
            Map<String, String> previous,
            int[] nodesExplored
    ) {
        visited.add(currentId);
        nodesExplored[0]++;

        if (currentId.equals(destinationId)) {
            return true;
        }

        for (Road road : graph.getRoadsFrom(currentId)) {

            if (!road.isUsable()) {
                continue;
            }

            String neighborId = road.getTo().getId();

            if (visited.contains(neighborId)) {
                continue;
            }

            previous.put(neighborId, currentId);

            if (search(
                    graph,
                    neighborId,
                    destinationId,
                    visited,
                    previous,
                    nodesExplored
            )) {
                return true;
            }
        }

        return false;
    }
}