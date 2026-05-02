package model.map;

import java.util.ArrayDeque;
import java.util.Queue;

import model.core.Game;
import model.core.Ticker;
import model.entities.Vehicle;
import test.Prototype;

public class Surface {
    /// Fields:
    private int snowThickness;
    private boolean isIce;
    private Queue<Integer> passTimes;
    private int saltTimer;
    private boolean hasGravel;
    private int surfaceId;
    private static int idCounter = 1;

    /// Getters:
    public int getSnowThickness() {
        return snowThickness;
    }
    public boolean  getIsIce() {
        return isIce;
    }

    /// Setters:
    public void setIsIce(boolean isIce) {
        if (this.isIce != isIce) {
            Prototype.getInstance().changed("surface" + this.surfaceId, "isIce", String.valueOf(this.isIce), String.valueOf(isIce));
            this.isIce = isIce;
        }
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

        surfaceId = idCounter++;
    }

    /// Functions:

    public void vehiclePasses(Vehicle v) {
        if (Game.getTicker() != null) {
            passTimes.add(Game.getTicker().getCurrent());

            while (!passTimes.isEmpty() && (Game.getTicker().getCurrent() - passTimes.peek() >= 10)) {
                passTimes.poll();
            }
        } else {
            passTimes.add(1);
            if (passTimes.size() > 10) {
                passTimes.poll();
            }
        }

        if (passTimes.size() >= 5 && saltTimer == 0) {
            if (!this.isIce) {
                Prototype.getInstance().changed("surface" + this.surfaceId, "isIce", "false", "true");
                this.isIce = true;
            }
        }
    }

    public int sweepSnow() {
        addSnow(-snowThickness);
        int snowAmount = snowThickness;
        hasGravel = false;
        return snowAmount;
    }

    public void breakIce() {
        if (this.isIce) {
            Prototype.getInstance().changed("surface" + this.surfaceId, "isIce", "true", "false");
            this.isIce = false;
        }
    }

public void meltAll() {
    int oldSnow = snowThickness;
    addSnow(-snowThickness);
    int newSnow = snowThickness;
    
    if (oldSnow != newSnow) {
        Prototype.getInstance().changed("surface" + this.surfaceId, "snowThickness", String.valueOf(oldSnow), String.valueOf(newSnow));
    }
    
    if (this.isIce) {
        Prototype.getInstance().changed("surface" + this.surfaceId, "isIce", "true", "false");
        this.isIce = false;
    }
}

    public void applySalt() {
        boolean hadSalt = (this.saltTimer > 0);

        if (snowThickness > 35) {
            saltTimer = 5;
        } else {
            saltTimer = 3;
        }

        if (!hadSalt && this.saltTimer > 0) {
            Prototype.getInstance().changed("surface" + this.surfaceId, "hasSalt", "false", "true");
        }
    }

    public void addGravel() {
        if (!this.hasGravel) {
            Prototype.getInstance().changed("surface" + this.surfaceId, "hasGravel", "false", "true");
            this.hasGravel = true;
        }
    }

    public void addSnow(int amount) {
        int oldSnow = getSnowThickness();
        if (saltTimer == 0) {
            snowThickness += amount;
        }

        int newSnow = getSnowThickness();

        if (oldSnow != newSnow) {
            Prototype.getInstance().changed(getClass().getSimpleName().toLowerCase() + surfaceId, "snowThickness", String.valueOf(oldSnow), String.valueOf(newSnow));
        }

    }

    public void tickTimers() {
        if (this.saltTimer > 0) {
            this.saltTimer--;

            if (this.saltTimer == 0) {
                this.snowThickness = 0;

                if (this.isIce) {
                    Prototype.getInstance().changed("surface" + this.surfaceId, "isIce", "true", "false");
                    this.isIce = false;
                }

                Prototype.getInstance().changed("surface" + this.surfaceId, "hasSalt", "true", "false");
            }
        }
    }

}
