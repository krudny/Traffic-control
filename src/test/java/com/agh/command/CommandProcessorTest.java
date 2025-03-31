package com.agh.command;

import com.agh.model.intersection.IIntersection;
import com.agh.model.road.IRoad;
import com.agh.model.road.Lane;
import com.agh.model.road.RoadDirection;
import com.agh.model.vehicle.Vehicle;
import com.agh.simulation.SimulationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommandProcessorTest {

    @TempDir
    Path tempDir;

    private SimulationManager mockManager;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        mockManager = mock(SimulationManager.class);
        tempFile = new File(tempDir.toFile(), "commands.json");
    }

    @Test
    void process_shouldExecuteAddVehicleCommand() throws IOException {
        String json = "{\"commands\": [{\"type\": \"addVehicle\", \"vehicleId\": \"car1\", \"startRoad\": \"NORTH\", \"endRoad\": \"SOUTH\"}]}";
        Files.writeString(tempFile.toPath(), json);

        CommandProcessor processor = new CommandProcessor();
        IIntersection mockIntersection = mock(IIntersection.class);
        IRoad mockRoad = mock(IRoad.class);
        Lane mockLane = mock(Lane.class);

        when(mockManager.getIntersection()).thenReturn(mockIntersection);
        when(mockIntersection.getRoadByDirection(any(RoadDirection.class))).thenReturn(mockRoad);
        when(mockRoad.getInboundLane()).thenReturn(mockLane);

        processor.process(mockManager, tempFile.getAbsolutePath());

        verify(mockIntersection).getRoadByDirection(RoadDirection.NORTH);
        verify(mockLane).addVehicle(any(Vehicle.class));
    }

    @Test
    void process_shouldExecuteStepCommand() throws IOException {
        String json = "{\"commands\": [{\"type\": \"step\"}]}";
        Files.writeString(tempFile.toPath(), json);

        CommandProcessor processor = new CommandProcessor();

        processor.process(mockManager, tempFile.getAbsolutePath());

        verify(mockManager).nextStep();
    }

    @Test
    void process_shouldExecuteMultipleCommands() throws IOException {
        String json = "{\"commands\": [" +
                "{\"type\": \"addVehicle\", \"vehicleId\": \"car1\", \"startRoad\": \"NORTH\", \"endRoad\": \"SOUTH\"}, " +
                "{\"type\": \"step\"}" +
                "]}";
        Files.writeString(tempFile.toPath(), json);

        CommandProcessor processor = new CommandProcessor();
        IIntersection mockIntersection = mock(IIntersection.class);
        IRoad mockRoad = mock(IRoad.class);
        Lane mockLane = mock(Lane.class);

        when(mockManager.getIntersection()).thenReturn(mockIntersection);
        when(mockIntersection.getRoadByDirection(any(RoadDirection.class))).thenReturn(mockRoad);
        when(mockRoad.getInboundLane()).thenReturn(mockLane);

        processor.process(mockManager, tempFile.getAbsolutePath());

        verify(mockIntersection).getRoadByDirection(RoadDirection.NORTH);
        verify(mockLane).addVehicle(any(Vehicle.class));
        verify(mockManager).nextStep();
    }

    @Test
    void process_shouldHandleInvalidJson() {
        CommandProcessor processor = new CommandProcessor();
        File nonExistentFile = new File(tempDir.toFile(), "nonexistent.json");

        assertThrows(IOException.class, () -> processor.process(mockManager, nonExistentFile.getAbsolutePath()));
    }
}