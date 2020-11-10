package com.noirix.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarCreateRequest {

    private String model;

    private Long creationYear;

    private Long userId;

    private Double price;

    private String color;
}
