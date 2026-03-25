package model.buildings;

import java.util.List;

import model.entities.Car;
import model.entities.Vehicle;

public class Home extends Building{
    /**
     * The list of cars that belong to the home
     */
    private List<Car> cars;

    /**
     * When a car suffers an accident or arrives home, it's parked in the home, before it's deployed
     * @param v The vehicle entering the home
     */
    public void enterVehicle(Vehicle v){}

    /**
     * The home deploys the car towards its workplace
     * @param v The vehcile that's currently parked in the home
     */
    public void deployVehicle(Vehicle v){}

}
