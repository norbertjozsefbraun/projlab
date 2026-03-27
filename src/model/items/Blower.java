package model.items;

import model.map.Field;
import test.Skeleton;

public class Blower extends Head {

    /**
     * Cleans the specified field.
     * @param f the field to clean
     */
    @Override
    public void clean(Field f) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "clean", "f");


        sk.returnMethod();
    }
}