package model.items;

import model.map.Field;
import model.map.Surface;
public class IceCracker extends Head {

    /**
     * Cleans the field by breaking the ice on it, so it becomes snow using the IceCracker head.
     * @param f the field to clean
     */
    @Override
    public void clean(Field f) {
        Surface s = f.getSurface();
        if (s.getIsIce()) {
            s.breakIce(); 
            f.addSnow(10);
            s.setIsIce(false);
            System.out.println("Jégtörés sikeres: a jég hóvá alakult.");
        }
    }
}