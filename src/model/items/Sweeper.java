package model.items;
import model.map.Field;

public class Sweeper extends Head {

    /**
     * Cleans the field by sweeping the snow on it using the Sweeper head.
     * The swept snow is added to the right neighbor field if it exists, otherwise it is removed from the game.
     * @param f the field to clean
     */
    @Override
    public void clean(Field f) {
        int amount = f.getSurface().sweepSnow();
        
        Field neighbor = f.getRightNeighbour();

        if (neighbor != null) {
            neighbor.addSnow(amount);
        } else {
            System.out.println("\t Nincs szomszéd, a hó kikerül a pályáról");
        }

    }
}