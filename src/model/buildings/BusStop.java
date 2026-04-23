package model.buildings;

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
    private Shop shop;
    private Game game;

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
     * Checks, if it's the arriving bus' last stop
     * If it is, it tells the shop to credit the money and increase the bus' finished rounds
     * @param v The vehicle entering the busstop
     */
    public void enterVehicle(Vehicle v){
        buses.add((Bus)v);
        v.setCurrentBuilding(this);
        Bus b = (Bus)v;
        if(b.getPreviousStop().equals(b.getStopB()) && this.equals(b.getStopA())){
            game.getShop().addFunds(18);
            game.increaseRounds();
        }
    }

    /**
     * It deployes the bus towards its destination.
     * @param v The vehicle leaving the busstop.
     */
    public void deployVehicle(Vehicle v){
        Bus b = (Bus)v;
        b.setPreviousStop(this);
        buses.remove((Bus)v);
        this.getLocation().acceptVehicle(v);
    }

}
