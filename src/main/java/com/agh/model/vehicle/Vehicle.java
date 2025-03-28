package com.agh.model.vehicle;

import com.agh.model.road.RoadDirection;

public class Vehicle {
    private final Integer id;
    private final Route route;
    private VehicleStatus status;

    public Vehicle(Integer id, RoadDirection startDirection, RoadDirection endDirection) {
        this.id = id;
        this.route = new Route(startDirection, endDirection);
        this.status = VehicleStatus.AT_QUEUE;
    }
}
