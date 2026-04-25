package model.items;


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
    Gravel() {
        super();
    }

    /**
     * Consumes the specified quantity of gravel.
     * @param quantity the amount of gravel to consume
     */
    @Override
    public void consume(int quantity) {
        super.consume(quantity);
    }
}