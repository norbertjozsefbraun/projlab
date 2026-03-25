package model.items;

public class Biokerosene extends Resource {

    Biokerosene(int a, int p){
        amount=a;
        unitPrice=p;
    }

    Biokerosene(){}
    
    @Override
    public void consume(Integer quantity) {
        // Skeleton.call("biokerosene:biokerosene", "consume(" + quantity + ")");
        // Skeleton.returnValue("biokerosene:biokerosene", "consume");
    }
}