package model.entities;

import model.buildings.Home;
import model.buildings.WorkPlace;
import model.map.Field;

public class Car extends Vehicle {
    /**
     * The starting point of the car.
     */
    private Home home;

    /**
     * The workplace where the car is heading.
     */
    private WorkPlace work;

    /**
     * Returns the home of teh car.
     * @return home of the car
     */
    public Home getHome() {
        return home;
    }
    
    /**
     * Returns teh work of the car
     * @return workplace of the car
     */
    public WorkPlace getWorkPlace() {
        return work;
    }

    /**
     * Sets the home to the given value
     * @param h the given home
     */
    public void setHome(Home h) {
        home = h;
    }

    /**
     * Sets the work to the given value
     * @param h the given work place
     */
    public void setWork(WorkPlace w) {
        work = w;
    }

    /**
     * The car tries to find the shortest way between heme and work.
     * @param n The number of fileds the vehivle has to move
     * @param goingTo The field where the vehicle is heading
     */
    @Override
    public void move(int n, Field gointTo) {
        //TODO
    }

    /**
     * The car tries to find the shortest way between heme and work.
     * @param n The number of fileds the vehivle has to move
     */
    @Override
    public void move(int n) {
        //TODO
    }

    @Override
    public void slip(int n) {
        //TODO
    }
}
