package com.agh.simulation;

import com.agh.model.intersection.IIntersection;
import com.agh.model.road.IRoad;
import com.agh.model.road.RoadDirection;
import com.agh.model.trafficLight.TrafficLightSignal;
import com.agh.model.vehicle.Vehicle;
import com.agh.model.vehicle.VehicleStatus;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SimulationManager {
    private final IIntersection intersection;
    private static final Logger LOGGER = LogManager.getLogger();

    public void init() {
        nextStep();
        intersection.getRoads().forEach(road -> road.getTrafficLight().changeState());
        nextStep();
        nextStep();
    }

    public void nextStep() {
        LOGGER.info("Sim step");
        TrafficState trafficState = new TrafficState();

        // check lights
        intersection.getRoads().forEach(trafficState::addLightStateForRoad);

        // select first vehicles on every line and set their status
        List<IRoad> roads = intersection.getRoads();
        for (IRoad road : roads) {
            road.getInboundLane().getNextVehicle().ifPresent(vehicle -> {
                vehicle.setStatus(VehicleStatus.APPROACHING_INTERSECTION);
                trafficState.addWaitingVehicle(vehicle);
                LOGGER.info("Vehicle approaching: {}", vehicle);
            });
        }

        makeStep(trafficState);
        LOGGER.info("AFTER STEP");
        for(IRoad road : roads) {
            LOGGER.info("ROAD: {}", road.getInboundLane());
        }

    }

    public void makeStep(TrafficState trafficState) {
        ArrayList<Vehicle> to_leave = new ArrayList<>();
        LOGGER.info(trafficState.getVehiclesToMove());
        LOGGER.info(trafficState.getLightStates());

        // check which vehicles are going to move somehow
        trafficState.getVehiclesToMove().forEach((direction, vehicles) -> {
            vehicles.forEach(vehicle -> {
                if (trafficState.getLightStates().get(direction) == TrafficLightSignal.GREEN ) {
                    vehicle.setStatus(VehicleStatus.IN_INTERSECTION);
                    LOGGER.info("Vehicle in intersection: {}", vehicle);
                    to_leave.add(vehicle);
                }
            });
        });

        // move those vehicles
        if (!intersection.isCollision(to_leave)) {
            LOGGER.info("Removed: {}", to_leave);
            to_leave.forEach(vehicle -> {
                vehicle.setStatus(VehicleStatus.LEAVING_INTERSECTION);
                RoadDirection sourceDirection = vehicle.getRoute().sourceDirection();
                intersection.getRoadByDirection(sourceDirection).getInboundLane().removeNextVehicle();
            });
        }
    }

}
