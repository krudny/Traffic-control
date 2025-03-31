package com.agh.model.road;

import com.agh.model.trafficLight.TrafficLight;

public interface IRoad {

    RoadDirection getRoadDirection();
    TrafficLight getTrafficLight();
    Lane getInboundLane();
    Lane getOutboundLane();
}
