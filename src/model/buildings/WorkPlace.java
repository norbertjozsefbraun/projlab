package model.buildings;

import java.util.Map;

import model.entities.Car;
import model.entities.Vehicle;

import test.Skeleton;

public class WorkPlace extends Building{
    /**
     * The list of cars that are currently waiting at their workplace and how much time they have to wait at the workplace
     */
    private Map<Car, Integer> waitingCars;

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
     * @param v The car entering the workplace
     */
    public void enterVehicle(Vehicle v){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this, "enterVehicle","Vehicle v");
        waitingCars.put((Car)v,  2);
        v.setCurrentBuilding(this);
        skeleton.returnMethod();
    }

    /**
     * When the allocated waiting time for a car is over
     * The workplace deploys the car towards its home
     * @param v The car thats waiting time is over
     */
    public void deployVehicle(Vehicle v){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this, "deployVehicle", "Vehicle v");
        getLocation().acceptVehicle(v);
        skeleton.returnMethod();
    }


    /**
     * After every turn it decreases the waiting time for every car waiting at the workplace
     * If a cars waiting time is over it deploys the car
     */
    public void processWaiting(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this, "processWaiting");
        waitingCars.forEach((car,time)-> {
            if(time>0){
                time--;
            }else{
                deployVehicle(car);
            }
        });
    }
}
