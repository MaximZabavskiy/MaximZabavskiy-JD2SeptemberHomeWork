package com.noirix.controller;

import com.noirix.controller.request.CarCreateRequest;
import com.noirix.controller.request.SearchCriteria;
import com.noirix.domain.Car;
import com.noirix.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    public final CarService carService;

    public static final String CAR_PAGE = "cars";
    public static final String CARS_LIST_ATTRIBUTE = "cars";

    //cars
    @GetMapping
    public ModelAndView getAllCars() {
        ModelAndView result = new ModelAndView();

        result.setViewName(CAR_PAGE);
        result.addObject(CARS_LIST_ATTRIBUTE, carService.findAll());

        return result;
    }

    @GetMapping("/create")
    public ModelAndView getCarCreateRequest() {
        ModelAndView result = new ModelAndView();

        result.setViewName("createcar");
        result.addObject("carCreateRequest", new CarCreateRequest());

        return result;
    }

    @GetMapping(value = "/search")
    public ModelAndView search(@ModelAttribute SearchCriteria criteria) {

        ModelAndView result = new ModelAndView();

        result.setViewName(CAR_PAGE);
        result.addObject(CARS_LIST_ATTRIBUTE, carService.search(criteria.getQuery()).stream().limit(criteria.getLimit()).collect(Collectors.toList()));

        return result;
    }

    //    /users/1
    @GetMapping(value = "/{id}")
    public ModelAndView search(@PathVariable("id") Long carId) {
        ModelAndView result = new ModelAndView();

        result.setViewName(CAR_PAGE);
        result.addObject(CARS_LIST_ATTRIBUTE, Collections.singletonList(carService.findById(carId)));

        return result;

    }

    @PostMapping
    public ModelAndView createCar(@ModelAttribute CarCreateRequest carCreateRequest) {

        //converters
        Car car = new Car();
        car.setModel(carCreateRequest.getModel());
        car.setCreationYear((carCreateRequest.getCreationYear()));
        car.setUserId(carCreateRequest.getUserId());
        car.setPrice(carCreateRequest.getPrice());
        car.setColor(carCreateRequest.getColor());
        carService.save(car);

        ModelAndView result = new ModelAndView();

        result.setViewName(CAR_PAGE);
        result.addObject(CARS_LIST_ATTRIBUTE, carService.findAll());

        return result;
    }

}
