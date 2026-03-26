package model.map;

import model.entities.Vehicle;
import test.Skeleton;

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


    /// Functional functions:
    public void moveToNextField(Vehicle v) {
        //TODO
    }

    @Override
    public void acceptVehicle(Vehicle v) {
        //TODO
    }

    public void addSnow(int amount){
        //TODO
    }

    private void checkAccident() {
        //TODO
    }







}
