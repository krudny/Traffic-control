package com.agh.command;

import com.agh.simulation.SimulationManager;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AddVehicleCommand.class, name = "addVehicle"),
        @JsonSubTypes.Type(value = StepCommand.class, name = "step")
})
public interface Command {
    void execute();
}