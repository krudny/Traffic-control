package com.agh.simulation;

import com.agh.model.intersection.IIntersection;
import com.agh.model.road.IRoad;
import com.agh.model.road.RoadDirection;
import com.agh.model.trafficLight.TrafficLight;
import com.agh.model.trafficLight.TrafficLightSignal;
import com.agh.model.trafficLight.strategies.TrafficLightStrategy;
import com.agh.model.vehicle.Vehicle;
import com.agh.model.vehicle.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
@Getter
public class SimulationManager {
    private final IIntersection intersection;
    private final TrafficLightStrategy strategy;
    private static final Logger LOGGER = LogManager.getLogger();


    public void nextStep() {
        strategy.toggleLights();
        TrafficState trafficState = new TrafficState();
        ArrayList<Vehicle> vehiclesToLeave = new ArrayList<>();
        setLights(trafficState);
        selectFirstVehicles(trafficState);
        selectWhichAreGoingToMove(trafficState, vehiclesToLeave);
        moveVehicles(vehiclesToLeave);
    }


    private void setLights(TrafficState state) {
        intersection.getRoads().forEach(state::addLightStateForRoad);
    }

    private void selectFirstVehicles(TrafficState state) {
        List<IRoad> roads = intersection.getRoads();
        for (IRoad road : roads) {
            road.getInboundLane().getNextVehicle().ifPresent(vehicle -> {
                if (road.getTrafficLight().getCurrentState() == TrafficLightSignal.RED) {
                    vehicle.setStatus(VehicleStatus.STOPPED_AT_RED_LIGHT);
                } else {
                    vehicle.setStatus(VehicleStatus.APPROACHING_INTERSECTION);
                    state.addWaitingVehicle(vehicle);
                }
                LOGGER.info("{}", vehicle);
            });
        }
    }

    private void selectWhichAreGoingToMove(TrafficState state, ArrayList<Vehicle> vehiclesToLeave) {
        state.getVehiclesToMove().forEach((direction, vehicles) -> {
            vehicles.forEach(vehicle -> {
                if (state.getLightStates().get(direction) == TrafficLightSignal.GREEN) {
                    vehicle.setStatus(VehicleStatus.IN_INTERSECTION);
                    vehiclesToLeave.add(vehicle);
                    LOGGER.info("{}", vehicle);
                } else {
                    vehicle.setStatus(VehicleStatus.STOPPED_AT_RED_LIGHT);
                }
            });
        });
    }

    private void moveVehicles(ArrayList<Vehicle> vehiclesToLeave) {
        while (!vehiclesToLeave.isEmpty()) {
            List<Vehicle> vehiclesToRemove = new ArrayList<>();

            for (Vehicle vehicle : intersection.getMostPriority(vehiclesToLeave)) {
                vehicle.setStatus(VehicleStatus.LEAVING_INTERSECTION);
                RoadDirection sourceDirection = vehicle.getRoute().sourceDirection();
                intersection.getRoadByDirection(sourceDirection).getInboundLane().removeNextVehicle();
                vehiclesToRemove.add(vehicle);
                LOGGER.info("{}", vehicle);
            }

            vehiclesToLeave.removeAll(vehiclesToRemove);
        }
    }
}
