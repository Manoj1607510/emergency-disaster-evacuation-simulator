package com.evacuation.model;

import java.util.Collections;
import java.util.List;

public class RouteResult {

    private final List<Location> path;
    private final double totalCost;
    private final boolean reachable;
    private final int nodesExplored;

    public RouteResult(
            List<Location> path,
            double totalCost,
            boolean reachable,
            int nodesExplored
    ) {
        this.path = List.copyOf(path);
        this.totalCost = totalCost;
        this.reachable = reachable;
        this.nodesExplored = nodesExplored;
    }

    public List<Location> getPath() {
        return Collections.unmodifiableList(path);
    }

    public double getTotalCost() {
        return totalCost;
    }

    public boolean isReachable() {
        return reachable;
    }

    public int getNodesExplored() {
        return nodesExplored;
    }

    public int getPathLength() {
        return path.size();
    }

    @Override
    public String toString() {
        if (!reachable) {
            return "No route available";
        }

        return "RouteResult{" +
                "path=" + path +
                ", totalCost=" + totalCost +
                ", nodesExplored=" + nodesExplored +
                '}';
    }
}