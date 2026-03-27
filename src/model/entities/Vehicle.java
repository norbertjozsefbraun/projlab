package model.entities;

import model.map.Field;
import model.map.Intersection;
import model.map.Road;

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
     * @return the road tha vhicle is on
     */
    public Road getCurrentRoad() {
        return currentRoad;
    }

    /**
     * Returns the previous intersection
     * @return teh previous intersection
     */
    public Intersection getPreviousIntersection() {
        return  previousIntersection;
    }

    /**
     * Sets teh vehicleId to given value
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
     * Sets the previous intersection to the given intersection
     * @param r the given intersection
     */
    public void setPreviousIntersection(Intersection i) {
        previousIntersection = i;
    }

    /**
     * Moves the vehicle the given number of field to the direction of the given field.
     * @param n The number of fileds the vehivle has to move
     * @param goingTo The field where the vehicle is heading
     */
    public abstract void move(int n, Field goingTo);

    /**
     * Moves the vehicle the given number of fields.
     * @param n The number of fileds the vehivle has to move
     */
    public abstract void move(int n);

    /**
     * Slips the vehicle the given number of fileds.
     * @param n
     */
    public abstract void slip(int n);
}
