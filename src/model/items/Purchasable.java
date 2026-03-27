package model.items;
import model.core.Shop;

/**
 * Interface representing items that can be purchased in the shop.
 */
public interface Purchasable {
    int getPrice();
    void pay(Shop s);
}
