package com.evacuation.simulation;

import com.evacuation.algorithm.Dijkstra;
import com.evacuation.graph.CityGraph;
import com.evacuation.model.Location;
import com.evacuation.model.Person;
import com.evacuation.model.PersonStatus;
import com.evacuation.model.Road;
import com.evacuation.model.RouteResult;
import com.evacuation.model.Shelter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvacuationEngine {

    private final Dijkstra dijkstra;
    private final Map<String, PersonRoute> routes;
    private final Map<String, Shelter> assignedShelters;
    private final SimulationEventLog eventLog;

    public EvacuationEngine() {
        dijkstra = new Dijkstra();
        routes = new HashMap<>();
        assignedShelters = new HashMap<>();
        eventLog = new SimulationEventLog();
    }

    public void assignRoutes(
            CityGraph city,
            List<Person> people,
            List<Shelter> shelters
    ) {
        routes.clear();
        assignedShelters.clear();

        eventLog.addEvent(
                SimulationEventType.SIMULATION_STARTED,
                "Evacuation routes assigned for "
                        + people.size()
                        + " people"
        );

        for (Person person : people) {

            Shelter shelter = findNearestShelter(
                    city,
                    person.getCurrentLocation(),
                    shelters
            );

            if (shelter == null) {
                person.setStatus(PersonStatus.TRAPPED);

                eventLog.addEvent(
                        SimulationEventType.PERSON_TRAPPED,
                        person.getName()
                                + " has no reachable shelter"
                );

                continue;
            }

            RouteResult route =
                    dijkstra.findShortestPath(
                            city,
                            person.getCurrentLocation().getId(),
                            shelter.getLocation().getId()
                    );

            if (!route.isReachable()) {
                person.setStatus(PersonStatus.TRAPPED);

                eventLog.addEvent(
                        SimulationEventType.PERSON_TRAPPED,
                        person.getName()
                                + " has no available evacuation route"
                );

                continue;
            }

            person.setDestination(
                    shelter.getLocation()
            );

            person.setStatus(
                    PersonStatus.EVACUATING
            );

            PersonRoute personRoute =
                    new PersonRoute(
                            person,
                            route
                    );

            personRoute.startMoving();

            routes.put(
                    person.getId(),
                    personRoute
            );

            assignedShelters.put(
                    person.getId(),
                    shelter
            );
        }
    }

    public void advance(
            CityGraph city,
            double timeStep
    ) {
        for (PersonRoute personRoute :
                routes.values()) {

            if (personRoute.hasArrived()) {
                continue;
            }

            if (personRoute.getState()
                    == EvacuationState.STRANDED) {
                continue;
            }

            if (!personRoute.hasNextLocation()) {
                completeEvacuation(personRoute);
                continue;
            }

            Location current =
                    personRoute.getCurrentLocation();

            Location next =
                    personRoute.getNextLocation();

            Road road =
                    findRoad(
                            city,
                            current,
                            next
                    );

            if (road == null || road.isBlocked()) {

                if (!recalculateRoute(
                        city,
                        personRoute
                )) {
                    personRoute.setState(
                            EvacuationState.STRANDED
                    );

                    personRoute.getPerson().setStatus(
                            PersonStatus.TRAPPED
                    );

                    eventLog.addEvent(
                            SimulationEventType.PERSON_TRAPPED,
                            personRoute.getPerson().getName()
                                    + " became trapped because no alternate route exists"
                    );
                }

                continue;
            }

            double movement =
                    calculateMovement(
                            personRoute.getPerson(),
                            road,
                            timeStep
                    );

            personRoute.addProgress(movement);

            if (personRoute.getProgress()
                    >= road.getEffectiveCost()) {

                personRoute.moveToNextLocation();

                personRoute.getPerson()
                        .setCurrentLocation(next);

                if (personRoute.hasArrived()) {
                    completeEvacuation(personRoute);
                }
            }
        }
    }

    private boolean recalculateRoute(
            CityGraph city,
            PersonRoute personRoute
    ) {
        Person person =
                personRoute.getPerson();

        Shelter shelter =
                assignedShelters.get(
                        person.getId()
                );

        if (shelter == null) {
            return false;
        }

        RouteResult newRoute =
                dijkstra.findShortestPath(
                        city,
                        person.getCurrentLocation().getId(),
                        shelter.getLocation().getId()
                );

        if (!newRoute.isReachable()) {
            return false;
        }

        personRoute.replaceRoute(
                newRoute
        );

        personRoute.startMoving();

        eventLog.addEvent(
                SimulationEventType.ROUTE_RECALCULATED,
                person.getName()
                        + " received a new route to "
                        + shelter.getName()
        );

        return true;
    }

    private double calculateMovement(
            Person person,
            Road road,
            double timeStep
    ) {
        double speed =
                person.getWalkingSpeed();

        double trafficFactor =
                road.getTrafficMultiplier();

        return (speed / trafficFactor)
                * timeStep;
    }

    private Road findRoad(
            CityGraph city,
            Location from,
            Location to
    ) {
        for (Road road :
                city.getRoadsFrom(from.getId())) {

            if (road.getTo().getId()
                    .equals(to.getId())) {

                return road;
            }
        }

        return null;
    }

    private void completeEvacuation(
            PersonRoute personRoute
    ) {
        Person person =
                personRoute.getPerson();

        Shelter shelter =
                assignedShelters.get(
                        person.getId()
                );

        if (shelter == null) {
            personRoute.setState(
                    EvacuationState.STRANDED
            );

            person.setStatus(
                    PersonStatus.TRAPPED
            );

            return;
        }

        if (!shelter.admitPerson()) {

            personRoute.setState(
                    EvacuationState.STRANDED
            );

            person.setStatus(
                    PersonStatus.TRAPPED
            );

            eventLog.addEvent(
                    SimulationEventType.SHELTER_FULL,
                    shelter.getName()
                            + " is full and cannot admit "
                            + person.getName()
            );

            eventLog.addEvent(
                    SimulationEventType.PERSON_TRAPPED,
                    person.getName()
                            + " could not enter "
                            + shelter.getName()
            );

            return;
        }

        person.setCurrentLocation(
                shelter.getLocation()
        );

        person.setStatus(
                PersonStatus.EVACUATED
        );

        eventLog.addEvent(
                SimulationEventType.PERSON_EVACUATED,
                person.getName()
                        + " reached "
                        + shelter.getName()
        );
    }

    private Shelter findNearestShelter(
            CityGraph city,
            Location currentLocation,
            List<Shelter> shelters
    ) {
        Shelter bestShelter = null;
        double bestCost =
                Double.POSITIVE_INFINITY;

        for (Shelter shelter : shelters) {

            if (!shelter.hasSpace()) {
                continue;
            }

            RouteResult route =
                    dijkstra.findShortestPath(
                            city,
                            currentLocation.getId(),
                            shelter.getLocation().getId()
                    );

            if (!route.isReachable()) {
                continue;
            }

            if (route.getTotalCost() < bestCost) {
                bestCost =
                        route.getTotalCost();

                bestShelter = shelter;
            }
        }

        return bestShelter;
    }

    public PersonRoute getPersonRoute(
            String personId
    ) {
        return routes.get(personId);
    }

    public Map<String, PersonRoute> getAllRoutes() {
        return Map.copyOf(routes);
    }

    public SimulationEventLog getEventLog() {
        return eventLog;
    }

    public void clearRoutes() {
        routes.clear();
        assignedShelters.clear();
        eventLog.clear();
    }
}