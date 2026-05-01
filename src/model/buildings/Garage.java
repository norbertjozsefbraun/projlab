package model.buildings;

import java.util.ArrayList;
import java.util.List;
import model.core.Game;
import model.entities.SnowPlow;
import model.entities.Vehicle;

public class Garage extends Building{
    /**
     * The list of snowplows that belong to the garage
     */
    private List<SnowPlow> snowPlows = new ArrayList<>();

    /**
     * The number of times a snowplow has run over a car
     */
    private Integer destroyedNum = 0;

    public Garage() {
        snowPlows = new ArrayList<>();
        destroyedNum = 0;
    }

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
     * Sets the vehicles currentBuilding to this Building and sets its currentField to null
     * Manages destroyedNum
     * @param v The vehicle respawning in the garage
     */
    public void enterVehicle(Vehicle v){
        increaseDestroyedNum();

        v.setCurrentBuilding(this);
        v.setCurrentField(null);

        if(destroyedNum > 3){
            Game.gameOver();
        }
    }

    /**
     * The garage deploys the snowplow currenlty in the garage
     * Sets its currentBuilding and previousIntersection
     * @param v The vehicle currently parking in the garage
     */
    public void deployVehicle(Vehicle v){

        v.setCurrentBuilding(null);
        v.setPreviousIntersection(getLocation());

        getLocation().acceptVehicle(v);
    }

    /**
     * When the player buys a new snowplow it is added to the garage
     * @param s The new snowplow the player bought in the shop
     */
    public void newSnowPlow(SnowPlow s){
        snowPlows.add(s);
        s.setCurrentBuilding(this);
        s.setCurrentField(null);
    }

    /**
     * Increases the destoryedNum attribute, When a snowplow runs over a car
     */
    public void increaseDestroyedNum(){
        this.destroyedNum++;
    }

}
