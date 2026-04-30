package model.map;

import java.util.ArrayList;
import java.util.List;
import model.map.RoadType;

public class Road {
    /// Fields:
    private int roadId;
    private String roadName;
    private Intersection destinationA;
    private Intersection destinationB;
    private List<Lane> lanesToA;
    private List<Lane> lanesToB;
    private RoadType roadType;

    static int idCounter = 0;

    /// Constructors:
    public Road(){
        lanesToA = new ArrayList<>();
        lanesToB = new ArrayList<>();
        roadId = idCounter++;
    }
    public Road(String name, RoadType type, int numOfLanesInOneDirection, int numOfFieldsOnOneLane) {
        roadName = name;
        roadType = type;
        lanesToA = new ArrayList<>();
        lanesToB = new ArrayList<>();
        roadId = idCounter++;

        // Initialise lanes in each direction
        for (int i = 0; i < numOfLanesInOneDirection; i++) {
            Lane newLane = new Lane(this, numOfFieldsOnOneLane);
            lanesToB.add(newLane);
        }
        for (int i = 0; i < numOfLanesInOneDirection; i++) {
            Lane newLane = new Lane(this, numOfFieldsOnOneLane);
            lanesToA.add(newLane);
        }

        // Link left and right neighbours
        for (int i = 0; i < numOfLanesInOneDirection; i++) {
            for (int j = 0; j < numOfFieldsOnOneLane; j++) {

                Field currentFieldA = lanesToA.get(i).getField(j);
                Field currentFieldB = lanesToB.get(i).getField(j);

                // Set left neighbour (if not the leftmost lane)
                if (i > 0) {
                    currentFieldA.setLeftNeighbour(lanesToA.get(i - 1).getField(j));
                    currentFieldB.setLeftNeighbour(lanesToB.get(i - 1).getField(j));
                }

                // Set right neighbour (if not the rightmost lane)
                if (i < numOfLanesInOneDirection - 1) {
                    currentFieldA.setRightNeighbour(lanesToA.get(i + 1).getField(j));
                    currentFieldB.setRightNeighbour(lanesToB.get(i + 1).getField(j));
                }
            }
        }
    }

    /// Getters:
    public String getName() {
        return roadName;
    }
    public int getId() { return roadId; }
    public Intersection getDestinationA() {
        return destinationA;
    }
    public Intersection getDestinationB() {
        return destinationB;
    }
    public RoadType getRoadType() { return roadType; }
    public List<Lane> getLanesToA() { return lanesToA; }
    public List<Lane> getLanesToB() { return lanesToB; }
    public List<Lane> getLanesTowards(Intersection destination) {
        List<Lane> result;

        if (destination.equals(this.destinationA)) {
            result = this.lanesToA;
        } else if (destination.equals(this.destinationB)) {
            result = this.lanesToB;
        } else {
            result = new ArrayList<>();
        }
        return result;
    }

    /// Setters:
    public void setDestinationA(Intersection destinationA) {
        this.destinationA = destinationA;
    }
    public void setDestinationB(Intersection destinationB) {
        this.destinationB = destinationB;
    }
    public void setLanesToA(List<Lane> lanesToA) {
        this.lanesToA = lanesToA;
    }
    public void setLanesToB(List<Lane> lanesToB) {
        this.lanesToB = lanesToB;
    }

    /// Functional functions:
    public void snowfall() {
        for(Lane lane: lanesToA){
            lane.snowfall();
        }
        for(Lane lane: lanesToB){
            lane.snowfall();
        }
    }

    public void tickTimers() {
        if (lanesToA != null) {
            for (Lane lane : lanesToA) {
                lane.tickTimers();
            }
        }
        if (lanesToB != null) {
            for (Lane lane : lanesToB) {
                lane.tickTimers();
            }
        }
    }

}
