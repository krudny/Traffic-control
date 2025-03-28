package com.agh.model.road;

import com.agh.model.trafficLight.TrafficLight;
import lombok.Getter;

@Getter
public class SingleDirectionRoad {
    private final Lane inboundLane;
    private final Lane outboundLane;
    private final TrafficLight trafficLight;
    private final RoadDirection roadDirection;

    public SingleDirectionRoad(RoadDirection direction, TrafficLight trafficLight) {
        this.roadDirection = direction;
        this.trafficLight = trafficLight;
        this.inboundLane = new Lane(LaneDirection.INBOUND);
        this.outboundLane = new Lane(LaneDirection.OUTBOUND);
    }
}
