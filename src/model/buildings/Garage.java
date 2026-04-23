package model.buildings;

import java.util.List;
import model.core.Game;
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
     * Getter for the list of snowplows
     * @return the list of snowplows that belong to the garage
     */
    public List<SnowPlow> getSnowPlows(){
        return snowPlows;
    }

    /**
     * Getter for the destroyedNum attribute
     * @return the number of times the snowplow in the garage has destroyed a car
     */
    public Integer getDestroyedNum(){
        return destroyedNum;
    }

    /// Setters
    public void setDestroyedNum(Integer destroyedNum) {
        this.destroyedNum = destroyedNum;
    }

    public void setsnowPlows(List<SnowPlow> snowPlow){
        for(SnowPlow s : snowPlow){
            this.snowPlows.add(s);
        }
    }

    /**
     * When a snowplow destorys a car it respwans in its garage
     * @param v The vehicle respawning in the garage
     */
    public void enterVehicle(Vehicle v){
        //Todo: PLEASE IMPLEMENT THE REST THIS IS JUST THE PART I NEED
        increaseDestroyedNum();
        v.setCurrentBuilding(this);
        if(destroyedNum > 3){
            Game.gameOver();
        }
    }

    /**
     * The garage deploys the snowplow currenlty in the garage
     * @param v The vehicle currently parking in the garage
     */
    public void deployVehicle(Vehicle v){
        getLocation().acceptVehicle(v);
    }

    /**
     * When the player buys a new snowplow it is added to the garage
     * @param s The new snowplow the player bought in the shop
     */
    public void newSnowPlow(SnowPlow s){
        snowPlows.add(s);
    }

    /**
     * Increases the destoryedNum attribute, When a snowplow runs over a car
     */
    public void increaseDestroyedNum(){
        this.destroyedNum++;
    }

}
