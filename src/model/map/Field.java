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
        skeleton.call(this, "getRightNeighbour");

        skeleton.returnMethod("Field", skeleton.getObjectName(rightNeighbour));
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

    public void setAccidentTimer(int accidentTimer) {
        this.accidentTimer = accidentTimer;
    }



    /// Functional functions:
    public void moveToNextField(Vehicle v) {
        skeleton.call(this, "moveToNextField", skeleton.getObjectName(v));

        Field currentField = v.getCurrentField();
        if(currentField != null) {
            // get next field
            Field nextField = currentField.getNextField();
            if(nextField != null) {
                nextField.acceptVehicle(v);

                // if it didn't move, try nextField's left neighbour
                if(currentField.equals(v.getCurrentField())) {
                    Field left = nextField.getLeftNeighbour();
                    if(left != null) {
                        left.acceptVehicle(v);
                    }
                }
                if(currentField.equals(v.getCurrentField())) {
                    Field right = nextField.getRightNeighbour();
                    if(right != null) {
                        right.acceptVehicle(v);
                    }
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

        if(isPassable()) {
            v.getCurrentField().removeVehicle(v);
            v.setCurrentField(this);
            vehicles.add(v);

            surface.vehiclePasses(v);
        }

        skeleton.returnMethod();
    }

    public void addSnow(int amount){
        skeleton.call(this, "addSnow", "5");
        surface.addSnow(amount);
        skeleton.returnMethod();
    }

    private void checkAccident() {
        //TODO
    }







}
