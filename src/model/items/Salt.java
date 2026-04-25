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

}