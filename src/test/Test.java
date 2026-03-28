package test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        skeleton.reset();
    }

    public static void testCarSlipsOnIce() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        Car c = new Car();
        skeleton.ctor(c, "c");
        Field prev = new Field();
        skeleton.ctor(prev, "prev");
        Field f1 = new Field();
        skeleton.ctor(f1, "f1");
        Surface s1 = new Surface();
        skeleton.ctor(s1, "s1");
        Field f2 = new Field();
        skeleton.ctor(f2, "f2");
        Field f3 = new Field();
        skeleton.ctor(f3, "f3");

        s1.setIsIce(true);
        f1.setSurface(s1);

        prev.setVehicles(new ArrayList<>(List.of(c)));
        prev.setNextField(f1);
        c.setCurrentField(prev);
        f1.setNextField(f2);
        f2.setNextField(f3);

        c.move(1);
    }

    public static void testCarIsBlocked() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        Car c = new Car();
        skeleton.ctor(c, "v");

        Field current = new Field();
        skeleton.ctor(current, "current");
        Field next =  new Field();
        skeleton.ctor(next, "next");
        Field left =  new Field();
        skeleton.ctor(left, "left");
        Field right =  new Field();
        skeleton.ctor(right, "right");

        next.setAccidentTimer(1);
        left.setAccidentTimer(2);
        right.setAccidentTimer(2);

        current.setNextField(next);
        next.setLeftNeighbour(left);
        next.setRightNeighbour(right);

        current.setVehicles(List.of(c));
        c.setCurrentField(current);

        c.move(1);

        skeleton.reset();
    }

    public static void testSnowfallInGame() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        World world = new World();
        skeleton.ctor(world, "w");

        Road road = new Road();
        skeleton.ctor(road, "r");

        Lane lane = new Lane();
        skeleton.ctor(lane, "l");

        Field field = new Field();
        skeleton.ctor(field, "f");

        Surface surface = new Surface();
        skeleton.ctor(surface, "s");

        field.setSurface(surface);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(field)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));


        world.snowfall();


        skeleton.reset();
    }

    //IceCracker Working use-case test
    /** It tests the functionality of the IceCracker head when attached to a SnowPlow and moving. */
    public static void testIceCracker(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        SnowPlow sp = new SnowPlow();
        IceCracker icecracker = new IceCracker();
        Field cf = new Field();
        Field nf = new Field();
        Surface s = new Surface();

        skeleton.ctor(sp, "sp");
        skeleton.ctor(icecracker, "icecracker");
        skeleton.ctor(cf, "cf");
        skeleton.ctor(nf, "nf");
        skeleton.ctor(s, "s");

        sp.setActiveHead(icecracker);
        sp.setCurrentField(cf);
        cf.setNextField(nf);
        nf.setSurface(s);

        sp.move(1);

        skeleton.reset();

    }

    //SweeperHeadWorking use-case test
    /** It tests the functionality of the Sweeper head when attached to a SnowPlow and moving. */
    // public static void testSweeper() {
    //     Skeleton skeleton = Skeleton.getInstance();
    //     skeleton.reset();
        
    //     SnowPlow sp = new SnowPlow();
    //     Sweeper sweeper = new Sweeper();
    //     Field c1 = new Field();
    //     Field field = new Field();

    //     sp.changeHead(sweeper);
    //     field.setRightNeighbour(field);

    //     sp.move(1);


    //     skeleton.reset();
    // }

    public static void testSweeper() {
    Skeleton skeleton = Skeleton.getInstance();
    skeleton.reset();
    
    // 1. Objektumok létrehozása
    SnowPlow sp = new SnowPlow();
    Sweeper sweeper = new Sweeper();
    Field c1 = new Field();      // Start mező (cf)
    Field field = new Field();   // Cél mező (nf)
    Field neighbor = new Field(); // Ide kerül a hó!
    Surface s1 = field.getSurface(); // A célmező felülete

    // 2. Regisztráció (Hogy szép legyen a log)
    skeleton.ctor(sp, "sp");
    skeleton.ctor(sweeper, "sweeper");
    skeleton.ctor(c1, "c1");
    skeleton.ctor(field, "field");
    skeleton.ctor(neighbor, "neighbor");
    skeleton.ctor(s1, "s");

    // 3. ÖSSZEKÖTÉS (A seprőnek kell a szomszéd!)
    sp.setCurrentField(c1);
    c1.setNextField(field);
    
    // Beállítjuk a szomszédot, hogy a sweeper tudjon hová seperni
    field.setRightNeighbour(neighbor); 
    
    // Felszereljük a seprűt
    sp.setActiveHead(sweeper);

    // 4. Futtatás
    // A move(1) hatására: c1 -> field -> sweeper:clean(field) -> neighbor:addSnow(10)
    sp.move(1);

    skeleton.reset();
}

    //Salter refill use-case test
    /** It tests the Salter's ability to refill its salt supply. */
    public static void testSalterRefill() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        Shop shop = new Shop();
        shop.setBalance(10);
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
        Surface s = new Surface();

        skeleton.ctor(sp, "sp");
        skeleton.ctor(dragon, "dragon");
        skeleton.ctor(cf, "cf");
        skeleton.ctor(nf, "nf");
        skeleton.ctor(s, "s");

        sp.setCurrentField(cf);
        cf.setNextField(nf);
        nf.setSurface(s);
        sp.setActiveHead(dragon);

        sp.move(1);

        skeleton.reset();
    }

    public static void testSwappingHeads() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        //init
        List<Head> heads = new ArrayList<>();
        Sweeper sweeper = new Sweeper();
        skeleton.ctor(sweeper, "sweeper");
        Salter salter = new Salter();
        skeleton.ctor(salter, "salter");
        
        SnowPlow snowplow = new SnowPlow();
        skeleton.ctor(snowplow, "snowplow");
        snowplow.setActiveHead(sweeper);
        snowplow.setHeads(heads);
        snowplow.addHead(sweeper);
        snowplow.addHead(salter);

        //func call
        snowplow.changeHead(salter);

        skeleton.reset();
    }

    public static void testCarMoves() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        //init
        Surface surface = new Surface();
        skeleton.ctor(surface, "surface");
        Field currentField = new Field();
        skeleton.ctor(currentField, "currentField");
        Field nextField = new Field();
        skeleton.ctor(nextField, "nextField");
        nextField.setSurface(surface);
        currentField.setNextField(nextField);
        
        List<Vehicle> vehicles = new ArrayList<>();
        Car car = new Car();
        skeleton.ctor(car, "car");
        vehicles.add(car);
        car.setCurrentField(currentField);
        
        World world = new World();
        skeleton.ctor(world, "w");

        Road road = new Road();
        skeleton.ctor(road, "r");

        Lane lane = new Lane();
        skeleton.ctor(lane, "l");

        Field field = new Field();
        skeleton.ctor(field, "f");

        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(field)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        Game game = new Game(vehicles, world);

        //func call
        game.makeTurn();

        skeleton.reset();
    }

    public static void testSnowPlowAndCarCollision() {
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

    public static void testBusCompletingRound() {
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

    public static void testGameOver(){
        Skeleton skeleton = Skeleton.getInstance();
        SnowPlow snowPlow = new SnowPlow();
        List<Vehicle> vehcs = new ArrayList<>();
        vehcs.add(snowPlow);
        Car car = new Car();
        vehcs.add(car);


        skeleton.ctor(snowPlow , "snowPlow");
        Field field = new Field();
        List<Vehicle> justCar = new ArrayList<>();
        justCar.add(car);
        field.setVehicles(justCar);
        skeleton.ctor(field , "field");
        Garage garage = new Garage();
        skeleton.ctor(garage , "garage");
        World world = new World();
        Game game = new Game(vehcs , world);
        skeleton.ctor(game , "game");
        garage.setDestroyedNum(3);
        Field tempField = new Field();
        snowPlow.setCurrentField(tempField);
        tempField.setNextField(field);
        Sweeper tempSw = new Sweeper();
        snowPlow.setActiveHead(tempSw);
        snowPlow.setGarage(garage);
        snowPlow.move(1);


        skeleton.reset();
    }

    public static void testCarEntersWorkplace(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        World world = new World();
        skeleton.ctor(world,"world");

        Car car = new Car();
        skeleton.ctor(car,"car");
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(car);

        Field cField = new Field();
        skeleton.ctor(cField, "carField");

        Game game = new Game(vehicles, world);
        skeleton.ctor(game,"game");

        Intersection intersection = new Intersection();
        skeleton.ctor(intersection,"intersection");

        WorkPlace workPlace = new WorkPlace();
        skeleton.ctor(workPlace,"workPlace");
        
        //Initialization
        car.setCurrentField(cField);
        List<Building> buildings = new ArrayList<>();
        buildings.add(workPlace);
        car.setBuildings(buildings);
        workPlace.setLocation(intersection);
        intersection.setBuilding(workPlace);

        //Snowfall initialization
        Road road = new Road();
        skeleton.ctor(road, "r");

        Lane lane = new Lane();
        skeleton.ctor(lane, "l");

        Field field = new Field();
        skeleton.ctor(field, "f");

        Surface surface = new Surface();
        skeleton.ctor(surface, "s");

        field.setSurface(surface);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(field)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        game.makeTurn();

        skeleton.reset();
        
    }
    
    public static void testCarArrivesHome(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        World world = new World();
        skeleton.ctor(world,"world");

        Car car = new Car();
        skeleton.ctor(car,"car");
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(car);

        Field cField = new Field();
        skeleton.ctor(cField, "carField");

        Game game = new Game(vehicles, world);
        skeleton.ctor(game,"game");

        Intersection intersection = new Intersection();
        skeleton.ctor(intersection,"intersection");

        Home home = new Home();
        skeleton.ctor(home,"home");
        
        //Initialization
        car.setCurrentField(cField);
        List<Building> buildings = new ArrayList<>();
        buildings.add(home);
        car.setBuildings(buildings);
        home.setLocation(intersection);
        intersection.setBuilding(home);
        cField.setNextField(null);

        //Snowfall initialization
        Road road = new Road();
        skeleton.ctor(road, "r");

        Lane lane = new Lane();
        skeleton.ctor(lane, "l");

        Field field = new Field();
        skeleton.ctor(field, "f");

        Surface surface = new Surface();
        skeleton.ctor(surface, "s");

        field.setSurface(surface);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(field)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        game.makeTurn();

        skeleton.reset();
    }

    public static void testCarWaitsAtWorkplaceAndLeaves(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        World world = new World();
        skeleton.ctor(world,"world");

        Car car = new Car();
        skeleton.ctor(car,"car");
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(car);

        Game game = new Game(vehicles, world);
        skeleton.ctor(game,"game");

        Intersection intersection = new Intersection();
        skeleton.ctor(intersection,"intersection");

        WorkPlace workPlace = new WorkPlace();
        skeleton.ctor(workPlace,"workPlace");
        
        //Initialization
        List<Building> buildings = new ArrayList<>();
        buildings.add(workPlace);
        car.setBuildings(buildings);
        workPlace.setLocation(intersection);
        intersection.setBuilding(workPlace);
        Map<Car, Integer> cars= new HashMap<Car, Integer>();
        cars.put(car, 2);
        workPlace.setWaitingCars(cars);
        car.setCurrentBuilding(workPlace);
        car.setCurrentField(null);

        //Snowfall initialization
        Road road = new Road();
        skeleton.ctor(road, "r");

        Lane lane = new Lane();
        skeleton.ctor(lane, "l");

        Field field = new Field();
        skeleton.ctor(field, "f");

        Surface surface = new Surface();
        skeleton.ctor(surface, "s");

        field.setSurface(surface);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(field)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        for(int i = 0;i<2;i++){
            game.makeTurn();
            workPlace.processWaiting();    
        }
        skeleton.reset();
    }
    
    public static void testCarsCollide(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        World world = new World();
        skeleton.ctor(world,"world");

        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        //First car init
        Car car1 = new Car();
        skeleton.ctor(car1,"car1");
        ///vehicles.add(car1); lets say it already moved

        //Second car init
        Car car2 = new Car();
        skeleton.ctor(car2,"car2");
        vehicles.add(car2);

        //Game init
        Game game = new Game(vehicles, world);
        skeleton.ctor(game,"game");

        //Fields init
        Field car2CurrentField = new Field();
        skeleton.ctor(car2CurrentField,"car2CurrentField");
        Field targetField = new Field();
        skeleton.ctor(targetField,"targetField");
        Field car2NextField = new Field();
        skeleton.ctor(car2NextField,"car2NextField");

        //Surface init
        Surface car2NextFieldSurface = new Surface();
        skeleton.ctor(car2NextFieldSurface,"car2NextFieldSurface");

        //Setting up the conditions
        car2NextFieldSurface.setIsIce(true);
        car2NextField.setSurface(car2NextFieldSurface);
        car1.setCurrentField(targetField);
        car2.setCurrentField(car2CurrentField);
        car2CurrentField.setNextField(car2NextField);
        car2NextField.setNextField(targetField);

        //Snowfall initialization
        Road road = new Road();
        skeleton.ctor(road, "r");

        Lane lane = new Lane();
        skeleton.ctor(lane, "l");

        Field field = new Field();
        skeleton.ctor(field, "f");

        Surface surface = new Surface();
        skeleton.ctor(surface, "s");

        field.setSurface(surface);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(field)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        //Begin
        game.makeTurn();

        skeleton.reset();

    }
}
