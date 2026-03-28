package model.map;

import test.Skeleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class World {
    /// Fields:
    private List<Road> roads;
    private List<Intersection> intersections;
    private Skeleton skeleton = Skeleton.getInstance();

    /// Constructor:
    public World(){
        roads = null;
        intersections = null;
    }

    /// Getters:
    public List<Lane> getLanesTowards(Intersection destination) {
        //TODO
        return null;
    }

    /// Setters:
    public void setRoads(List<Road> roads){
        this.roads = roads;
    }

    public void setIntersections(List<Intersection> intersections){
        this.intersections = intersections;
    }


    /// Functional functions:
    public Queue<Intersection> calculateRoute(Intersection start, Intersection destination) {
        //TODO
        return null;
    }

    public void snowfall() {
        skeleton.call(this, "snowfall");
        for(Road road: roads){
            road.snowfall();
        }
        skeleton.returnMethod();
    }

    public void tickTimers() {
        //TODO
    }
}
