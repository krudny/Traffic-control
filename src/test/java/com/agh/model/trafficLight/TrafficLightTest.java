package com.agh.model.trafficLight;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrafficLightTest {
    private TrafficLight trafficLight;

    @BeforeEach
    void setUp() {
        trafficLight = new TrafficLight();
    }

    @Test
    void testInitialState() {
        assertEquals(TrafficLightSignal.RED, trafficLight.getCurrentState(), "Initial state should be RED");
    }

    @Test
    void testChangeStateFromRedToGreen() {
        trafficLight.setCurrentState(TrafficLightSignal.RED);
        trafficLight.changeState();
        assertEquals(TrafficLightSignal.GREEN, trafficLight.getCurrentState(), "State should change from RED to GREEN");
    }

    @Test
    void testChangeStateFromGreenToRed() {
        trafficLight.setCurrentState(TrafficLightSignal.GREEN);
        trafficLight.changeState();
        assertEquals(TrafficLightSignal.RED, trafficLight.getCurrentState(), "State should change from GREEN to RED");
    }
}