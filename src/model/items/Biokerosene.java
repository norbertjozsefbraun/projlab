package model.items;

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
        if (this.amount >= amount) {
            this.amount -= amount;
        } else {
            System.out.println("\tNincs elég biokerosene a fogyasztáshoz.");
        }
    }
}