package model.items;

import model.core.Shop;
import model.map.Field;
import test.Skeleton;

public abstract class Head implements CleanerEquipment, Purchasable {
    protected int price;
    protected boolean equipped = false;

    public int getPrice() {
        return price;
    }

    public void setPrice(int p){
        price=p;
    }

    @Override
    public void pay(Shop s) {
        Skeleton sk = Skeleton.getInstance();
        
        sk.call(this, "pay", "s");
        
        s.deduct(this.price);
        
        sk.returnMethod();
    }

    public void setEquipped(boolean isEquipped) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "setEquipped", String.valueOf(isEquipped));
        this.equipped = isEquipped;
        sk.returnMethod();
    }

    public abstract void clean(Field f);
}