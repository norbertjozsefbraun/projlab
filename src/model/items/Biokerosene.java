package model.items;
import test.Skeleton;

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

    /**
     * Consumes the specified quantity of biokerosene.
     * @param quantity the amount of biokerosene to consume
     */
    @Override
    public void consume(int quantity) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "consume", String.valueOf(quantity));

        super.consume(quantity);

        sk.returnMethod();
    }
}