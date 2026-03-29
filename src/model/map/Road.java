package model.map;

import java.util.ArrayList;
import java.util.List;
import test.Skeleton;

public class Road {
    /// Fields:
    private String roadName;
    private Intersection destinationA;
    private Intersection destinationB;
    private List<Lane> lanesToA;
    private List<Lane> lanesToB;
    private RoadType roadType;
    private Skeleton skeleton = Skeleton.getInstance();

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
        skeleton.call(this, "getLanesTowards", skeleton.getObjectName(destination));
        if(destinationA.equals(destination)) { skeleton.returnMethod("List<Lane>", "lanesToA"); return lanesToA; }
        if(destinationB.equals(destination)) { skeleton.returnMethod("List<Lane>", "lanesToB"); return lanesToB; }
        skeleton.returnMethod();
        return null;
    }
    public String getName() {
        if(roadName==null) return skeleton.getObjectName(this);

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
        skeleton.call(this, "snowfall");
        int choice = skeleton.getChoice("Ez az út (" + skeleton.getObjectName(this) + ") alagút?",new String[]{"Igen", "Nem"});
        if(choice == 2){
            for(Lane lane: lanesToA){
                lane.snowfall();
            }
            for(Lane lane: lanesToB){
                lane.snowfall();
            }
        }
        skeleton.returnMethod();
    }

}
