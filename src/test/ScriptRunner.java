package test;

import static test.ScriptRunnerHelper.createItem;
import static test.ScriptRunnerHelper.executeAndCapture;
import static test.ScriptRunnerHelper.loadThisWorld;
import static test.ScriptRunnerHelper.normalizePlayerType;
import static test.ScriptRunnerHelper.stripQuotes;

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

import model.core.Game;
import model.entities.DirectionType;
import model.entities.Vehicle;
import model.items.*;
import model.entities.Bus;
import model.entities.Car;
import model.entities.DirectionType;
import model.core.Session;
import model.core.Shop;
import model.entities.SnowPlow;
import model.core.Player;
import model.items.*;
import model.map.World;

/**
 * Reads a test script from a file and executes each line as a command.
 * Each line in the file corresponds to one of the supported game commands.
 */
public class ScriptRunner {
    private final StringBuilder capturedOutput = new StringBuilder();

    private int currentVehicleIndex = 0;

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
                line = line.strip();
                if (line.isEmpty()) continue;
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
                    case "roll"        -> executeAndCapture(this::roll, capturedOutput);
                    case "move"        -> executeAndCapture(() -> move(st), capturedOutput);
                    case "save"        -> save(st);
                    case "ls"          -> executeAndCapture(this::ls, capturedOutput);
                    case "transaction" -> executeAndCapture(() -> transaction(st), capturedOutput);
                    case "fill"        -> executeAndCapture(() -> fill(st), capturedOutput);
                    case "setheads"    -> executeAndCapture(() -> setHeads(st), capturedOutput);
                    default             -> executeAndCapture(() -> System.out.println("Unknown command: " + command), capturedOutput);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read script: " + e.getMessage());
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
        List<Vehicle> vehicles = new ArrayList<>();
        int nextVehicleId = 1;
        int currentSnowPlowIndex = 1;

        for (int i = 1; i <= playerCount; i++) {
            Player player = new Player();
            player.setName(playerNames.get(i));
            player.setType(playerTypes.get(i));
            players.add(player);

            if ("bus".equals(player.getType())) {
                Bus bus = new Bus();
                bus.setVehicleId(nextVehicleId++);
                bus.setPlayer(player);
                bus.setCanMove(true);
                bus.setDirection(DirectionType.AH);
                player.addVehicle(bus);
                vehicles.add(bus);
            } else {
                SnowPlow snowPlow = new SnowPlow();
                snowPlow.setVehicleId(nextVehicleId++);
                snowPlow.setPlayer(player);
                snowPlow.setCanMove(true);
                snowPlow.setDirection(DirectionType.AH);

                Head sweeper = new Sweeper();
                Head iceCracker = new IceCracker();
                List<Head> heads = new ArrayList<>();
                heads.add(sweeper);
                heads.add(iceCracker);
                snowPlow.setHeads(heads);

                String defaultHeadCode = snowPlowHeads.getOrDefault(currentSnowPlowIndex, "sw");
                if ("ic".equals(defaultHeadCode)) {
                    snowPlow.setActiveHead(iceCracker);
                    iceCracker.setEquipped(true);
                } else {
                    snowPlow.setActiveHead(sweeper);
                    sweeper.setEquipped(true);
                }

                currentSnowPlowIndex++;
                player.addVehicle(snowPlow);
                vehicles.add(snowPlow);
            }
        }

        for (int i = 0; i < carCount; i++) {
            Car car = new Car();
            car.setVehicleId(nextVehicleId++);
            car.setCanMove(true);
            vehicles.add(car);
        }

        String vmipath = "TODO";
        World defaultWorld = loadThisWorld(vmipath);

        Session session = Session.getInstance();
        session.newGame(vehicles, defaultWorld);
        session.getGame().setPlayers(players);

        System.out.println("Game started with " + players.size() + " players, "
                + (vehicles.size() - carCount) + " driven vehicles and " + carCount + " cars.");
    }

    private void roadconfig(StringTokenizer st) {
        // TODO: implement roadconfig command - Keve
    }

    private void connect(StringTokenizer st) {
        // TODO: implement connect command - Keve
    }

    private void setb(StringTokenizer st) {
        // TODO: implement setb command - Zeki
    }

    private void setVeh(StringTokenizer st) {
        // TODO: implement setVeh command - Bazsi
    }

    private void setFieldContents(StringTokenizer st) {
        // TODO: implement setFieldContents command - Keve
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

    private void roll() {
        // TODO: implement roll command - KEVE
        Session session = Session.getInstance();
        Game game = session.getGame();

        if (game != null) {
            /// rollDice metodust at kell allitani public-ra (?)
            //game.rollDice();
        }
    }


    private void move(StringTokenizer st) {
        // TODO: implement move command - KEVE BAZSI (ZEKI)
        Session session = Session.getInstance();
        Game game = session.getGame();

        if (game == null || game.getVehicles() == null || game.getVehicles().isEmpty()) {
            return;
        }

        // ha uj kor, havazas
        if (currentVehicleIndex == 0) {
            if (game.getWorld() != null) {
                game.getWorld().snowfall();
            }
        }

        Vehicle currentVehicle = game.getVehicles().get(currentVehicleIndex);

        // irany beallitasa
        if (st.hasMoreTokens()) {
            String direction = st.nextToken();
            switch (direction) {
                case "ri": currentVehicle.setDirection(DirectionType.RI); break;
                case "le": currentVehicle.setDirection(DirectionType.LE); break;
                default: currentVehicle.setDirection(DirectionType.AH); break;
            }
        }
        // move
        currentVehicle.move(game.getDice().nextInt());

        // tick timers
        game.getWorld().tickTimers();

        // következő hívás a következő járművet mozgatja
        currentVehicleIndex++;
        if (currentVehicleIndex >= game.getVehicles().size()) {
            currentVehicleIndex = 0;

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
    }

    private void transaction(StringTokenizer st) {
        // TODO: implement transaction command - BAZSI
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
            String idStr = st.nextToken();
            
        if (!st.hasMoreTokens()) return;
            String resourceType = st.nextToken();
            
        if (!st.hasMoreTokens()) return;
            int amountToAdd = Integer.parseInt(st.nextToken());

        Session session = Session.getInstance();
        List<Vehicle> vehicles = session.getGame().getVehicles();

        for (Vehicle v : vehicles) {
            if (v.getVehicleId() == Integer.parseInt(idStr)) {
                    SnowPlow snowPlow = (SnowPlow) v;
            }
        }
    }


       
       



    }

