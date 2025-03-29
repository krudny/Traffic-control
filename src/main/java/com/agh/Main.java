package com.agh;

import com.agh.command.CommandProcessor;
import com.agh.model.intersection.IIntersection;
import com.agh.model.intersection.OneInboundLaneIntersection;
import com.agh.model.road.RoadDirection;
import com.agh.model.trafficLight.strategies.TimeStrategy;
import com.agh.model.trafficLight.strategies.TrafficLightStrategy;
import com.agh.model.vehicle.Vehicle;
import com.agh.simulation.SimulationManager;
import com.agh.simulation.TrafficState;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        IIntersection intersection = new OneInboundLaneIntersection();
        TrafficLightStrategy strategy = new TimeStrategy(3, intersection);
        SimulationManager manager = new SimulationManager(intersection, strategy);

        CommandProcessor processor = new CommandProcessor();
        try {
            processor.process(manager);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}