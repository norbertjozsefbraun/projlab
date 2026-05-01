package test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
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
import model.map.Intersection;
import model.map.Road;
import model.map.RoadType;
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
        World world = new World();
        world.setRoads(new ArrayList<>());
        world.setIntersections(new ArrayList<>());

        Map<String, Road> roadsByName = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(vmipath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.strip();
                if (line.isEmpty()) {
                    continue;
                }

                StringTokenizer st = new StringTokenizer(line);
                if (!st.hasMoreTokens()) {
                    continue;
                }

                String command = st.nextToken().toLowerCase();
                switch (command) {
                    case "roadconfig" -> parseRoadConfig(st, world, roadsByName);
                    case "connect" -> {
                        if (!parseConnect(st, world, roadsByName)) {
                            System.out.println("Map cannot be initialized due to logic error");
                            return createEmptyWorld();
                        }
                    }
                    case "setb" -> {
                        if (!parseSetBuilding(st, world)) {
                            System.out.println("Map cannot be initialized due to logic error");
                            return createEmptyWorld();
                        }
                    }
                    default -> {
                        // Ignore unknown lines to keep loader permissive for test assets.
                    }
                }
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Map cannot be initialized due to logic error");
            return createEmptyWorld();
        }

        return world;
    }

    private static World createEmptyWorld() {
        World empty = new World();
        empty.setRoads(new ArrayList<>());
        empty.setIntersections(new ArrayList<>());
        return empty;
    }

    private static void parseRoadConfig(StringTokenizer st, World world, Map<String, Road> roadsByName) {
        while (st.hasMoreTokens()) {
            String roadName = st.nextToken();
            if (!st.hasMoreTokens()) {
                throw new IllegalArgumentException("Missing road type");
            }
            String roadTypeRaw = st.nextToken().toLowerCase();

            if (!st.hasMoreTokens()) {
                throw new IllegalArgumentException("Missing lane count");
            }
            int laneCount = Integer.parseInt(st.nextToken());

            if (!st.hasMoreTokens()) {
                throw new IllegalArgumentException("Missing lane length");
            }
            int laneLength = Integer.parseInt(st.nextToken());

            RoadType roadType = switch (roadTypeRaw) {
                case "tunnel" -> RoadType.TUNNEL;
                case "bridge" -> RoadType.BRIDGE;
                case "standard" -> RoadType.STANDARD;
                default -> throw new IllegalArgumentException("Unknown road type");
            };

            Road road = new Road(roadName, roadType, laneCount, laneLength);
            world.getRoads().add(road);
            roadsByName.put(roadName, road);
        }
    }

    private static boolean parseConnect(StringTokenizer st, World world, Map<String, Road> roadsByName) {
        Intersection intersection = new Intersection();

        while (st.hasMoreTokens()) {
            String roadName = st.nextToken();
            if (!st.hasMoreTokens()) {
                return false;
            }

            String preferredEnd = st.nextToken().toUpperCase();
            if (!"A".equals(preferredEnd) && !"B".equals(preferredEnd)) {
                return false;
            }

            Road road = roadsByName.get(roadName);
            if (road == null) {
                return false;
            }

            Intersection destinationA = road.getDestinationA();
            Intersection destinationB = road.getDestinationB();

            if ("A".equals(preferredEnd)) {
                if (destinationA == null) {
                    road.setDestinationA(intersection);
                } else if (destinationB == null) {
                    road.setDestinationB(intersection);
                } else {
                    return false;
                }
            } else {
                if (destinationB == null) {
                    road.setDestinationB(intersection);
                } else if (destinationA == null) {
                    road.setDestinationA(intersection);
                } else {
                    return false;
                }
            }

            if (!intersection.getConnectedRoads().contains(road)) {
                intersection.getConnectedRoads().add(road);
            }
        }

        world.getIntersections().add(intersection);
        return true;
    }

    private static boolean parseSetBuilding(StringTokenizer st, World world) {
        if (!st.hasMoreTokens()) {
            return false;
        }
        int intersectionId;
        try {
            intersectionId = Integer.parseInt(st.nextToken());
        } catch (NumberFormatException ex) {
            return false;
        }

        if (!st.hasMoreTokens()) {
            return false;
        }
        String buildingCode = st.nextToken().toLowerCase();
        if (st.hasMoreTokens()) {
            return false;
        }

        Intersection targetIntersection = null;
        List<Intersection> intersections = world.getIntersections();
        for (Intersection intersection : intersections) {
            if (intersection.getId() == intersectionId) {
                targetIntersection = intersection;
                break;
            }
        }
        if (targetIntersection == null) {
            return false;
        }

        Building building = switch (buildingCode) {
            case "bs" -> new BusStop();
            case "ho" -> new Home();
            case "wo" -> new WorkPlace();
            case "ga" -> new Garage();
            default -> null;
        };
        if (building == null) {
            return false;
        }

        targetIntersection.setBuilding(building);
        building.setLocation(targetIntersection);
        return true;
    }

    public static Purchasable createItem(String itemName) {
        switch (itemName.toLowerCase()) {
            case "salt": return new Salt();
            case "grav": return new Gravel();
            case "bio": return new Biokerosene();
            case "sw": return new Sweeper();
            case "ic": return new IceCracker();
            case "bw":
            case "bl": return new Blower();
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