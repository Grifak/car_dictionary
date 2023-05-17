package com.example.borodavkin_java.controller;

import lombok.RequiredArgsConstructor;
import com.example.borodavkin_java.model.CarGuideResponse;
import com.example.borodavkin_java.model.CarRequest;
import com.example.borodavkin_java.model.ModelResponse;
import com.example.borodavkin_java.model.Sorting;
import com.example.borodavkin_java.service.CarService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/car-guide")
@Slf4j
public class CarController {
    private final CarService carService;

    @GetMapping("/car-mark")
    public String getFuelCarMark(
            Model model,
            @ModelAttribute("carRequest") CarRequest carRequest
    ){
        log.info("CarRequest = {}", carRequest);
        Boolean asc = carRequest.getAsc() == null || carRequest.getAsc();

        var markList = carService.getMarkList(carRequest.getMarkName(), asc);

        model.addAttribute("markList", markList);

        return "table";
    }

    @GetMapping("/car-model")
    public String getFuelCarModel(
            @ModelAttribute("carRequest") CarRequest carRequest,
            Model model
    ){
        log.info("CarRequest = {}", carRequest);
        Boolean asc = carRequest.getAsc() == null || carRequest.getAsc();

        var modelList = carService.getModelListByMarkId(carRequest.getMarkId(), carRequest.getModelName(), asc);

        model.addAttribute("modelList", modelList);

        return "model-table";
    }

    @GetMapping("/car-guide")
    public String getCarGuide(
            @ModelAttribute("carRequest") CarRequest carRequest,
            Model model
    ){
        var carGuideList = carService.getCarGuide(carRequest.getMarkId(), carRequest.getModelId());

        model.addAttribute("carGuideList", carGuideList);

        return "guide-table";
    }
}
