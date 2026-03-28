package model.core;
import java.util.ArrayList;
import java.util.List;
import model.entities.Vehicle;
import model.map.World;
import test.Skeleton;


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
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.ctor(getInstance() , "session");
        skeleton.call(getInstance() , "save");
        skeleton.returnMethod();
    }

    public void load(){
        //Todo: Implement this
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.ctor(getInstance() , "session");
        skeleton.call(getInstance() , "load()");
        skeleton.returnMethod();
    }

    public void newGame(List<Vehicle> vehcs, World world){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.ctor(getInstance() , "session");
        skeleton.call(getInstance(), "newGame(List<Vehicle> vehcs, World world)");
        //Initializing a new game object for the game field and setting the menu state to ingame
        game = new Game(vehcs, world);
        menu.setState(Menu.States.INGAME);
        skeleton.returnMethod();
    }

    public static void addResult(Integer r){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.ctor(getInstance() , "session");
        skeleton.call( getInstance(), "addResult");
        //adding the result then saving
        results.add(r);
        save();
        skeleton.returnMethod();
    }
}
