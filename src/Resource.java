public abstract class Resource implements Purchasable {
    protected Integer amount = 0;
    protected Integer unitPrice = 10;

    @Override
    public int getPrice() {
        return unitPrice;
    }

    @Override
    public void pay(Shop shop) {
        // Skeleton.call("resource", "pay(shop)");
        // Skeleton.returnValue("resource", "pay");
    }

    public void consume(Integer quantity) {
        // Skeleton.call("resource", "consume(" + quantity + ")");
        // Skeleton.returnValue("resource", "consume");
    }

    public void add(Integer quantity) {
        // Skeleton.call("resource", "add(" + quantity + ")");
        // Skeleton.returnValue("resource", "add");
    }
}