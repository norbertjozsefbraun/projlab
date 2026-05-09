package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.buildings.BusStop;
import model.buildings.Building;
import model.buildings.Garage;
import model.buildings.Home;
import model.buildings.WorkPlace;
import model.core.Game;
import model.core.Player;
import model.core.Session;
import model.core.Shop;
import model.entities.Bus;
import model.entities.Car;
import model.entities.DirectionType;
import model.entities.SnowPlow;
import model.entities.Vehicle;
import model.items.*;
import model.map.Field;
import model.map.Intersection;
import model.map.Road;
import model.map.RoadType;
import model.map.World;
import view.GameFrame;

/**
 * Controller in the MVC pattern.
 * Turn flow:
 * New Round → (for plows: choose destination → roll → step N) →
 * (for buses: roll → step N) → Round Complete
 */
public class GameController {

    public enum TurnPhase {
        CHOOSE_DESTINATION, // Snowplow: click an intersection on the map
        ROLL_DICE,          // Roll for current vehicle
        MOVING,             // Step the current vehicle
        ROUND_COMPLETE      // All vehicles done
    }

    private GameFrame frame;
    private TurnPhase phase = TurnPhase.ROUND_COMPLETE;
    private int lastDiceRoll = 0;
    private int stepsRemaining = 0;
    private int drivenVehicleIndex = 0;
    private List<Vehicle> drivenVehicles = new ArrayList<>();

    /** Pixel positions of each intersection for the map rendering. */
    private Map<Integer, int[]> intersectionPositions = new HashMap<>();

    /** All intersections in the world, for destination selection. */
    private List<Intersection> allIntersections = new ArrayList<>();

    public GameController() {
    }

    public void setFrame(GameFrame frame) {
        this.frame = frame;
    }

    public int getLastDiceRoll() {
        return lastDiceRoll;
    }

    public int getStepsRemaining() {
        return stepsRemaining;
    }

    public TurnPhase getPhase() {
        return phase;
    }

    public Map<Integer, int[]> getIntersectionPositions() {
        return intersectionPositions;
    }

    // ── Actions ─────────────────────────────────────────────

    public void newGame() {
        Field.resetIdCounter();
        Road.resetIdCounter();
        Intersection.resetIdCounter();
        model.map.Surface.resetIdCounter();
        Vehicle.resetIdCounter();

        // Roads (1 lane unless noted)
        Road road1 = new Road("Fő utca", RoadType.STANDARD, 1, 4);
        Road road2 = new Road("Első sugárút", RoadType.STANDARD, 1, 4);
        Road road3 = new Road("Második sugárút", RoadType.STANDARD, 1, 4);
        Road road4 = new Road("Tölgyfa utca", RoadType.STANDARD, 1, 4);
        Road road5 = new Road("Harmadik sugárút", RoadType.STANDARD, 1, 4);
        Road road6 = new Road("Negyedik sugárút", RoadType.STANDARD, 1, 4);
        Road road7 = new Road("Szilfa utca", RoadType.TUNNEL, 1, 4);
        Road road8 = new Road("Ötödik sugárút", RoadType.STANDARD, 1, 3);
        Road road9 = new Road("Hatodik sugárút", RoadType.STANDARD, 1, 3);
        Road road10 = new Road("Autópálya", RoadType.BRIDGE, 3, 5); // 3 lanes per direction!

        // Intersections
        Intersection i1 = new Intersection();
        Intersection i2 = new Intersection();
        Intersection i3 = new Intersection();
        Intersection i4 = new Intersection();
        Intersection i5 = new Intersection();
        Intersection i6 = new Intersection();
        Intersection i7 = new Intersection();
        Intersection i8 = new Intersection();

        connectRoad(road1, i1, i2);
        connectRoad(road2, i1, i3);
        connectRoad(road3, i2, i4);
        connectRoad(road4, i3, i4);
        connectRoad(road5, i3, i5);
        connectRoad(road6, i4, i6);
        connectRoad(road7, i5, i6);
        connectRoad(road8, i5, i7);
        connectRoad(road9, i6, i8);
        connectRoad(road10, i7, i8);

        // Buildings
        Garage garage = new Garage();
        i1.setBuilding(garage);
        garage.setLocation(i1);

        WorkPlace workPlace = new WorkPlace();
        i2.setBuilding(workPlace);
        workPlace.setLocation(i2);

        Home home = new Home();
        i3.setBuilding(home);
        home.setLocation(i3);

        BusStop busStopA = new BusStop();
        i5.setBuilding(busStopA);
        busStopA.setLocation(i5);

        BusStop busStopB = new BusStop();
        i8.setBuilding(busStopB);
        busStopB.setLocation(i8);

        // World assembly
        World world = new World();
        List<Road> roads = new ArrayList<>();
        roads.add(road1);
        roads.add(road2);
        roads.add(road3);
        roads.add(road4);
        roads.add(road5);
        roads.add(road6);
        roads.add(road7);
        roads.add(road8);
        roads.add(road9);
        roads.add(road10);
        world.setRoads(roads);

        allIntersections.clear();
        allIntersections.add(i1);
        allIntersections.add(i2);
        allIntersections.add(i3);
        allIntersections.add(i4);
        allIntersections.add(i5);
        allIntersections.add(i6);
        allIntersections.add(i7);
        allIntersections.add(i8);
        world.setIntersections(new ArrayList<>(allIntersections));

        // Intersection pixel positions (2 × 4 grid)
        intersectionPositions.clear();
        intersectionPositions.put(i1.getId(), new int[] { 160, 70 });
        intersectionPositions.put(i2.getId(), new int[] { 540, 70 });
        intersectionPositions.put(i3.getId(), new int[] { 160, 220 });
        intersectionPositions.put(i4.getId(), new int[] { 540, 220 });
        intersectionPositions.put(i5.getId(), new int[] { 160, 370 });
        intersectionPositions.put(i6.getId(), new int[] { 540, 370 });
        intersectionPositions.put(i7.getId(), new int[] { 160, 520 });
        intersectionPositions.put(i8.getId(), new int[] { 540, 520 });

        // Session & game
        List<Vehicle> vehicles = new ArrayList<>();
        Session session = Session.getInstance();
        session.newGame(vehicles, world);

        busStopA.setGame(session.getGame());
        busStopB.setGame(session.getGame());

        List<Player> players = new ArrayList<>();

        // Snow Plow (in garage)
        Player plowPlayer = new Player();
        plowPlayer.setName("PlowDriver");
        plowPlayer.setType("plow");
        players.add(plowPlayer);

        SnowPlow snowPlow = new SnowPlow(plowPlayer, garage);
        snowPlow.setDirection(DirectionType.AH);
        snowPlow.setDestinationIntersection(i4);
        Head sweeper = new Sweeper();
        List<Head> heads = new ArrayList<>();
        heads.add(sweeper);
        snowPlow.setHeads(heads);
        snowPlow.setActiveHead(sweeper);
        sweeper.setEquipped(true);
        plowPlayer.addVehicle(snowPlow);
        vehicles.add(snowPlow);
        garage.setDestroyedNum(0);

        // Bus (on ElmSt heading toward BusStopB)
        Player busPlayer = new Player();
        busPlayer.setName("BusDriver");
        busPlayer.setType("bus");
        players.add(busPlayer);

        Field busField = road7.getLanesToB().get(0).getFirstField();
        Bus bus = new Bus(busPlayer, busField, road7);
        bus.setStopA(busStopA);
        bus.setStopB(busStopB);
        bus.setPreviousStop(busStopA);
        bus.setPreviousIntersection(i5);
        bus.setDestinationIntersection(i8);
        bus.setDirection(DirectionType.AH);
        bus.setCurrentRoad(road7);
        List<Building> busBuildings = new ArrayList<>();
        busBuildings.add(busStopA);
        busBuildings.add(busStopB);
        bus.setBuildings(busBuildings);
        busPlayer.addVehicle(bus);
        vehicles.add(bus);

        // NPC car
        Car car = new Car(home, workPlace);
        vehicles.add(car);

        session.getGame().setPlayers(players);
        session.getGame().setVehicles(vehicles);

        // Driven vehicles
        drivenVehicles.clear();
        for (Vehicle v : vehicles) {
            if (v instanceof SnowPlow || v instanceof Bus)
                drivenVehicles.add(v);
        }

        drivenVehicleIndex = 0;
        stepsRemaining = 0;
        lastDiceRoll = 0;
        startVehicleTurn();

        refreshView();
    }

    /** New Round: snowfall, NPC auto-move (synchronous), timers, then first vehicle's turn. */
    public void newRound() {
        Game game = getGame();
        if (game == null || phase != TurnPhase.ROUND_COMPLETE)
            return;

        // Snowfall
        game.getWorld().snowfall();

        // Move NPC cars synchronously (no animation)
        for (Vehicle v : game.getVehicles()) {
            if (v instanceof Car) {
                int roll = game.rollDice();
                for (int i = 0; i < roll; i++) {
                    try { v.move(); } catch (Exception e) { break; }
                }
            }
        }

        // Tick timers and advance round
        Game.ticker.tick();
        game.getWorld().tickTimers();
        game.increaseRounds();

        // Start first driven vehicle's turn
        drivenVehicleIndex = 0;
        lastDiceRoll = 0;
        startVehicleTurn();

        refreshView();
    }

    /** Roll dice for current vehicle. */
    public void rollDice() {
        Game game = getGame();
        if (game == null || phase != TurnPhase.ROLL_DICE)
            return;

        lastDiceRoll = game.rollDice();
        stepsRemaining = lastDiceRoll;
        phase = TurnPhase.MOVING;
        refreshView();
    }

    /** Move current vehicle one step. */
    public void step() {
        if (phase != TurnPhase.MOVING || stepsRemaining <= 0)
            return;

        Vehicle current = getCurrentVehicle();
        if (current == null)
            return;

        try {
            current.move();
        } catch (Exception e) {
            System.err.println("Move error: " + e.getMessage());
        }

        stepsRemaining--;
        if (stepsRemaining <= 0) {
            drivenVehicleIndex++;
            if (drivenVehicleIndex < drivenVehicles.size()) {
                startVehicleTurn();
            } else {
                phase = TurnPhase.ROUND_COMPLETE;
            }
        }
        refreshView();
    }

    /** Set direction for the current driven vehicle. */
    public void setDirection(DirectionType dir) {
        Vehicle v = getCurrentVehicle();
        if (v != null) {
            v.setDirection(dir);
            refreshView();
        }
    }

    /**
     * Called when user clicks an intersection on the map.
     * Used to set the snowplow's destination during CHOOSE_DESTINATION phase.
     */
    public void onIntersectionClicked(int intersectionId) {
        if (phase != TurnPhase.CHOOSE_DESTINATION)
            return;

        Vehicle v = getCurrentVehicle();
        if (!(v instanceof SnowPlow))
            return;

        for (Intersection inter : allIntersections) {
            if (inter.getId() == intersectionId) {
                v.setDestinationIntersection(inter);
                phase = TurnPhase.ROLL_DICE;
                refreshView();
                return;
            }
        }
    }

    /** Returns the instruction text for the current phase. */
    public String getInstructionText() {
        Vehicle v = getCurrentVehicle();
        String vName = v != null ? v.toString().toUpperCase() : "N/A";

        return switch (phase) {
            case CHOOSE_DESTINATION -> "Click an intersection to set " + vName + "'s destination";
            case ROLL_DICE -> "Roll the dice for " + vName;
            case MOVING -> "Step " + vName + " (" + stepsRemaining + " step" + (stepsRemaining != 1 ? "s" : "")
                    + " remaining)";
            case ROUND_COMPLETE -> "Click New Round to advance";
        };
    }

    // ── Shop & Head Management ──────────────────────────────

    /** Add funds to the shop balance. */
    public void addFunds(int amount) {
        Game game = getGame();
        if (game == null) return;
        game.getShop().addFunds(amount);
        refreshView();
    }

    /** Buy a head by name and add it to the snowplow. */
    public void buyHead(String headName, int price) {
        Game game = getGame();
        if (game == null) return;

        SnowPlow plow = getSnowPlow();
        if (plow == null) return;

        Shop shop = game.getShop();
        if (shop.getBalance() < price) return;

        Head newHead;
        switch (headName) {
            case "Sweeper" -> newHead = new Sweeper();
            case "Blower" -> newHead = new Blower();
            case "IceCracker" -> newHead = new IceCracker();
            case "Salter" -> newHead = new Salter();
            case "GravelSpreader" -> newHead = new GravelSpreader();
            case "Dragon" -> newHead = new Dragon();
            default -> { return; }
        }
        newHead.setPrice(price);

        shop.transaction(newHead, 1, plow.getPlayer(), plow);
        refreshView();
    }

    /** Buy a resource and fill the matching head on the snowplow. */
    public void buyResource(String type, int unitPrice, int amount) {
        Game game = getGame();
        if (game == null) return;

        SnowPlow plow = getSnowPlow();
        if (plow == null) return;

        Shop shop = game.getShop();
        if (shop.getBalance() < unitPrice) return;

        Resource res;
        switch (type) {
            case "Salt" -> res = new Salt(amount, unitPrice);
            case "Gravel" -> res = new Gravel(amount, unitPrice);
            case "Biokerosene" -> res = new Biokerosene(amount, unitPrice);
            default -> { return; }
        }

        shop.transaction(res, amount, plow.getPlayer(), plow);
        refreshView();
    }

    /** Change the active head on the snowplow (fejcsere). */
    public void changeHead(Head head) {
        SnowPlow plow = getSnowPlow();
        if (plow == null) return;
        plow.changeHead(head);
        refreshView();
    }

    /** Helper: find the first SnowPlow in the game. */
    private SnowPlow getSnowPlow() {
        Game game = getGame();
        if (game == null) return null;
        for (Vehicle v : game.getVehicles()) {
            if (v instanceof SnowPlow) return (SnowPlow) v;
        }
        return null;
    }

    // ── Internal ────────────────────────────────────────────

    /** Determines the starting phase for the current vehicle's turn. */
    private void startVehicleTurn() {
        Vehicle v = getCurrentVehicle();
        if (v instanceof SnowPlow) {
            phase = TurnPhase.CHOOSE_DESTINATION;
        } else {
            phase = TurnPhase.ROLL_DICE;
        }
        stepsRemaining = 0;
        lastDiceRoll = 0;
    }

    private void connectRoad(Road road, Intersection a, Intersection b) {
        road.setDestinationA(a);
        road.setDestinationB(b);
        a.getConnectedRoads().add(road);
        b.getConnectedRoads().add(road);
    }

    private Game getGame() {
        return Session.getInstance().getGame();
    }

    public Vehicle getCurrentVehicle() {
        if (drivenVehicles.isEmpty() || drivenVehicleIndex >= drivenVehicles.size())
            return null;
        return drivenVehicles.get(drivenVehicleIndex);
    }

    private void refreshView() {
        if (frame != null)
            frame.refresh();
    }
}
