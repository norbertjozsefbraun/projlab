package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import model.entities.SnowPlow;
import model.items.Biokerosene;
import model.items.Blower;
import model.items.Dragon;
import model.items.Gravel;
import model.items.GravelSpreader;
import model.items.IceCracker;
import model.items.Purchasable;
import model.items.Salt;
import model.items.Salter;
import model.items.Sweeper;
import model.map.World;

public final class ScriptRunnerHelper {
    private ScriptRunnerHelper() {
    }

    public static String stripQuotes(String value) {
        if (value == null || value.length() < 2) {
            return value;
        }
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    public static String normalizePlayerType(String rawType) {
        if (rawType == null) {
            return null;
        }
        String normalized = rawType.trim().toLowerCase();
        if (normalized.startsWith("!")) {
            normalized = normalized.substring(1);
        }

        if ("bus".equals(normalized)) {
            return "bus";
        }
        if ("plow".equals(normalized)
                || "snowplow".equals(normalized)
                || "sp".equals(normalized)) {
            return "plow";
        }
        return null;
    }

    public static World loadThisWorld(String vmipath) {
        return new World();
    }

    public static Purchasable createItem(String itemName) {
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

    public static void executeAndCapture(Runnable action, StringBuilder capturedOutput) {
        PrintStream previousStdout = System.out;
        ByteArrayOutputStream commandBuffer = new ByteArrayOutputStream();

        try (PrintStream tempStdout = new PrintStream(commandBuffer, true, StandardCharsets.UTF_8)) {
            System.setOut(tempStdout);
            action.run();
        } finally {
            System.setOut(previousStdout);
        }

        String commandOutput = commandBuffer.toString(StandardCharsets.UTF_8);
        capturedOutput.append(commandOutput);
    }
}