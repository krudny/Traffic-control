package com.agh.model.vehicle;

import com.agh.model.road.RoadDirection;

public record Route(RoadDirection sourceDirection, RoadDirection destinationDirection) {}