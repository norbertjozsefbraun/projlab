package model.items;
import model.map.Field;

public class Sweeper extends Head {
    @Override
    public void clean(Field f) {

        int amount = f.getSurface().sweepSnow();

        // 3. Szomszéd mező lekérdezése
        Field neighbor = f.getRightNeighbour();

        if (neighbor != null) {
            neighbor.addSnow(amount);
        } else {
            System.out.println("\t Nincs szomszéd, a hó kikerül a pályáról");
        }

    }
}