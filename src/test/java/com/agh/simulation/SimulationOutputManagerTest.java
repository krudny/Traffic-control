package com.agh.simulation;

import com.agh.model.StepStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimulationOutputManagerTest {

    @TempDir
    Path tempDir;

    private SimulationOutputManager manager;

    @BeforeEach
    void setUp() {
        manager = new SimulationOutputManager();
    }

    @Test
    void constructor_shouldInitializeEmptyStepStatusesList() {
        assertNotNull(manager.getStepStatuses());
        assertTrue(manager.getStepStatuses().isEmpty());
    }

    @Test
    void constructor_shouldConfigureObjectMapper() {
        ObjectMapper objectMapper = manager.getObjectMapper();
        assertTrue(objectMapper.isEnabled(SerializationFeature.INDENT_OUTPUT));
        assertFalse(objectMapper.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS));
        assertFalse(objectMapper.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
    }

    @Test
    void addStep_shouldAddStepStatusToList() {
        List<String> leftVehicles = Arrays.asList("car1", "car2");
        manager.addStep(leftVehicles);

        assertEquals(1, manager.getStepStatuses().size());
        StepStatus addedStatus = manager.getStepStatuses().get(0);
        assertEquals(leftVehicles, addedStatus.getLeftVehicles());
    }

    @Test
    void addStep_shouldAddMultipleSteps() {
        List<String> leftVehicles1 = Arrays.asList("car1", "car2");
        List<String> leftVehicles2 = Arrays.asList("car3");

        manager.addStep(leftVehicles1);
        manager.addStep(leftVehicles2);

        assertEquals(2, manager.getStepStatuses().size());
        assertEquals(leftVehicles1, manager.getStepStatuses().get(0).getLeftVehicles());
        assertEquals(leftVehicles2, manager.getStepStatuses().get(1).getLeftVehicles());
    }

    @Test
    void exportToJson_shouldWriteToFile() throws IOException {
        List<String> leftVehicles = Arrays.asList("car1", "car2");
        manager.addStep(leftVehicles);
        File outputFile = new File(tempDir.toFile(), "output.json");

        manager.exportToJson(outputFile.getAbsolutePath());

        assertTrue(outputFile.exists());
        String content = Files.readString(outputFile.toPath());
        assertTrue(content.contains("\"leftVehicles\""));
        assertTrue(content.contains("car1"));
        assertTrue(content.contains("car2"));
    }


    @Test
    void getStepStatuses_shouldReturnUnmodifiableList() {
        List<StepStatus> statuses = manager.getStepStatuses();
        assertEquals(0, statuses.size());
    }
}