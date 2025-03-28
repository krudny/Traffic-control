package com.agh.simulation;

import com.agh.model.intersection.IIntersection;
import com.agh.model.road.IRoad;
import com.agh.model.road.RoadDirection;
import com.agh.model.vehicle.Vehicle;
import com.agh.model.vehicle.VehicleStatus;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class SimulationManager {
    private final IIntersection intersection;
    private static final Logger LOGGER = LogManager.getLogger();

    public void nextStep() {
        LOGGER.info("Sim step");
        TrafficState trafficState = new TrafficState();

        intersection.getRoads().forEach(trafficState::addLightStateForRoad);

        List<IRoad> roads = intersection.getRoads();
        for (IRoad road : roads) {
            road.getInboundLane().getNextVehicle().ifPresent(vehicle -> {
                vehicle.setStatus(VehicleStatus.APPROACHING_INTERSECTION);
                trafficState.addWaitingVehicle(vehicle);
                LOGGER.info("Vehicle approaching: {}", vehicle);

            });
        }


    }
}
