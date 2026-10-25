package com.evacuation.algorithm;

import com.evacuation.graph.CityGraph;
import com.evacuation.model.Location;
import com.evacuation.model.Road;
import com.evacuation.model.RouteResult;

import java.util.*;

public class BFS {

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

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();

        queue.add(startId);
        visited.add(startId);

        int nodesExplored = 0;

        while (!queue.isEmpty()) {

            String currentId = queue.poll();
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

                visited.add(neighborId);
                previous.put(neighborId, currentId);
                queue.add(neighborId);
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
                nodesExplored
        );
    }
}