package model.buildings;

import java.util.List;

import model.entities.SnowPlow;
import model.entities.Vehicle;

public class Garage extends Building{
    /**
     * The list of snowplows that belong to the garage
     */
    private List<SnowPlow> snowPlows;

    /**
     * The number of times a snowplow has run over a car
     */
    private Integer destroyedNum;


    /**
     * When a snowplow destorys a car it respwans in its garage
     * @param v The vehicle respawning in the garage
     */
    public void enterVehicle(Vehicle v){}

    /**
     * The garage deploys the snowplow currenlty in the garage
     * @param v The vehicle currently parking in the garage
     */
    public void deployVehicle(Vehicle v){}

    /**
     * When the player buys a new snowplow it is added to the garage
     * @param s The new snowplow the player bought in the shop
     */
    public void newSnowPlow(SnowPlow s){}

    /**
     * Increases the destoryedNum attribute, When a snowplow runs over a car
     */
    public void increaseDestroyedNum(){}

}
