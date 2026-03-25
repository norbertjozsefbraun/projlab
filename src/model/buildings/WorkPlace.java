package model.buildings;

import java.util.Map;

import model.entities.Car;
import model.entities.Vehicle;

public class WorkPlace extends Building{
    /**
     * The list of cars that are currently waiting at their workplace and how much time they have to wait at the workplace
     */
    private Map<Car, Integer> waitingCars;

    /**
     * When a car arrives at the workplace, it puts the new car into to waitingCars map and allocates them a waiting time
     * @param v The car entering the workplace
     */
    public void enterVehicle(Vehicle v){}


    /**
     * When the allocated waiting time for a car is over
     * The workplace deploys the car towards its home
     * @param v The car thats waiting time is over
     */
    public void deployVehicle(Vehicle v){}


    /**
     * After every turn it decreases the waiting time for every car waiting at the workplace
     */
    public void processWaiting(){}
}
