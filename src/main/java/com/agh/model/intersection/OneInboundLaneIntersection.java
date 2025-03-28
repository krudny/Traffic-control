package com.agh.model.intersection;

import com.agh.model.road.RoadDirection;
import com.agh.model.road.SingleDirectionRoad;
import com.agh.model.trafficLight.TrafficLight;
import com.agh.model.trafficLight.TrafficLightSignal;
import lombok.Getter;

@Getter
public class OneInboundLaneIntersection {
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


}
