package com.example.borodavkin_java.enums;

import java.util.Map;

public enum EngineType {
    PETROL("бензин"),
    DIESEL("дизель"),
    ELECTRO("электро"),
    HYDROGEN("гидроген"),
    HYBRID("гибрид"),
    SUG("СУГ");

    private String name;

    EngineType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EngineType fromString(String name){
        var engineTypeMap = Map.of(
                PETROL.name, PETROL,
                DIESEL.name, DIESEL,
                ELECTRO.name, ELECTRO,
                HYDROGEN.name, HYDROGEN,
                HYBRID.name, HYBRID,
                SUG.name, SUG
        );

        return engineTypeMap.get(name);
    }
}
