package com.example.borodavkin_java.model;

import com.example.borodavkin_java.enums.EngineType;
import com.example.borodavkin_java.enums.TransmissionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarGuideResponse {
    private EngineType engineType;

    private String engineVolume;

    private TransmissionType transmissionType;
}
