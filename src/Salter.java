public class Salter extends ResourceConsumingHead {
    private Salt salt = new Salt();

    @Override
    public void clean(Field f) {
        // Skeleton.call("salter:salter", "clean(field)");


        // if (this.hasResource()) {
        //     salt.consume(5);

        //     SkeletonLogger.call("surface:surface", "applySalt()");
        //     f.getSurface().applySalt();
        //     SkeletonLogger.returnValue("surface:surface", "applySalt");
            
        //     System.out.println("  [Note: A mező mostantól sózott, a hó nem tapad meg rajta.]");
        // } else {
        //     System.out.println("  [Note: Nincs elég só, a sózás elmarad.]");
        // }

        // Skeleton.returnValue("salter:salter", "clean");
    }


    @Override
    public void refill(Resource r) {
        // Skeleton.call("salter:salter", "refill(resource)");
        
        this.addAmount(14);
        
        // Skeleton.returnValue("salter:salter", "refill");
    }

    public void addAmount(int amount) {
        // Skeleton.call("salter:Salter", "addAmount(" + amount + ")");
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        this.salt.add(amount);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Skeleton.returnValue("salter:Salter", "addAmount");
    }


}