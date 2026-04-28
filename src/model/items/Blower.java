package model.items;

import model.map.Field;

public class Blower extends Head {

    /**
     * Cleans the field by blowing the snow on it using the Blower head.
     * The blown snow is added to the right neighbor field if it exists, otherwise it is removed from the game.
     * @param f the field to clean
     */
    @Override
    public void clean(Field f) {
        int snowToMove = f.getSurface().getSnowThickness();

        Field neighbor = f.getRightNeighbour();

        if (neighbor != null) {
            Field farNeighbor = neighbor.getRightNeighbour();
            if (farNeighbor != null) {
                farNeighbor.addSnow(snowToMove);
                System.out.println("Hó áthelyezve távolra");
            } else {
                System.out.println("Hó eltűnt a rendszerből");
            }
        } else {
            System.out.println("Hó eltűnt a rendszerből");
        }

        f.getSurface().sweepSnow();
    }
}