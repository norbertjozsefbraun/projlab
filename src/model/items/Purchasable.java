package model.items;
import model.core.Player;
import model.core.Shop;
import model.entities.SnowPlow;

/**
 * Interface representing items that can be purchased in the shop.
 */
public interface Purchasable {
    int getPrice();
    boolean pay(Shop s); 
    void onPurchased(Player player, SnowPlow snowplow, int amount);
}
