package model.entities;

import java.util.ArrayList;
import model.buildings.BusStop;
import model.core.Player;
import model.map.Field;
import model.map.Intersection;
import model.map.Road;

public class Bus extends Vehicle {
    /**
     * The player that drives the bus.
     */
    private Player player;

    /**
     * The starting busstop, one round is (stopA - stopB- stopA).
     */
    private BusStop stopA;

    /**
     * The other busstop.
     */
    private BusStop stopB;

    /**
     * The previous busstop.
     */
    private BusStop previousStop;

    public Bus() {}

    /**
     * The constructor if the bus starts from bus stop.
     * @param player the player who owns the bus
     * @param stopA the starting stop
     * @param stopB the destination stop
     */
    public Bus(Player player, BusStop stopA, BusStop stopB) {
        vehicleId = idCounter++;
        this.player = player;
        canMove = true;
        this.stopA = stopA;
        this.stopB = stopB;
        previousStop = null;
        buildings = new ArrayList<>();
        buildings.add(stopA);
        buildings.add(stopB);
        currentBuilding = stopA;
        currentRoad = null;
        currentField = null;
        stopA.enterVehicle(this);
    }

    /**
     * The constructor if the bus starts on the map.
     * @param player the player who owns the bus
     * @param stopA the starting stop
     * @param stopB the destination stop
     * @param field the starting field
     * @param road the starting road
     */
    public Bus(Player player, BusStop stopA, BusStop stopB, Field field, Road road) {
        vehicleId = idCounter++;
        this.player = player;
        canMove = true;
        this.stopA = stopA;
        this.stopB = stopB;
        previousStop = null;
        buildings = new ArrayList<>();
        buildings.add(stopA);
        buildings.add(stopB);
        currentBuilding = null;
        previousStop = stopA;
        currentRoad = road;
        currentField = field;
        field.acceptVehicle(this);
    }

    /**
     * Returns the player object.
     * @return the player
     */
    public Player getPlayerId() {
        return player;
    }

    /**
     * Returns the starting stop.
     * @return the starting busstop
     */
    public BusStop getStopA() {
        return stopA;
    }

    /**
     * Returns the other stop.
     * @return the other busstop
     */
    public BusStop getStopB() {
        return stopB;
    }

    /**
     * Returns the previous stop.
     * @return the previous busstop
     */
    public BusStop getPreviousStop() {
        return previousStop;
    }

    /**
     * Set the player to the given value.
     * @param p the given player referrence
     */
    public void setPlayer(Player p) {
        player = p;
    }

    /**
     * Sets the given stop to stopA
     * @param bs the given busstop
     */
    public void setStopA(BusStop bs) {
        stopA = bs;
    }

    /**
     * Sets the given stop to stopB
     * @param bs the given busstop
     */
    public void setStopB(BusStop bs) {
        stopB = bs;
    }

    /**
     * Sets the previous stop to the given stop
     * @param prev the given stop
     */
    public void setPreviousStop(BusStop prev) {
        previousStop = prev;
    }

    /**
     * Moves the bus the given number of fields.
     * @param n The number of fileds the bus has to move
     */
    @Override
    public void move(int n) {
        for (int i=0; i<n; i++) {
            if (!canMove) break;
            
            if (currentField == null && currentBuilding != null) {
                currentBuilding.deployVehicle(this);
                return;
            }

            Intersection inter = (previousIntersection == currentRoad.getDestinationA()) ? currentRoad.getDestinationB() : currentRoad.getDestinationA();
            if (currentField.getNextField() != null) {
                currentField.moveToNextField(this, direction);
            }
            else if(currentField.getNextField() == null){
                if (buildings.contains(inter.getBuilding())) {
                    inter.goToBuilding(this);
                    currentBuilding.deployVehicle(this);
                }
                else inter.acceptVehicle(this);
            }
        }
    }

    /**
     * This method doesn't do anything because the bus can't slip.
     */
    @Override
    public void slip(int n) {
    }

    /**
     * Retruns the bus to the prevoius stop.
     */
    @Override
    public void returnToStart() {
        previousStop.enterVehicle(this);
    }
}
