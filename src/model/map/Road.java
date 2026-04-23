package model.map;

import java.util.ArrayList;
import java.util.List;
import model.map.RoadType;

public class Road {
    /// Fields:
    private String roadName;
    private Intersection destinationA;
    private Intersection destinationB;
    private List<Lane> lanesToA;
    private List<Lane> lanesToB;
    private RoadType roadType;

    /// Constructors:
    public Road(){
        lanesToA = new ArrayList<>();
        lanesToB = new ArrayList<>();
    }
    public Road(String name, RoadType type, int numOfLanesInOneDirection, int numOfFieldsOnOneLane) {
        roadName = name;
        roadType = type;
        lanesToA = new ArrayList<>();
        lanesToB = new ArrayList<>();

        // Initialise lanes in each direction
        for (int i = 0; i < numOfLanesInOneDirection; i++) {
            Lane newLane1 = new Lane(this, numOfFieldsOnOneLane);
            Lane newLane2 = new Lane(this, numOfFieldsOnOneLane);

            lanesToA.add(newLane1);
            lanesToB.add(newLane2);
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
    public List<Lane> getLanesTowards(Intersection destination) {
        if(destinationA.equals(destination)) { return lanesToA; }
        if(destinationB.equals(destination)) { return lanesToB; }
        return null;
    }
    public String getName() {
        return roadName;
    }
    public Intersection getDestinationA() {
        return destinationA;
    }
    public Intersection getDestinationB() {
        return destinationB;
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

}
