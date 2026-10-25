package com.evacuation;

import com.evacuation.disaster.Disaster;
import com.evacuation.disaster.DisasterManager;
import com.evacuation.disaster.DisasterType;
import com.evacuation.graph.CityGraph;
import com.evacuation.model.Road;
import com.evacuation.service.CityBuilder;

public class Main {

    public static void main(String[] args) {

        CityGraph city =
                CityBuilder.createDefaultCity();

        DisasterManager disasterManager =
                new DisasterManager();

        Road testRoad =
                city.getRoadsFrom("L06")
                        .stream()
                        .filter(road ->
                                road.getId().equals("R10"))
                        .findFirst()
                        .orElseThrow();

        System.out.println(
                "===== ORIGINAL ROAD ====="
        );

        System.out.println(
                testRoad.getId()
                        + " | Condition: "
                        + testRoad.getCondition()
                        + " | Traffic: "
                        + testRoad.getTrafficLevel()
        );

        Disaster disaster =
                new Disaster(
                        "D01",
                        "Central Fire",
                        DisasterType.FIRE,
                        city.getLocation("L06"),
                        15,
                        10
                );

        disasterManager.addDisaster(
                disaster,
                city
        );

        System.out.println();
        System.out.println(
                "===== AFTER DISASTER ====="
        );

        System.out.println(
                testRoad.getId()
                        + " | Condition: "
                        + testRoad.getCondition()
                        + " | Traffic: "
                        + testRoad.getTrafficLevel()
        );

        disasterManager.clearDisasters(
                city
        );

        System.out.println();
        System.out.println(
                "===== AFTER RESET ====="
        );

        System.out.println(
                testRoad.getId()
                        + " | Condition: "
                        + testRoad.getCondition()
                        + " | Traffic: "
                        + testRoad.getTrafficLevel()
        );

        System.out.println();
        System.out.println(
                "===== RESET TEST ====="
        );

        boolean resetSuccessful =
                testRoad.getCondition()
                        == testRoad.getOriginalCondition()
                        && testRoad.getTrafficLevel()
                        == testRoad.getOriginalTrafficLevel();

        System.out.println(
                "Reset Successful: "
                        + resetSuccessful
        );
    }
}