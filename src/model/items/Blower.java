package model.items;

import model.map.Field;
import test.Skeleton;

public class Blower extends Head {

    @Override
    public void clean(Field f) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "clean", "f");


        sk.returnMethod();
    }
}