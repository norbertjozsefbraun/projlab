package model.map;

import java.util.ArrayList;
import java.util.List;
import model.entities.Car;
import model.entities.DirectionType;
import model.entities.SnowPlow;
import model.entities.Vehicle;

public class Field extends Node {
    /// Fields:
    private int fieldId;
    private Field nextField;
    private Field rightNeighbour;
    private Field leftNeighbour;
    private Surface surface;
    private int accidentTimer;

    static int idCounter = 0;

    /// Constructor:
    public Field() {
        surface = new Surface();
        nextField = null;
        rightNeighbour = null;
        leftNeighbour = null;
        fieldId = idCounter++;
    }

    /// Getters:
    public int getId() {
        return fieldId;
    }
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

    /**
     * Moving to the next field (for cars)
     * Chooses direction automatically, if it cant move to a field in one direction
     * Priority: 1:AH, 2:LE, 3:RI
     * @param v: The vehicle that is moving
     */
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

    /**
     * Moving to the next field (for snowplows and buses)
     * @param v: The vehicle that is moving
     * @param direction: AH, RI, LE
     */
    public void moveToNextField(Vehicle v, DirectionType direction) {
        Field currentField = v.getCurrentField();
        if (currentField != null) {
            Field forwardField = currentField.getNextField();

            if (forwardField != null) {
                Field targetField = forwardField;

                if (direction.equals(DirectionType.LE)) {
                    targetField = forwardField.getLeftNeighbour();
                } else if (direction.equals(DirectionType.RI)) {
                    targetField = forwardField.getRightNeighbour();
                }

                if (targetField != null) {
                    targetField.acceptVehicle(v);
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
        if (this.vehicles.size() >= 2) {
            boolean emergencyClearance = false;

            // find if there is a snowplow on the field
            for (Vehicle currVehicle : this.vehicles) {
                if (currVehicle.causesEmergencyClearance()) {
                    emergencyClearance = true;
                    break;
                }
            }

            List<Vehicle> involvedVehicles = new ArrayList<>(this.vehicles);

            // if snowplow hit other vehicles, all of them return to the start
            if (emergencyClearance) {
                for (Vehicle currVehicle : involvedVehicles) {
                    currVehicle.setCanMove(true);
                    currVehicle.returnToStart();
                }
            } else {
                // register accident
                this.accidentTimer = 5;
                for (Vehicle currVehicle : involvedVehicles) {
                    currVehicle.setCanMove(false);
                }
            }
        }
    }


    public void tickTimers() {
        if (this.accidentTimer > 0) {
            this.accidentTimer--;
        }
        if (this.surface != null) {
            this.surface.tickTimers();
        }
    }




}
