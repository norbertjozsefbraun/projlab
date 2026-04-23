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
import model.map.RoadType;


public class Test {

    public static void testIceGetsOnField() {

        Car car1 = new Car();
        car1.setCanMove(true);

        Field prev = new Field();

        Field f =  new Field();

        Surface s = new Surface();
        f.setSurface(s);

        prev.setVehicles(new ArrayList<>(List.of(car1)));
        prev.setNextField(f);
        car1.setCurrentField(prev);

        car1.move(1);

    }

    public static void testCarSlipsOnIce() {

        Car c = new Car();
        Field prev = new Field();
        Field f1 = new Field();
        Surface s1 = new Surface();
        Field f2 = new Field();
        Surface s2 = new Surface();
        Field f3 = new Field();
        Surface s3 = new Surface();

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

        Car c = new Car();

        Field current = new Field();
        Field next =  new Field();
        Field left =  new Field();
        Field right =  new Field();

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

    }

    public static void testSnowfallInGame() {

        World world = new World();

        Road road = new Road();

        Lane lane = new Lane();

        Field field = new Field();

        Surface surface = new Surface();

        field.setSurface(surface);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(field)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));


        world.snowfall();


    }

    //IceCracker Working use-case test
    /** It tests the functionality of the IceCracker head when attached to a SnowPlow and moving. */
    public static void testIceCracker(){

        SnowPlow sp = new SnowPlow();
        IceCracker icecracker = new IceCracker();
        Field cf = new Field();
        Field nf = new Field();
        Surface s = new Surface();


        Garage g = new Garage();
        g.setDestroyedNum(0);
        sp.setGarage(g);
        sp.setActiveHead(icecracker);
        sp.setCurrentField(cf);
        cf.setNextField(nf);
        nf.setSurface(s);

        sp.move(1);


    }

    //SweeperHeadWorking use-case test
    /** It tests the functionality of the Sweeper head when attached to a SnowPlow and moving. */
    // public static void testSweeper() {
        
    //     SnowPlow sp = new SnowPlow();
    //     Sweeper sweeper = new Sweeper();
    //     Field c1 = new Field();
    //     Field field = new Field();

    //     sp.changeHead(sweeper);
    //     field.setRightNeighbour(field);

    //     sp.move(1);


    // }

    public static void testSweeper() {
    
    // 1. Objektumok létrehozása
    SnowPlow sp = new SnowPlow();
    Sweeper sweeper = new Sweeper();
    Field c1 = new Field();      // Start mező (cf)
    Field field = new Field();   // Cél mező (nf)
    Field neighbor = new Field(); // Ide kerül a hó!
    Surface s1 = new Surface(); // A célmező felülete

    // 2. Regisztráció (Hogy szép legyen a log)

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

}

    //Salter refill use-case test
    /** It tests the Salter's ability to refill its salt supply. */
    public static void testSalterRefill() {

        Shop shop = new Shop();
        shop.setBalance(10);
        Salter salter = new Salter();
        Salt saltResource = new Salt(10, 5);
        salter.setSalt(saltResource);


        saltResource.pay(shop); 
        salter.refill(saltResource);

    }

    //DragonHeadWorking use-case test
    /** It tests the functionality of the Dragon head when attached to a SnowPlow and moving. */
    public static void testDragon() {

        SnowPlow sp = new SnowPlow();
        Dragon dragon = new Dragon();
        Field cf = new Field();
        Field nf = new Field();
        Surface s = new Surface();
        Biokerosene biokerosene = new Biokerosene();


        Garage g = new Garage();
        g.setDestroyedNum(0);
        sp.setGarage(g);
        sp.setCurrentField(cf);
        cf.setNextField(nf);
        nf.setSurface(s);
        sp.setActiveHead(dragon);
        dragon.setKerosene(biokerosene);

        sp.move(1);

    }

    public static void testSwappingHeads() {

        //init
        List<Head> heads = new ArrayList<>();
        Sweeper sweeper = new Sweeper();
        Salter salter = new Salter();
        
        SnowPlow snowplow = new SnowPlow();
        snowplow.setActiveHead(sweeper);
        snowplow.setHeads(heads);
        snowplow.addHead(sweeper);
        snowplow.addHead(salter);

        //func call
        snowplow.changeHead(salter);

    }

    public static void testCarMoves() {

        //init
        Surface surface = new Surface();
        Field currentField = new Field();
        Field nextField = new Field();
        nextField.setSurface(surface);
        currentField.setNextField(nextField);
        
        List<Vehicle> vehicles = new ArrayList<>();
        Car car = new Car();
        vehicles.add(car);
        car.setCanMove(true);
        car.setCurrentField(currentField);
        
        World world = new World();

        Road road = new Road();

        Lane lane = new Lane();

        Field field = new Field();

        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(field)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        Game game = new Game(vehicles, world);

        //func call
        game.makeTurn();

    }

    public static void testSnowPlowAndCarCollision() {

        //init
        Surface s = new Surface();
        Field cf = new Field();
        Field nf = new Field();
        nf.setSurface(s);
        cf.setNextField(nf);

        Garage g = new Garage();
        Home h = new Home();
        g.setDestroyedNum(0);

        IceCracker iceCracker = new IceCracker();

        SnowPlow snowplow = new SnowPlow();
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

    }

    public static void testBusCompletingRound() {

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
        Surface sB = new Surface();
        Field cf = new Field();
        fieldsBA.add(cf);
        Field nfA = new Field();
        nfA.setSurface(sA);
        fieldsAB.add(nfA);
        Field nfB = new Field();
        nfB.setSurface(sB);
        fieldsAC.add(nfB);
        Field bB = new Field();
        fieldsCA.add(bB);

        Intersection destA = new Intersection();
        Intersection destB = new Intersection();
        Intersection destC = new Intersection();

        BusStop stopA = new BusStop();
        stopA.setLocation(destA);
        stopA.setGame(game);
        stopA.setBuses(buses);
        buildings.add(stopA);
        BusStop stopB = new BusStop();
        buildings.add(stopB);

        Road roadA = new Road("roadA", RoadType.STANDARD, 1, 1);
        roads.add(roadA);
        Road roadB = new Road("roadB", RoadType.STANDARD, 1, 1);
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

    }

    public static void testGameOver(){
        SnowPlow snowPlow = new SnowPlow();
        Car car = new Car();
        List<Vehicle> vehcs = new ArrayList<>();
        vehcs.add(snowPlow);
        vehcs.add(car);
        List<Vehicle> justCar = new ArrayList<>();
        justCar.add(car);


        Surface s = new Surface();
        Field nextField = new Field();
        nextField.setVehicles(justCar);
        nextField.setSurface(s);
        Garage garage = new Garage();
        garage.setDestroyedNum(3);
        Home home = new Home();
        World world = new World();
        Game game = new Game(vehcs , world);
        Field currentField = new Field();
        snowPlow.setCurrentField(currentField);
        currentField.setNextField(nextField);
        Sweeper tempSw = new Sweeper();
        snowPlow.setActiveHead(tempSw);
        snowPlow.setGarage(garage);
        car.setHome(home);
        snowPlow.move(1);


    }

    public static void testCarsCollide(){

        World world = new World();

        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        //First car init
        Car car1 = new Car();
        ///vehicles.add(car1); lets say it already moved

        //Second car init
        Car car2 = new Car();
        vehicles.add(car2);

        //Game init
        Game game = new Game(vehicles, world);

        //Fields init
        Field car2CurrentField = new Field();
        Field targetField = new Field();
        Field car2NextField = new Field();

        //Surface init
        Surface car2CurrentFieldSurface = new Surface();
        Surface car2NextFieldSurface = new Surface();
        Surface targetS = new Surface();

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
        car2.setCurrentRoad(road);

        Lane lane = new Lane();

        road.setDestinationA(i1);
        road.setDestinationB(i2);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(car2CurrentField,car2NextField,targetField)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        //Begin
        game.makeTurn();


    }
    
    public static void testCarEntersWorkplace(){

        //Initialization
        World world = new World();

        Car car = new Car();
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(car);

        Field cField = new Field();

        Game game = new Game(vehicles, world);

        Intersection intersection = new Intersection();

        Intersection prevInter = new Intersection();

        WorkPlace workPlace = new WorkPlace();
        
        Road road = new Road();

        Lane lane = new Lane();

        Field field = new Field();

        Surface surface = new Surface();
        
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

        
    }
    
    public static void testCarArrivesHome(){

        //Initialization
        World world = new World();

        Car car = new Car();
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(car);

        Field cField = new Field();

        Game game = new Game(vehicles, world);

        Intersection intersection = new Intersection();

        Intersection prevInter = new Intersection();

        Home home = new Home();
        
        Road road = new Road();

        Lane lane = new Lane();

        Field field = new Field();

        Surface surface = new Surface();
        
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

    }

    public static void testCarLeavesHome(){

        //Initialization
        World world = new World();

        Car car = new Car();
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        vehicles.add(car);

        Field cField = new Field();

        Game game = new Game(vehicles, world);

        Intersection intersection = new Intersection();

        Intersection prevInter = new Intersection();

        Home home = new Home();
        
        Road road = new Road();

        Lane lane = new Lane();

        Field field = new Field();

        Surface surface = new Surface();
        
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

    }
    
    public static void testCarWaitsAtWorkplaceAndLeaves(){

        //Initialization
        World world = new World();

        Car car = new Car();
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(car);

        Field cField = new Field();

        Game game = new Game(vehicles, world);

        Intersection intersection = new Intersection();

        Intersection prevInter = new Intersection();

        WorkPlace workPlace = new WorkPlace();
        
        Road road = new Road();

        Lane lane = new Lane();

        Field field = new Field();

        Surface surface = new Surface();
        
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
        

    }
    
    public static void testCarCrashesIntoBus(){

        World world = new World();

        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        //Bus init
        Bus bus = new Bus();
        //vehicles.add(bus);

        //Car init
        Car car = new Car();
        vehicles.add(car);

        //Game init
        Game game = new Game(vehicles, world);

        //Fields init
        Field busStartingField = new Field();
        Field targetField = new Field();
        Field carStartingField = new Field();

        //Surface init
        Surface targetSurface = new Surface();
        Surface busStartingFieldSurface = new Surface();
        Surface carStaringFieldSurface = new Surface();

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
        car.setCurrentRoad(road);
        bus.setCurrentRoad(road);

        Lane lane = new Lane();

        road.setDestinationA(i1);
        road.setDestinationB(i2);
        lane.setRoad(road);
        lane.setFields(new ArrayList<>(List.of(busStartingField,carStartingField,targetField)));
        road.setLanesToA(new ArrayList<>(List.of(lane)));
        world.setRoads(new ArrayList<>(List.of(road)));

        //Begin
        bus.move(1);
        game.makeTurn();

    }
}
