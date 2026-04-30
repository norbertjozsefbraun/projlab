package model.map;


import java.util.*;

public class World {
    /// Fields:
    private List<Road> roads;
    private List<Intersection> intersections;

    /// Constructor:
    public World(){
        roads = null;
        intersections = null;
    }

    /// Getters:
    public List<Lane> getLanesTowards(Intersection destination) {
        List<Lane> foundLanes = new ArrayList<>();

        for (Road road : this.roads) {
            List<Lane> lanesFromRoad = road.getLanesTowards(destination);

            if (lanesFromRoad != null && !lanesFromRoad.isEmpty()) {
                foundLanes.addAll(lanesFromRoad);
            }
        }
        return foundLanes;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public Field getFieldById(String roadName, int fieldId) {
        Road road = null;
        for(Road r : this.roads){
            if(r.getName().equals(roadName)){
                road = r;
                break;
            }
        }
        if(road != null) {
            var lanesToA = road.getLanesToA();
            var lanesToB = road.getLanesToB();

            for(Lane lane : lanesToA) {
                var fields = lane.getFields();
                for(Field field : fields){
                    if(field.getId() == fieldId) { return field; }
                }
            }
            for(Lane lane : lanesToB) {
                var fields = lane.getFields();
                for(Field field : fields){
                    if(field.getId() == fieldId) { return field; }
                }
            }
            return null;
        }
        return null;
    }

    /// Setters:
    public void setRoads(List<Road> roads){
        this.roads = roads;
    }

    public void setIntersections(List<Intersection> intersections){
        this.intersections = intersections;
    }


    /// Functional functions:
    public Queue<Intersection> calculateRoute(Intersection start, Intersection destination) {
        Queue<Intersection> finalRoute = new LinkedList<>();
        if (start.equals(destination)) {
            return finalRoute;
        }

        Queue<Intersection> queue = new LinkedList<>();
        Set<Intersection> visited = new HashSet<>();
        Map<Intersection, Intersection> parentMap = new HashMap<>();

        queue.add(start);
        visited.add(start);
        boolean targetFound = false;

        while (!queue.isEmpty()) {
            Intersection current = queue.poll();

            if (current.equals(destination)) {
                targetFound = true;
                break;
            }

            // Végigmegyünk a jelenlegi kereszteződésből induló utakon
            for (Road road : current.getConnectedRoads()) {
                Intersection neighbor;

                // Meghatározzuk a túloldalt
                if (road.getDestinationA().equals(current)) {
                    neighbor = road.getDestinationB();
                } else {
                    neighbor = road.getDestinationA();
                }

                // Ha még nem jártunk ott, felvesszük a listára
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        // Útvonal visszafejtése
        if (targetFound) {
            LinkedList<Intersection> tempPath = new LinkedList<>();
            Intersection step = destination;

            while (!step.equals(start)) {
                tempPath.addFirst(step);
                step = parentMap.get(step);
            }

            finalRoute.addAll(tempPath);
        }
        return finalRoute;
    }

    public void snowfall() {
        for(Road road: roads){
            if(road.getRoadType() != RoadType.TUNNEL) {
                road.snowfall();
            }
        }
    }

    public void tickTimers() {
        if (this.roads != null) {
            for (Road road : this.roads) {
                road.tickTimers();
            }
        }
    }
}
