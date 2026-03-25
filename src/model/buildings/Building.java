package model.buildings;

import model.entities.Vehicle;
import model.map.Intersection;

public abstract class Building {
    /** 
     * The intersection the building is in.
     */
    private Intersection location;


    /** 
     * Decides what the building does, if a vehicle enters it
     *  @param v The vehicle entering the building
     */
    public abstract void enterVehicle(Vehicle v);

    /** 
     * Decides what the building does, if a vehicle leaves it
     *  @param v The vehicle leaving the building
     */
    public abstract void deployVehicle(Vehicle v);

}
