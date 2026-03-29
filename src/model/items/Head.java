package model.items;

import model.core.Shop;
import model.map.Field;
import test.Skeleton;

public abstract class Head implements CleanerEquipment, Purchasable {
    protected int price;
    protected boolean equipped = false;

    /**
     * Gets the price of the head.
     * @return the price of the head
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price of the head.
     * @param p the price to set
     */
    public void setPrice(int p){
        price=p;
    }

    /**
     * Pays for the head by deducting its price from the shop's balance.
     * @param s the shop to pay
     */
    @Override
    public void pay(Shop s) {
        Skeleton sk = Skeleton.getInstance();
        
        sk.call(this, "pay", sk.getObjectName(s));
        
        s.deduct(this.price);
        
        sk.returnMethod();
    }

    /**
     * Checks if the head is currently equipped.
     * @return true if the head is equipped, false otherwise
     */    public boolean isEquipped() {
        return equipped;
    }

    /**
     * Sets the equipped status of the head.
     * @param isEquipped true to equip the head, false to unequip
     */
    public void setEquipped(boolean isEquipped) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "setEquipped", String.valueOf(isEquipped));
        this.equipped = isEquipped;
        sk.returnMethod();
    }

    /**
     * Cleans the specified field using the head's cleaning capabilities.
     * @param f the field to clean
     */
    public abstract void clean(Field f);
}