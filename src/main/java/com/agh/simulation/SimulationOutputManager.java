package com.agh.simulation;

import com.agh.model.StepStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
public class SimulationOutputManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final List<StepStatus> stepStatuses = new ArrayList<>();

    @JsonIgnore
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SimulationOutputManager() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public void addStep(List<String> leftVehicles) {
        stepStatuses.add(new StepStatus(leftVehicles));
    }

    public void exportToJson(String filePath) {
        try {
            objectMapper.writeValue(new File(filePath), this);
            LOGGER.info("Symulacja zapisana do pliku: " + filePath);
        } catch (IOException e) {
            LOGGER.error("Błąd podczas zapisu JSON: ", e);
        }
    }
}

