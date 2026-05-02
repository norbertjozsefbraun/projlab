import model.map.*;
import test.ScriptRunnerHelper;
import java.util.*;
public class TempFieldIds {
  public static void main(String[] args) {
    World world = ScriptRunnerHelper.loadThisWorld("test\\tests\\base-mechanic\\world.txt");
    Road ut1 = new Road("ut1", RoadType.STANDARD, 1, 1);
    Road ut2 = new Road("ut2", RoadType.STANDARD, 1, 1);
    world.getRoads().add(ut1);
    world.getRoads().add(ut2);
    Intersection inter = new Intersection();
    ut1.setDestinationA(inter);
    ut2.setDestinationB(inter);
    inter.getConnectedRoads().add(ut1);
    inter.getConnectedRoads().add(ut2);
    world.getIntersections().add(inter);
    System.out.println("ut1 ids: " + fieldIds(ut1));
    System.out.println("ut2 ids: " + fieldIds(ut2));
  }
  static String fieldIds(Road r) {
    List<Integer> ids = new ArrayList<>();
    for (Lane l : r.getLanesToA()) for (Field f : l.getFields()) ids.add(f.getId());
    for (Lane l : r.getLanesToB()) for (Field f : l.getFields()) ids.add(f.getId());
    return ids.toString();
  }
}
