package model.items;

/**
 * The ResourceUser interface defines the methods for objects that can use and manage resources.
 * It includes methods to check if the object has enough resources and to refill the object with a resource.
 */
public interface ResourceUser {
    boolean hasResource();
    void refill(Resource r);
}