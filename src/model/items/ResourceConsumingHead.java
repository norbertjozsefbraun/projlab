package model.items;

public abstract class ResourceConsumingHead extends Head implements ResourceUser {
    /**
     * Checks if the head has enough resources to perform its cleaning function.
     * @return true if the head has enough resources, false otherwise
     */
    @Override
    public abstract boolean hasResource();

    /**
     * Refills the head with the specified resource.
     * @param r the resource to refill with
     */
    public abstract void refill(Resource r);

    /**
     * Gets the current resource used by this head.
     * @return the resource object
     */
    public abstract Resource getResource();
}