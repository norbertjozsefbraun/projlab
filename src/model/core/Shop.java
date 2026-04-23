package model.core;

public class Shop {

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
        if (amount <= 0) return;
        balance += amount;
    }

    /**
     * Decreases the balance with the given amount of money
     * @param amount the given amount
     * @return true or false based on the result of the deduction
     */
    public boolean deduct(int amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        return true;
    }
}
