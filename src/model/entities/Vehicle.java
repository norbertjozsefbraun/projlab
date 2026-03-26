package model.entities;

import model.map.Field;
import model.map.Intersection;
import model.map.Road;

public abstract class Vehicle {
    /**
     * Unique identifier of the vehicles.
     */
    private int vehicleId;

    /**
     * The field the vehicle is on.
     */
    private Field currentField;

    /**
     * The road the vehicle is on.
     */
    private Road currentRoad;

    /**
     * The previous intersection from where the vehicle is coming.
     */
    private Intersection previousIntersection;

    /**
     * Getter for currentField
     */
    public Field getCurrentField(){
        return currentField;
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
