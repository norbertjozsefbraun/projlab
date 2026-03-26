package model.map;

import java.util.ArrayList;
import java.util.List;

public class Road {
    private Intersection destinationA;
    private Intersection destinationB;
    private List<Lane> lanesToA;
    private List<Lane> lanesToB;

    private RoadType roadType;

    public Road(RoadType type, Intersection a, Intersection b) {
        roadType = type;
        lanesToA = new ArrayList<>();
        lanesToB = new ArrayList<>();
        destinationA = a;
        destinationB = b;
    }

    public List<Lane> getLanesTowards(Intersection destination) {
        //TODO
        return null;
    }

    public void snowfall() {
        //TODO
    }

}
