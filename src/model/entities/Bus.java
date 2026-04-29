package model.entities;

import model.buildings.BusStop;
import model.core.Player;
import model.map.Intersection;

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
