package model.core;

import java.util.List;

public class Menu {
    /// base enum for state machine
    enum States{
        MAIN,
        SETPLAYERS,
        INGAME,
        PREVIOUS
    }

    /// Fields:
    private States state;

    /// Getters:
    public States getState() {
        return state;
    }

    /// Setters:
    public void setState(States state) {
        this.state = state;
    }

    /// Functional functions:
    public void listResults(){
        List<Integer> results = Session.getInstance().getResults();
        //output to interface the results either graphical representation or CLI depending on phase
    }

    public void start(){
        //Todo: Do this later down the line, this function doesn't matter as of this phase
    }
}
