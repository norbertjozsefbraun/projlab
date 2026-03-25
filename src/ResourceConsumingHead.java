public abstract class ResourceConsumingHead extends Head implements ResourceUser {
    protected int maxCapacity = 50;

    @Override
    public boolean hasResource() {
        // Skeleton.call("rch:resourceConsumingHead", "hasResource()");
        
        // System.out.print("    ? Van elég nyersanyag a tartályban? (y/n): ");
        // boolean result = SkeletonScanner.nextBoolean();
        
        // Skeleton.returnValue("rch:resourceConsumingHead", "hasResource() : return " + result);
        return true;
    }

    @Override
    public void refill(Resource r) {
        // Skeleton.call("rch:resourceConsumingHead", "refill(resource)");
        // Skeleton.returnValue("rch:resourceConsumingHead", "refill");
    }
}