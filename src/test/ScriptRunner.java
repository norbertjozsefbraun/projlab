package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import model.entities.Vehicle;
import model.items.*;
import model.core.Session;
import model.core.Shop;
import model.entities.SnowPlow;
import model.core.Player;
import model.items.*;

/**
 * Reads a test script from a file and executes each line as a command.
 * Each line in the file corresponds to one of the supported game commands.
 */
public class ScriptRunner {

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
                switch (st.nextToken().toLowerCase()) {
                    case "randomize"   -> randomize();
                    case "derandomize" -> derandomize();
                    case "start"       -> start(st);
                    case "lsh"         -> lsh(st);
                    case "ch"          -> ch(st);
                    case "roll"        -> roll();
                    case "move"        -> move(st);
                    case "save"        -> save(st);
                    case "ls"          -> ls();
                    case "transaction" -> transaction(st);
                    case "fill"        -> fill(st);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read script: " + e.getMessage());
        }
    }

    private void randomize() {
        // TODO: implement randomize command - NORBI
    }

    private void derandomize() {
        // TODO: implement derandomize command - BAZSI
        Session session = Session.getInstance();
        session.getGame().setDerandomized(true);
    }

    private void start(StringTokenizer st) {
        // TODO: implement start command - NORBI
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

        for (int i=0;i<snowPlow.getHeads().size();i++) {
            System.out.println("\t" + i + ": " + snowPlow.getHeads().get(i).getClass().getSimpleName());
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
    }

    private void move(StringTokenizer st) {
        // TODO: implement move command - KEVE BAZSI (ZEKI)
    }

    private void save(StringTokenizer st) {
        // TODO: implement save command - NORBI
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

    private Purchasable createItem(String itemName) {
        switch (itemName.toLowerCase()) {
            case "salt": return new Salt();
            case "grav": return new Gravel();
            case "bio": return new Biokerosene();
            case "sw": return new Sweeper();
            case "ic": return new IceCracker();
            case "bw": return new Blower();
            case "dr": return new Dragon();
            case "st": return new Salter();
            case "gr": return new GravelSpreader();
            case "sp": return new SnowPlow();
            default: return null;
        }
    }
}
