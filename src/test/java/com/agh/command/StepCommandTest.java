package com.agh.command;

import com.agh.simulation.SimulationManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class StepCommandTest {

    @Test
    void execute_shouldCallNextStepOnManager() {
        SimulationManager mockManager = mock(SimulationManager.class);
        StepCommand command = new StepCommand(mockManager);
        command.execute();
        verify(mockManager).nextStep();
    }
}