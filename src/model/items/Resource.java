package model.items;

import java.util.List;

import model.core.Player;
import model.core.Shop;
import model.entities.SnowPlow;
import test.Prototype;

public abstract class Resource implements Purchasable {
    protected int amount = 0;
    protected int unitPrice = 10;
    protected int maxAmount = 100;

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
    public boolean pay(Shop shop) {
        if (shop.getBalance() >= this.unitPrice) {
            shop.deduct(this.unitPrice);
            //System.out.println("A tranzakció sikeres végrehajtása és rögzítése");
            return true;
        } else {
            //System.out.println("A tranzakció elutasítása fedezethiány miatt");
            return false;
        }
    }

    /**
     * Consumes the specified quantity of the resource.
     * @param quantity the quantity to consume
     */
    public void consume(int quantity) {
        if (this.amount >= quantity) {
            this.amount -= quantity; // Deduct the consumed quantity from the current amount
        } else {
            System.out.println("Üzemanyag kifogyott!");
        }
    }

    /**
     * Adds the specified quantity of the resource.
     * @param quantity the quantity to add
     */
    public void add(int quantity) {
        this.amount += quantity;
        if (this.amount > this.maxAmount) {
            this.amount = this.maxAmount; // Ensure the amount does not exceed the maximum limit
        }

    }
    
    /**
     * Gets the unit price of the resource for a specific shop.
     * @param shop the shop for which to get the unit price
     * @return the unit price of the resource for the specified shop
     */
    public int getUnitPrice(Shop shop) {
        return this.unitPrice;
    }

    /**
     * Gets the maximum amount of the resource that can be stored.
     * @return the maximum amount of the resource
     */
    public int getMaxAmount() {
        return maxAmount;
    }

    /**
     * Sets the maximum amount of the resource that can be stored.  
     * @param maxAmount
     */
    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    /**
     * Defines the action to be taken when the resource is purchased by a player.
     * @param player the player who purchased the resource
     * @param snowPlow the snow plow associated with the purchase
     */
    @Override
public void onPurchased(Player player, SnowPlow snowPlow, int amount) {
    if (snowPlow != null) {
        List<Head> allHeads = snowPlow.getHeads(); // Get all heads from the snow plow

        for (Head h : allHeads) {
            if (h instanceof ResourceConsumingHead) {
                ResourceConsumingHead resHead = (ResourceConsumingHead) h;
                // Create a new resource instance based on the type of the current resource
                Resource resourceToFill = null;
                    
                if (this instanceof Biokerosene) {
                    resourceToFill = new Biokerosene(amount, this.unitPrice);
                } else if (this instanceof Salt) {
                    resourceToFill = new Salt(amount, this.unitPrice);
                } else if (this instanceof Gravel) {
                    resourceToFill = new Gravel(amount, this.unitPrice);
                }
                // If the resource type is not compatible with the head's resource type, skip to the next head
                int oldAmount = resHead.getResource().getAmount();
                resHead.refill(resourceToFill);

                // If the refill was successful and the resource amount has increased, log the change using the Prototype class
                if (resHead.getResource().getAmount() > oldAmount) {
                    Prototype.getInstance().changed(
                        "snowplow" + snowPlow.getVehicleId(),
                        this.getClass().getSimpleName() + "Amount", // e.g., "SaltAmount"
                        String.valueOf(oldAmount),
                        String.valueOf(resHead.getResource().getAmount())
                    );
                }
            }
        }
}
}

}