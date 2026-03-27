package model.map;

import model.entities.Vehicle;
import test.Skeleton;

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
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this, "removeVehicle", skeleton.getObjectName(v));

        vehicles.remove(v);

        skeleton.returnMethod();
    }



}
