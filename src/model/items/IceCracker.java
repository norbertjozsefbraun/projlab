package model.items;

import model.map.Field;
import model.map.Surface;
import test.Skeleton;
public class IceCracker extends Head {

    /**
     * Cleans the field by breaking the ice on it, so it becomes snow using the IceCracker head.
     * @param f the field to clean
     */
    @Override
    public void clean(Field f) {

        Skeleton sk = Skeleton.getInstance();
        
        sk.call(this, "clean", "f");

        Surface s = f.getSurface();
        
        s.breakIce();

        sk.returnMethod();
    }
}