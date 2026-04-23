package model.items;

public class Biokerosene extends Resource {
    /**
     * Constructs a new Biokerosene instance with the specified amount and unit price.
     * @param a the amount of biokerosene
     * @param p the unit price of biokerosene
     */
    public Biokerosene(int a, int p){
        amount=a;
        unitPrice=p;
    }

    public Biokerosene(){}

}