public abstract class Head implements CleanerEquipment, Purchasable {
    protected int price = 100;
    protected boolean equipped = false;

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void pay(Shop s) {
        // Skeleton.call("head", "pay(shop)");
        // Skeleton.returnValue("head", "pay");
    }

    public void setEquipped(boolean isEquipped) {
        // Skeleton.call("head", "setEquipped(" + isEquipped + ")");
        // Skeleton.returnValue("head", "setEquipped");
    }

    public abstract void clean(Field f);
}