import model.map.*;
import java.util.*;
public class TempCheck {
  public static void main(String[] args) {
    World w = new World();
    w.setRoads(new ArrayList<>());
    w.setIntersections(new ArrayList<>());
    Road r1 = new Road("1esut", RoadType.STANDARD, 2, 5);
    Road r2 = new Road("ut1", RoadType.STANDARD, 1, 1);
    w.getRoads().add(r1);
    w.getRoads().add(r2);
    System.out.println("r1 fields: " + countFields(r1));
    System.out.println("r2 ids: " + fieldIds(r2));
  }
  static int countFields(Road r) {
    int c = 0;
    for (Lane l : r.getLanesToA()) c += l.getFields().size();
    for (Lane l : r.getLanesToB()) c += l.getFields().size();
    return c;
  }
  static String fieldIds(Road r) {
    List<Integer> ids = new ArrayList<>();
    for (Lane l : r.getLanesToA()) for (Field f : l.getFields()) ids.add(f.getId());
    for (Lane l : r.getLanesToB()) for (Field f : l.getFields()) ids.add(f.getId());
    return ids.toString();
  }
}
