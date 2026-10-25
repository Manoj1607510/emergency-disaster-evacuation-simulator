package com.evacuation.service;

import com.evacuation.graph.CityGraph;
import com.evacuation.model.Location;
import com.evacuation.model.Person;
import com.evacuation.model.Shelter;

import java.util.ArrayList;
import java.util.List;

public class CityPopulationBuilder {

    public static List<Person> createDefaultPopulation(
            CityGraph city
    ) {
        List<Person> people = new ArrayList<>();

        Location northwood = city.getLocation("L01");
        Location northSchool = city.getLocation("L02");
        Location centralMall = city.getLocation("L05");
        Location business = city.getLocation("L06");
        Location centralPolice = city.getLocation("L08");
        Location riversideHomes = city.getLocation("L10");
        Location riversideSchool = city.getLocation("L11");
        Location eastIndustrial = city.getLocation("L13");
        Location eastPolice = city.getLocation("L15");
        Location southResidential = city.getLocation("L16");
        Location southMedical = city.getLocation("L17");

        people.add(new Person("P001", "Resident 001", northwood, 5.0));
        people.add(new Person("P002", "Resident 002", northwood, 4.5));
        people.add(new Person("P003", "Resident 003", northwood, 5.5));
        people.add(new Person("P004", "Student 001", northSchool, 4.5));
        people.add(new Person("P005", "Student 002", northSchool, 5.0));
        people.add(new Person("P006", "Visitor 001", centralMall, 5.5));
        people.add(new Person("P007", "Visitor 002", centralMall, 4.5));
        people.add(new Person("P008", "Worker 001", business, 5.0));
        people.add(new Person("P009", "Worker 002", business, 4.5));
        people.add(new Person("P010", "Worker 003", business, 5.5));
        people.add(new Person("P011", "Resident 004", centralPolice, 5.0));
        people.add(new Person("P012", "Resident 005", riversideHomes, 4.0));
        people.add(new Person("P013", "Resident 006", riversideHomes, 5.0));
        people.add(new Person("P014", "Student 003", riversideSchool, 4.5));
        people.add(new Person("P015", "Student 004", riversideSchool, 5.0));
        people.add(new Person("P016", "Worker 004", eastIndustrial, 4.0));
        people.add(new Person("P017", "Worker 005", eastIndustrial, 4.5));
        people.add(new Person("P018", "Officer 001", eastPolice, 5.0));
        people.add(new Person("P019", "Resident 007", southResidential, 4.5));
        people.add(new Person("P020", "Resident 008", southResidential, 5.0));
        people.add(new Person("P021", "Patient 001", southMedical, 3.5));
        people.add(new Person("P022", "Patient 002", southMedical, 4.0));

        return people;
    }

    public static List<Shelter> createDefaultShelters(
            CityGraph city
    ) {
        List<Shelter> shelters = new ArrayList<>();

        Location centralShelterLocation =
                city.getLocation("L18");

        Location southShelterLocation =
                city.getLocation("L19");

        Location northShelterLocation =
                city.getLocation("L20");

        shelters.add(
                new Shelter(
                        "S01",
                        "Safe Haven Central",
                        centralShelterLocation,
                        500
                )
        );

        shelters.add(
                new Shelter(
                        "S02",
                        "Safe Haven South",
                        southShelterLocation,
                        400
                )
        );

        shelters.add(
                new Shelter(
                        "S03",
                        "Safe Haven North",
                        northShelterLocation,
                        300
                )
        );

        return shelters;
    }
}