package model.map;

import java.util.ArrayDeque;
import java.util.Queue;
import model.entities.Vehicle;

public class Surface {
    /// Fields:
    private int snowThickness;
    private boolean isIce;
    private Queue<Integer> passTimes;
    private int saltTimer;

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

        //int alt = skeleton.getChoice("Áthaladt 5 jármű az utolsó 10 időegységben?", new String[]{"Igen", "Nem"});
        //if(alt == 1){
        //    isIce = true;
        //}

    }

    public int sweepSnow() {

        int snowAmount = snowThickness;
        snowThickness = 0;

        return snowAmount;

        //TODO gravelt is el kell tavolitania a járműnek.
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

    public void addGravel() {
        //TODO
    }

    public void addSnow(int amount) {
        snowThickness += amount;
    }

}
