package com.agh.simulation;

import com.agh.model.road.IRoad;
import com.agh.model.road.RoadDirection;
import com.agh.model.trafficLight.TrafficLightSignal;
import com.agh.model.vehicle.Vehicle;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class TrafficState {
    private final Map<RoadDirection, List<Vehicle>> vehiclesToMove;
    private final Map<RoadDirection, TrafficLightSignal> lightStates;

    public TrafficState() {
        vehiclesToMove = new HashMap<>();
        lightStates = new HashMap<>();
    }

    public void addWaitingVehicle(Vehicle vehicle) {
        List<Vehicle> vehicles = vehiclesToMove.getOrDefault(vehicle.getRoute().sourceDirection(), new ArrayList<Vehicle>());
        vehicles.add(vehicle);
        vehiclesToMove.put(vehicle.getRoute().sourceDirection(), vehicles);
    }

    public void addLightStateForRoad(IRoad road) {
        lightStates.put(road.getRoadDirection(), road.getTrafficLight().getCurrentState());
    }
}
