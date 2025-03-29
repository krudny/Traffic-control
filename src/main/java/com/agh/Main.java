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
        Vehicle vehicle4 = new Vehicle(4, RoadDirection.SOUTH, RoadDirection.NORTH);
        Vehicle vehicle5 = new Vehicle(5, RoadDirection.NORTH, RoadDirection.SOUTH);
        Vehicle vehicle6 = new Vehicle(6, RoadDirection.SOUTH, RoadDirection.NORTH);



        List<Vehicle> vehicles = List.of(vehicle1, vehicle2, vehicle3, vehicle4, vehicle5, vehicle6);

        vehicles.forEach(vehicle -> {
            intersection.getRoadByDirection(vehicle.getRoute().sourceDirection()).getInboundLane().addVehicle(vehicle);
        });

        manager.init();
    }
}