package model.buildings;

import java.util.ArrayList;
import java.util.List;
import model.core.Game;
import model.core.Shop;
import model.entities.Bus;
import model.entities.Vehicle;

public class BusStop extends Building{
    /**
     * The list of buses in the busstop
     */
    private List<Bus> buses;

    //Fields
    private Shop shop; ///unused???
    private Game game;

    public BusStop() {
        buses = new ArrayList<>();
    }

    public BusStop(Game game) {
        buses = new ArrayList<>();
        this.game = game;
    }

    //Getters
    public List<Bus> getBuses(){
        return buses;
    }

    public Shop getShop(){
        return shop;
    }

    public Game getGame(){
        return game;
    }

    //Setters
    public void setBuses(List<Bus> buses){
        this.buses = buses;
    }

    public void setGame(Game g){
        game=g;
    }

    public void setShop(Shop s){
        shop = s;
    }

    /**
     * Adds the bus to the buses list
     * Sets the vehicles currentBuilding to this BusStop and sets its currentField to null
     * Checks, if it's the arriving bus' last stop
     * If it is, it tells the shop to credit the money and increase the bus' finished rounds
     * @param v The vehicle entering the busstop
     */
    public void enterVehicle(Vehicle v){
        buses.add((Bus)v);

        if (v.getCurrentField() != null) {
            v.getCurrentField().removeVehicle(v);
        }
        v.setCurrentBuilding(this);
        v.setCurrentField(null);

        Bus b = (Bus)v;
        // previousStop can be null for newly created buses — guard against NPE
        if (b.getPreviousStop() != null && b.getPreviousStop().equals(b.getStopB()) && this.equals(b.getStopA())) {
            if (game != null) {
                game.getShop().addFunds(18);
                game.increaseRounds();
            }
        }
    }

    /**
     * It deployes the bus towards its destination.
     * Sets its currentBuilding, previousStop and previousIntersection
     * Removes from buses list
     * @param v The vehicle leaving the busstop.
     */
    public void deployVehicle(Vehicle v){
        Bus b = (Bus)v;

        if(b.getCurrentBuilding().equals(b.getStopA())){
            v.setDestinationIntersection(b.getStopB().getLocation());
        }else if(b.getCurrentBuilding().equals(b.getStopB())){
            v.setDestinationIntersection(b.getStopA().getLocation());
        }

        this.getLocation().acceptVehicle(v);
        if (v.getCurrentField() != null) {
            b.setCurrentBuilding(null);
            b.setPreviousStop(this);
            b.setPreviousIntersection(getLocation());
            buses.remove((Bus)v);
        }
    }

}
