package model.entities;

import java.util.List;
import model.buildings.Building;
import model.map.Field;
import model.map.Intersection;
import model.map.Road;

public abstract class Vehicle {
    /**
     * Unique identifier of the vehicles.
     */
    protected int vehicleId;

    /**
     * The ability of the vehicle to move.
     */
    protected boolean canMove;

    /**
     * The field the vehicle is on.
     */
    protected Field currentField;

    /**
     * The road the vehicle is on.
     */
    protected Road currentRoad;

    /**
     * The building the vehicle is in.
     */
    protected Building currentBuilding;

    /**
     * The previous intersection from where the vehicle is coming.
     */
    protected Intersection previousIntersection;

    /**
     * The intersection the vehicle is heading.
     */
    protected Intersection destinationIntersection;

    /**
     * The list of buildings the vehicle can enter.
     */
    protected List<Building> buildings;
    
    /**
     * Returns the unique identifier of the vehicle
     * @return the vehicleId
     */
    public int getVehicleId() {
        return vehicleId;
    }

    /**
     * Returns wheter a vehicle can move or not
     * @return true or false based on the ability to move
     */
    public boolean getCanMove() {
        return canMove;
    }

    /**
     * Returns the current field tha vehicle is on
     * @return the field tha vhicle is on
     */
    public Field getCurrentField() {
        return  currentField;
    }

    /**
     * Returns the current road tha vehicle is on
     * @return the road tha vehicle is on
     */
    public Road getCurrentRoad() {
        return currentRoad;
    }

    /**
     * Returns the current building the vehicle is in
     * @return the current building
     */
    public Building getCurrentBuilding() {
        return currentBuilding;
    }

    /**
     * Returns the previous intersection
     * @return the previous intersection
     */
    public Intersection getPreviousIntersection() {
        return previousIntersection;
    }

    /**
     * Returns the destion intersection
     * @return the destinstion intersection
     */
    public Intersection getDestinationIntersection() {
        return destinationIntersection;
    }

    /**
     * Returns the list of buildings the vehicle can enter
     * @return the list of buildings
     */
    public List<Building> getBuildings() {
        return buildings;
    }

    /**
     * Sets the vehicleId to given value
     * @param id the given id
     */
    public void setVehicleId(int id) {
        vehicleId = id;
    }

    /**
     * Sets wehter a vehicle can move or not
     * @param ability the ability to move
     */
    public void setCanMove(boolean ability) {
        canMove = ability;
    }

    /**
     * Sets the current field to the given field
     * @param f the given field
     */
    public void setCurrentField(Field f) {
        currentField = f;
    }

    /**
     * Sets the current road to the given road
     * @param r the given road
     */
    public void setCurrentRoad(Road r) {
        currentRoad = r;
    }

    /**
     * Sets the current buidling to the given one
     * @param b the given building
     */
    public void setCurrentBuilding(Building b) {
        currentBuilding = b;
    }

    /**
     * Sets the previous intersection to the given intersection
     * @param r the given intersection
     */
    public void setPreviousIntersection(Intersection i) {
        previousIntersection = i;
    }

    /**
     * Sets the destination intersection to the given intersection
     * @param r the given intersection
     */
    public void setDestinationIntersection(Intersection i) {
        destinationIntersection = i;
    }

    /**
     * Sets the given list of buildings to the vehicle.
     * @param b the given list of buildings
     */
    public void setBuildings(List<Building> b) {
        buildings = b;
    }

    /**
     * Moves the vehicle the given number of fields.
     * @param n The number of fileds the vehicle has to move
     */
    public abstract void move(int n);

    /**
     * Slips the vehicle the given number of fileds.
     * @param n
     */
    public abstract void slip(int n);

    /**
     * Tells if the vehicle can resolve the emergency crash.
     * @return true or false (always false except snowplow, then true)
     */
    public boolean causesEmergencyClearance() {
        return false;
    }

    /**
     * Returns the vehicle to its starting position.
     */
    public abstract void returnToStart();
}
