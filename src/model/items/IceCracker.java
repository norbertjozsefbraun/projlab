package model.items;

import model.map.Field;
import model.map.Surface;
import test.Skeleton;

public class IceCracker extends Head {
    @Override
    public void clean(Field f) {

        Skeleton sk = Skeleton.getInstance();
        
        sk.call(this, "clean", "f");

        Surface s = f.getSurface();
        
        s.breakIce();

        sk.returnMethod();
    }
}