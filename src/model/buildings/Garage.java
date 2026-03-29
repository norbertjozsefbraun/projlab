package model.buildings;

import java.util.List;
import model.core.Game;
import model.entities.SnowPlow;
import model.entities.Vehicle;
import test.Skeleton;

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
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this , "enterVehicle", skeleton.getObjectName(v));
        increaseDestroyedNum();
        v.setCurrentBuilding(this);
        if(destroyedNum > 3){
            Game.gameOver();
        }
        skeleton.returnMethod();
    }

    /**
     * The garage deploys the snowplow currenlty in the garage
     * @param v The vehicle currently parking in the garage
     */
    public void deployVehicle(Vehicle v){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this, "deployVehicle", skeleton.getObjectName(v));
        getLocation().acceptVehicle(v);
        skeleton.returnMethod();
    }

    /**
     * When the player buys a new snowplow it is added to the garage
     * @param s The new snowplow the player bought in the shop
     */
    public void newSnowPlow(SnowPlow s){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this, "newSnowPlow", skeleton.getObjectName(s));
        snowPlows.add(s);
        skeleton.returnMethod();
    }

    /**
     * Increases the destoryedNum attribute, When a snowplow runs over a car
     */
    public void increaseDestroyedNum(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this, "increaseDestroyedNum");
        this.destroyedNum++;
        skeleton.returnMethod();
    }

}
