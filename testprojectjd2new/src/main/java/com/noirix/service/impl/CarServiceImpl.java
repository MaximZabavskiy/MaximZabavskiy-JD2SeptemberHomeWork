package com.noirix.service.impl;

import com.noirix.domain.Car;
import com.noirix.repository.CarRepository;
import com.noirix.service.CarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car save(Car car) {
        //1. Validation layer
        //2. Convert http request params into User object
        //3. Extended calls into DB or external services
        return carRepository.save(car);
    }

    @Override
    public Car findById(Long carId) {
        return carRepository.findById(carId);
    }

    @Override
    public List<Car> search(String query) {
        return carRepository.search(query); //Ctrl+Alb+B - go to implementation of method
    }
}
