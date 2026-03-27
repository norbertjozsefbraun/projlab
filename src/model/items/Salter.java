package model.items;
import test.Skeleton;
import model.map.Field;

public class Salter extends ResourceConsumingHead {
    private Salt salt = new Salt();

    /** Sets the salt resource for the Salter head.
     * @param s the Salt resource to set
     */
    public void setSalt(Salt s) {
        this.salt = s;
    }

    /** Cleans the field by applying salt to it using the Salter head.
     * Consumes salt if the head has resources, otherwise does nothing.
     * @param f the field to clean
     */
    @Override
    public void clean(Field f) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "clean", "f");
        if (this.hasResource()) {
            salt.consume(5);

            Skeleton.getInstance().call(f.getSurface(), "applySalt");
            f.getSurface().applySalt();
            Skeleton.getInstance().returnMethod();
            
            System.out.println("\t[Note: A mező mostantól sózott.]");
        } else {
            System.out.println("\t[Note: Nincs elég só, a sózás elmarad.]");
        }

        sk.returnMethod();
    }

    /** Refills the Salter head with the specified resource.
     * @param r the resource to refill with
     */
    @Override
    public void refill(Resource r) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "refill", "r");
        
        this.addAmount(14);
        
        sk.returnMethod();
    }

    /** Adds the specified amount of salt to the Salter head.
     * @param amount the amount of salt to add
     */
    public void addAmount(int amount) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "addAmount", String.valueOf(amount));

        this.salt.add(amount);
        
        sk.returnMethod();
    }


}