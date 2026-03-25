package model.items;

import model.core.Shop;
import model.map.Field;

public abstract class Head implements CleanerEquipment, Purchasable {
    protected int price;
    protected boolean equipped = false;

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void pay(Shop s) {
        // Skeleton.call("head", "pay(shop)");
        //s.deduct(this.price);
        // Skeleton.returnValue("head", "pay");
    }

    public void setEquipped(boolean isEquipped) {
        // Skeleton.call("head", "setEquipped(" + isEquipped + ")");
        this.equipped = isEquipped;
        // Skeleton.returnValue("head", "setEquipped");
    }

    public abstract void clean(Field f);
}