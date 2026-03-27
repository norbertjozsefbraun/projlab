package model.items;
import test.Skeleton;

public abstract class ResourceConsumingHead extends Head implements ResourceUser {
    protected int maxCapacity = 50;

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public boolean hasResource() {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "hasResource");

        String[] options = {"i","n"};

        int choice = sk.getChoice("Rendelkezik az eszköz elegendő erőforrással?", options);
        boolean result = (choice == 1);

        sk.returnMethod("boolean", String.valueOf(result));
        return result;
    }

    @Override
    public void refill(Resource r) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "refill", "r");
        
        sk.returnMethod();
    }
}