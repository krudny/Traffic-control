package com.agh.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class StepStatus {
    private final List<String> leftVehicles;

    public StepStatus(List<String> leftVehicles) {
        this.leftVehicles = leftVehicles;
    }
}