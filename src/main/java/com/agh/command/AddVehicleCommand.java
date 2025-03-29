package com.agh.command;

import com.agh.model.intersection.IIntersection;
import com.agh.model.road.IRoad;
import com.agh.model.road.RoadDirection;
import com.agh.model.vehicle.Vehicle;
import com.agh.simulation.SimulationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record AddVehicleCommand(String vehicleId, String startRoad, String endRoad, SimulationManager manager) implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute() {
        RoadDirection startDirection = RoadDirection.valueOf(startRoad.toUpperCase());
        RoadDirection endDirection = RoadDirection.valueOf(endRoad.toUpperCase());

        IIntersection intersection = manager.getIntersection();
        Vehicle vehicle = new Vehicle(vehicleId, startDirection, endDirection);
        IRoad road = intersection.getRoadByDirection(startDirection);
        road.getInboundLane().addVehicle(vehicle);

        LOGGER.info("Added vehicle {}", vehicle);
    }
}
