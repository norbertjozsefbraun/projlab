package model.core;

import test.Skeleton;

public class Shop {
    Skeleton skeleton = Skeleton.getInstance();

    /**
     * The balance that stores the amount of money the player gather.
     */
    private int balance;

    /**
     * Returns the balance
     * @return the balance
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
        skeleton.call(this, "addFunds", String.valueOf(amount));
        if (amount <= 0) {
            skeleton.returnMethod();
            return;
        }
        balance += amount;
        skeleton.returnMethod();
    }

    /**
     * Decreases the balance with the given amount of money
     * @param amount the given amount
     * @return true or false based on the result of the deduction
     */
    public boolean deduct(int amount) {
        skeleton.call(this, "deduct", String.valueOf(amount));
        if (amount <= 0 || amount > balance) {
            skeleton.returnMethod();
            return false;
        }
        balance -= amount;
        skeleton.returnMethod();
        return true;
    }
}
