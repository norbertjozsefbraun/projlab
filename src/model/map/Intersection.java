package model.map;

import model.buildings.Building;
import model.entities.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Intersection extends Node {
    /// Fields:
    private List<Road> connectedRoads;
    private Building building;

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
    /// Setters:

    /// Functional functions:
    @Override
    public void acceptVehicle(Vehicle v){
        //TODO
    }

    public void goToBuilding(Vehicle v){
        //TODO
    }

}
