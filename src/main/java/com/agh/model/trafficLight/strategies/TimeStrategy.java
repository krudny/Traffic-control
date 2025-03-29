package com.agh.model.trafficLight.strategies;

import com.agh.model.intersection.IIntersection;
import com.agh.model.trafficLight.TrafficLight;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
public class TimeStrategy implements TrafficLightStrategy{
    private final int transitionTime;
    private int currentTime;
    private final IIntersection intersection;
    private static final Logger LOGGER = LogManager.getLogger();

    public TimeStrategy(int transitionTime, IIntersection intersection) {
        if (transitionTime > 0) {
            this.transitionTime = transitionTime;
        } else {
            throw new IllegalArgumentException("Transition time must be greater than zero.");
        }

        this.intersection = intersection;
    }

    public void toggleLights() {
        currentTime += 1;
        if (currentTime % transitionTime == 0) {
            intersection.getAllTrafficLights().forEach(TrafficLight::changeState);
            LOGGER.info("Changed lights");
        }
    }
}
