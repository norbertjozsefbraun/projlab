package model.items;

import test.Prototype;

public class Gravel extends Resource {
   /**
    * Constructor for Gravel resource.
    * @param a the initial amount of gravel
    * @param p the unit price of gravel
    */
   public Gravel(int a, int p) {
       amount = a;
       unitPrice = p;
   }

    /** Default constructor for Gravel. */
    public Gravel() {
        super();
    }

    /**
     * Consumes the specified quantity of gravel.
     * @param quantity the amount of gravel to consume
     */
    @Override
    public void consume(int quantity) {
        int oldAmount = this.amount;
        super.consume(quantity); // Call the consume method from the Resource class to handle the actual consumption logic
        Prototype.getInstance().changed("Gravel", "amount", String.valueOf(oldAmount), String.valueOf(this.amount));
    }
}