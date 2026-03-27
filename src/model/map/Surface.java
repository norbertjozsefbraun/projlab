package model.map;

import model.entities.Vehicle;
import test.Skeleton;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Surface {
    /// Fields:
    private int snowThickness;
    private boolean isIce;
    private Queue<Integer> passTimes;
    private int saltTimer;
    private Skeleton skeleton =  Skeleton.getInstance();

    /// Getters:
    public int getSnowThickness() {
        return snowThickness;
    }

    /// Constructor:
    public Surface() {
        snowThickness = 0;
        isIce = false;
        passTimes = new ArrayDeque<>();
        saltTimer = 0;
    }

    /// Functions:
    public void vehiclePasses(Vehicle v){
        skeleton.call(this, "vehiclePasses", skeleton.getObjectName(v));

        //int alt = skeleton.getChoice("Áthaladt 5 jármű az utolsó 10 időegységben?", new String[]{"Igen", "Nem"});
        //if(alt == 1){
        //    isIce = true;
        //}

        skeleton.returnMethod();
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
