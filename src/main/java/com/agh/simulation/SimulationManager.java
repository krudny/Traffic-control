package com.agh.simulation;

import com.agh.model.intersection.IIntersection;
import com.agh.model.road.IRoad;
import com.agh.model.road.RoadDirection;
import com.agh.model.trafficLight.TrafficLight;
import com.agh.model.trafficLight.TrafficLightSignal;
import com.agh.model.trafficLight.strategies.TrafficLightStrategy;
import com.agh.model.vehicle.Vehicle;
import com.agh.model.vehicle.VehicleStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class SimulationManager {
    private final IIntersection intersection;
    private final TrafficLightStrategy strategy;
    @JsonIgnore
    private final SimulationOutputManager outputManager;
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
        intersection.getRoads().forEach(road ->
                road.getInboundLane().getNextVehicle().ifPresent(vehicle -> {
                    VehicleStatus status = road.getTrafficLight().getCurrentState() == TrafficLightSignal.RED
                            ? VehicleStatus.STOPPED_AT_RED_LIGHT
                            : VehicleStatus.APPROACHING_INTERSECTION;
                    vehicle.setStatus(status);

                    if (status == VehicleStatus.APPROACHING_INTERSECTION) {
                        state.addWaitingVehicle(vehicle);
                    }

                    LOGGER.info("{}", vehicle);
                })
        );
    }

    private void selectWhichAreGoingToMove(TrafficState state, ArrayList<Vehicle> vehiclesToLeave) {
        state.getVehiclesToMove().forEach((direction, vehicles) ->
                vehicles.stream()
                        .peek(vehicle -> vehicle.setStatus(state.getLightStates().get(direction) == TrafficLightSignal.GREEN
                                ? VehicleStatus.IN_INTERSECTION
                                : VehicleStatus.STOPPED_AT_RED_LIGHT))
                        .filter(vehicle -> state.getLightStates().get(direction) == TrafficLightSignal.GREEN)
                        .forEach(vehicle -> {
                            vehiclesToLeave.add(vehicle);
                            LOGGER.info("{}", vehicle);
                        })
        );
    }

    private void moveVehicles(ArrayList<Vehicle> vehiclesToLeave) {
        List<String> leftVehicles = new ArrayList<>();

        while (!vehiclesToLeave.isEmpty()) {
            List<Vehicle> vehiclesToRemove = new ArrayList<>();

            for (Vehicle vehicle : intersection.getMostPriority(vehiclesToLeave)) {
                vehicle.setStatus(VehicleStatus.LEAVING_INTERSECTION);
                RoadDirection sourceDirection = vehicle.getRoute().sourceDirection();
                intersection.getRoadByDirection(sourceDirection).getInboundLane().removeNextVehicle();
                vehiclesToRemove.add(vehicle);
                leftVehicles.add(vehicle.getId());
                LOGGER.info("{}", vehicle);
            }

            vehiclesToLeave.removeAll(vehiclesToRemove);
        }

        outputManager.addStep(leftVehicles);
    }

    public void saveSimulationOutput(String filePath) {
        outputManager.exportToJson(filePath);
    }
}
