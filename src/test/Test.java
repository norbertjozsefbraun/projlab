package test;

import java.util.ArrayList;
import java.util.List;
import model.buildings.BusStop;
import model.buildings.Garage;
import model.buildings.Home;
import model.entities.Bus;
import model.entities.Car;
import model.entities.SnowPlow;
import model.items.Head;
import model.items.Salter;
import model.items.Sweeper;
import model.map.Field;

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
        // //model.items.Salter refill use-case test
        // model.core.Shop shop = new model.core.Shop();
        // model.items.Salter salter = new model.items.Salter();
        // model.items.Salt saltResource = new model.items.Salt();

        // saltResource.pay(shop); 
        // salter.refill(saltResource);

        // //model.items.IceCracker Working use-case test
        // model.entities.SnowPlow sp = new model.entities.SnowPlow();
        // model.items.IceCracker icecracker = new model.items.IceCracker();
        // model.map.Field cf = new model.map.Field();
        // model.map.Field nf = new model.map.Field();

        // sp.changeHead(icecracker);
        // sp.move(1, 120);


        // //DragonHeadWorking use-case test
        // model.entities.SnowPlow sp = new model.entities.SnowPlow();
        // model.items.Dragon dragon = new model.items.Dragon();
        // model.map.Field cf = new model.map.Field();
        // model.map.Field nf = new model.map.Field();

        // sp.changeHead(dragon);
        // sp.move(1, 150);

        // //SweeperHeadWorking use-case test
        // model.entities.SnowPlow sp = new model.entities.SnowPlow();
        // model.items.Sweeper sweeper = new model.items.Sweeper();
        // model.map.Field c1 = new model.map.Field();
        // model.map.Field field = new model.map.Field();

        // sp.changeHead(sweeper);
        // nf.setRightNeighbor(field);

        // sp.move(1, 200);

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
