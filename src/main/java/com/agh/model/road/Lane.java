package com.agh.model.road;

import com.agh.model.vehicle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Deque;

@AllArgsConstructor
@Getter
public class Lane {
    private final LaneDirection laneDirection;
    private Deque<Vehicle> vehicles;

    public Lane(LaneDirection laneDirection) {
        this.laneDirection = laneDirection;
        this.vehicles = new ArrayDeque<Vehicle>();
    }

    public Vehicle getNextVehicle() {
        return vehicles.getFirst();
    }

    public void addVehicle(Vehicle newVehicle) {
        vehicles.add(newVehicle);
    }
}
