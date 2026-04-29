package model.entities;

import java.util.List;
import model.buildings.Garage;
import model.core.Player;
import model.core.Shop;
import model.items.Head;
import model.items.Purchasable;
import model.map.Field;
import model.map.Intersection;
import test.Prototype;

public class SnowPlow extends Vehicle implements Purchasable {
    Prototype proto = Prototype.getInstance();

    /**
     * The player that drives the snowplow.
     */
    private Player player;
    
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
     * Returns the player.
     * @return the player
     */
    public Player getPlayer() {
        return player;
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
     * Set the player to the given object.
     * @param p the given player
     */
    public void setPlayer(Player p) {
        player = p;
    } 

    /**
     * Setes the garage to the given value
     * @param g the given garage
     */
    public void setGarage(Garage g) {
        garage = g;
    }

    /**
     * Sets the list of heads to the given list.
     * @param heads the given list of heads
    */
   public void setHeads(List<Head> heads) {
        proto.changed(toString(), "setActiveHead", activeHead.getClass().getSimpleName(), h.getClass().getSimpleName());
       this.heads = heads;
    }
    
    /**
     * Sets the active head to the given head
     * @param h the given head
     */
    public void setActiveHead(Head h) {
        proto.changed(toString(), "setActiveHead", activeHead.getClass().getSimpleName(), h.getClass().getSimpleName());
        activeHead = h;
    }

    /**
     * Sets the price to the given value
     * @param p the given price
     */
    public void setPrice(int p) {
        price = p;
    }

    /**
     * Moves the vehicle the given number of fields.
     * @param n The number of fileds the vehivle has to move
     */
    @Override
    public void move(int n) {
        for (int i=0; i<n; i++) {
            if (currentField.getNextField() != null) {
                currentField.moveToNextField(this, direction);
                if(this.getGarage().getDestroyedNum() < 4) {
                    activeHead.clean(currentField);
                }   
            }
            else if(currentField.getNextField() == null){
                Intersection inter = (previousIntersection == currentRoad.getDestinationA()) ? currentRoad.getDestinationB() : currentRoad.getDestinationA();
                inter.acceptVehicle(this);
                if(this.getGarage().getDestroyedNum() < 4) {
                    activeHead.clean(currentField);
                }
            }
        }
    }
  
    /**
     * This method doesn't do anything because the snowplow can't slip.
    */
   @Override
   public void slip(int n) {
    }
    
    /**
     * Removes the current head and mounts the given one if it is available.
     * @param h The head that will be mounted.
    */
   public void changeHead(Head h) {
        if (heads.contains(h)) {
            activeHead.setEquipped(false);
            h.setEquipped(true);
            activeHead = h;
        }
    }

    /**
     * Add the given head to the list of heads if possible
     * @param h the given head
     */
    public void addHead(Head h) {
        if (!heads.contains(h)) {
            heads.add(h);
        }
    }
    
    /**
     * Pays for the new snowplow.
     * @param s the shop
     */
    @Override
    public boolean pay(Shop s) {
        if (s.deduct(price)) {
            return true;
        }
        return false;
    }

    /**
     * Gives the player a snowplow.
     * @param player the player
     * @param amount how many (only one can be at once)
     * @param snowplow null
     */
    @Override
    public void onPurchased(Player player, int amount, SnowPlow snowplow) {
        player.addVehicle(this);
    }

    /**
     * Tells if the vehicle can resolve the emergency crash.
     * @return true or false (always false except snowplow, then true)
     */
    @Override
    public boolean causesEmergencyClearance() {
        return true;
    }

    /**
     * Retruns the snowplow to the garage.
     */
    @Override
    public void returnToStart() {
        garage.enterVehicle(this);
    }
}
