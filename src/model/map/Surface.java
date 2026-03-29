package model.map;

import java.util.ArrayDeque;
import java.util.Queue;
import model.entities.Vehicle;
import test.Skeleton;

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
    public boolean  getIsIce() {
        return isIce;
    }

    /// Setters:
    public void setIsIce(boolean isIce) {
        this.isIce = isIce;
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
        skeleton.call(this, "sweepSnow");

        int snowAmount = snowThickness;
        snowThickness = 0;

        skeleton.returnMethod("int", String.valueOf(snowAmount));
        return snowAmount;
    }

    public void breakIce() {
        skeleton.call(this, "breakIce");
        isIce = false;
        skeleton.returnMethod();
    }

    public void meltAll() {
        skeleton.call(this, "meltAll");
        snowThickness = 0;
        isIce = false;
        skeleton.returnMethod();
    }

    public void applySalt() {
        //TODO
    }

    public void addSnow(int amount) {
        skeleton.call(this, "addSnow", String.valueOf(amount));
        snowThickness += amount;
        skeleton.returnMethod();
    }

}
