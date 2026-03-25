package model.items;

import model.core.Shop;

public interface Purchasable {
    int getPrice();
    void pay(Shop s);
}
