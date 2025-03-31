package com.agh.model.vehicle;

import com.agh.model.road.RoadDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class RouteTest {

    @Test
    void constructor_shouldCreateRouteWithCorrectDirections() {
        RoadDirection sourceDirection = RoadDirection.NORTH;
        RoadDirection destinationDirection = RoadDirection.SOUTH;
        Route route = new Route(sourceDirection, destinationDirection);
        assertEquals(sourceDirection, route.sourceDirection());
        assertEquals(destinationDirection, route.destinationDirection());
    }

    @Test
    void equals_shouldReturnTrueForEqualRoutes() {
        Route route1 = new Route(RoadDirection.NORTH, RoadDirection.SOUTH);
        Route route2 = new Route(RoadDirection.NORTH, RoadDirection.SOUTH);

        assertEquals(route1, route2);
        assertEquals(route1.hashCode(), route2.hashCode());
    }

    @Test
    void equals_shouldReturnFalseForDifferentRoutes() {
        Route route1 = new Route(RoadDirection.NORTH, RoadDirection.SOUTH);
        Route route2 = new Route(RoadDirection.EAST, RoadDirection.WEST);

        assertNotEquals(route1, route2);
    }

    @Test
    void toString_shouldReturnCorrectString() {
        Route route = new Route(RoadDirection.NORTH, RoadDirection.SOUTH);
        String result = route.toString();

        assertTrue(result.contains("NORTH"));
        assertTrue(result.contains("SOUTH"));
    }
}