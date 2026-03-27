package model.entities;

import java.util.List;
import model.buildings.Garage;
import model.core.Shop;
import model.items.Head;
import model.items.Purchasable;
import model.map.Field;

public class SnowPlow extends Vehicle implements Purchasable {
    /**
     * The unique identifier of the player that drives the snowplow.
     */
    private int playerId;
    
    /**
     * The garage where the snowplow belongs.
     */
    private Garage garage;

    /**
     * The cleaning heads that belong to the snowplow.
     */
    private List<Head> heads;

    /**
     * This head is mounted to the snowplow, this gets the cleaning done.
     */
    private Head activeHead;

    /**
     * The price of a new snowplow
     */
    private int price;

    /**
     * Returns the unique identifier of the player.
     * @return the playerid
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Returns tha garage of the snowplow
     * @return the garage of the snowplow
     */
    public Garage getGarage() {
        return garage;
    }
    
    /**
     * Returns the list of heads the snowplow has.
     * @return list of heads
     */
    public List<Head> getHeads() {
        return heads;
    }

    /**
     * Returns the active head of the snwoplow
     * @return teh active head
     */
    public Head getActiveHead() {
        return activeHead;
    }

    /**
     * Returns the price of the snowplow.
     * @return the price of the snowplow
     */
    @Override
    public int getPrice() {
        return price;
    }
    
    /**
     * Set the playerid to the given value.
     * @param id teh given id
     */
    public void setPlayerId(int id) {
        playerId = id;
    } 

    /**
     * Setes the garage to the given value
     * @param g teh given garage
     */
    public void setGarage(Garage g) {
        garage = g;
    }

    /**
     * Sets the list of heads to the given list.
     * @param heads the given list of heads
    */
   public void setHeads(List<Head> heads) {
       this.heads = heads;
    }
    
    /**
     * Sets the active head to the given head
     * @param h the given head
     */
    public void setActiveHead(Head h) {
        activeHead = h;
    }

    /**
     * Sets the price to the given value
     * @param p teh given price
     */
    public void setPrice(int p) {
        price = p;
    }

    @Override
    public void move(int n, Field gointTo) {
        //TODO;
    }
    
    @Override
    public void move(int n) {
        //TODO
    }
    
    /**
     * This method doesn't do anything because the snowplow can't slip.
    */
   @Override
   public void slip(int n) {
       //TODO
    }
    
    /**
     * Removes the current head and mounts the given one if it is available.
     * @param h The head that will be mounted.
    */
   public void changeHead(Head h) {
        //TODO
    }
    
    /**
     * Pays for the new snowplow.
     * @param s the shop
     */
    @Override
    public void pay(Shop s) {
        //TODO
    }
}
