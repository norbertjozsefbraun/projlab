package model.entities;

import java.util.ArrayList;
import java.util.List;
import model.buildings.Garage;
import model.core.Player;
import model.core.Shop;
import model.items.Head;
import model.items.Purchasable;
import model.map.Field;
import model.map.Intersection;
import model.map.Road;
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

    public SnowPlow() {}

    
    /**
     * Constructor if the snowplow starts from the garage.
     * @param player the player who owns the snowplow
     * @param garage the starting building
    */
   public SnowPlow(Player player, Garage garage) {
       vehicleId = idCounter++;
       this.player = player;
       canMove = true;
       buildings = new ArrayList<>();
       buildings.add(garage);
       currentBuilding = garage;
       this.garage = garage;
       currentField = null;
       currentRoad = null;
       heads = new ArrayList<>();
       garage.enterVehicle(this);
    } 
    
    /**
     * Constructor if the snowplow.
     * @param player the player who owns the snowplow
     */
    public SnowPlow(Player player, Field field, Road road) {
        vehicleId = idCounter++;
        this.player = player;
        canMove = true;
        this.garage = null;
        buildings = new ArrayList<>();
        buildings.add(garage);
        currentBuilding = null;
        currentField = field;
        currentRoad = road;
        heads = new ArrayList<>();
        // field.acceptVehicle(this);
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(this);
        field.setVehicles(vehicles);
    } 

    /**
     * The constructor if the snowplow starts on the map.
     * @param player the player who owns the snowplow
     * @param garage the garage of the snwoplow
     * @param field the starting field
     * @param road teh starting road
     */
    public SnowPlow(Player player, Garage garage, Field field, Road road) {
        vehicleId = idCounter++;
        this.player = player;
        canMove = true;
        this.garage = garage;
        buildings = new ArrayList<>();
        buildings.add(garage);
        currentBuilding = null;
        currentField = field;
        currentRoad = road;
        heads = new ArrayList<>();
        // field.acceptVehicle(this);
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(this);
        field.setVehicles(vehicles);
    }

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
     * @param p the given price
     */
    public void setPrice(int p) {
        price = p;
    }

    /**
     * Moves the vehicle.
     */
    @Override
    public void move() {
        if (currentField == null && currentBuilding != null) {
            currentBuilding.deployVehicle(this);
            return;
        }
        if (currentField.getNextField() != null) {

            Field targetField = currentField.getNextField();
            if (direction == DirectionType.LE && targetField.getLeftNeighbour() != null) {
                targetField = targetField.getLeftNeighbour();
            } else if (direction == DirectionType.RI && targetField.getRightNeighbour() != null) {
                targetField = targetField.getRightNeighbour();
            }

            currentField.moveToNextField(this, direction);
            if (this.getGarage().getDestroyedNum() <= 4 && activeHead != null && targetField != null) {
                activeHead.clean(targetField);
            }
        } else if (currentField.getNextField() == null) {
            Intersection inter = (previousIntersection == currentRoad.getDestinationA()) ? currentRoad.getDestinationB() : currentRoad.getDestinationA();
            inter.acceptVehicle(this);
            if (this.getGarage().getDestroyedNum() < 4 && activeHead != null && currentField != null) {
                activeHead.clean(currentField);
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
            proto.changed(toString(), "activeHead", activeHead.getClass().getSimpleName().toLowerCase(), h.getClass().getSimpleName().toLowerCase());
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
    public void onPurchased(Player player, SnowPlow snowplow, int amount) {
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
