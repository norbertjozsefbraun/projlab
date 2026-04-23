package model.core;
import java.util.ArrayList;
import java.util.List;
import model.entities.Vehicle;
import model.map.World;

public class Session {
    private static Session session = null;

    /// Fields:
    private static List<Integer> results;
    private Menu menu;
    private Game game;

    /// Constructor:
    private Session(){
        results = new ArrayList<>();
        menu = new Menu();
    }

    /// Get instance:

    public static Session getInstance(){
        if(session == null){
            session = new Session();
        }
        return session;
    }

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

    public static void save(){
        //Todo: Implement this
    }

    public void load(){
        //Todo: Implement this
    }

    public void newGame(List<Vehicle> vehcs, World world){
        setGame(new Game(vehcs, world));
        menu.setState(Menu.States.INGAME);
    }

    public static void addResult(Integer r){
        results.add(r);
        save();
    }
}
