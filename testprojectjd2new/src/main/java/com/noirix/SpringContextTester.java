package com.noirix;

import com.noirix.domain.Car;
import com.noirix.service.CarService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

//@Slf4j
public class SpringContextTester {
    private static final Logger log = Logger.getLogger(SpringContextTester.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext("com.noirix");

        CarService carService = annotationConfigApplicationContext.getBean(CarService.class);

        log.info(carService.findAll().stream().map(Car::getModel).collect(Collectors.joining(", ")));

        log.info(carService.findById(4L).toString());

        List<Car> testCreate = carService.search("TestCreate");

        for (Car car : testCreate) {
            log.info(car.toString());
        }

        Car carForSave =
                Car.builder()
                        .model("SUZUKI")
                        .creationYear(2019)
                        .userId(2L)
                        .price(60000.00)
                        .color("YELLOW")
                        .build();

        log.info(carService.save(carForSave).toString());

        //Add search method to service
        //Realise search method with JDBCTemplate or NamedParamJDBCTemplate
    }
}
