public abstract class Resource implements Purchasable {
    protected int amount = 0;
    protected int unitPrice = 10;

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
        this.amount -= quantity;
        if (this.amount < 0) this.amount = 0;
        // Skeleton.returnValue("resource", "consume");
    }

    public void add(Integer quantity) {
        // Skeleton.call("resource", "add(" + quantity + ")");
        this.amount += quantity;
        // Skeleton.returnValue("resource", "add");
    }
}