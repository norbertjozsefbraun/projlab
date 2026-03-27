package model.core;

import test.Skeleton;

public class Ticker {
    /// Fields
    private Integer current;

    /// Constructor
    public Ticker(){
        Skeleton skeleton = Skeleton.getInstance();
        current = 0;
        skeleton.ctor(this , "ticker");
    }

    /// Getters:
    public Integer getCurrent(){
        return current;
    }
    /// Setters:
    public void setCurrent(Integer current){
        this.current = current;
    }

    /// Functional functions:

    /**
    Ticks the ticker by one unit.
     */
    public void tick() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this , "tick()");
        current++;
        skeleton.returnMethod();
    }

    /**
    Getter could be used as well, but due to the diagram and the avoidance of future
     misunderstandings, this function does the same as the getter.
     */
    public Integer getTick(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this , "getTick()");
        skeleton.returnMethod();
        return this.getCurrent();
    }

}
