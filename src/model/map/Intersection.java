package model.map;

import model.buildings.Building;
import model.core.Session;
import model.entities.Vehicle;
import model.map.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

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

       Intersection finalDestination = v.getDestinationIntersection();
        if (finalDestination == null) {
            return;
        }
        World world = Session.getInstance().getGame().getWorld();

        // calculate next target intersection
        Queue<Intersection> route = world.calculateRoute(this, finalDestination);
        if (route == null || route.isEmpty()) {
            return;
        }

        Intersection nextIntersection = route.poll();
        if (this.equals(nextIntersection)) {
            nextIntersection = route.poll();
        }

        if (nextIntersection == null) return;

        // find roqd
        Road targetRoad = null;
        for (Road road : connectedRoads) {
            if (nextIntersection.equals(road.getDestinationA()) ||
                    nextIntersection.equals(road.getDestinationB())) {
                targetRoad = road;
                break;
            }
        }
        if (targetRoad == null) {
            return;
        }
        // find lanes to the right direction
        var lanes = targetRoad.getLanesTowards(nextIntersection);

        // try to go to one of the lanes
        for(Lane lane : lanes) {
            Field prevField = v.getCurrentField();

            lane.getFirstField().acceptVehicle(v);

            if (prevField != v.getCurrentField()) {
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
