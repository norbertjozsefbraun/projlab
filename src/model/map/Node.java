package model.map;

import model.entities.Vehicle;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    protected List<Vehicle> vehicles;

    public Node() {
        vehicles = new ArrayList<>();
    }

    public void acceptVehicle(Vehicle v) {
        //TODO
    }

    public void removeVehicle(Vehicle v) {

        vehicles.remove(v);

    }



}
