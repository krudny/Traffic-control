package com.agh.model.intersection;

import com.agh.model.road.IRoad;
import com.agh.model.road.RoadDirection;
import com.agh.model.trafficLight.TrafficLight;
import com.agh.model.trafficLight.TrafficLightSignal;
import com.agh.model.vehicle.Route;
import com.agh.model.vehicle.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OneInboundLaneIntersectionTest {

    private OneInboundLaneIntersection intersection;

    @BeforeEach
    void setUp() {
        intersection = new OneInboundLaneIntersection();
    }

    @Test
    void testGetRoads() {
        List<IRoad> roads = intersection.getRoads();

        assertEquals(4, roads.size(), "Should return 4 roads");

        // Check that roads have the correct directions
        List<RoadDirection> directions = roads.stream()
                .map(IRoad::getRoadDirection)
                .toList();

        assertTrue(directions.contains(RoadDirection.NORTH), "Should contain NORTH road");
        assertTrue(directions.contains(RoadDirection.SOUTH), "Should contain SOUTH road");
        assertTrue(directions.contains(RoadDirection.WEST), "Should contain WEST road");
        assertTrue(directions.contains(RoadDirection.EAST), "Should contain EAST road");
    }

    @Test
    void testGetRoadByDirection() {
        IRoad northRoad = intersection.getRoadByDirection(RoadDirection.NORTH);
        IRoad southRoad = intersection.getRoadByDirection(RoadDirection.SOUTH);
        IRoad westRoad = intersection.getRoadByDirection(RoadDirection.WEST);
        IRoad eastRoad = intersection.getRoadByDirection(RoadDirection.EAST);

        assertEquals(RoadDirection.NORTH, northRoad.getRoadDirection(), "North road should have NORTH direction");
        assertEquals(RoadDirection.SOUTH, southRoad.getRoadDirection(), "South road should have SOUTH direction");
        assertEquals(RoadDirection.WEST, westRoad.getRoadDirection(), "West road should have WEST direction");
        assertEquals(RoadDirection.EAST, eastRoad.getRoadDirection(), "East road should have EAST direction");
    }

    @Test
    void testInitialTrafficLightStates() {
        TrafficLight northLight = intersection.getNorthRoad().getTrafficLight();
        TrafficLight southLight = intersection.getSouthRoad().getTrafficLight();
        TrafficLight westLight = intersection.getWestRoad().getTrafficLight();
        TrafficLight eastLight = intersection.getEastRoad().getTrafficLight();

        assertEquals(TrafficLightSignal.GREEN, northLight.getCurrentState(), "North traffic light should be GREEN initially");
        assertEquals(TrafficLightSignal.GREEN, southLight.getCurrentState(), "South traffic light should be GREEN initially");
        assertEquals(TrafficLightSignal.RED, westLight.getCurrentState(), "West traffic light should be RED initially");
        assertEquals(TrafficLightSignal.RED, eastLight.getCurrentState(), "East traffic light should be RED initially");
    }

    @Test
    void testIsCollisionWithCollidingRoutes() {
        // Create two vehicles with colliding routes
        Vehicle vehicle1 = new Vehicle("v1", RoadDirection.NORTH, RoadDirection.EAST);
        Vehicle vehicle2 = new Vehicle("v2", RoadDirection.WEST, RoadDirection.SOUTH);

        List<Vehicle> vehicles = List.of(vehicle1, vehicle2);

        assertTrue(intersection.isCollision(vehicles), "Routes should be detected as colliding");
    }

    @Test
    void testIsCollisionWithNonCollidingRoutes() {
        // Create two vehicles with non-colliding routes
        Vehicle vehicle1 = new Vehicle("v1", RoadDirection.NORTH, RoadDirection.SOUTH);
        Vehicle vehicle2 = new Vehicle("v2", RoadDirection.EAST, RoadDirection.WEST);

        List<Vehicle> vehicles = List.of(vehicle1, vehicle2);

        assertTrue(intersection.isCollision(vehicles), "Routes should be detected as colliding");
    }

    @Test
    void testIsCollisionWithSingleVehicle() {
        Vehicle vehicle = new Vehicle("v1", RoadDirection.NORTH, RoadDirection.SOUTH);
        List<Vehicle> vehicles = List.of(vehicle);

        assertFalse(intersection.isCollision(vehicles), "Single vehicle should not cause collision");
    }

    @Test
    void testGetMostPriorityWithRightTurnPriority() {
        // Vehicle1 turning right, Vehicle2 not going forward
        Vehicle vehicle1 = new Vehicle("v1", RoadDirection.NORTH, RoadDirection.EAST);
        Vehicle vehicle2 = new Vehicle("v2", RoadDirection.WEST, RoadDirection.NORTH);

        ArrayList<Vehicle> vehicles = new ArrayList<>(List.of(vehicle1, vehicle2));
        ArrayList<Vehicle> result = intersection.getMostPriority(vehicles);

        assertEquals(1, result.size(), "Only one vehicle should be allowed to move");
        assertEquals(vehicle1, result.get(0), "Vehicle1 should have priority (turning right)");
    }

    @Test
    void testGetMostPriorityWithForwardPriority() {
        // Vehicle1 going forward, Vehicle2 not
        Vehicle vehicle1 = new Vehicle("v1", RoadDirection.NORTH, RoadDirection.SOUTH);
        Vehicle vehicle2 = new Vehicle("v2", RoadDirection.SOUTH, RoadDirection.EAST);

        ArrayList<Vehicle> vehicles = new ArrayList<>(List.of(vehicle1, vehicle2));
        ArrayList<Vehicle> result = intersection.getMostPriority(vehicles);

        assertEquals(1, result.size(), "Only one vehicle should be allowed to move");
        assertEquals(vehicle1, result.get(0), "Vehicle1 should have priority (going forward)");
    }

    @Test
    void testIsGoingForward() {
        Route forwardRoute = new Route(RoadDirection.NORTH, RoadDirection.SOUTH);
        Route nonForwardRoute = new Route(RoadDirection.NORTH, RoadDirection.EAST);

        assertTrue(intersection.isGoingForward(forwardRoute), "Should detect forward route correctly");
        assertFalse(intersection.isGoingForward(nonForwardRoute), "Should detect non-forward route correctly");
    }

    @Test
    void testIsTurningRight() {
        Route rightTurn = new Route(RoadDirection.NORTH, RoadDirection.EAST);
        Route nonRightTurn = new Route(RoadDirection.NORTH, RoadDirection.SOUTH);

        assertTrue(intersection.isTurningRight(rightTurn), "Should detect right turn correctly");
        assertFalse(intersection.isTurningRight(nonRightTurn), "Should detect non-right turn correctly");
    }

    @Test
    void testGetAllTrafficLights() {
        List<TrafficLight> lights = intersection.getAllTrafficLights();

        assertEquals(4, lights.size(), "Should return 4 traffic lights");
        // Verify the lights have the expected initial states
        long greenCount = lights.stream()
                .filter(light -> light.getCurrentState() == TrafficLightSignal.GREEN)
                .count();
        long redCount = lights.stream()
                .filter(light -> light.getCurrentState() == TrafficLightSignal.RED)
                .count();

        assertEquals(2, greenCount, "Should have 2 GREEN lights");
        assertEquals(2, redCount, "Should have 2 RED lights");
    }
}