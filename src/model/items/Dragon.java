package model.items;
import test.Skeleton;
import model.map.Field;

public class Dragon extends ResourceConsumingHead {
    private Biokerosene kerosene = new Biokerosene();

    /**
     * Set the kerosene resource for the Dragon head.
     * @param kerosene
     */
    public void setKerosene(Biokerosene kerosene) {
        this.kerosene = kerosene;
    }

    /**
     * Cleans the field by melting all the snow on it using the Dragon head.
     * Consumes kerosene if the head has resources, otherwise does nothing.
     * @param f the field to clean
     */
    @Override
    public void clean(Field f) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "clean", "f");

        if (this.hasResource()) {
            kerosene.consume(10); 
            
            sk.call(f.getSurface(), "meltAll");
            f.getSurface().meltAll();
            sk.returnMethod();
        } else {
            System.out.println("\tDragon üres, a takarítás elmarad.");
        }

        sk.returnMethod();
    }
    /**
     * Refills the Dragon head with the specified resource.
     * @param r the resource to refill with
     */
    @Override
    public void refill(Resource r) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "refill", "r");
        
        this.addAmount(10);
        
        sk.returnMethod();
    }

    /**
     * Adds the specified amount of kerosene to the Dragon head.
     * @param amount the amount of kerosene to add
     */
    public void addAmount(int amount) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "addAmount", String.valueOf(amount));

        this.kerosene.add(amount);
        
        sk.returnMethod();
    }
}