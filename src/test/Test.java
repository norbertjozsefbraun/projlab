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
        car1.setCanMove(true);

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
        Surface s2 = new Surface();
        skeleton.ctor(s2, "s2");
        Field f3 = new Field();
        skeleton.ctor(f3, "f3");
        Surface s3 = new Surface();
        skeleton.ctor(s3, "s3");

        s1.setIsIce(true);
        f1.setSurface(s1);
        f2.setSurface(s2);
        f3.setSurface(s3);

        prev.setVehicles(new ArrayList<>(List.of(c)));
        prev.setNextField(f1);
        c.setCanMove(true);
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
        c.setCanMove(true);
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
        skeleton.ctor(s, "surface");

        Garage g = new Garage();
        g.setDestroyedNum(0);
        sp.setGarage(g);
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
    Surface s1 = new Surface(); // A célmező felülete

    // 2. Regisztráció (Hogy szép legyen a log)
    skeleton.ctor(sp, "sp");
    skeleton.ctor(sweeper, "sweeper");
    skeleton.ctor(c1, "cf");
    skeleton.ctor(field, "field");
    skeleton.ctor(neighbor, "neighbor");
    skeleton.ctor(s1, "surface");

    // 3. ÖSSZEKÖTÉS (A seprőnek kell a szomszéd!)
    Garage g = new Garage();
    g.setDestroyedNum(0);
    sp.setGarage(g);
    sp.setCurrentField(c1);
    c1.setNextField(field);
    neighbor.setSurface(s1);
    
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
        Biokerosene biokerosene = new Biokerosene();

        skeleton.ctor(sp, "sp");
        skeleton.ctor(dragon, "dragon");
        skeleton.ctor(cf, "cf");
        skeleton.ctor(nf, "nf");
        skeleton.ctor(s, "surface");
        skeleton.ctor(biokerosene, "biokerosene");

        Garage g = new Garage();
        g.setDestroyedNum(0);
        sp.setGarage(g);
        sp.setCurrentField(cf);
        cf.setNextField(nf);
        nf.setSurface(s);
        sp.setActiveHead(dragon);
        dragon.setKerosene(biokerosene);

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
        car.setCanMove(true);
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
        Surface s = new Surface();
        skeleton.ctor(s, "surface");
        Field cf = new Field();
        skeleton.ctor(cf, "currentField");
        Field nf = new Field();
        skeleton.ctor(nf, "nextField");
        nf.setSurface(s);
        cf.setNextField(nf);

        Garage g = new Garage();
        skeleton.ctor(g, "garage");
        Home h = new Home();
        skeleton.ctor(h, "home");
        g.setDestroyedNum(0);

        IceCracker iceCracker = new IceCracker();
        skeleton.ctor(iceCracker, "iceCracker");

        SnowPlow snowplow = new SnowPlow();
        skeleton.ctor(snowplow, "snowplow");
        snowplow.setCurrentField(cf);
        snowplow.setGarage(g);
        snowplow.setActiveHead(iceCracker);

        Car c = new Car();
        c.setCurrentField(nf);
        c.setHome(h);

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(c);
        nf.setVehicles(vehicles);

        //func call
        snowplow.move(1);

        skeleton.reset();
    }

    public static void testBusCompletingRound() {
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        //init
        List<Building> buildings = new ArrayList<>();
        List<Field> fieldsBA = new ArrayList<>();
        List<Field> fieldsAB = new ArrayList<>();
        List<Field> fieldsAC = new ArrayList<>();
        List<Field> fieldsCA = new ArrayList<>();
        List<Lane> lanesAA = new ArrayList<>();
        List<Lane> lanesAB = new ArrayList<>();
        List<Lane> lanesBC = new ArrayList<>();
        List<Lane> lanesBA = new ArrayList<>();
        List<Road> roads = new ArrayList<>();
        List<Bus> buses = new ArrayList<>();
        List<Vehicle> vehicles = new ArrayList<>();

        Shop shop = new Shop();
        shop.setBalance(0);
        World world = new World();
        Game game = new Game(vehicles, world);
        game.setShop(shop);

        Surface sA = new Surface();
        skeleton.ctor(sA, "surfaceA");
        Surface sB = new Surface();
        skeleton.ctor(sB, "surfaceB");
        Field cf = new Field();
        skeleton.ctor(cf, "currentField");
        fieldsBA.add(cf);
        Field nfA = new Field();
        skeleton.ctor(nfA, "nextFieldA");
        nfA.setSurface(sA);
        fieldsAB.add(nfA);
        Field nfB = new Field();
        skeleton.ctor(nfB, "nextFieldB");
        nfB.setSurface(sB);
        fieldsAC.add(nfB);
        Field bB = new Field();
        fieldsCA.add(bB);

        Intersection destA = new Intersection();
        skeleton.ctor(destA, "intersection");
        Intersection destB = new Intersection();
        Intersection destC = new Intersection();

        BusStop stopA = new BusStop();
        skeleton.ctor(stopA, "stopA");
        stopA.setLocation(destA);
        stopA.setGame(game);
        stopA.setBuses(buses);
        buildings.add(stopA);
        BusStop stopB = new BusStop();
        skeleton.ctor(stopB, "stopB");
        buildings.add(stopB);

        Road roadA = new Road("roadA", RoadType.STANDARD, 1, 1);
        skeleton.ctor(roadA, "roadA");
        roads.add(roadA);
        Road roadB = new Road("roadB", RoadType.STANDARD, 1, 1);
        skeleton.ctor(roadB, "roadB");
        roads.add(roadB);

        destA.setBuilding(stopA);
        destA.setConnectedRoads(roads);
        roadA.setDestinationA(destA);
        roadA.setDestinationB(destB);
        roadB.setDestinationA(destA);
        roadB.setDestinationB(destC);
        Lane lAA = new Lane(roadA, 1);
        Lane lAB = new Lane(roadA, 1);
        Lane lBC = new Lane(roadB, 1);
        Lane lBA = new Lane(roadB, 1);
        lAA.setFields(fieldsBA);
        lAB.setFields(fieldsAB);
        lBC.setFields(fieldsAC); 
        lBA.setFields(fieldsCA); 
        lanesAA.add(lAA);
        lanesAB.add(lAB);
        lanesBC.add(lBC);
        lanesBA.add(lBA);
        roadA.setLanesToA(lanesAA);
        roadA.setLanesToB(lanesAB);
        roadB.setLanesToA(lanesBA);
        roadB.setLanesToB(lanesBC);

        Bus b = new Bus();
        skeleton.ctor(b, "bus");
        b.setCanMove(true);
        b.setCurrentRoad(roadA);
        b.setCurrentField(cf);
        b.setStopA(stopA);
        b.setStopB(stopB);
        b.setPreviousStop(stopB);
        b.setPreviousIntersection(destB);
        b.setBuildings(buildings);

        //func call
        b.move(1);

        skeleton.reset();
    }

    public static void testGameOver(){
        Skeleton skeleton = Skeleton.getInstance();
        SnowPlow snowPlow = new SnowPlow();
        skeleton.ctor(snowPlow , "snowPlow");
        Car car = new Car();
        skeleton.ctor(car, "car");
        List<Vehicle> vehcs = new ArrayList<>();
        vehcs.add(snowPlow);
        vehcs.add(car);
        List<Vehicle> justCar = new ArrayList<>();
        justCar.add(car);


        Surface s = new Surface();
        skeleton.ctor(s, "surface");
        Field nextField = new Field();
        skeleton.ctor(nextField , "nextField");
        nextField.setVehicles(justCar);
        nextField.setSurface(s);
        Garage garage = new Garage();
        skeleton.ctor(garage , "garage");
        garage.setDestroyedNum(3);
        Home home = new Home();
        skeleton.ctor(home, "home");
        World world = new World();
        Game game = new Game(vehcs , world);
        skeleton.ctor(game , "game");
        Field currentField = new Field();
        skeleton.ctor(currentField, "currentField");
        snowPlow.setCurrentField(currentField);
        currentField.setNextField(nextField);
        Sweeper tempSw = new Sweeper();
        snowPlow.setActiveHead(tempSw);
        snowPlow.setGarage(garage);
        car.setHome(home);
        snowPlow.move(1);


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
        Surface car2CurrentFieldSurface = new Surface();
        skeleton.ctor(car2CurrentFieldSurface,"car2CurrentFieldSurface");
        Surface car2NextFieldSurface = new Surface();
        skeleton.ctor(car2NextFieldSurface,"car2NextFieldSurface");
        Surface targetS = new Surface();
        skeleton.ctor(targetS, "targetS");

        //Intersection init
        Intersection i1 = new Intersection();
        Intersection i2 = new Intersection();

        //Buildings init
        Home home = new Home();
        List<Building> buildings = new ArrayList<>();
        buildings.add(home);

        //Setting up the conditions
        targetField.setSurface(targetS);
        car2NextFieldSurface.setIsIce(true);
        car2NextField.setSurface(car2NextFieldSurface);
        car2CurrentField.setSurface(car2CurrentFieldSurface);
        car1.setCurrentField(targetField);
        List<Vehicle> vehics = new ArrayList<>();
        vehics.add(car1);
        targetField.setVehicles(vehics);
        car2.setCurrentField(car2CurrentField);
        car2CurrentField.setNextField(car2NextField);
        car2NextField.setNextField(targetField);
        car2.setCanMove(true);
        car1.setCanMove(true);
        car2.setBuildings(buildings);
        

        //Snowfall initialization
        Road road = new Road();
        skeleton.ctor(road, "r");
        car2.setCurrentRoad(road);

        Lane lane = new Lane();
        skeleton.ctor(lane, "l");

        road.setDestinationA(i1);
        road.setDestinationB(i2);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(car2CurrentField,car2NextField,targetField)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        //Begin
        game.makeTurn();

        skeleton.reset();

    }
    
    public static void testCarEntersWorkplace(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        //Initialization
        World world = new World();
        skeleton.ctor(world,"world");

        Car car = new Car();
        skeleton.ctor(car,"car");
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(car);

        Field cField = new Field();
        skeleton.ctor(cField, "carField");

        Game game = new Game(vehicles, world);
        skeleton.ctor(game,"game");

        Intersection intersection = new Intersection();
        skeleton.ctor(intersection,"intersection");

        Intersection prevInter = new Intersection();

        WorkPlace workPlace = new WorkPlace();
        skeleton.ctor(workPlace,"workPlace");
        
        Road road = new Road();
        skeleton.ctor(road, "r");

        Lane lane = new Lane();
        skeleton.ctor(lane, "l");

        Field field = new Field();
        skeleton.ctor(field, "f");

        Surface surface = new Surface();
        skeleton.ctor(surface, "s");
        
        //Snowfall init
        field.setSurface(surface);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(field)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        //Car init 
        List<Building> buildings = new ArrayList<>();
        buildings.add(workPlace);
        car.setCurrentField(cField);
        car.setBuildings(buildings);
        car.setCanMove(true);
        car.setCurrentRoad(road);
        car.setPreviousIntersection(prevInter);

        //Field init
        cField.setNextField(null);
        
        //Building init
        workPlace.setLocation(intersection);
        Map<Car, Integer> parkedCars = new HashMap<>();
        workPlace.setWaitingCars(parkedCars);

        //Intersection init
        intersection.setBuilding(workPlace);

        //Road init
        road.setDestinationA(intersection);
        road.setDestinationB(prevInter);

        game.makeTurn();

        skeleton.reset();
        
    }
    
    public static void testCarArrivesHome(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        //Initialization
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

        Intersection prevInter = new Intersection();

        Home home = new Home();
        skeleton.ctor(home,"home");
        
        Road road = new Road();
        skeleton.ctor(road, "r");

        Lane lane = new Lane();
        skeleton.ctor(lane, "l");

        Field field = new Field();
        skeleton.ctor(field, "f");

        Surface surface = new Surface();
        skeleton.ctor(surface, "s");
        
        //Snowfall init
        field.setSurface(surface);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(field)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        //Car init 
        List<Building> buildings = new ArrayList<>();
        buildings.add(home);
        car.setCurrentField(cField);
        car.setBuildings(buildings);
        car.setCanMove(true);
        car.setCurrentRoad(road);
        car.setPreviousIntersection(prevInter);

        //Field init
        cField.setNextField(null);
        
        //Building init
        home.setLocation(intersection);

        //Intersection init
        intersection.setBuilding(home);

        //Road init
        road.setDestinationA(intersection);
        road.setDestinationB(prevInter);

        game.makeTurn();

        skeleton.reset();
    }

    public static void testCarLeavesHome(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        //Initialization
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

        Intersection prevInter = new Intersection();

        Home home = new Home();
        skeleton.ctor(home,"home");
        
        Road road = new Road();
        skeleton.ctor(road, "r");

        Lane lane = new Lane();
        skeleton.ctor(lane, "l");

        Field field = new Field();
        skeleton.ctor(field, "f");

        Surface surface = new Surface();
        skeleton.ctor(surface, "s");
        
        //Snowfall init
        field.setSurface(surface);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(field)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        //Car init 
        List<Building> buildings = new ArrayList<>();
        buildings.add(home);
        car.setCurrentField(null);
        car.setBuildings(buildings);
        car.setCanMove(true);
        car.setCurrentRoad(road);
        car.setPreviousIntersection(prevInter);
        car.setCurrentBuilding(home);

        //Field init
        cField.setNextField(field);
        
        //Building init
        home.setLocation(intersection);
        List<Car> parkedCars = new ArrayList<Car>();
        parkedCars.add(car);

        //Intersection init
        intersection.setBuilding(home);
        List<Road> roads = new ArrayList<>();
        roads.add(road);
        intersection.setConnectedRoads(roads);

        //Road init
        road.setDestinationA(intersection);
        road.setDestinationB(prevInter);

        game.makeTurn();

        skeleton.reset();
    }
    
    public static void testCarWaitsAtWorkplaceAndLeaves(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        //Initialization
        World world = new World();
        skeleton.ctor(world,"world");

        Car car = new Car();
        skeleton.ctor(car,"car");
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(car);

        Field cField = new Field();
        skeleton.ctor(cField, "carField");

        Game game = new Game(vehicles, world);
        skeleton.ctor(game,"game");

        Intersection intersection = new Intersection();
        skeleton.ctor(intersection,"intersection");

        Intersection prevInter = new Intersection();

        WorkPlace workPlace = new WorkPlace();
        skeleton.ctor(workPlace,"workPlace");
        
        Road road = new Road();
        skeleton.ctor(road, "r");

        Lane lane = new Lane();
        skeleton.ctor(lane, "l");

        Field field = new Field();
        skeleton.ctor(field, "f");

        Surface surface = new Surface();
        skeleton.ctor(surface, "s");
        
        //Snowfall init
        field.setSurface(surface);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(field)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        //Car init 
        List<Building> buildings = new ArrayList<>();
        buildings.add(workPlace);
        car.setCurrentField(null);
        car.setBuildings(buildings);
        car.setCanMove(true);
        car.setCurrentRoad(road);
        car.setPreviousIntersection(prevInter);
        car.setCurrentBuilding(workPlace);

        //Field init
        cField.setNextField(field);
        
        //Building init
        workPlace.setLocation(intersection);
        Map<Car, Integer> parkedCars = new HashMap<Car, Integer>();
        parkedCars.put(car,2);
        workPlace.setWaitingCars(parkedCars);

        //Intersection init
        intersection.setBuilding(workPlace);
        List<Road> roads = new ArrayList<>();
        roads.add(road);
        intersection.setConnectedRoads(roads);

        //Road init
        road.setDestinationA(intersection);
        road.setDestinationB(prevInter);

        for(int i =0;i<3;i++){
            game.makeTurn();
        }
        

        skeleton.reset();
    }
    
    public static void testCarCrashesIntoBus(){
        Skeleton skeleton = Skeleton.getInstance();
        skeleton.reset();

        World world = new World();
        skeleton.ctor(world,"world");

        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        //Bus init
        Bus bus = new Bus();
        skeleton.ctor(bus,"bus");
        //vehicles.add(bus);

        //Car init
        Car car = new Car();
        skeleton.ctor(car,"car");
        vehicles.add(car);

        //Game init
        Game game = new Game(vehicles, world);
        skeleton.ctor(game,"game");

        //Fields init
        Field busStartingField = new Field();
        skeleton.ctor(busStartingField,"busStartingField");
        Field targetField = new Field();
        skeleton.ctor(targetField,"targetField");
        Field carStartingField = new Field();
        skeleton.ctor(carStartingField,"carStartingField");

        //Surface init
        Surface targetSurface = new Surface();
        skeleton.ctor(targetSurface,"targetSurface");
        Surface busStartingFieldSurface = new Surface();
        skeleton.ctor(busStartingFieldSurface,"busStartingFieldSurface");
        Surface carStaringFieldSurface = new Surface();
        skeleton.ctor(carStaringFieldSurface, "carStaringFieldSurface");

        //Intersection init
        Intersection i1 = new Intersection();
        Intersection i2 = new Intersection();

        //Building init
        WorkPlace workPlace = new WorkPlace();
        BusStop b1 = new BusStop();
        BusStop b2 = new BusStop();
        workPlace.setLocation(i1);
        car.setBuildings(List.of(workPlace));
        car.setPreviousIntersection(i1);
        bus.setBuildings(List.of(b1,b2));
        bus.setStopA(b1);
        bus.setStopB(b2);
        bus.setPreviousIntersection(i1);

        //Setting up the conditions
        targetField.setSurface(targetSurface);

        bus.setCanMove(true);
        busStartingFieldSurface.setIsIce(true);
        bus.setCurrentField(busStartingField);
        busStartingField.setNextField(targetField);
        busStartingField.setSurface(busStartingFieldSurface);
        busStartingField.setVehicles(new ArrayList<>(List.of(bus)));

        car.setCurrentField(carStartingField);
        car.setCanMove(true);
        carStartingField.setNextField(busStartingField);
        carStartingField.setVehicles(new ArrayList<>(List.of(car)));
        carStartingField.setSurface(carStaringFieldSurface);
        

        //Snowfall initialization
        Road road = new Road();
        skeleton.ctor(road, "r");
        car.setCurrentRoad(road);
        bus.setCurrentRoad(road);

        Lane lane = new Lane();
        skeleton.ctor(lane, "l");

        road.setDestinationA(i1);
        road.setDestinationB(i2);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(busStartingField,carStartingField,targetField)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        //Begin
        bus.move(1);
        game.makeTurn();

        skeleton.reset();
    }
}
