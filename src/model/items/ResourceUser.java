package model.items;

public interface ResourceUser {
    boolean hasResource();
    void refill(Resource r);
}