package model.core;

import java.util.ArrayList;
import java.util.List;
import model.entities.Vehicle;

/**
 * Represents a player in the game.
 * Responsible for tracking players and binding vehicles to them.
 */
public class Player {
    /** The unique name of the player. */
    private String name;

    /** The type of the driver: "bus" = bus driver, "plow" = snowplow driver. */
    private String type;

    /** The list of vehicles belonging to this player. */
    private List<Vehicle> vehicles;

    public Player() {
        vehicles = new ArrayList<>();
    }

    /**
     * Returns the player's name.
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the player's type.
     * @return "bus" for bus driver, "plow" for snowplow driver
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the player's type.
     * @param type the type to set ("bus" or "plow")
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the list of vehicles belonging to this player.
     * @return the player's vehicle list
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets the player's vehicle list.
     * @param vehcs the list of vehicles to assign
     */
    public void setVehicles(List<Vehicle> vehcs) {
        this.vehicles = vehcs;
    }

    /**
     * Adds a vehicle to the player's vehicle list.
     * @param v the vehicle to add
     */
    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }
}
