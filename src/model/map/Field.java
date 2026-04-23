package model.map;

import java.util.List;
import model.entities.Car;
import model.entities.SnowPlow;
import model.entities.Vehicle;

public class Field extends Node {
    /// Fields:
    private Field nextField;
    private Field rightNeighbour;
    private Field leftNeighbour;
    private Surface surface;
    private int accidentTimer;

    /// Constructor:
    public Field() {
        surface = new Surface();
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

    public void setAccidentTimer(int accidentTimer) {
        this.accidentTimer = accidentTimer;
    }



    /// Functional functions:
    public void moveToNextField(Vehicle v) {

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

    }

    private boolean isPassable() {
        return (accidentTimer == 0) && (surface.getSnowThickness() < 35);
    }

    @Override
    public void acceptVehicle(Vehicle v) {

        if(isPassable()) {
            v.getCurrentField().removeVehicle(v);
            v.setCurrentField(this);
            vehicles.add(v);
            
            surface.vehiclePasses(v);
            
            checkAccident();

            if(surface.getIsIce()) {
                v.slip(2);
            }
        }

    }

    public void addSnow(int amount){
        surface.addSnow(amount);
    }

    private void checkAccident() {
        //TODO, THIS IS JUST THE PART I NEED PLEASE IMPLEMENT GENERAL SOLUTION

        boolean hasSnowPlow = false;
        for(var currVehicle : this.vehicles) {
            if(currVehicle.getClass().getSimpleName().equals("SnowPlow")) hasSnowPlow = true;
        }

        if(this.vehicles.size() >= 2){
            for(var currVehicle : this.vehicles){
                if(currVehicle.getClass().getSimpleName().equals("SnowPlow")){
                    ((SnowPlow) currVehicle).getGarage().enterVehicle(currVehicle);

                }else if(currVehicle.getClass().getSimpleName().equals("Car")){
                    currVehicle.setCanMove(false);
                    if (hasSnowPlow) {
                        ((Car) currVehicle).getHome().enterVehicle(currVehicle);
                    }
                }
            }
        }
    }







}
