package model.items;

public abstract class ResourceConsumingHead extends Head implements ResourceUser {
    protected int maxCapacity = 50;

    /**
     * Gets the maximum capacity of the head for holding resources.
     * @return the maximum capacity of the head
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Sets the maximum capacity of the head for holding resources.
     * @param maxCapacity the maximum capacity to set
     */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /**
     * Checks if the head has enough resources to perform its cleaning function.
     * @return true if the head has enough resources, false otherwise
     */
    @Override
    public boolean hasResource() {
        return true;
    }

    /**
     * Refills the head with the specified resource.
     * @param r the resource to refill with
     */
    @Override
    public void refill(Resource r) {
        
    }
}