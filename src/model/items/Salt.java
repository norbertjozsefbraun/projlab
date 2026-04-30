package model.items;

import test.Prototype;

public class Salt extends Resource {
    
    /** Constructor for Salt resource, initializes the amount and unit price.
     * @param a the initial amount of salt
     * @param p the unit price of salt
     */
    public Salt(int a, int p){
        amount=a;
        unitPrice=p;
    }

    /** Default constructor for Salt resource, initializes the amount and unit price to default values. */
    public Salt(){}

    /**
     * Consumes the specified amount of salt.
     * @param amount the amount of salt to consume
     */
    @Override
    public void consume(int amount) {
        int oldAmount = this.amount;
        if (this.amount >= amount) {
            this.amount -= amount;
            Prototype.getInstance().changed("Salt", "amount", String.valueOf(oldAmount), String.valueOf(this.amount));
        } else {
            System.out.println("\tNincs elég só a fogyasztáshoz.");
        }
    }

}