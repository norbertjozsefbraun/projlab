package model.items;
import test.Skeleton;

public class Biokerosene extends Resource {

    Biokerosene(int a, int p){
        amount=a;
        unitPrice=p;
    }

    Biokerosene(){}
    
    @Override
    public void consume(int quantity) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "consume", String.valueOf(quantity));

        super.consume(quantity);

        sk.returnMethod();
    }
}