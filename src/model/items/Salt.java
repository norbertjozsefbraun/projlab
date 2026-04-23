package model.items;

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

    /** Consumes the specified quantity of salt, reducing the amount accordingly.
     * @param quantity the quantity of salt to consume
     */
    @Override
    public void consume(int quantity) {

        super.consume(quantity);

    }
}