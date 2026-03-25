package model.core;

public class Ticker {
    /// Fields
    private Integer current;

    /// Constructor
    public Ticker(){
        current = 0;
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

    /*
    Ticks the ticker by one unit.
     */
    public void tick() {
        current++;
    }

    /*
    Getter could be used as well, but due to the diagram and the avoidance of future
    misunderstandings this function does the same as the getter.
     */
    public Integer getTick(){
        return this.getCurrent();
    }

}
