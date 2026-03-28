package model.entities;

import model.buildings.BusStop;
import model.map.Intersection;
import test.Skeleton;

public class Bus extends Vehicle {
    /**
     * The unique identifier of the player that drives the bus.
     */
    private int playerId;

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
     * Returns the unique identifier of the player.
     * @return the playerid
     */
    public int getPlayerId() {
        return playerId;
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
     * Set the playerid to the given value.
     * @param id teh given id
     */
    public void setPlayerId(int id) {
        playerId = id;
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
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this, "move", String.valueOf(n));
        for (int i=0; i<n; i++) {

            if (currentField.getNextField() != null) {
                currentField.moveToNextField(this);
            }

            Intersection inter = (previousIntersection == currentRoad.getDestinationA()) ? currentRoad.getDestinationB() : currentRoad.getDestinationA();
            if (buildings.contains(inter.getBuilding())) {
                inter.goToBuilding(this);
                inter.getBuilding().deployVehicle(this);
            }
            inter.acceptVehicle(this);

        }
        skeleton.returnMethod();
    }

    /**
     * This method doesn't do anything because the bus can't slip.
     */
    @Override
    public void slip(int n) {
    }
}
