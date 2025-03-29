package com.agh.command;

import com.agh.simulation.SimulationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record StepCommand(SimulationManager manager) implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute() {
        manager.nextStep();
        LOGGER.info("Next step");
    }
}