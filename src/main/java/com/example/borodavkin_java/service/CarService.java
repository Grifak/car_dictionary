package com.example.borodavkin_java.service;

import lombok.RequiredArgsConstructor;
import com.example.borodavkin_java.model.CarGuideResponse;
import com.example.borodavkin_java.model.MarkResponse;
import com.example.borodavkin_java.model.ModelResponse;
import com.example.borodavkin_java.repository.CarRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CarService {
    private final CarRepository carRepository;

    public List<MarkResponse> getMarkList(String markName, Boolean asc) {
        return carRepository.getMarkList(markName, asc);
    }

    public List<ModelResponse> getModelListByMarkId(String markId, String modelName, Boolean asc) {
        return carRepository.getModelByMarkIdAndClientType(markId, modelName, asc);
    }

    public List<CarGuideResponse> getCarGuide(String markId, String modelId) {
        return carRepository.getCarGuideList(markId, modelId);
    }
}
