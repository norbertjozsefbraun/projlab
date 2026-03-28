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

    /// Getters: TODO
    public Integer getDestroyedNum() {
        return destroyedNum;
    }

    /// Setters: TODO
    public void setDestroyedNum(Integer destroyedNum) {
        this.destroyedNum = destroyedNum;
    }

    /**
     * When a snowplow destorys a car it respwans in its garage
     * @param v The vehicle respawning in the garage
     */
    public void enterVehicle(Vehicle v){
        //Todo: PLEASE IMPLEMENT THE REST THIS IS JUST THE PART I NEED
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.call(this , "enterVehicle(Vehicle v)");
        destroyedNum++;
        if(destroyedNum > 3){
            Game.gameOver();
        }
        skeleton.returnMethod();
    }

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
