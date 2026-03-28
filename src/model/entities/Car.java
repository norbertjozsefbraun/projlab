package model.entities;

import model.buildings.Home;
import model.buildings.WorkPlace;
import test.Skeleton;

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
     * Moves the car only one field.
     * @param n not relevant
     */
    @Override
    public void move(int n) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this, "move", "1");
        currentField.moveToNextField(this);
        skeleton.returnMethod();
    }

    /**
     * Slips the car the given number of fileds.
     * @param n the number of fields it slips
     */
    @Override
    public void slip(int n) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this, "slip", String.valueOf(n));
        for (int i=0; i<n; i++) {
            move(1);
        }
        skeleton.returnMethod();
    }
}
