package com.agh.command;

import com.agh.simulation.SimulationManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class CommandProcessor {
    public void process(SimulationManager manager, String inputFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CommandList commandList = objectMapper.readValue(new File(inputFilePath), CommandList.class);

        for (Command command : commandList.commands()) {
            if (command instanceof AddVehicleCommand addVehicleCommand) {
                Command updatedCommand = new AddVehicleCommand(
                        addVehicleCommand.vehicleId(),
                        addVehicleCommand.startRoad(),
                        addVehicleCommand.endRoad(),
                        manager
                );
                updatedCommand.execute();
            } else if (command instanceof StepCommand stepCommand) {
                Command updatedCommand = new StepCommand(
                        manager
                );
                updatedCommand.execute();
            }
        }
    }
}