package com.agh.simulation;

import com.agh.model.intersection.IIntersection;
import com.agh.model.road.IRoad;
import com.agh.model.road.RoadDirection;
import com.agh.model.trafficLight.TrafficLightSignal;
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
    private static final Logger LOGGER = LogManager.getLogger();

    public void init() {
        nextStep();
//        intersection.getRoads().forEach(road -> road.getTrafficLight().changeState());
        nextStep();
//        intersection.getRoads().forEach(road -> road.getTrafficLight().changeState());
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
                if (trafficState.getLightStates().get(direction) == TrafficLightSignal.GREEN) {
                    vehicle.setStatus(VehicleStatus.IN_INTERSECTION);
                    LOGGER.info("Vehicle in intersection: {}", vehicle);
                    to_leave.add(vehicle);
                }
            });
        });

        // move those vehicles
        while (!to_leave.isEmpty()) {
            List<Vehicle> vehiclesToRemove = new ArrayList<>();

            Iterator<Vehicle> iterator = intersection.getMostPriority(to_leave).iterator();
            while (iterator.hasNext()) {
                Vehicle vehicle = iterator.next();
                vehicle.setStatus(VehicleStatus.LEAVING_INTERSECTION);
                RoadDirection sourceDirection = vehicle.getRoute().sourceDirection();
                intersection.getRoadByDirection(sourceDirection).getInboundLane().removeNextVehicle();
                vehiclesToRemove.add(vehicle);
                LOGGER.info("Removed: {}", vehicle);
            }

            to_leave.removeAll(vehiclesToRemove);
        }
    }
}
