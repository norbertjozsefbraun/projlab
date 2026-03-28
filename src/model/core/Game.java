package model.core;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.entities.SnowPlow;
import model.entities.Vehicle;
import model.map.World;
import model.map.Field;
import test.Skeleton;


public class Game {
    /// Fields:

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



    /// Constructor:
    public Game(List<Vehicle> vehcs, World world){
        Skeleton skeleton = Skeleton.getInstance();

        vehicles = vehcs;
        ticker = new Ticker();
        this.world = world;
        shop = new Shop();
        rounds = 0;
        dice = new Random();

        skeleton.ctor(vehicles , "vehicles");
        skeleton.ctor(this.world , "world");

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

    /*
    rolldice() - this method will take a random number from 1-6 inclusively from both ends
    @no params
     */
    private Integer rollDice(){
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

        //Getting the skeleton instance
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this , "makeTurn()");
        //Falling snow
        this.world.snowfall();



        //Iterating through the vehicles
        for(var currVehicle : vehicles){

            //Rolling the dice
            Integer rolled = rollDice();

            //If SnowPlow, we need special treatment.
            if(currVehicle.getClass().getSimpleName().equals("SnowPlow")){

                //Getting the available heads
                List<String> availableHeads = new ArrayList<>();
                for(var currHead : ((SnowPlow) currVehicle).getHeads()){
                    availableHeads.add(currHead.getClass().getSimpleName());
                }

                //Converting it to String[]
                String[] strArray = new String[availableHeads.size()];
                for(int i = 0; i < availableHeads.size() ; i++){
                    strArray[i] = availableHeads.get(i);
                }

                //Handling user input
                int choice = -1;

                if(!availableHeads.isEmpty()){
                    choice = skeleton.getChoice("Do you want to change heads? (Please enter a single integer that's the beginning of an option)\n", strArray);
                }

                //If changed we change it to necessary head
                if(choice != -1 && (availableHeads.get(choice) != ((SnowPlow) currVehicle).getActiveHead().getClass().getSimpleName())){
                    ((SnowPlow) currVehicle).changeHead(((SnowPlow) currVehicle).getHeads().get(choice));
                }
            }

            currVehicle.move(rolled);
        }
        skeleton.returnMethod();
    }

    public static void gameOver(){


        Skeleton skeleton = Skeleton.getInstance();
        Game tempG = new Game(new ArrayList<>() , new World());
        skeleton.ctor(tempG , "game");
        skeleton.call( tempG, "gameOver()");

        //adding the result to session's results list
        Session.addResult(rounds);
        skeleton.returnMethod();
    }
    public void increaseRounds(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this , "increaseRounds()");
        //increase the rounds
        this.rounds++;
        skeleton.returnMethod();
    }
    public List<Field> getPos(){

        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this , "getPos()");
        //Initializing the return list of fields that have the vehicles' positions
        List<Field> returnList = new ArrayList<>();
        for(var currVehicle : vehicles){
            //Getting the positions
            returnList.add(currVehicle.getCurrentField());
        }

        skeleton.returnMethod("List<Field>" , "returnList");
        return returnList;
    }
}
