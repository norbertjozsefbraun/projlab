package test;
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
        SnowPlow sp = new model.entities.SnowPlow();
        IceCracker icecracker = new IceCracker();
        Field cf = new Field();
        Field nf = new Field();

        sp.changeHead(icecracker);
        sp.move(1);

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

        //model.items.Salter refill use-case test
        Shop shop = new Shop();
        Salter salter = new Salter();
        Salt saltResource = new Salt(10, 5);

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
}
