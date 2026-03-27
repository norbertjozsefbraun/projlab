package test;
import java.util.ArrayList;
import java.util.List;
import model.buildings.*;
import model.core.*;
import model.entities.*;
import model.items.*;
import model.map.*;

public class Test {

    public static void testIceGetsOnField() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        Car car1 = new Car();
        skeleton.ctor(car1, "c");

        Field prev = new Field();
        skeleton.ctor(prev, "prev");

        Field f =  new Field();
        skeleton.ctor(f, "f");

        Surface s = new Surface();
        skeleton.ctor(s, "s");
        f.setSurface(s);

        prev.setVehicles(new ArrayList<>(List.of(car1)));
        prev.setNextField(f);
        car1.setCurrentField(prev);

        car1.move(1);


        // elso fv. meghivasa

        skeleton.reset();
    }

    public static void testCarIsBlocked() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();


         Car car1 = new Car();
         skeleton.ctor(car1, "car1");

         Field prev = new Field();
         Field f =  new Field();

         prev.setVehicles(List.of(car1));



        // elso fv. meghivasa

        skeleton.reset();
    }

    //IceCracker Working use-case test
    /** It tests the functionality of the IceCracker head when attached to a SnowPlow and moving. */
    public static void testIceCracker(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        SnowPlow sp = new model.entities.SnowPlow();
        IceCracker icecracker = new IceCracker();
        Field cf = new Field();
        Field nf = new Field();

        skeleton.ctor(sp, "sp");
        skeleton.ctor(icecracker, "icecracker");
        skeleton.ctor(cf, "cf");
        skeleton.ctor(nf, "nf");

        sp.setActiveHead(icecracker);
        sp.setCurrentField(cf);
        cf.setNextField(nf);
        
        sp.move(1);

        skeleton.reset();

    }

    //SweeperHeadWorking use-case test
    /** It tests the functionality of the Sweeper head when attached to a SnowPlow and moving. */
    public static void testSweeper() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();
        
        SnowPlow sp = new SnowPlow();
        Sweeper sweeper = new Sweeper();
        Field c1 = new Field();
        Field field = new Field();

        sp.changeHead(sweeper);
        field.setRightNeighbour(field);

        sp.move(1);


        skeleton.reset();
    }

    //Salter refill use-case test
    /** It tests the Salter's ability to refill its salt supply. */
    public static void testSalterRefill() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        Shop shop = new Shop();
        Salter salter = new Salter();
        Salt saltResource = new Salt(10, 5);
        salter.setSalt(saltResource);

        skeleton.ctor(shop, "shop");
        skeleton.ctor(salter, "salter");
        skeleton.ctor(saltResource, "salt");

        saltResource.pay(shop); 
        salter.refill(saltResource);

        skeleton.reset();
    }

    //DragonHeadWorking use-case test
    /** It tests the functionality of the Dragon head when attached to a SnowPlow and moving. */
    public static void testDragon() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        SnowPlow sp = new SnowPlow();
        Dragon dragon = new Dragon();
        Field cf = new Field();
        Field nf = new Field();

        sp.changeHead(dragon);
        sp.move(1);


        skeleton.reset();
    }

    public static void swappingHeads() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        //init
        List<Head> heads = new ArrayList<>();
        Sweeper sweeper = new Sweeper();
        skeleton.ctor(sweeper, "sweeper");
        Salter salter = new Salter();
        skeleton.ctor(salter, "salter");
        heads.add(sweeper);
        heads.add(salter);

        SnowPlow snowplow = new SnowPlow();
        skeleton.ctor(snowplow, "snowplow");
        snowplow.setActiveHead(sweeper);
        snowplow.setHeads(heads);

        //func call
        snowplow.changeHead(salter);

        skeleton.reset();
    }

    public static void snowPlowAnsCArCollision() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        //init
        Field cf = new Field();
        Field nf = new Field();
        cf.setNextField(nf);

        Garage g = new Garage();
        Home h = new Home();

        SnowPlow snowplow = new SnowPlow();
        skeleton.ctor(snowplow, "snowplow");
        snowplow.setCurrentField(cf);
        snowplow.setGarage(g);

        Car c = new Car();
        c.setCurrentField(nf);
        c.setHome(h);

        //func call
        snowplow.move(1);

        skeleton.reset();
    }

    public static void busCompletingRound() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        //init
        Field cf = new Field();
        BusStop stopA = new BusStop();
        BusStop stopB = new BusStop();

        Bus b = new Bus();
        b.setCurrentField(cf);
        b.setStopA(stopA);
        b.setStopB(stopB);
        b.setPreviousStop(stopB);

        //func call
        b.move(1);

        skeleton.reset();
    }

}
