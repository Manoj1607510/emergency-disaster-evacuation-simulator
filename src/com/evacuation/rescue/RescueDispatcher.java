package com.evacuation.rescue;

import com.evacuation.algorithm.Dijkstra;
import com.evacuation.graph.CityGraph;
import com.evacuation.model.*;

import java.util.ArrayList;
import java.util.List;

public class RescueDispatcher {

    private final RescueManager rescueManager;
    private final Dijkstra dijkstra;

    public RescueDispatcher() {
        rescueManager = new RescueManager();
        dijkstra = new Dijkstra();
    }

    public void addRequest(RescueRequest request) {
        rescueManager.addRequest(request);
    }

    public DispatchResult dispatchNext(
            CityGraph graph,
            List<Vehicle> vehicles
    ) {
        RescueRequest request =
                rescueManager.processNextRequest();

        if (request == null) {
            return null;
        }

        Vehicle vehicle = findAvailableVehicle(vehicles);

        if (vehicle == null) {
            rescueManager.addRequest(request);
            return null;
        }

        RouteResult route = dijkstra.findShortestPath(
                graph,
                vehicle.getCurrentLocation().getId(),
                request.getLocation().getId()
        );

        if (!route.isReachable()) {
            rescueManager.addRequest(request);
            return null;
        }

        vehicle.setAvailable(false);

        vehicle.setCurrentLocation(
                request.getLocation()
        );

        return new DispatchResult(
                request,
                vehicle,
                route
        );
    }

    private Vehicle findAvailableVehicle(
            List<Vehicle> vehicles
    ) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable()) {
                return vehicle;
            }
        }

        return null;
    }

    public int getQueueSize() {
        return rescueManager.getQueueSize();
    }

    public boolean hasPendingRequests() {
        return !rescueManager.isEmpty();
    }

    public static class DispatchResult {

        private final RescueRequest request;
        private final Vehicle vehicle;
        private final RouteResult route;

        public DispatchResult(
                RescueRequest request,
                Vehicle vehicle,
                RouteResult route
        ) {
            this.request = request;
            this.vehicle = vehicle;
            this.route = route;
        }

        public RescueRequest getRequest() {
            return request;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public RouteResult getRoute() {
            return route;
        }
    }
}