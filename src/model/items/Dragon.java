package model.items;
import test.Skeleton;
import model.map.Field;

public class Dragon extends ResourceConsumingHead {
    private Biokerosene kerosene = new Biokerosene();

    @Override
    public void clean(Field f) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "clean", "f");

        if (this.hasResource()) {
            kerosene.consume(10); 
            
            sk.call(f.getSurface(), "meltAll");
            f.getSurface().meltAll();
            
            System.out.println("\tA Dragon mindent elolvasztott a mezőn.");
        } else {
            System.out.println("\tDragon üres, a takarítás elmarad.");
        }

        sk.returnMethod();
    }

    @Override
    public void refill(Resource r) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "refill", "r");
        
        this.addAmount(10);
        
        sk.returnMethod();
    }

    public void addAmount(int amount) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "addAmount", String.valueOf(amount));

        this.kerosene.add(amount);
        
        sk.returnMethod();
    }
}