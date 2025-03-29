package com.agh.model.vehicle;

import com.agh.model.road.RoadDirection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Vehicle {
    private final String id;
    private final Route route;
    private VehicleStatus status;

    public Vehicle(String id, RoadDirection startDirection, RoadDirection endDirection) {
        this.id = id;
        this.route = new Route(startDirection, endDirection);
        this.status = VehicleStatus.AT_QUEUE;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", route=" + route +
                ", status=" + status +
                '}';
    }
}
