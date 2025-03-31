package com.agh;


import com.agh.command.CommandProcessor;
import com.agh.model.intersection.IIntersection;
import com.agh.model.intersection.OneInboundLaneIntersection;
import com.agh.model.trafficLight.strategies.TimeStrategy;
import com.agh.model.trafficLight.strategies.TrafficLightStrategy;
import com.agh.simulation.SimulationManager;
import com.agh.simulation.SimulationOutputManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String inputFilePath = args.length > 0 ? args[0] : "examplein.json";
        String outputFilePath = args.length > 1 ? args[1] : "exampleout.json";

        IIntersection intersection = new OneInboundLaneIntersection();
        TrafficLightStrategy strategy = new TimeStrategy(3, intersection);
        SimulationOutputManager outputManager = new SimulationOutputManager();
        SimulationManager manager = new SimulationManager(intersection, strategy, outputManager);

        CommandProcessor processor = new CommandProcessor();

        try {
            processor.process(manager, inputFilePath);
        } catch (IOException e) {
            System.err.println("Błąd podczas wczytywania pliku wejściowego: " + inputFilePath);
            e.printStackTrace();
            System.exit(1);
        }

        manager.saveSimulationOutput(outputFilePath);
    }
}
