package com.agh.model.intersection;

import com.agh.model.road.IRoad;
import com.agh.model.road.RoadDirection;

import java.util.List;

public interface IIntersection {
    List<IRoad> getRoads();
    IRoad getRoadByDirection(RoadDirection direction);
}
