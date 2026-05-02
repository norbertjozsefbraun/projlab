package model.entities;

import java.util.ArrayList;
import java.util.List;
import model.buildings.Home;
import model.buildings.WorkPlace;
import model.map.Field;
import model.map.Intersection;
import model.map.Road;

public class Car extends Vehicle {
    /**
     * The starting point of the car.
     */
    private Home home;

    /**
     * The workplace where the car is heading.
     */
    private WorkPlace work;

    public Car() {}

    /**
     * The constructor for the car if it starts from work.
     * @param home The destiantion
     * @param work The starting building
     */
    public Car(WorkPlace work, Home home) {
        vehicleId = idCounter++;
        canMove = true;
        this.work = work;
        this.home = home;
        //currentBuilding = home;
        buildings = new ArrayList<>();
        buildings.add(home);
        buildings.add(work);
        currentRoad = null;
        currentField = null;
        //destinationIntersection = work.getLocation();
        work.enterVehicle(this);
    }

    /**
     * The constructor for the car if it starts from home.
     * @param home The starting building
     * @param work The destiantion
     */
    public Car(Home home, WorkPlace work) {
        vehicleId = idCounter++;
        canMove = true;
        this.home = home;
        this.work = work;
        //currentBuilding = home;
        buildings = new ArrayList<>();
        buildings.add(home);
        buildings.add(work);
        currentRoad = null;
        currentField = null;
        //destinationIntersection = work.getLocation();
        home.enterVehicle(this);
    }

    /**
     * The constructor if the car starts on the map.
     * @param field the starting field
     * @param road the starting road
     */
    public Car(Field field, Road road) {
        vehicleId = idCounter++;
        canMove = true;
        this.home = null;
        this.work = null;
        currentBuilding = null;
        buildings = new ArrayList<>();
        buildings.add(home);
        buildings.add(work);
        currentRoad = road;
        currentField = field;
        // field.acceptVehicle(this);
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(this);
        field.setVehicles(vehicles);
    }

    /**
     * The constructor if the car starts on the map.
     * @param home the home
     * @param work the workplace
     * @param field the starting field
     * @param road the starting road
     */
    public Car(Home home, WorkPlace work, Field field, Road road) {
        vehicleId = idCounter++;
        canMove = true;
        this.home = home;
        this.work = work;
        currentBuilding = null;
        buildings = new ArrayList<>();
        buildings.add(home);
        buildings.add(work);
        currentRoad = road;
        currentField = field;
        home.getLocation().setBuilding(home);
        work.getLocation().setBuilding(work);
        destinationIntersection = work.getLocation();
        // field.acceptVehicle(this);
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(this);
        field.setVehicles(vehicles);
    }

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
     * Moves the car.
     */
    @Override
    public void move() {
        if(!canMove) return;

        if (currentField == null && currentBuilding != null) {
            currentBuilding.deployVehicle(this);
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

            move();
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
