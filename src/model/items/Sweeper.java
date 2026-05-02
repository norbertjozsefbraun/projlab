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
        int amount = f.getSurface().sweepSnow(); // Get the amount of snow swept from the surface of the field
        
        Field neighbor = f.getRightNeighbour(); // Get the right neighbor field of the current field

        if (neighbor != null) { // If there is a right neighbor, add the swept snow to it
            neighbor.addSnow(amount);
        } else { // If there is no right neighbor, the swept snow is removed from the game
            System.out.println("\t Nincs szomszéd, a hó kikerül a pályáról");
        }

    }
}