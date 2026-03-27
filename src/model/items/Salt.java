package model.items;
import test.Skeleton;

public class Salt extends Resource {
    
    public Salt(int a, int p){
        amount=a;
        unitPrice=p;
    }

    public Salt(){}

    @Override
    public void consume(int quantity) {
        Skeleton sk = Skeleton.getInstance();

        sk.call(this, "consume", String.valueOf(quantity));
        super.consume(quantity);

        sk.returnMethod();
    }
}