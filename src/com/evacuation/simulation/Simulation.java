package com.evacuation.simulation;

import com.evacuation.graph.CityGraph;
import com.evacuation.model.Person;
import com.evacuation.model.Shelter;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private final CityGraph city;
    private final List<Person> people;
    private final List<Shelter> shelters;

    private SimulationStatus status;
    private int elapsedTime;

    public Simulation(
            CityGraph city,
            List<Person> people,
            List<Shelter> shelters
    ) {
        if (city == null) {
            throw new IllegalArgumentException(
                    "City graph cannot be null"
            );
        }

        if (people == null) {
            throw new IllegalArgumentException(
                    "People list cannot be null"
            );
        }

        if (shelters == null) {
            throw new IllegalArgumentException(
                    "Shelters list cannot be null"
            );
        }

        this.city = city;
        this.people = new ArrayList<>(people);
        this.shelters = new ArrayList<>(shelters);
        this.status = SimulationStatus.READY;
        this.elapsedTime = 0;
    }

    public CityGraph getCity() {
        return city;
    }

    public List<Person> getPeople() {
        return List.copyOf(people);
    }

    public List<Shelter> getShelters() {
        return List.copyOf(shelters);
    }

    public SimulationStatus getStatus() {
        return status;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void start() {
        if (status == SimulationStatus.READY
                || status == SimulationStatus.PAUSED) {

            status = SimulationStatus.RUNNING;
        }
    }

    public void pause() {
        if (status == SimulationStatus.RUNNING) {
            status = SimulationStatus.PAUSED;
        }
    }

    public void stop() {
        status = SimulationStatus.STOPPED;
    }

    public void complete() {
        status = SimulationStatus.COMPLETED;
    }

    public void advanceTime() {
        if (status == SimulationStatus.RUNNING) {
            elapsedTime++;
        }
    }

    public int getTotalPopulation() {
        return people.size();
    }

    public long getEvacuatedCount() {
        return people.stream()
                .filter(Person::isEvacuated)
                .count();
    }

    public long getAtRiskCount() {
        return people.stream()
                .filter(Person::isAtRisk)
                .count();
    }

    public double getEvacuationPercentage() {

        if (people.isEmpty()) {
            return 100.0;
        }

        return (getEvacuatedCount() * 100.0)
                / people.size();
    }
}