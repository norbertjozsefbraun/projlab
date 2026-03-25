public class Salt extends Resource {
    
    Salt(int a, int p){
        amount=a;
        unitPrice=p;
    }

    Salt(){}

    @Override
    public void consume(Integer quantity) {
        // Skeleton.call("salt:salt", "consume(" + quantity + ")");
        super.consume(quantity);
        // Skeleton.returnValue("salt:salt", "consume");
    }
}