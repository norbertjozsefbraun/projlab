package model.items;

import test.Prototype;

public class Biokerosene extends Resource {
    /**
     * Constructs a new Biokerosene instance with the specified amount and unit price.
     * @param a the amount of biokerosene
     * @param p the unit price of biokerosene
     */
    public Biokerosene(int a, int p) {
        amount = a;
        unitPrice = p;
    }
    
    /** Default constructor for Biokerosene. */
    public Biokerosene(){}

    /**
     * Consumes the specified amount of biokerosene.
     * @param amount the amount of biokerosene to consume
     */
    @Override
    public void consume(int amount){
        int oldAmount = this.amount; // Store the old amount for change tracking
        if (this.amount >= amount) {
            this.amount -= amount;
            Prototype.getInstance().changed("Biokerosene", "amount", String.valueOf(oldAmount), String.valueOf(this.amount));
        } else {
            //System.out.println("\tNincs elég biokerosene a fogyasztáshoz.");
        }
    }
}