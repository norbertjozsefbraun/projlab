package model.items;

import model.core.Shop;
import test.Skeleton;

public abstract class Resource implements Purchasable {
    protected int amount = 0;
    protected int unitPrice = 10;

    /**
     * Gets the price of the resource.
     * @return the price of the resource
     */
    public int getPrice() {
        return unitPrice;
    }

    /**
     * Gets the amount of the resource.
     * @return amount of the resource
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the resource.
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Gets the unit price of the resource.
     * @return the unit price of the resource
     */
    public int getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the unit price of the resource.
     * @param unitPrice the unit price to set
     */
    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Pays for the resource by deducting its price from the shop's balance.
     * @param shop the shop to pay
     */
    @Override
    public void pay(Shop shop) {

        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "pay", sk.getObjectName(shop));
        
        shop.deduct(this.unitPrice);
        
        sk.returnMethod();
    }

    /**
     * Consumes the specified quantity of the resource.
     * @param quantity the quantity to consume
     */
    public void consume(int quantity) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "consume", String.valueOf(quantity));
        
        this.amount -= quantity;
        if (this.amount < 0) {
            this.amount = 0;
        }
        sk.returnMethod();
    }

    /**
     * Adds the specified quantity of the resource.
     * @param quantity the quantity to add
     */
    public void add(int quantity) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "add", String.valueOf(quantity));
        
        this.amount += quantity;
        
        sk.returnMethod();
    }
}