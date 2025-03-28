package com.agh.model.trafficLight;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TrafficLight implements ITrafficLight {
    private TrafficLightSignal currentState;

    public TrafficLight() {
        currentState = TrafficLightSignal.RED;
    }

    public TrafficLight(TrafficLightSignal state) {
        this.currentState = state;
    }

    public TrafficLightSignal changeState() {
        this.currentState = switch (currentState) {
            case TrafficLightSignal.RED -> TrafficLightSignal.GREEN;
            case TrafficLightSignal.YELLOW -> TrafficLightSignal.RED;
            case TrafficLightSignal.GREEN -> TrafficLightSignal.YELLOW;
        };

        return currentState;
    }
}
