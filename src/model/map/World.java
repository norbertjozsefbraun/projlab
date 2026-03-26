package model.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class World {
    private List<Road> roads;
    private List<Intersection> intersections;

    public World(){
        roads = new ArrayList<Road>();
        intersections = new ArrayList<Intersection>();
    }

    public List<Lane> getLanesTowards(Intersection destination) {
        //TODO
        return null;
    }

    public Queue<Intersection> calculateRoute(Intersection start, Intersection destination) {
        //TODO
        return null;
    }

    public void snowfall() {
        //TODO
    }

    public void tickTimers() {
        //TODO
    }
}
