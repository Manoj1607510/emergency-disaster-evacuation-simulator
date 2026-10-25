package com.evacuation.service;

import com.evacuation.graph.CityGraph;
import com.evacuation.model.*;

public class CityBuilder {

    public static CityGraph createDefaultCity() {

        CityGraph city = new CityGraph();

        Location northwood = new Location(
                "L01", "Northwood Residences",
                LocationType.RESIDENTIAL, 15, 15
        );

        Location northSchool = new Location(
                "L02", "Northwood School",
                LocationType.SCHOOL, 25, 12
        );

        Location northHospital = new Location(
                "L03", "North General Hospital",
                LocationType.HOSPITAL, 40, 15
        );

        Location northFire = new Location(
                "L04", "North Fire Station",
                LocationType.FIRE_STATION, 50, 25
        );

        Location centralMall = new Location(
                "L05", "Central Mall",
                LocationType.COMMERCIAL, 30, 35
        );

        Location business = new Location(
                "L06", "Business District",
                LocationType.COMMERCIAL, 45, 40
        );

        Location centralJunction = new Location(
                "L07", "Central Junction",
                LocationType.INTERSECTION, 35, 50
        );

        Location centralPolice = new Location(
                "L08", "Central Police Station",
                LocationType.POLICE_STATION, 20, 48
        );

        Location centralPark = new Location(
                "L09", "Central Park",
                LocationType.PARK, 50, 55
        );

        Location riversideHomes = new Location(
                "L10", "Riverside Homes",
                LocationType.RESIDENTIAL, 15, 70
        );

        Location riversideSchool = new Location(
                "L11", "Riverside School",
                LocationType.SCHOOL, 25, 78
        );

        Location riversidePark = new Location(
                "L12", "Riverside Park",
                LocationType.PARK, 40, 72
        );

        Location eastIndustrial = new Location(
                "L13", "East Industrial Zone",
                LocationType.INDUSTRIAL, 75, 35
        );

        Location eastFire = new Location(
                "L14", "East Fire Station",
                LocationType.FIRE_STATION, 85, 45
        );

        Location eastPolice = new Location(
                "L15", "East Police Station",
                LocationType.POLICE_STATION, 78, 60
        );

        Location southResidential = new Location(
                "L16", "South Residential",
                LocationType.RESIDENTIAL, 60, 78
        );

        Location southMedical = new Location(
                "L17", "South Medical Center",
                LocationType.HOSPITAL, 75, 75
        );

        Location safeHavenCentral = new Location(
                "L18", "Safe Haven Central",
                LocationType.SHELTER, 55, 45
        );

        Location safeHavenSouth = new Location(
                "L19", "Safe Haven South",
                LocationType.SHELTER, 65, 90
        );

        Location safeHavenNorth = new Location(
                "L20", "Safe Haven North",
                LocationType.SHELTER, 65, 15
        );

        city.addLocation(northwood);
        city.addLocation(northSchool);
        city.addLocation(northHospital);
        city.addLocation(northFire);
        city.addLocation(centralMall);
        city.addLocation(business);
        city.addLocation(centralJunction);
        city.addLocation(centralPolice);
        city.addLocation(centralPark);
        city.addLocation(riversideHomes);
        city.addLocation(riversideSchool);
        city.addLocation(riversidePark);
        city.addLocation(eastIndustrial);
        city.addLocation(eastFire);
        city.addLocation(eastPolice);
        city.addLocation(southResidential);
        city.addLocation(southMedical);
        city.addLocation(safeHavenCentral);
        city.addLocation(safeHavenSouth);
        city.addLocation(safeHavenNorth);

        city.addRoad(new Road("R01", northwood, northSchool, 1.5, RoadType.RESIDENTIAL_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));
        city.addRoad(new Road("R02", northSchool, northHospital, 2.0, RoadType.MAIN_ROAD, TrafficLevel.HIGH, RoadCondition.OPEN, true));
        city.addRoad(new Road("R03", northHospital, northFire, 1.4, RoadType.MAIN_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));

        city.addRoad(new Road("R04", northSchool, centralMall, 2.8, RoadType.MAIN_ROAD, TrafficLevel.HIGH, RoadCondition.OPEN, true));
        city.addRoad(new Road("R05", northHospital, business, 2.6, RoadType.MAIN_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));
        city.addRoad(new Road("R06", northFire, safeHavenNorth, 2.0, RoadType.SECONDARY_ROAD, TrafficLevel.LOW, RoadCondition.OPEN, true));

        city.addRoad(new Road("R07", centralMall, business, 1.8, RoadType.MAIN_ROAD, TrafficLevel.HIGH, RoadCondition.OPEN, true));
        city.addRoad(new Road("R08", centralMall, centralPolice, 1.7, RoadType.SECONDARY_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));
        city.addRoad(new Road("R09", centralMall, centralJunction, 1.9, RoadType.MAIN_ROAD, TrafficLevel.HIGH, RoadCondition.OPEN, true));

        city.addRoad(new Road("R10", business, centralJunction, 1.6, RoadType.MAIN_ROAD, TrafficLevel.SEVERE, RoadCondition.OPEN, true));
        city.addRoad(new Road("R11", business, eastIndustrial, 2.5, RoadType.MAIN_ROAD, TrafficLevel.HIGH, RoadCondition.OPEN, true));
        city.addRoad(new Road("R12", business, safeHavenNorth, 2.7, RoadType.SECONDARY_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));

        city.addRoad(new Road("R13", centralPolice, centralJunction, 1.5, RoadType.SECONDARY_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));
        city.addRoad(new Road("R14", centralPolice, riversideHomes, 2.6, RoadType.MAIN_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));

        city.addRoad(new Road("R15", centralJunction, centralPark, 1.8, RoadType.SECONDARY_ROAD, TrafficLevel.HIGH, RoadCondition.OPEN, true));
        city.addRoad(new Road("R16", centralJunction, riversidePark, 2.5, RoadType.MAIN_ROAD, TrafficLevel.HIGH, RoadCondition.OPEN, true));
        city.addRoad(new Road("R17", centralJunction, safeHavenCentral, 1.2, RoadType.MAIN_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));

        city.addRoad(new Road("R18", riversideHomes, riversideSchool, 1.4, RoadType.RESIDENTIAL_ROAD, TrafficLevel.LOW, RoadCondition.OPEN, true));
        city.addRoad(new Road("R19", riversideSchool, riversidePark, 2.0, RoadType.SECONDARY_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));

        city.addRoad(new Road("R20", riversidePark, southResidential, 2.8, RoadType.MAIN_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));
        city.addRoad(new Road("R21", riversidePark, safeHavenCentral, 2.4, RoadType.MAIN_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));

        city.addRoad(new Road("R22", eastIndustrial, eastFire, 1.7, RoadType.SECONDARY_ROAD, TrafficLevel.HIGH, RoadCondition.OPEN, true));
        city.addRoad(new Road("R23", eastIndustrial, eastPolice, 2.2, RoadType.MAIN_ROAD, TrafficLevel.SEVERE, RoadCondition.OPEN, true));
        city.addRoad(new Road("R24", eastPolice, southResidential, 2.0, RoadType.MAIN_ROAD, TrafficLevel.HIGH, RoadCondition.OPEN, true));
        city.addRoad(new Road("R25", eastPolice, southMedical, 1.8, RoadType.MAIN_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));

        city.addRoad(new Road("R26", southResidential, southMedical, 1.6, RoadType.SECONDARY_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));
        city.addRoad(new Road("R27", southResidential, safeHavenSouth, 2.3, RoadType.MAIN_ROAD, TrafficLevel.LOW, RoadCondition.OPEN, true));
        city.addRoad(new Road("R28", southMedical, safeHavenSouth, 2.0, RoadType.MAIN_ROAD, TrafficLevel.MEDIUM, RoadCondition.OPEN, true));

        city.addRoad(new Road("R29", safeHavenCentral, business, 1.4, RoadType.MAIN_ROAD, TrafficLevel.LOW, RoadCondition.OPEN, true));
        city.addRoad(new Road("R30", safeHavenCentral, safeHavenSouth, 3.0, RoadType.MAIN_ROAD, TrafficLevel.LOW, RoadCondition.OPEN, true));

        return city;
    }
}