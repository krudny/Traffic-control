package com.agh.simulation;

import com.agh.model.intersection.IIntersection;
import com.agh.model.road.RoadDirection;
import com.agh.model.trafficLight.TrafficLightSignal;
import com.agh.model.trafficLight.strategies.TrafficLightStrategy;
import com.agh.model.vehicle.Vehicle;
import com.agh.model.vehicle.VehicleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.*;


class SimulationManagerTest {

    @Mock
    private IIntersection intersection;

    @Mock
    private TrafficLightStrategy strategy;

    @Mock
    private TrafficState state;

    @Mock
    private SimulationOutputManager outputManager;

    private SimulationManager simulationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        simulationManager = new SimulationManager(intersection, strategy, outputManager);
    }

    @Test
    void testNextStep() {
        simulationManager.nextStep();

        verify(strategy).toggleLights();
    }

    @Test
    void testSelectWhichAreGoingToMoveWithGreenLight() {
        TrafficState state = mock(TrafficState.class);
        ArrayList<Vehicle> vehiclesToLeave = new ArrayList<>();

        Map<RoadDirection, List<Vehicle>> vehiclesToMove = new HashMap<>();
        RoadDirection direction = RoadDirection.NORTH;
        Vehicle vehicle = mock(Vehicle.class);
        vehiclesToMove.put(direction, Collections.singletonList(vehicle));

        Map<RoadDirection, TrafficLightSignal> lightStates = new HashMap<>();
        lightStates.put(direction, TrafficLightSignal.GREEN);

        when(state.getVehiclesToMove()).thenReturn(vehiclesToMove);
        when(state.getLightStates()).thenReturn(lightStates);

        simulationManager.selectWhichAreGoingToMove(state, vehiclesToLeave);

        verify(vehicle).setStatus(VehicleStatus.IN_INTERSECTION);
        assertEquals(1, vehiclesToLeave.size());
        assertEquals(vehicle, vehiclesToLeave.get(0));
    }

    @Test
    void testSelectWhichAreGoingToMoveWithRedLight() {
        TrafficState state = mock(TrafficState.class);
        ArrayList<Vehicle> vehiclesToLeave = new ArrayList<>();

        Map<RoadDirection, List<Vehicle>> vehiclesToMove = new HashMap<>();
        RoadDirection direction = RoadDirection.NORTH;
        Vehicle vehicle = mock(Vehicle.class);
        vehiclesToMove.put(direction, Collections.singletonList(vehicle));

        Map<RoadDirection, TrafficLightSignal> lightStates = new HashMap<>();
        lightStates.put(direction, TrafficLightSignal.RED);

        when(state.getVehiclesToMove()).thenReturn(vehiclesToMove);
        when(state.getLightStates()).thenReturn(lightStates);

        simulationManager.selectWhichAreGoingToMove(state, vehiclesToLeave);

        verify(vehicle).setStatus(VehicleStatus.STOPPED_AT_RED_LIGHT);
        assertTrue(vehiclesToLeave.isEmpty());
    }


    @Test
    void testMoveVehiclesWithEmptyList() {
        ArrayList<Vehicle> vehiclesToLeave = new ArrayList<>();

        simulationManager.moveVehicles(vehiclesToLeave);

        verify(outputManager).addStep(Collections.emptyList());
    }
}