package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import model.buildings.Building;
import model.buildings.BusStop;
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
import model.map.*;

import static test.ScriptRunnerHelper.createItem;
import static test.ScriptRunnerHelper.executeAndCapture;
import static test.ScriptRunnerHelper.loadThisWorld;
import static test.ScriptRunnerHelper.normalizePlayerType;
import static test.ScriptRunnerHelper.stripQuotes;

/**
 * Reads a test script from a file and executes each line as a command.
 * Each line in the file corresponds to one of the supported game commands.
 */
public class ScriptRunner {
    private final StringBuilder capturedOutput = new StringBuilder();

    private int currentVehicleIndex = 0;
    private int movesLeft = 0;
    private boolean isNewRound = true;


    /**
     * Reads the file at the given path line by line and dispatches each line
     * to the appropriate command handler.
     *
     * @param path the path to the script file
     */
    public void runFromFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                executeCommandLine(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read script: " + e.getMessage());
        }
    }

    public void runFromStdIn() {
        try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if ("exit".equalsIgnoreCase(line.strip())) {
                    break;
                }
                executeCommandLine(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read stdin: " + e.getMessage());
        }
    }

    public void executeCommandLine(String rawLine) {
        if (rawLine == null) {
            return;
        }

        String line = rawLine.strip();
        if (line.isEmpty()) {
            return;
        }

        StringTokenizer st = new StringTokenizer(line);
        String command = st.nextToken().toLowerCase();
        if (!"save".equals(command)) {
            capturedOutput.setLength(0);
        }

        switch (command) {
            case "randomize"   -> executeAndCapture(this::randomize, capturedOutput);
            case "derandomize" -> executeAndCapture(this::derandomize, capturedOutput);
            case "start"       -> executeAndCapture(() -> start(st), capturedOutput);
            case "roadconfig"  -> executeAndCapture(() -> roadconfig(st), capturedOutput);
            case "connect"     -> executeAndCapture(() -> connect(st), capturedOutput);
            case "setb"        -> executeAndCapture(() -> setb(st), capturedOutput);
            case "setveh"      -> executeAndCapture(() -> setVeh(st), capturedOutput);
            case "setfieldcontents" -> executeAndCapture(() -> setFieldContents(st), capturedOutput);
            case "lsh"         -> executeAndCapture(() -> lsh(st), capturedOutput);
            case "ch"          -> executeAndCapture(() -> ch(st), capturedOutput);
            case "roll"        -> executeAndCapture(() -> roll(st), capturedOutput);
            case "move"        -> executeAndCapture(() -> move(st), capturedOutput);
            case "save"        -> save(st);
            case "ls"          -> executeAndCapture(this::ls, capturedOutput);
            case "transaction" -> executeAndCapture(() -> transaction(st), capturedOutput);
            case "fill"        -> executeAndCapture(() -> fill(st), capturedOutput);
            case "setheads"    -> executeAndCapture(() -> setHeads(st), capturedOutput);
            default             -> executeAndCapture(() -> System.out.println("Unknown command: " + command), capturedOutput);
        }
    }

    private void randomize() {
        Session session = Session.getInstance();
        session.getGame().setDerandomized(false);
    }

    private void derandomize() {
        Session session = Session.getInstance();
        session.getGame().setDerandomized(true);
    }

    private void start(StringTokenizer st) {

        String vmipath = "test\\tests\\base-mechanic\\world.txt";
        World defaultWorld = loadThisWorld(vmipath);

        List<Vehicle> vehicles = new ArrayList<>();

        Session session = Session.getInstance();
        session.newGame(vehicles, defaultWorld);

        int playerCount = -1;
        int carCount = 0;
        Map<Integer, String> playerNames = new HashMap<>();
        Map<Integer, String> playerTypes = new HashMap<>();
        Map<Integer, String> snowPlowHeads = new HashMap<>();

        while (st.hasMoreTokens()) {
            String option = st.nextToken();

            if ("-pc".equals(option)) {
                if (!st.hasMoreTokens()) {
                    System.out.println("Missing value for -pc.");
                    return;
                }
                playerCount = Integer.parseInt(st.nextToken());
                continue;
            }

            if ("-cc".equals(option)) {
                if (!st.hasMoreTokens()) {
                    System.out.println("Missing value for -cc.");
                    return;
                }
                carCount = Integer.parseInt(st.nextToken());
                continue;
            }

            if (option.startsWith("-p") && option.length() > 2 && Character.isDigit(option.charAt(2))) {
                int playerIndex = Integer.parseInt(option.substring(2));
                if (!st.hasMoreTokens()) {
                    System.out.println("Missing player name for " + option + ".");
                    return;
                }
                String playerName = stripQuotes(st.nextToken());

                if (!st.hasMoreTokens()) {
                    System.out.println("Missing player type for " + option + ".");
                    return;
                }
                String rawType = st.nextToken();
                String normalizedType = normalizePlayerType(rawType);
                if (normalizedType == null) {
                    System.out.println("Unknown player type: " + rawType + ". Use !bus or !plow.");
                    return;
                }

                playerNames.put(playerIndex, playerName);
                playerTypes.put(playerIndex, normalizedType);
                continue;
            }

            if (option.startsWith("-sh") && option.length() > 3 && Character.isDigit(option.charAt(3))) {
                int snowPlowIndex = Integer.parseInt(option.substring(3));
                if (!st.hasMoreTokens()) {
                    System.out.println("Missing value for " + option + ".");
                    return;
                }

                String headCode = st.nextToken().toLowerCase();
                if (!"sw".equals(headCode) && !"ic".equals(headCode)) {
                    System.out.println("Unknown head code for " + option + ": " + headCode + ". Use sw or ic.");
                    return;
                }
                snowPlowHeads.put(snowPlowIndex, headCode);
                continue;
            }

            System.out.println("Unknown start option: " + option);
            return;
        }

        if (playerCount <= 0) {
            System.out.println("Invalid player count. Use -pc <positive integer>.");
            return;
        }

        for (int i = 1; i <= playerCount; i++) {
            if (!playerNames.containsKey(i) || !playerTypes.containsKey(i)) {
                System.out.println("Missing player definition for -p" + i + ".");
                return;
            }
        }

        List<Player> players = new ArrayList<>();
        int nextVehicleId = 1;
        int currentSnowPlowIndex = 1;

        for (int i = 1; i <= playerCount; i++) {
            Player player = new Player();
            player.setName(playerNames.get(i));
            player.setType(playerTypes.get(i));
            players.add(player);

            if ("bus".equals(player.getType())) {
                BusStop stopA = (BusStop) Session.getInstance().getGame().getWorld().getIntersections().get(4).getBuilding();
                BusStop stopB = (BusStop) Session.getInstance().getGame().getWorld().getIntersections().get(5).getBuilding();
                Bus bus = new Bus(player, stopA, stopB);
                bus.setDirection(DirectionType.AH);
                player.addVehicle(bus);
                vehicles.add(bus);
            } else {
                Garage garage = (Garage) Session.getInstance().getGame().getWorld().getIntersections().get(1).getBuilding();
                SnowPlow snowPlow = new SnowPlow(player, garage);
                snowPlow.setDirection(DirectionType.AH);

                Head sweeper = new Sweeper();
                Head iceCracker = new IceCracker();
                List<Head> heads = new ArrayList<>();
                

                String defaultHeadCode = snowPlowHeads.getOrDefault(currentSnowPlowIndex, "sw");
                if ("ic".equals(defaultHeadCode)) {
                    heads.add(iceCracker);
                    snowPlow.setHeads(heads);

                    snowPlow.setActiveHead(iceCracker);
                    iceCracker.setEquipped(true);
                } else {
                    snowPlow.setActiveHead(sweeper);
                    heads.add(sweeper);
                    snowPlow.setHeads(heads);
                    sweeper.setEquipped(true);
                }

                currentSnowPlowIndex++;
                player.addVehicle(snowPlow);
                vehicles.add(snowPlow);
            }
        }

        for (int i = 0; i < carCount; i++) {
            Home home = (Home) Session.getInstance().getGame().getWorld().getIntersections().get(3).getBuilding();
            WorkPlace workPlace = (WorkPlace) Session.getInstance().getGame().getWorld().getIntersections().get(2).getBuilding();
            Car car = new Car(home, workPlace);
            vehicles.add(car);
        }

        session.getGame().setPlayers(players);
        session.getGame().setVehicles(vehicles);

        System.out.println("Game started with " + players.size() + " players, "
                + (vehicles.size() - carCount) + " driven vehicles and " + carCount + " cars.");
    }

    /**
     * Creates one or more roads based on the params
     * roadconfig <road1> <roadtype1> <lanecountforroad1> <lanelengthforroad1> ...
     */
    private void roadconfig(StringTokenizer st) {
        Session session = Session.getInstance();
        if (session.getGame() == null || session.getGame().getWorld() == null) {
            return;
        }

        while (st.hasMoreTokens()) {
            String roadName = st.nextToken();
            String typeStr = st.nextToken().toUpperCase();
            int laneCount = Integer.parseInt(st.nextToken());
            int laneLength = Integer.parseInt(st.nextToken());

            RoadType roadType;
            switch (typeStr) {
                case "TUNNEL" -> roadType = RoadType.TUNNEL;
                case "BRIDGE" -> roadType = RoadType.BRIDGE;
                default -> roadType = RoadType.STANDARD;
            }

            Road road = new Road(roadName, roadType, laneCount, laneLength);

            session.getGame().getWorld().getRoads().add(road);
        }
    }

    /**
     * Creates an intersection and connects the roads to it
     * connect <road1> <A/B> <road2> <A/B> ...
     */
    private void connect(StringTokenizer st) {
        Session session = Session.getInstance();
        if (session.getGame() == null || session.getGame().getWorld() == null) {
            return;
        }

        // create new intersection
        Intersection intersection = new Intersection();
        List<Road> allRoads = session.getGame().getWorld().getRoads();

        while (st.hasMoreTokens()) {
            String roadName = st.nextToken();
            if (!st.hasMoreTokens()) break;
            String end = st.nextToken().toUpperCase();

            // find road by name
            Road currentRoad = null;
            for (Road r : allRoads) {
                if (r.getName() != null && r.getName().equals(roadName)) {
                    currentRoad = r;
                    break;
                }
            }

            if (currentRoad == null) {
                continue;
            }

            // add intersection ref to road
            if (end.equals("A")) {
                currentRoad.setDestinationA(intersection);
            } else if (end.equals("B")) {
                currentRoad.setDestinationB(intersection);
            } else return;

            // add road ref to intersection
            intersection.getConnectedRoads().add(currentRoad);
        }

        // add intersection ref to world
        session.getGame().getWorld().getIntersections().add(intersection);
    }

    /**
     * PARAMS: <intersection ID> <bs/ho/wo/ga>
     * Adott ID-ju intersection buildengjet beallitja tipus szerint
     * @param st
     */
    private void setb(StringTokenizer st) {
        // TODO: implement setb command - Zeki
        if(!st.hasMoreTokens()){
            System.out.println("Missing intersection ID!");
            return;
        }
        int intersectionID = Integer.parseInt(st.nextToken());
        if(!st.hasMoreTokens()){
            System.out.println("Missing building type! You can choose from: <bs, ho, wo, ga>");
            return;
        }

        String veh = st.nextToken();
        if(st.hasMoreTokens()){
            System.out.println("Too many arguments!");
            return;
        }
        Session session = Session.getInstance();
        Game game = session.getGame();

        //Megkeressük azt az intersectiont aminek az id-ja egyezik az inputkent kapott
        var intersection = game.getWorld().getIntersections().stream().filter(i -> i.getId() == intersectionID).findFirst().orElse(null);

        // Ha nincs ilyen ID
        if (intersection == null){
            System.out.println("No Intersection with the provided ID exists!");
            return;
        }
        
        switch(veh){
            case "bs" -> {
                BusStop stop = new BusStop();
                intersection.setBuilding(stop);       
                stop.setLocation(intersection);
            }   

            case "ho" ->{
                Home home = new Home();
                intersection.setBuilding(home);
                home.setLocation(intersection);
            }
            
            case "wo" ->{
                WorkPlace wp = new WorkPlace();
                intersection.setBuilding(wp);
                wp.setLocation(intersection);
            }

            case "ga" ->{
                Garage garage = new Garage();
                intersection.setBuilding(garage);
                garage.setLocation(intersection);
            }
            default ->{
                System.out.println("Unknown type of building! Available options: <bs, ho, wo, ga>");
                return;
            }   
        } 
    }

    private void setVeh(StringTokenizer st) {
        if (!st.hasMoreTokens()) {
            System.out.println("Missing road name.");
            return;
        }
        String roadName = stripQuotes(st.nextToken());

        if (!st.hasMoreTokens()) {
            System.out.println("Missing field id.");
            return;
        }
        String fieldId = st.nextToken();

        if (!st.hasMoreTokens()) {
            System.out.println("Missing intersection id.");
            return;
        }
        String intersectionId = st.nextToken();

        if (!st.hasMoreTokens()) {
            System.out.println("Missing player name.");
            return;
        }
        String playerName = stripQuotes(st.nextToken());

        if (!st.hasMoreTokens()) {
            System.out.println("Missing vehicle type.");
            return;
        }
        String vehType = st.nextToken();
        
        Session session = Session.getInstance();
        Game game = session.getGame();
        World world = game.getWorld();

        List<Building> buildings = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String strId = st.nextToken();
            if (!strId.equals("null")) {
                int bId = Integer.parseInt(strId);
                for (Intersection i : world.getIntersections()) {
                    if (i.getId() == bId) {
                        buildings.add(i.getBuilding());
                    }
                }
            }
        }

        Player player = null;
        for (Player p : game.getPlayers()) {
            if (p.getName().equals(playerName)) player = p;
        }

        Road road = null;
        for (Road r : world.getRoads()) {
            if (r.getName().equals(roadName)) road = r;
        }

        Field field = null;
        if (!fieldId.equals("null")) field = world.getFieldById(roadName, Integer.parseInt(fieldId));     
        List<Vehicle> vehicles = new ArrayList<>();

        switch (vehType) {
            case "sp":
                if (buildings.isEmpty()) return;
                Garage garage = (Garage)buildings.get(0);
                if (intersectionId.equals("null")) {
                    vehicles.add(new SnowPlow(player, garage, field, road));
                }
                else {
                    vehicles.add(new SnowPlow(player, garage));
                }
                break;
            case "bu":
                if (buildings.size() < 2) return;
                BusStop stopA = (BusStop)buildings.get(0);
                BusStop stopB = (BusStop)buildings.get(1);
                if (intersectionId.equals("null")) {
                    vehicles.add(new Bus(player, stopA, stopB, field, road));
                }
                else {
                    vehicles.add(new Bus(player, stopA, stopB));
                }
                break;
            case "ca":
                if (buildings.size() < 2) return;
                Home home = (Home)buildings.get(0);
                WorkPlace work = (WorkPlace)buildings.get(1);
                if (intersectionId.equals("null")) {
                    vehicles.add(new Car(home, work, field, road));
                }
                else {
                    vehicles.add(new Car(home, work));
                }
                break;
        }
        game.setVehicles(vehicles);
    }

    /**
     * setFieldContents <fieldID> <snowDepth> <hasIce> <hasGravel>
     */
    private void setFieldContents(StringTokenizer st) {
        Session session = Session.getInstance();
        if (session.getGame() == null || session.getGame().getWorld() == null) {
            return;
        }

        if (st.countTokens() < 4) {
            return;
        }

        try {
            int targetFieldId = Integer.parseInt(st.nextToken());
            int snowDepth = Integer.parseInt(st.nextToken());
            boolean hasIce = Integer.parseInt(st.nextToken()) == 1;
            boolean hasGravel = Integer.parseInt(st.nextToken()) == 1;

            // find field
            Field targetField = session.getGame().getWorld().getFieldById(targetFieldId);

            if (targetField == null) {
                return;
            }

            if (targetField.getSurface() != null) {
                targetField.getSurface().setSnowThickness(snowDepth);
                targetField.getSurface().setIsIce(hasIce);
                targetField.getSurface().setHasGravel(hasGravel);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        }
    }

    /**
     * Sets the heads of the specified snowplow.
     * @param st the string tokenizer containing the command arguments
     */
    private void setHeads(StringTokenizer st) {
        if (!st.hasMoreTokens()) {
            System.out.println("Missing snowplow id.");
            return;
        }
        String idStr = st.nextToken();
        
        Session session = Session.getInstance();
        List<Vehicle> vehicles = session.getGame().getVehicles();
        
        SnowPlow snowPlow = null;
        for (Vehicle v : vehicles) {
            if (v.getVehicleId() == Integer.parseInt(idStr)) {
                snowPlow = (SnowPlow) v;
                break;
            }
        }
        
        if (snowPlow == null) {
            System.out.println("Snowplow not found.");
            return;
        }
        
        List<Head> heads = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String headCode = st.nextToken().toLowerCase();
            Purchasable item = createItem(headCode);
            if (item instanceof Head) {
                heads.add((Head) item);
            } else {
                System.out.println("Unknown head type: " + headCode);
                return;
            }
        }
        
        if (heads.isEmpty()) {
            System.out.println("No heads specified.");
            return;
        }
        
        snowPlow.setHeads(heads);
        snowPlow.setActiveHead(heads.get(0));
        heads.get(0).setEquipped(true);
    }
 
    /**
     * Lists the heads of the specified snowplow.
     * @param st the string tokenizer containing the command arguments
     */
    private void lsh(StringTokenizer st) {
        String idStr = st.nextToken();
        Session session = Session.getInstance();
        List<Vehicle> vehicles = session.getGame().getVehicles();

        SnowPlow snowPlow = null;
        for (Vehicle v : vehicles) {
            if (v.getVehicleId() == Integer.parseInt(idStr)) {
                snowPlow = (SnowPlow) v;
            }
        }

        if (snowPlow == null) {
            System.out.println("Snowplow not found.");
            return;
        }
    }

    /**
     * Changes the current head to the specified type. 
     * @param st the string tokenizer containing the command arguments
     */
    private void ch(StringTokenizer st) {
        if (!st.hasMoreTokens()) return;
            String spId = st.nextToken();

        if (!st.hasMoreTokens()) return;
            String newHeadType = st.nextToken();

        Session session = Session.getInstance();
        List<Vehicle> vehicles = session.getGame().getVehicles();
        SnowPlow snowPlow = null;
        for (Vehicle v : vehicles) {
            if (v.getVehicleId() == Integer.parseInt(spId)) {
                snowPlow = (SnowPlow) v;
            }
        }

        Head oldHead = snowPlow.getActiveHead();
        Head activHead = switch (newHeadType) {
            case "dr" -> new Dragon();
            case "st" -> new Salter();
            case "sw" -> new Sweeper();
            case "gr" -> new GravelSpreader();
            case "bl" -> new Blower();
            default -> null;
        };

        snowPlow.changeHead(activHead);

    }

    private void roll(StringTokenizer st) {
        Session session = Session.getInstance();
        Game game = session.getGame();

        // ha van parameter, akkor annyit dob, kulonben: rollDice (1 vagy random)
        if (game != null) {
            if(st.hasMoreTokens()) {
                movesLeft = Integer.parseInt(st.nextToken());
            } else {
                movesLeft = game.rollDice();
            }
        }
    }


    private void move(StringTokenizer st) {
        // TODO: implement move command - KEVE BAZSI (ZEKI)
        Session session = Session.getInstance();
        Game game = session.getGame();

        if (game == null || game.getVehicles() == null || game.getVehicles().isEmpty()) {
            return;
        }

        // ha nem dobott, 1-et léphet
        if (movesLeft <= 0) {
            movesLeft = 1;
        }

        // ha új kör, havazik
        if (currentVehicleIndex == 0 && isNewRound) {
            if (game.getWorld() != null) {
                game.getWorld().snowfall();
            }
            isNewRound = false;
        }

        Vehicle currentVehicle = game.getVehicles().get(currentVehicleIndex);

        // irány beállítása
        if (st.hasMoreTokens()) {
            String direction = st.nextToken();
            switch (direction) {
                case "ri":
                    currentVehicle.setDirection(DirectionType.RI);
                    break;
                case "le":
                    currentVehicle.setDirection(DirectionType.LE);
                    break;
                default:
                    currentVehicle.setDirection(DirectionType.AH);
                    break;
            }
        }

        // move
        currentVehicle.move();

        movesLeft--;

        // ha nincs több lépése egy járműnek
        if (movesLeft <= 0) {
            currentVehicleIndex++;
            movesLeft = 0;
        }

        // kör vége
        if (currentVehicleIndex >= game.getVehicles().size()) {
            currentVehicleIndex = 0;
            isNewRound = true;

            if (game.getWorld() != null) {
                game.getWorld().tickTimers();
            }

            if (game.getRounds() != null) {
                game.setRounds(game.getRounds() + 1);
            }
        }
    }

    private void save(StringTokenizer st) {
        if (!st.hasMoreTokens()) {
            System.out.println("Missing save target folder path.");
            return;
        }

        StringBuilder folderArgBuilder = new StringBuilder(st.nextToken());
        while (st.hasMoreTokens()) {
            folderArgBuilder.append(" ").append(st.nextToken());
        }

        String folderArg = stripQuotes(folderArgBuilder.toString());
        Path targetFolder;
        try {
            targetFolder = Paths.get(folderArg);
        } catch (InvalidPathException e) {
            System.out.println("Invalid folder path: " + folderArg);
            return;
        }

        try {
            Files.createDirectories(targetFolder);

            Path outputFile = targetFolder.resolve("output");

            String outputSinceLastTestCommand = capturedOutput.toString();

            Files.writeString(outputFile, outputSinceLastTestCommand, StandardCharsets.UTF_8);
            System.out.println("Saved output to: " + outputFile.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to save output: " + e.getMessage());
        }
    }

    private void ls() {
        // TODO: implement ls command - ZEKI
        ArrayList<String> testCases = new ArrayList<>();
        testCases.add("auto-sikeres-lep");
        testCases.add("fejcsere");
        testCases.add("busz-kor-teljesitese");
        testCases.add("hokotro-es-auto-utkozese");
        testCases.add("havazas");
        testCases.add("jegpancel-kialakulasa");
        testCases.add("auto-megcsuszik-a-jegen");
        testCases.add("auto-megakad-a-hoban");
        testCases.add("autok-utkozese");
        testCases.add("auto-buszba-csuszik");
        testCases.add("auto-elindul-a-munkahelyerol");
        testCases.add("auto-megerkezik-a-munkahelyere");
        testCases.add("auto-elindul-otthonrol");
        testCases.add("auto-hazaer");
        testCases.add("jatek-vege");
        testCases.add("gravelspreader-working");
        testCases.add("hoeltakaritas-fuvassal");
        testCases.add("zuzalek-feltoltese");
        testCases.add("sozas");
        testCases.add("langszoro-ujratoltese");
        testCases.add("utszakasz-soprese");
        testCases.add("soszoro-fej-urjatoltese");
        testCases.add("jegtores");
        testCases.add("olvasztas");

        System.out.println("Elerheto tesztesetek:");
        int i = 1;
        for (String testCase : testCases) {
            System.out.println("\t" + i++ + ". " + testCase);
        }
    }

    private void transaction(StringTokenizer st) {
        if (!st.hasMoreTokens()) return;
        String itemName = st.nextToken();

        if (!st.hasMoreTokens()) return;
        int amount = Integer.parseInt(st.nextToken());

        if (!st.hasMoreTokens()) return;
        String playerName = st.nextToken();

        if (!st.hasMoreTokens()) return;
        int spId = Integer.parseInt(st.nextToken());

        Session session = Session.getInstance();
        Shop shop = session.getGame().getShop();
        List<Vehicle> vehicles = session.getGame().getVehicles();
        List<Player> players = session.getGame().getPlayers();
        SnowPlow snowPlow = null;
        Player player = null;

        if (spId != 0) {
            for (Vehicle v : vehicles) {
                if (v.getVehicleId() == spId) {
                    snowPlow = (SnowPlow) v;
                }
            }
        }
        for (Player p : players) {
            if (p.getName().equals(playerName)) {
                player = p;
            }
        }

        Purchasable item = createItem(itemName);
        if (item == null) {
            System.out.println("Unkown item " + itemName);
            return; 
        } 
        shop.transaction(item, amount, player, snowPlow);
    }

    /**
     * Fills the specified area with the given type.
     * @param st the string tokenizer containing the command arguments
     */
    private void fill(StringTokenizer st) {
        if (!st.hasMoreTokens()) return;
        int snowPlowId = Integer.parseInt(st.nextToken());
        if (!st.hasMoreTokens()) return;
        String resourceType = st.nextToken().toLowerCase();
        if (!st.hasMoreTokens()) return;
        int amountToAdd = Integer.parseInt(st.nextToken());

        Session session = Session.getInstance();
        if (session.getGame() == null || session.getGame().getVehicles() == null) {
            return;
        }

        SnowPlow snowPlow = null;
        for (Vehicle v : session.getGame().getVehicles()) {
            if (v.getVehicleId() == snowPlowId && v instanceof SnowPlow) {
                snowPlow = (SnowPlow) v;
                break;
            }
        }

        if (snowPlow == null) {
            return;
        }

        Resource resource = switch (resourceType) {
            case "salt" -> new Salt(amountToAdd, 0);
            case "grav" -> new Gravel(amountToAdd, 0);
            case "bio" -> new Biokerosene(amountToAdd, 0);
            default -> null;
        };

        if (resource == null) {
            return;
        }

        boolean filled = false;
        for (Head head : snowPlow.getHeads()) {
            if (!(head instanceof ResourceConsumingHead)) {
                continue;
            }
            ResourceConsumingHead resourceHead = (ResourceConsumingHead) head;
            Resource headResource = resourceHead.getResource();

            if ((resource instanceof Salt && headResource instanceof Salt)
                    || (resource instanceof Gravel && headResource instanceof Gravel)
                    || (resource instanceof Biokerosene && headResource instanceof Biokerosene)) {
                resourceHead.refill(resource);
                filled = true;
                break;
            }
        }

        if (!filled) {
            System.out.println("No compatible head found for resource " + resourceType + " on snowplow " + snowPlowId);
        }
    }




    }

