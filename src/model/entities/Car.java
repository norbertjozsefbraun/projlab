package model.entities;

import model.buildings.Home;
import model.buildings.WorkPlace;
import model.map.Intersection;

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
     * Returns the work of the car
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

        if(!canMove) return;

        if(currentField == null && !(this.getCurrentBuilding().equals(null))){
            getCurrentBuilding().deployVehicle(this);
            return;
        }

        if (currentField.getNextField() != null) {
            currentField.moveToNextField(this);
                return;
        }

        if(!canMove) {
            return;
        }

        Intersection inter = (previousIntersection == currentRoad.getDestinationA()) ? currentRoad.getDestinationB() : currentRoad.getDestinationA();
        if (buildings.contains(inter.getBuilding())) {
            inter.goToBuilding(this);
            return;
        }
        inter.acceptVehicle(this);
    }

    /**
     * Slips the car the given number of fileds.
     * @param n the number of fields it slips
     */
    @Override
    public void slip(int n) {
        for (int i=0; i<n; i++) {
            if (!canMove) break;

            move(1);
        }
    }

    /**
     * Retruns the car to its home.
     */
    @Override
    public void returnToStart() {
        home.enterVehicle(this);
    }
}
