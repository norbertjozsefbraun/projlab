package model.core;

import test.Skeleton;

import java.util.List;

public class Menu {
    /// base enum for state machine
    enum States{
        MAIN,
        SETPLAYERS,
        INGAME,
        PREVIOuS
    }

    /// Fields:
    private States state;

    /// Getters:
    public States getState() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.ctor(this , "menu");
        return state;
    }

    /// Setters:
    public void setState(States state) {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this , "setState()");
        this.state = state;
        skeleton.returnMethod();
    }

    /// Functional functions:
    public void listResults(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this , "listResults()");
        //getting the results
        List<Integer> results = Session.getInstance().getResults();
        //output to interface the results either graphical representation or CLI depending on phase
        skeleton.returnMethod();
    }

    public void start(){
        //Todo: Do this later down the line, this function doesn't matter as of this phase
    }
}
