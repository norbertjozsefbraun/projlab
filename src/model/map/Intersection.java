package model.map;

import model.buildings.Building;
import model.entities.Vehicle;
import test.Skeleton;

import java.util.ArrayList;
import java.util.List;

public class Intersection extends Node {
    /// Fields:
    private List<Road> connectedRoads;
    private Building building;
    private Skeleton skeleton = Skeleton.getInstance();

    /// Constructors:
    public Intersection() {
        building = null;
        connectedRoads = new ArrayList<Road>();
    }
    public Intersection(Building building) {
        this.building = building;
        connectedRoads = new ArrayList<Road>();
    }

    /// Getters:
    public Building getBuilding() {
        return building;
    }
    /// Setters:
    public void setConnectedRoads(List<Road> connectedRoads) {
        this.connectedRoads = connectedRoads;
    }

    /// Functional functions:

    /**
     * Moves vehicle to the next road
      * @param v
     */
    @Override
    public void acceptVehicle(Vehicle v){
        skeleton.call(this, "acceptVehicle", skeleton.getObjectName(v));

       if(connectedRoads.isEmpty()) return;

       String[] options = connectedRoads.stream()
                .map(Road::getName)
                .toArray(String[]::new);
       int option = skeleton.getChoice("Which road to go to?", options);

       Road targetRoad = connectedRoads.get(option-1);
       Intersection destination = targetRoad.getDestinationA();
       if(this.equals(destination)) {
           destination =  targetRoad.getDestinationB();
       }

       var lanes = targetRoad.getLanesTowards(destination);

       // try to get on one of the lanes
       for(Lane lane : lanes) {
           Field prevField = v.getCurrentField();
           lane.getFirstField().acceptVehicle(v);
           // if lane changed, stop trying
           if(!prevField.equals(v.getCurrentField())) {
               break;
           }
       }

       skeleton.returnMethod();

    }

    /**
     * Moves vehicle to the building connected to the intersection
     * @param v
     */
    public void goToBuilding(Vehicle v){
        skeleton.call(this, "goToBuilding", skeleton.getObjectName(v));
        if(building == null) return;

        building.enterVehicle(v);

        skeleton.returnMethod();
    }

}
