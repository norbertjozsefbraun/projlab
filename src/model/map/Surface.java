package model.map;

import model.entities.Vehicle;

import java.util.ArrayDeque;
import java.util.Queue;

public class Surface {
    private int snowThickness;
    private boolean isIce;
    private Queue<Integer> passTimes;
    private int saltTimer;

    public Surface() {
        snowThickness = 0;
        isIce = false;
        passTimes = new ArrayDeque<>();
        saltTimer = 0;
    }

    public void vehiclePasses(Vehicle v){
        //TODO
    }

    public int sweepSnow() {
        //TODO
        return 0;
    }

    public void breakIce() {
        isIce = false;
    }

    public void meltAll() {
        snowThickness = 0;
        isIce = false;
    }

    public void applySalt() {
        //TODO
    }

    public void applySnow(int amount) {
        snowThickness += amount;
    }

}
