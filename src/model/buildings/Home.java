package model.buildings;

import java.util.List;

import model.entities.Car;
import model.entities.Vehicle;

import test.Skeleton;

public class Home extends Building{
    /**
     * The list of cars that belong to the home
     */
    private List<Car> cars;

    /**
     * Getter for the list of cars 
     * @return The list of cars are parked at home
     */
    public List<Car> getCars(){
        return cars;
    }

    //Setters
    public void setCars(List<Car> cars){
        this.cars = cars;
    }

    /**
     * When a car suffers an accident or arrives home, it's parked in the home, before it's deployed
     * @param v The vehicle entering the home
     */
    public void enterVehicle(Vehicle v){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this, "enterVehicle","Vehicle v");
        v.setCurrentBuilding(this);
        skeleton.returnMethod();
    }

    /**
     * The home deploys the car towards its workplace
     * @param v The vehcile that's currently parked in the home
     */
    public void deployVehicle(Vehicle v){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this, "deployVehicle", "Vehicle v");
        getLocation().acceptVehicle(v);
        skeleton.returnMethod();
    }

}
