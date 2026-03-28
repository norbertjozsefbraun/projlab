package model.entities;

import model.buildings.Building;
import model.map.Field;
import model.map.Intersection;
import model.map.Road;
import test.Skeleton;

public abstract class Vehicle {
    /**
     * Unique identifier of the vehicles.
     */
    protected int vehicleId;

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
     * Returns the unique identifier of the vehicle
     * @return the vehicleId
     */
    public int getVehicleId() {
        return vehicleId;
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
        return  previousIntersection;
    }

    /**
     * Sets the vehicleId to given value
     * @param id the given id
     */
    public void setVehicleId(int id) {
        vehicleId = id;
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
     * Moves the vehicle the given number of fields.
     * @param n The number of fileds the vehicle has to move
     */
    public void move(int n) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this, "move", String.valueOf(n));
        for (int i=0; i<n; i++) {
            currentField.moveToNextField(this);
        }
        skeleton.returnMethod();
    }

    /**
     * Slips the vehicle the given number of fileds.
     * @param n
     */
    public abstract void slip(int n);
}
