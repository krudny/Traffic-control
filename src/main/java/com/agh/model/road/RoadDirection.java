package com.agh.model.road;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoadDirection {
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west");

    private final String direction;

    public RoadDirection opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
        };
    }
}
