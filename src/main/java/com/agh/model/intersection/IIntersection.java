package com.agh.model.intersection;

import com.agh.model.road.IRoad;
import com.agh.model.road.RoadDirection;
import com.agh.model.vehicle.Vehicle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface IIntersection {
    List<IRoad> getRoads();
    IRoad getRoadByDirection(RoadDirection direction);
    boolean isCollision(List<Vehicle> vehicles);
    ArrayList<Vehicle> getMostPriority(ArrayList<Vehicle> vehicles);
}
