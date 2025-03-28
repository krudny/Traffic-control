package com.agh.model.road;

import com.agh.model.vehicle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class Lane {
    private final LaneDirection laneDirection;
    private Deque<Vehicle> vehicles;

    public Lane(LaneDirection laneDirection) {
        this.laneDirection = laneDirection;
        this.vehicles = new ArrayDeque<Vehicle>();
    }

    public Optional<Vehicle> getNextVehicle() {
        if (vehicles.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(vehicles.getFirst());
    }

    public void addVehicle(Vehicle newVehicle) {
        vehicles.add(newVehicle);
    }
}
