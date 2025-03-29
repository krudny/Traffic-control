package com.agh.model.trafficLight;

public interface ITrafficLight {
    TrafficLightSignal getCurrentState();
    TrafficLightSignal changeState();
    void setCurrentState(TrafficLightSignal newState);
}
