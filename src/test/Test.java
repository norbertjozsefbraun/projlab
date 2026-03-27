package test;
import java.util.ArrayList;
import java.util.List;
import model.buildings.*;
import model.core.*;
import model.entities.*;
import model.items.*;
import model.map.*;

public class Test {

    public static void testCarIsBlocked() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        // inicializalas...
        /// Car car = new Car();
        /// skeleton.ctor(car, "car1");

        // elso fv. meghivasa

        skeleton.reset();
    }

    public static void testIceCracker(){
        //model.items.IceCracker Working use-case test

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

        sp.changeHead(icecracker);
        sp.move(1);
        skeleton.reset();

    }

    public static void testSweeper() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();
        
        //SweeperHeadWorking use-case test
        SnowPlow sp = new SnowPlow();
        Sweeper sweeper = new Sweeper();
        Field c1 = new Field();
        Field field = new Field();

        sp.changeHead(sweeper);
        field.setRightNeighbour(field);

        sp.move(1);


        skeleton.reset();
    }

    public static void testSalterRefill() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        //Salter refill use-case test
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

    public static void testDragon() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        //DragonHeadWorking use-case test
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
        Sweeper sw = new Sweeper();
        Salter sr = new Salter();
        heads.add(sw);
        heads.add(sr);

        SnowPlow sp = new SnowPlow();
        sp.setActiveHead(sw);
        sp.setHeads(heads);

        //func call
        sp.changeHead(sr);

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

        SnowPlow sp = new SnowPlow();
        sp.setCurrentField(cf);
        sp.setGarage(g);

        Car c = new Car();
        c.setCurrentField(nf);
        c.setHome(h);

        //func call
        sp.move(1);

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
