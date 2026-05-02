package model.items;

import model.core.Player;
import model.core.Shop;
import model.entities.SnowPlow;
import model.map.Field;

/**
 * The Head class represents a cleaning head that can be equipped to a snowplow. It implements the CleanerEquipment and Purchasable interfaces.
 * It provides methods for cleaning fields, handling purchases, and managing the equipped status of the head.
 */
public abstract class Head implements CleanerEquipment, Purchasable {
    protected int price; // Price of the head
    protected boolean equipped = false;

    /**
     * Gets the price of the head.
     * @return the price of the head
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price of the head.
     * @param p the price to set
     */
    public void setPrice(int p){
        price=p;
    }

    /** Pays for the head in the shop if the balance is sufficient.
     * @param s the shop to pay in
     * @return true if the payment was successful, false otherwise
     */
    @Override
    public boolean pay(Shop s) {
       if (s.getBalance() >= this.price) {
            s.deduct(this.price); // Deduct the price from the shop's balance
            //System.out.println("A tranzakció sikeres végrehajtása és rögzítése"); // [cite: 121]
            return true;
        } else {
            System.out.println("A tranzakció elutasítása fedezethiány miatt"); // [cite: 123]
            return false;
        }
    }

    /**
     * Checks if the head is currently equipped.
     * @return true if the head is equipped, false otherwise
     */    public boolean isEquipped() {
        return equipped;
    }

    /**
     * Sets the equipped status of the head.
     * @param isEquipped true to equip the head, false to unequip
     */
    public void setEquipped(boolean isEquipped) {
        this.equipped = isEquipped;
    }

    /**
     * Cleans the specified field using the head's cleaning capabilities.
     * @param f the field to clean
     */
    public abstract void clean(Field f);


    /**
     * Handles the logic when the head is purchased by a player, equipping it to the player's snowplow.
     * @param player the player who purchased the head
     * @param snowplow the snowplow to equip the head to
     * @param amount the amount of the head to purchase
     */
    public void onPurchased(Player player, SnowPlow snowplow, int amount) {
        if (snowplow != null) {
            snowplow.addHead(this);  // Equip the head to the snowplow
            this.setEquipped(true);
        }
    }
}