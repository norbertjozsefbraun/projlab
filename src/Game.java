import java.util.List;
import java.util.Random;

public class Game {
    /// Fields:
    private List<Vehicle> vehicles;
    public static Ticker ticker;
    private World world;
    private Shop shop;
    private Integer rounds;
    private Random dice;

    /// Constructor:
    public Game(List<Vehicle> vehcs, World world){
        vehicles = vehcs;
        ticker = new Ticker();
        this.world = world;
        shop = new Shop();
        rounds = 0;
        dice = new Random();
    }

    /// Getters:
    public List<Vehicle> getVehicles(){
        return vehicles;
    }

    public static Ticker getTicker() {
        return ticker;
    }

    public Integer getRounds() {
        return rounds;
    }

    public Random getDice() {
        return dice;
    }

    public World getWorld() {
        return world;
    }

    public Shop getShop() {
        return shop;
    }

    /// Setters:

    public void setDice(Random dice) {
        this.dice = dice;
    }

    public void setRounds(Integer rounds) {
        this.rounds = rounds;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public static void setTicker(Ticker ticker) {
        Game.ticker = ticker;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    /// Functional functions:
    private Integer rollDice(){
        int min = 1;
        int max = 6;

        return dice.nextInt((max-min)+1)+min;
    }

    public void makeTurn(){
        //Todo: complete this
    }

    public void gameOver(){
        //Todo: complete this
    }
    public void increaseRounds(){
        //Todo: complete this
    }
    public List<Field> getPos(){
        //Todo: complete this
        return null;
    }
}
