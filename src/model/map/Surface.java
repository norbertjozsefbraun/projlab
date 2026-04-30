package model.map;

import java.util.ArrayDeque;
import java.util.Queue;

import model.core.Game;
import model.core.Ticker;
import model.entities.Vehicle;

public class Surface {
    /// Fields:
    private int snowThickness;
    private boolean isIce;
    private Queue<Integer> passTimes;
    private int saltTimer;
    private boolean hasGravel;

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

    public void setSnowThickness(int snowThickness) {
        this.snowThickness = snowThickness;
    }

    public void setHasGravel(boolean hasGravel) {
        this.hasGravel = hasGravel;
    }

    /// Constructor:
    public Surface() {
        snowThickness = 0;
        isIce = false;
        passTimes = new ArrayDeque<>();
        saltTimer = 0;
        hasGravel = false;
    }

    /// Functions:

    public void vehiclePasses(Vehicle v) {
        passTimes.add(Game.getTicker().getCurrent());

        // remove old passes
        while (!passTimes.isEmpty() && (Game.getTicker().getCurrent() - passTimes.peek() >= 10)) {
            passTimes.poll();
        }

        // add ice
        if (passTimes.size() >= 5 && saltTimer == 0) {
            this.isIce = true;
        }

    }

    public int sweepSnow() {

        int snowAmount = snowThickness;
        snowThickness = 0;
        hasGravel = false;

        return snowAmount;
    }

    public void breakIce() {
        isIce = false;
    }

    public void meltAll() {
        snowThickness = 0;
        isIce = false;
    }

    public void applySalt() {
        if (snowThickness > 35) {
            saltTimer = 5;
        } else {
            saltTimer = 3;
        }
    }

    public void addGravel() {
        hasGravel = true;
    }

    public void addSnow(int amount) {
        if (saltTimer == 0) {
            snowThickness += amount;
        }
    }

    public void tickTimers() {
        if (this.saltTimer > 0) {
            this.saltTimer--;

            if (this.saltTimer == 0) {
                this.snowThickness = 0;
                this.isIce = false;
            }
        }
    }

}
