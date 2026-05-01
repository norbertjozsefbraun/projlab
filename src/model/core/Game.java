package model.core;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.entities.Vehicle;
import model.map.Field;
import model.map.World;

public class Game {
    /// Fields:

    //List of players
    private List<Player> players;

    //List of vehicles, that are on the map
    private List<Vehicle> vehicles;

    //Ticker for the world
    public static Ticker ticker;

    //World object for the map
    private World world;

    //Shop object for buying things
    private Shop shop;

    //Round counter for the bus(es)
    private static Integer rounds;

    //Dice to roll with
    private Random dice;

    //If true than roll will be always 1
    private boolean derandomized;

    /// Constructor:
    public Game(List<Vehicle> vehcs, World world){
        vehicles = vehcs;
        ticker = new Ticker();
        this.world = world;
        shop = new Shop();
        rounds = 0;
        dice = new Random();
        derandomized = false;
    }

    /// Getters:
    public List<Player> getPlayers() {
        return players;
    }

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

    public boolean isDerandomized() {
        return derandomized;
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

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setDerandomized(boolean value) {
        derandomized = value;
    }

    /// Functional functions:

    /*
    rolldice() - this method will take a random number from 1-6 inclusively from both ends
    @no params
     */
    public Integer rollDice(){
        if (derandomized) return 1;
        int min = 1;
        int max = 6;

        return dice.nextInt((max-min)+1)+min;
    }
    /**
    maketurn() - this method first makes the world be snowfallen upon on ever field as handled by world
                 then it will iterate through every vehicle and rolls the dice for each and every one of them
                 If the vehicle at a point is SnowPlow and there are other heads available for it, then it will
                 ask if you want to change the head, if not it will execute the move as implemented further down
                 the line

     @return - void
     */
    public void makeTurn(){
        //Falling snow
        this.world.snowfall();

        //Iterating through the vehicles
        for(var currVehicle : vehicles){

            //Rolling the dice
            Integer rolled = rollDice();

            currVehicle.move();
        }
    }

    public static void gameOver(){
        //adding the result to session's results list
        Session.addResult(rounds);
    }
    public void increaseRounds(){
        this.rounds++;
    }
    public List<Field> getPos(){
        List<Field> returnList = new ArrayList<>();
        for(var currVehicle : vehicles){
            returnList.add(currVehicle.getCurrentField());
        }
        return returnList;
    }
}
