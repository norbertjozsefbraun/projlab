package model.buildings;

import java.util.HashMap;
import java.util.Map;
import model.entities.Car;
import model.entities.Vehicle;

public class WorkPlace extends Building{
    /**
     * The list of cars that are currently waiting at their workplace and how much time they have to wait at the workplace
     */
    private Map<Car, Integer> waitingCars;

    public WorkPlace() {
        waitingCars = new HashMap<>();
    }

    /**
     * Getter for the map is waitincars
     * @return The map of cars that are currently waiting at their workplace
     */
    public Map<Car, Integer> getWaitingCars(){
        return waitingCars;
    }

    //Setter
    public void setWaitingCars(Map<Car, Integer> wc){
        this.waitingCars = wc;
    }

    /**
     * When a car arrives at the workplace, it puts the new car into to waitingCars map and allocates them a waiting time
     * Sets the vehicles currentBuilding to this Building and sets its currentField to null
     * @param v The car entering the workplace
     */
    public void enterVehicle(Vehicle v){
        waitingCars.put((Car)v,  2);
        v.setCurrentBuilding(this);
        v.setCurrentField(null);
        v.setDestinationIntersection(v.getBuildings().get(0).getLocation());
    }

    /**
     * When the allocated waiting time for a car is over
     * The workplace deploys the car towards its home
     * Sets its currentBuilding and previousIntersection
     * @param v The car thats waiting time is over
     */
    public void deployVehicle(Vehicle v){
        if(waitingCars.getOrDefault(v, -1) == 0){
            v.setCurrentBuilding(null);
            v.setPreviousIntersection(getLocation());
            getLocation().acceptVehicle(v);
        }else{
            processWaiting();
        }
    }


    /**
     * After every turn it decreases the waiting time for every car waiting at the workplace
     * If a cars waiting time is over it deploys the car
     */
    public void processWaiting(){
        waitingCars.forEach((car,time)-> {
            if(time>0){
                waitingCars.computeIfPresent(car, (c, i) -> i-1);
            }else{
                deployVehicle(car);
            }
        });
    }
}
