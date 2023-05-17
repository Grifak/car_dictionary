package com.example.borodavkin_java.model;

import lombok.Data;

@Data
public class CarRequest {
    private Boolean asc;
    private String markName;
    private String modelName;

    private String markId;
    private String modelId;
}
