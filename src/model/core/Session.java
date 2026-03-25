package model.core;
import java.util.List;
import model.entities.Vehicle;
import model.map.World;

public class Session {
    /// Fields:
    private List<Integer> results;
    private Menu menu;
    private Game game;

    /// Getters:
    public Game getGame() {
        return game;
    }

    public Menu getMenu() {
        return menu;
    }

    public List<Integer> getResults() {
        return results;
    }

    /// Setters:

    public void setGame(Game game) {
        this.game = game;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setResults(List<Integer> results) {
        this.results = results;
    }

    /// Functional functions:

    public void save(){
        //Todo: Implement this
    }

    public void load(){
        //Todo: Implement this
    }

    public void newGame(List<Vehicle> vehcs, World world){
        //Todo: Implement this
    }

    public void addResult(Integer r){
        //Todo: Implement this
    }
}
