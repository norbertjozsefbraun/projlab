package model.core;

import model.entities.SnowPlow;
import model.items.Purchasable;

public class Shop {
    /**
     * The balance that stores the amount of money the player gather.
     */
    private int balance;

    /**
     * Returns the balance
     * @return teh balance
     */
    public int getBalance() {
        return balance;
    }
    
    /**
     * Sets the balance to the given amount
     * @param n the given amount
     */
    public void setBalance(int n) {
        balance = n;
    }

    /**
     * Increases the balance with the given amount of money
     * @param amount the given amount
     */
    public void addFunds(int amount) {
        balance += amount;
    }

    /**
     * Decreases the balance with the given amount of money
     * @param amount the given amount
     */
    public void deduct(int amount) {
        balance += amount;
    }

    /**
     * Checks the price of the item and pays the money
     * @param item the purchusable item
     * @param sp the snowplow that gets the item
     */
    public void transaction(Purchasable item, SnowPlow sp) {
        //TODO
    }
}
