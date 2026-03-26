package model.map;

import model.entities.Vehicle;

public class Field extends Node {
    private Field nextField;
    private Field rightNeighbour;
    private Field leftNeighbour;

    private Surface surface;

    private int accidentTimer;

    public Field(Surface surface) {
        this.surface = surface;
    }

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

    public Field getRightNeighbour() {
        return rightNeighbour;
    }

    private void checkAccident() {
        //TODO
    }







}
