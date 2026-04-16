package model.items;
import model.map.Field;

import test.Skeleton;

public class GravelSpreader extends ResourceConsumingHead {
    private Gravel gravel = new Gravel();

    public void setGravel(Gravel gravel) {
        this.gravel = gravel;
    }

    /**
     * Cleans the field by spreading gravel on it using the GravelSpreader head.
     * Consumes gravel if the head has resources, otherwise does nothing.
     * @param f the field to clean
     */
    @Override
    public void clean(Field f) {
        if (this.hasResource()) {
            gravel.consume(5); 
            //f.getSurface().addGravel();
        } else {
            System.out.println("\tGravelSpreader üres, a szórás elmarad.");
        }
    }
    /**
     * Refills the GravelSpreader head with the specified resource.
     * @param r the resource to refill with
     */
    @Override
    public void refill(Resource r) {
        this.addAmount(10);
    }

    /**
     * Adds the specified amount of gravel to the GravelSpreader head.
     * @param amount the amount of gravel to add
     */
    public void addAmount(int amount) {
        this.gravel.add(amount);
    }
}