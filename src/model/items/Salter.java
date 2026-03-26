package model.items;
import test.Skeleton;
import model.map.Field;

public class Salter extends ResourceConsumingHead {
    private Salt salt = new Salt();

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


    @Override
    public void refill(Resource r) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "refill", "r");
        
        this.addAmount(14);
        
        sk.returnMethod();
    }

    public void addAmount(int amount) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "addAmount", String.valueOf(amount));

        this.salt.add(amount);
        
        sk.returnMethod();
    }


}