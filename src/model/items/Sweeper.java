package model.items;
import test.Skeleton;
import model.map.Field;

public class Sweeper extends Head {
    @Override
    public void clean(Field f) {
        Skeleton sk = Skeleton.getInstance();
        sk.call(this, "clean", "f");

        int amount = f.getSurface().sweepSnow();

        // 3. Szomszéd mező lekérdezése
        Field neighbor = f.getRightNeighbour();

        String[] options = {"i","n"};
        int choice = sk.getChoice("Van jobb oldali szomszédja a mezőnek?", options);

        if (choice == 1) {
            if (neighbor != null) {
                neighbor.addSnow(amount);
            } 
        } else {
            System.out.println("\t Nincs szomszéd, a hó kikerül a pályáról");
        }

        sk.returnMethod();
    }
}