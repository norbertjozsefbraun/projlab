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
        return state;
    }

    /// Setters:
    public void setState(States state) {
        this.state = state;
    }

    /// Functional functions:
    public void listResults(){
        //Todo: Implement this
    }

    public void start(){
        //Todo: Implement this
    }
}
