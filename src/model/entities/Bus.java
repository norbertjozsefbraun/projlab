package model.entities;

import model.buildings.BusStop;

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
     * The current stop the bus is in, null if not in bus stop.
     */
    private BusStop currentStop;

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
     * Returns the current bus stop the bus is in
     * @return the current busstop
     */
    public BusStop getCurrentStop() {
        return currentStop;
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
     * Sets the current stop to the given stop
     * @param prev the given stop
     */
    public void setCurrentStop(BusStop cs) {
        currentStop = cs;
    }

    /**
     * This method doesn't do anything because the bus can't slip.
     */
    @Override
    public void slip(int n) {
    }
}
