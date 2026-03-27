package model.map;

import model.entities.Vehicle;
import test.Skeleton;

import java.util.ArrayList;
import java.util.List;

public class Field extends Node {
    /// Fields:
    private Field nextField;
    private Field rightNeighbour;
    private Field leftNeighbour;
    private Surface surface;
    private int accidentTimer;
    private Skeleton skeleton = Skeleton.getInstance();

    /// Constructor:
    public Field() {
        surface = new Surface();
        skeleton.ctor(surface, "surface" + surface.hashCode());
        nextField = null;
        rightNeighbour = null;
        leftNeighbour = null;
    }

    /// Getters:
    public Field getRightNeighbour() {
        return rightNeighbour;
    }

    public Field getLeftNeighbour() {
        return leftNeighbour;
    }

    public Field getNextField() {
        return nextField;
    }

    public Surface getSurface() {
        return surface;
    }

    public int getAccidentTimer() {
        return accidentTimer;
    }

    /// Setters:
    public void setRightNeighbour(Field rightNeighbour) {
        this.rightNeighbour = rightNeighbour;
    }

    public void setLeftNeighbour(Field leftNeighbour) {
        this.leftNeighbour = leftNeighbour;
    }

    public void setNextField(Field nextField) {
        this.nextField = nextField;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }



    /// Functional functions:
    public void moveToNextField(Vehicle v) {
        skeleton.call(this, "moveToNextField", skeleton.getObjectName(v));

        Field currentField = v.getCurrentField();
        if (currentField != null) {
            Field nextField = currentField.nextField;
            if (nextField != null) {
                if(nextField.isPassable()) {
                    nextField.acceptVehicle(v);
                }
                else if (nextField.leftNeighbour != null && nextField.leftNeighbour.isPassable()) {
                    nextField.leftNeighbour.acceptVehicle(v);
                }
                else if(nextField.rightNeighbour != null && nextField.rightNeighbour.isPassable()) {
                    nextField.rightNeighbour.acceptVehicle(v);
                }
            }
        }

        skeleton.returnMethod();
    }

    private boolean isPassable() {
        return (accidentTimer == 0) && (surface.getSnowThickness() < 35);
    }

    @Override
    public void acceptVehicle(Vehicle v) {
        skeleton.call(this, "acceptVehicle", skeleton.getObjectName(v));

        vehicles.add(v);
        v.getCurrentField().removeVehicle(v);
        
        surface.vehiclePasses(v);
        v.setCurrentField(this);
        skeleton.returnMethod();
    }

    public void addSnow(int amount){
        //TODO
        //Pls ez a fuggveny hivja meg a surface applySnow fuggvenyet is
    }

    private void checkAccident() {
        //TODO
    }







}
