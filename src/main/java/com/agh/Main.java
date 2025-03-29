package com.agh;

import com.agh.command.CommandProcessor;
import com.agh.model.intersection.IIntersection;
import com.agh.model.intersection.OneInboundLaneIntersection;
import com.agh.model.road.RoadDirection;
import com.agh.model.vehicle.Vehicle;
import com.agh.simulation.SimulationManager;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        IIntersection intersection = new OneInboundLaneIntersection();
        SimulationManager manager = new SimulationManager(intersection);

        CommandProcessor processor = new CommandProcessor();
        try {
            processor.process(manager);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}