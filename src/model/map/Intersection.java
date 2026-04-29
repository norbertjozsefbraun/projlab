package model.map;

import model.buildings.Building;
import model.entities.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Intersection extends Node {
    /// Fields:
    private int intersectionId;
    private List<Road> connectedRoads;
    private Building building;

    static int idCounter = 0;

    /// Constructors:
    public Intersection() {
        building = null;
        connectedRoads = new ArrayList<Road>();
        intersectionId = idCounter++;
    }
    public Intersection(Building building) {
        this.building = building;
        connectedRoads = new ArrayList<Road>();
        intersectionId = idCounter++;
    }

    /// Getters:
    public int getId () {
        return intersectionId;
    }
    public Building getBuilding() {
        return building;
    }
    public List<Road> getConnectedRoads() { return connectedRoads; }

    /// Setters:
    public void setConnectedRoads(List<Road> connectedRoads) {
        this.connectedRoads = connectedRoads;
    }

    public void setBuilding(Building b){
        this.building = b;
    }

    /// Functional functions:

    /**
     * Moves vehicle to the next road
      * @param v
     */
    @Override
    public void acceptVehicle(Vehicle v){

       if(connectedRoads.isEmpty()) return;

       Road targetRoad = connectedRoads.get(0);
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


    }

    /**
     * Moves vehicle to the building connected to the intersection
     * @param v
     */
    public void goToBuilding(Vehicle v){
        if(building == null) return;

        building.enterVehicle(v);

    }

}
