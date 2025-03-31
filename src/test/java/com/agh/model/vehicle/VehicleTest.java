package com.agh.model.vehicle;

import com.agh.model.road.RoadDirection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void constructor_shouldCreateVehicleWithCorrectProperties() {
        String id = "car1";
        RoadDirection startDirection = RoadDirection.NORTH;
        RoadDirection endDirection = RoadDirection.SOUTH;

        Vehicle vehicle = new Vehicle(id, startDirection, endDirection);

        assertEquals(id, vehicle.getId());
        assertEquals(startDirection, vehicle.getRoute().sourceDirection());
        assertEquals(endDirection, vehicle.getRoute().destinationDirection());
        assertEquals(VehicleStatus.AT_QUEUE, vehicle.getStatus());
    }

    @Test
    void setStatus_shouldChangeVehicleStatus() {
        Vehicle vehicle = new Vehicle("car1", RoadDirection.NORTH, RoadDirection.SOUTH);
        vehicle.setStatus(VehicleStatus.AT_QUEUE);
        assertEquals(VehicleStatus.AT_QUEUE, vehicle.getStatus());
    }

    @Test
    void toString_shouldReturnCorrectString() {
        Vehicle vehicle = new Vehicle("car1", RoadDirection.NORTH, RoadDirection.SOUTH);

        String result = vehicle.toString();

        assertTrue(result.contains("car1"));
        assertTrue(result.contains("NORTH"));
        assertTrue(result.contains("SOUTH"));
        assertTrue(result.contains("AT_QUEUE"));
    }

    @ParameterizedTest
    @MethodSource("provideDirectionCombinations")
    void constructor_shouldHandleAllDirectionCombinations(RoadDirection start, RoadDirection end) {
        String id = "car1";

        Vehicle vehicle = new Vehicle(id, start, end);

        assertEquals(start, vehicle.getRoute().sourceDirection());
        assertEquals(end, vehicle.getRoute().destinationDirection());
    }

    private static Stream<Arguments> provideDirectionCombinations() {
        return Stream.of(
                Arguments.of(RoadDirection.NORTH, RoadDirection.SOUTH),
                Arguments.of(RoadDirection.NORTH, RoadDirection.EAST),
                Arguments.of(RoadDirection.NORTH, RoadDirection.WEST),
                Arguments.of(RoadDirection.SOUTH, RoadDirection.NORTH),
                Arguments.of(RoadDirection.SOUTH, RoadDirection.EAST),
                Arguments.of(RoadDirection.SOUTH, RoadDirection.WEST),
                Arguments.of(RoadDirection.EAST, RoadDirection.NORTH),
                Arguments.of(RoadDirection.EAST, RoadDirection.SOUTH),
                Arguments.of(RoadDirection.EAST, RoadDirection.WEST),
                Arguments.of(RoadDirection.WEST, RoadDirection.NORTH),
                Arguments.of(RoadDirection.WEST, RoadDirection.SOUTH),
                Arguments.of(RoadDirection.WEST, RoadDirection.EAST)
        );
    }
}
