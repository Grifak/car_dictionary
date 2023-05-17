package com.example.borodavkin_java.enums;

import java.util.Map;

public enum TransmissionType {
    AUTOMATIC("автоматическая"),
    MECHANIC("механическая"),
    VARIATOR("вариатор"),
    ROBOT("робот");

    private String name;

    TransmissionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TransmissionType fromString(String name){
        var engineTypeMap = Map.of(
                AUTOMATIC.name, AUTOMATIC,
                MECHANIC.name, MECHANIC,
                VARIATOR.name, VARIATOR,
                ROBOT.name, ROBOT
        );

        return engineTypeMap.get(name);
    }
}
