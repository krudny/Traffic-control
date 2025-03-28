package com.agh;

import com.agh.model.intersection.IIntersection;
import com.agh.model.intersection.OneInboundLaneIntersection;
import com.agh.model.road.RoadDirection;
import com.agh.model.vehicle.Vehicle;
import com.agh.simulation.SimulationManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        IIntersection intersection = new OneInboundLaneIntersection();
        SimulationManager manager = new SimulationManager(intersection);
        Vehicle vehicle1 = new Vehicle(1, RoadDirection.NORTH, RoadDirection.SOUTH);
        Vehicle vehicle2 = new Vehicle(2, RoadDirection.WEST, RoadDirection.SOUTH);
        Vehicle vehicle3 = new Vehicle(3, RoadDirection.NORTH, RoadDirection.SOUTH);

        List<Vehicle> vehicles = List.of(vehicle1, vehicle2, vehicle3);

        vehicles.forEach(vehicle -> {
            intersection.getRoadByDirection(vehicle.getRoute().sourceDirection()).getInboundLane().addVehicle(vehicle);
        });

        manager.nextStep();
    }
}