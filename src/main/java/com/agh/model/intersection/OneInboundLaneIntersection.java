package com.agh.model.intersection;

import com.agh.model.road.IRoad;
import com.agh.model.road.RoadDirection;
import com.agh.model.road.SingleDirectionRoad;
import com.agh.model.trafficLight.TrafficLight;
import com.agh.model.trafficLight.TrafficLightSignal;
import com.agh.model.vehicle.Route;
import com.agh.model.vehicle.Vehicle;
import com.agh.model.vehicle.VehicleStatus;
import lombok.Getter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class OneInboundLaneIntersection implements IIntersection {
    private final SingleDirectionRoad northRoad;
    private final SingleDirectionRoad southRoad;
    private final SingleDirectionRoad westRoad;
    private final SingleDirectionRoad eastRoad;

    public OneInboundLaneIntersection() {
        TrafficLight northTrafficLight = new TrafficLight(TrafficLightSignal.GREEN);
        TrafficLight southTrafficLight = new TrafficLight(TrafficLightSignal.GREEN);
        TrafficLight westTrafficLight = new TrafficLight(TrafficLightSignal.RED);
        TrafficLight eastTrafficLight = new TrafficLight(TrafficLightSignal.RED);

        this.northRoad = new SingleDirectionRoad(RoadDirection.NORTH, northTrafficLight);
        this.southRoad = new SingleDirectionRoad(RoadDirection.SOUTH, southTrafficLight);
        this.westRoad = new SingleDirectionRoad(RoadDirection.WEST, westTrafficLight);
        this.eastRoad = new SingleDirectionRoad(RoadDirection.EAST, eastTrafficLight);
    }

    public List<IRoad> getRoads() {
        return List.of(northRoad, southRoad, eastRoad, westRoad);
    }

    public IRoad getRoadByDirection(RoadDirection direction) {
        return switch (direction) {
            case NORTH -> northRoad;
            case SOUTH -> southRoad;
            case WEST -> westRoad;
            case EAST -> eastRoad;
        };
    }

    public boolean isCollision(List<Vehicle> vehicles) {
        if (vehicles.size() < 2) {
            return false;
        }

        RoadDirection direction1 = vehicles.getFirst().getRoute().destinationDirection();
        RoadDirection direction2 = vehicles.getLast().getRoute().destinationDirection();

        return Set.of(
                Set.of(RoadDirection.NORTH, RoadDirection.EAST),
                Set.of(RoadDirection.WEST, RoadDirection.SOUTH),
                Set.of(RoadDirection.EAST, RoadDirection.SOUTH),
                Set.of(RoadDirection.NORTH, RoadDirection.WEST)
        ).contains(Set.of(direction1, direction2));
    }

    public ArrayList<Vehicle> getMostPriority(ArrayList<Vehicle> vehicles) {
        Vehicle vehicle1 = vehicles.getFirst();
        Vehicle vehicle2 = vehicles.getLast();

        if (!isCollision(vehicles)) {
            return vehicles;
        }

        if (isTurningRight(vehicle1.getRoute()) && !isGoingForward(vehicle2.getRoute())) {
            return new ArrayList<>(List.of(vehicle1));
        }

        if (isGoingForward(vehicle1.getRoute())) {
            return new ArrayList<>(List.of(vehicle1));
        }

        if (isGoingForward(vehicle2.getRoute())) {
            return new ArrayList<>(List.of(vehicle2));
        }

        return new ArrayList<>(List.of(vehicle1));
    }


    public boolean isGoingForward(Route route) {
        return (route.sourceDirection() == route.destinationDirection().opposite());
    }

    public boolean isTurningRight(Route route) {
        return (route.sourceDirection() == route.destinationDirection().right());
    }

    public List<TrafficLight> getAllTrafficLights() {
        return getRoads().stream()
                .map(IRoad::getTrafficLight)
                .collect(Collectors.toList());
    }
}
