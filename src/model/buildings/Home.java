package model.buildings;

import java.util.ArrayList;
import java.util.List;
import model.entities.Car;
import model.entities.Vehicle;

public class Home extends Building{
    /**
     * The list of cars that belong to the home
     */
    private List<Car> cars;

    public Home() {
        cars = new ArrayList<>();
    }

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
     * Sets the vehicles currentBuilding to this Building and sets its currentField to null
     * @param v The vehicle entering the home
     */
    public void enterVehicle(Vehicle v){
        v.setCurrentBuilding(this);
        v.setCurrentField(null);
        v.setDestinationIntersection(v.getBuildings().get(1).getLocation());
    }

    /**
     * The home deploys the car towards its workplace
     * Sets its currentBuilding and previousIntersection
     * @param v The vehcile that's currently parked in the home
     */
    public void deployVehicle(Vehicle v){
        
        v.setCurrentBuilding(null);
        v.setPreviousIntersection(getLocation());

        getLocation().acceptVehicle(v);
    }

}
