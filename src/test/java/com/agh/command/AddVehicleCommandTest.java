package com.agh.command;

import com.agh.model.intersection.IIntersection;
import com.agh.model.road.IRoad;
import com.agh.model.road.Lane;
import com.agh.model.road.RoadDirection;
import com.agh.model.vehicle.Vehicle;
import com.agh.simulation.SimulationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddVehicleCommandTest {

    private SimulationManager mockManager;
    private IIntersection mockIntersection;
    private IRoad mockRoad;
    private Lane mockLane;

    @BeforeEach
    void setUp() {
        mockManager = mock(SimulationManager.class);
        mockIntersection = mock(IIntersection.class);
        mockRoad = mock(IRoad.class);
        mockLane = mock(Lane.class);

        when(mockManager.getIntersection()).thenReturn(mockIntersection);
        when(mockIntersection.getRoadByDirection(any(RoadDirection.class))).thenReturn(mockRoad);
        when(mockRoad.getInboundLane()).thenReturn(mockLane);
    }

    @Test
    void execute_shouldAddVehicleToCorrectRoad() {
        AddVehicleCommand command = new AddVehicleCommand("car1", "NORTH", "SOUTH", mockManager);
        command.execute();
        verify(mockIntersection).getRoadByDirection(RoadDirection.NORTH);
        verify(mockRoad).getInboundLane();
        verify(mockLane).addVehicle(any(Vehicle.class));
    }

    @Test
    void execute_shouldCreateVehicleWithCorrectProperties() {
        String vehicleId = "car1";
        String startRoad = "NORTH";
        String endRoad = "SOUTH";
        AddVehicleCommand command = new AddVehicleCommand(vehicleId, startRoad, endRoad, mockManager);

        doAnswer(invocation -> {
            Vehicle vehicle = invocation.getArgument(0);
            assertEquals(vehicleId, vehicle.getId());
            assertEquals(RoadDirection.NORTH, vehicle.getRoute().sourceDirection());
            assertEquals(RoadDirection.SOUTH, vehicle.getRoute().destinationDirection());
            return null;
        }).when(mockLane).addVehicle(any(Vehicle.class));

        command.execute();

        verify(mockLane).addVehicle(any(Vehicle.class));
    }

    @Test
    void execute_shouldHandleInvalidRoadDirection() {
        AddVehicleCommand command = new AddVehicleCommand("car1", "INVALID", "SOUTH", mockManager);

        assertThrows(IllegalArgumentException.class, command::execute);
    }
}