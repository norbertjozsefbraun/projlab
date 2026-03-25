package model.items;

import model.map.Field;

public class Dragon extends ResourceConsumingHead {
    private Biokerosene kerosene = new Biokerosene();

    @Override
    public void clean(Field f) {
        // SkeletonLogger.call("dragon:dragon", "clean(field)");


        // if (this.hasResource()) {
        //     kerosene.consume(10); 
        //     f.getSurface().meltAll();
        // } else {
        //     System.out.println("  [Note: model.items.Dragon üres, a takarítás elmarad]");
        // }

        // SkeletonLogger.returnValue("dragon:dragon", "clean");
    }
}