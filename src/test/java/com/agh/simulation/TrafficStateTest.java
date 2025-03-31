package com.agh.simulation;

import com.agh.model.road.IRoad;
import com.agh.model.road.RoadDirection;
import com.agh.model.trafficLight.TrafficLight;
import com.agh.model.trafficLight.TrafficLightSignal;
import com.agh.model.vehicle.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrafficStateTest {

    private TrafficState trafficState;

    @BeforeEach
    void setUp() {
        trafficState = new TrafficState();
    }

    @Test
    void testAddWaitingVehicle() {
        Vehicle vehicle = new Vehicle("v1", RoadDirection.NORTH, RoadDirection.SOUTH);
        trafficState.addWaitingVehicle(vehicle);

        Map<RoadDirection, List<Vehicle>> vehiclesToMove = trafficState.getVehiclesToMove();
        assertTrue(vehiclesToMove.containsKey(RoadDirection.NORTH), "Vehicles map should contain NORTH direction");
        assertEquals(1, vehiclesToMove.get(RoadDirection.NORTH).size(), "There should be one vehicle waiting");
        assertEquals(vehicle, vehiclesToMove.get(RoadDirection.NORTH).get(0), "The vehicle should be correctly stored");
    }

    @Test
    void testAddMultipleWaitingVehicles() {
        Vehicle vehicle1 = new Vehicle("v1", RoadDirection.NORTH, RoadDirection.SOUTH);
        Vehicle vehicle2 = new Vehicle("v2", RoadDirection.NORTH, RoadDirection.EAST);

        trafficState.addWaitingVehicle(vehicle1);
        trafficState.addWaitingVehicle(vehicle2);

        Map<RoadDirection, List<Vehicle>> vehiclesToMove = trafficState.getVehiclesToMove();
        assertEquals(2, vehiclesToMove.get(RoadDirection.NORTH).size(), "There should be two vehicles waiting in the NORTH direction");
    }

    @Test
    void testAddLightStateForRoad() {
        IRoad road = mock(IRoad.class);
        TrafficLight trafficLight = mock(TrafficLight.class);

        when(road.getRoadDirection()).thenReturn(RoadDirection.SOUTH);
        when(road.getTrafficLight()).thenReturn(trafficLight);
        when(trafficLight.getCurrentState()).thenReturn(TrafficLightSignal.RED);

        trafficState.addLightStateForRoad(road);

        Map<RoadDirection, TrafficLightSignal> lightStates = trafficState.getLightStates();
        assertEquals(TrafficLightSignal.RED, lightStates.get(RoadDirection.SOUTH), "Traffic light state should be RED for SOUTH road");
    }
}