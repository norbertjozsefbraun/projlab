package model.buildings;

import java.util.List;

import model.core.Game;
import model.entities.Bus;
import model.entities.Vehicle;

public class BusStop extends Building{
    /**
     * The list of buses that has the BusStop as one of their stops
     */
    private List<Bus> buses;

    /**
     * A game object
     */
    private Game game;
   
    /**
     * Checks, if it's the arriving bus' last stop
     * If it is, it tells the shop to credit the money and increase the bus' finished rounds
     * @param v The vehicle entering the busstop
     */
    public void enterVehicle(Vehicle v){}


    /**
     * It deployes the bus towards its destination.
     * @param v The vehicle leaving the busstop.
     */
    public void deployVehicle(Vehicle v){}

}
