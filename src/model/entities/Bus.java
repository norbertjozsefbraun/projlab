package model.entities;

import model.buildings.BusStop;
import model.map.Field;

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
     * Sets the previous stop to the given stop
     * @param prev the given stop
     */
    public void setPreviousStop(BusStop prev) {
        previousStop = prev;
    }

    @Override
    public void move(int n, Field gointTo) {
        //TODO
    }

    @Override
    public void move(int n) {
        //TODO
    }

    /**
     * This method doesn't do anything because the bus can't slip.
     */
    @Override
    public void slip(int n) {
        //TODO
    }
}
