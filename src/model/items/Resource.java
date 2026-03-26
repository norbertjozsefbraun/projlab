package model.items;

import model.core.Shop;
import test.Skeleton;

public abstract class Resource implements Purchasable {
    protected int amount = 0;
    protected int unitPrice = 10;

    public int getPrice() {
        return unitPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public void pay(Shop shop) {

        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "pay", "shop");
        
        shop.deduct(this.unitPrice);
        
        sk.returnMethod();
    }

    public void consume(int quantity) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "consume", String.valueOf(quantity));
        
        this.amount -= quantity;
        if (this.amount < 0) {
            this.amount = 0;
        }
        sk.returnMethod();
    }

    public void add(int quantity) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "add", String.valueOf(quantity));
        
        this.amount += quantity;
        
        sk.returnMethod();
    }
}