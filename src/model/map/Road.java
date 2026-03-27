package model.map;

import test.Skeleton;

import java.util.ArrayList;
import java.util.List;

public class Road {
    /// Fields:
    private String roadName;
    private Intersection destinationA;
    private Intersection destinationB;
    private List<Lane> lanesToA;
    private List<Lane> lanesToB;
    private RoadType roadType;
    private Skeleton skeleton = Skeleton.getInstance();

    /// Constructor:
    public Road(String name, RoadType type, int numOfLanesInOneDirection, int numOfFieldsOnOneLane) {
        roadName = name;
        roadType = type;
        lanesToA = new ArrayList<>();
        lanesToB = new ArrayList<>();

        // Initialise lanes in each direction
        for (int i = 0; i < numOfLanesInOneDirection; i++) {
            Lane newLane1 = new Lane(this, numOfFieldsOnOneLane);
            skeleton.ctor(newLane1, roadName + "Lane"+i);
            Lane newLane2 = new Lane(this, numOfFieldsOnOneLane);
            skeleton.ctor(newLane2, roadName + "LaneBack"+i);

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
        //TODO
        return null;
    }
    public String getName() {
        return roadName;
    }

    /// Setters:
    public void setDestinationA(Intersection destinationA) {
        this.destinationA = destinationA;
    }
    public void setDestinationB(Intersection destinationB) {
        this.destinationB = destinationB;
    }

    /// Functional functions:
    public void snowfall() {
        //TODO
    }

}
