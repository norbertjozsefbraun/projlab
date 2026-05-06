package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

import controller.GameController;
import controller.GameController.TurnPhase;
import model.buildings.BusStop;
import model.buildings.Garage;
import model.buildings.Home;
import model.buildings.WorkPlace;
import model.core.Game;
import model.core.Session;
import model.entities.Bus;
import model.entities.Car;
import model.entities.SnowPlow;
import model.entities.Vehicle;
import model.map.Field;
import model.map.Intersection;
import model.map.Lane;
import model.map.Road;
import model.map.World;

/**
 * Renders the game world as a 2D city map on a green background.
 * Intersections are clickable for snowplow destination selection.
 */
public class MapPanel extends JPanel {

    private static final int CELL = 18;
    private static final int LANE_GAP = 3;
    private static final int INTER_RADIUS = 20;
    private static final int CLICK_RADIUS = 28;

    private final GameController controller;

    public MapPanel(GameController controller) {
        this.controller = controller;
        setBackground(new Color(76, 140, 60));  // green grass

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    private void handleClick(int mx, int my) {
        if (controller.getPhase() != TurnPhase.CHOOSE_DESTINATION) return;

        Map<Integer, int[]> positions = controller.getIntersectionPositions();
        for (Map.Entry<Integer, int[]> entry : positions.entrySet()) {
            int[] pos = entry.getValue();
            double dist = Math.sqrt(Math.pow(mx - pos[0], 2) + Math.pow(my - pos[1], 2));
            if (dist <= CLICK_RADIUS) {
                controller.onIntersectionClicked(entry.getKey());
                return;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Session session = Session.getInstance();
        Game game = session.getGame();
        if (game == null || game.getWorld() == null) {
            g2.setColor(new Color(40, 80, 30));
            g2.setFont(new Font("SansSerif", Font.BOLD, 18));
            g2.drawString("Click \"New Game\" to start.", 30, 50);
            return;
        }

        World world = game.getWorld();
        Map<Integer, int[]> positions = controller.getIntersectionPositions();
        if (positions.isEmpty()) return;

        Map<Integer, double[]> fieldPositions = new HashMap<>();
        Map<Integer, Vehicle> fieldToVehicle = new HashMap<>();
        for (Vehicle v : game.getVehicles()) {
            if (v.getCurrentField() != null)
                fieldToVehicle.put(v.getCurrentField().getId(), v);
        }

        // Draw roads
        for (Road road : world.getRoads()) {
            Intersection intA = road.getDestinationA();
            Intersection intB = road.getDestinationB();
            if (intA == null || intB == null) continue;
            int[] posA = positions.get(intA.getId());
            int[] posB = positions.get(intB.getId());
            if (posA == null || posB == null) continue;
            drawRoad(g2, road, posA, posB, fieldPositions, fieldToVehicle);
        }

        // Draw intersections
        boolean choosing = controller.getPhase() == TurnPhase.CHOOSE_DESTINATION;
        for (Intersection inter : world.getIntersections()) {
            int[] pos = positions.get(inter.getId());
            if (pos == null) continue;
            drawIntersection(g2, inter, pos[0], pos[1], choosing);
        }

        // Draw vehicles
        for (Vehicle v : game.getVehicles()) {
            if (v.getCurrentField() != null) {
                double[] fp = fieldPositions.get(v.getCurrentField().getId());
                if (fp != null) drawVehicle(g2, v, (int) fp[0], (int) fp[1]);
            }
        }

        drawLegend(g2);
    }

    // ── Road drawing ────────────────────────────────────────

    private void drawRoad(Graphics2D g2, Road road, int[] posA, int[] posB,
                          Map<Integer, double[]> fieldPositions,
                          Map<Integer, Vehicle> fieldToVehicle) {
        double ax = posA[0], ay = posA[1], bx = posB[0], by = posB[1];
        double dx = bx - ax, dy = by - ay;
        double len = Math.sqrt(dx * dx + dy * dy);
        if (len == 0) return;
        double ux = dx / len, uy = dy / len;
        double px = -uy, py = ux;

        double margin = INTER_RADIUS + 4;
        double startT = margin / len, endT = 1.0 - margin / len;

        int nB = road.getLanesToB() != null ? road.getLanesToB().size() : 0;
        int nA = road.getLanesToA() != null ? road.getLanesToA().size() : 0;
        double bandWidth = (nB + nA) * (CELL + 2) + LANE_GAP;

        // Road band background
        drawRoadBand(g2, ax, ay, ux, uy, px, py, len, startT, endT, bandWidth);

        // Center line
        double s0x = ax + ux * len * startT, s0y = ay + uy * len * startT;
        double s1x = ax + ux * len * endT,   s1y = ay + uy * len * endT;
        g2.setColor(new Color(255, 210, 60, 160));
        g2.setStroke(new BasicStroke(1.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1f, new float[]{5, 3}, 0));
        g2.drawLine((int) s0x, (int) s0y, (int) s1x, (int) s1y);
        g2.setStroke(new BasicStroke(1f));

        // Road name
        double mx = (ax + bx) / 2, my = (ay + by) / 2;
        g2.setColor(new Color(50, 90, 40));
        g2.setFont(new Font("SansSerif", Font.ITALIC, 9));
        g2.drawString(road.getName() != null ? road.getName() : "",
                (int)(mx + px * (bandWidth / 2 + 10)), (int)(my + py * (bandWidth / 2 + 10)));

        // Lanes → B (Right hand traffic -> positive offset)
        double offset = LANE_GAP / 2.0 + CELL / 2.0;
        if (road.getLanesToB() != null) {
            for (int li = 0; li < road.getLanesToB().size(); li++) {
                drawLaneFields(g2, road.getLanesToB().get(li), ax, ay, ux, uy, px, py,
                        startT, endT, len, offset + li * (CELL + 2), fieldPositions, fieldToVehicle, false);
            }
        }
        // Lanes → A (Right hand traffic from B's perspective -> negative offset)
        offset = -(LANE_GAP / 2.0 + CELL / 2.0);
        if (road.getLanesToA() != null) {
            for (int li = 0; li < road.getLanesToA().size(); li++) {
                drawLaneFields(g2, road.getLanesToA().get(li), ax, ay, ux, uy, px, py,
                        startT, endT, len, offset - li * (CELL + 2), fieldPositions, fieldToVehicle, true);
            }
        }
    }

    private void drawRoadBand(Graphics2D g2, double ax, double ay,
                               double ux, double uy, double px, double py,
                               double len, double startT, double endT, double bw) {
        double s0x = ax + ux * len * startT, s0y = ay + uy * len * startT;
        double s1x = ax + ux * len * endT,   s1y = ay + uy * len * endT;
        double hw = bw / 2.0;
        int[] xp = {(int)(s0x+px*hw),(int)(s1x+px*hw),(int)(s1x-px*hw),(int)(s0x-px*hw)};
        int[] yp = {(int)(s0y+py*hw),(int)(s1y+py*hw),(int)(s1y-py*hw),(int)(s0y-py*hw)};
        g2.setColor(new Color(85, 85, 90));
        g2.fillPolygon(xp, yp, 4);
        g2.setColor(new Color(100, 100, 108));
        g2.drawPolygon(xp, yp, 4);
    }

    private void drawLaneFields(Graphics2D g2, Lane lane,
                                 double ax, double ay, double ux, double uy,
                                 double px, double py, double startT, double endT,
                                 double roadLen, double perpOffset,
                                 Map<Integer, double[]> fieldPositions,
                                 Map<Integer, Vehicle> fieldToVehicle,
                                 boolean reverse) {
        List<Field> fields = lane.getFields();
        if (fields == null || fields.isEmpty()) return;
        int n = fields.size();
        for (int i = 0; i < n; i++) {
            Field field = fields.get(i);
            double t = reverse ? startT + (endT - startT) * (n - 1 - i + 0.5) / n 
                               : startT + (endT - startT) * (i + 0.5) / n;
            double fx = ax + ux * roadLen * t + px * perpOffset;
            double fy = ay + uy * roadLen * t + py * perpOffset;
            fieldPositions.put(field.getId(), new double[]{fx, fy});
            drawFieldCell(g2, field, (int) fx, (int) fy);
        }
    }

    private void drawFieldCell(Graphics2D g2, Field field, int cx, int cy) {
        int half = CELL / 2;
        int x = cx - half, y = cy - half;
        int snow = field.getSurface().getSnowThickness();
        boolean ice = field.getSurface().getIsIce();
        boolean accident = field.getAccidentTimer() > 0;

        Color bg;
        if (accident)        bg = new Color(200, 50, 50);
        else if (snow >= 35) bg = new Color(100, 105, 125);
        else if (ice)        bg = new Color(90, 210, 240);
        else if (snow > 0) { int v = Math.max(160, 235 - snow * 4); bg = new Color(v, v, 255); }
        else                 bg = new Color(180, 185, 195);

        g2.setColor(bg);
        g2.fillRoundRect(x, y, CELL, CELL, 4, 4);
        g2.setColor(new Color(65, 68, 78));
        g2.drawRoundRect(x, y, CELL, CELL, 4, 4);

        if (snow > 0) {
            g2.setColor(new Color(30, 30, 50));
            g2.setFont(new Font("SansSerif", Font.PLAIN, 7));
            g2.drawString(String.valueOf(snow), x + 2, cy + 3);
        }
    }

    // ── Vehicle drawing ─────────────────────────────────────

    private void drawVehicle(Graphics2D g2, Vehicle v, int cx, int cy) {
        Color color; String letter;
        if (v instanceof SnowPlow)  { color = new Color(255, 150, 30); letter = "P"; }
        else if (v instanceof Bus)  { color = new Color(40, 190, 60);  letter = "B"; }
        else if (v instanceof Car)  { color = new Color(60, 100, 220); letter = "C"; }
        else                        { color = Color.MAGENTA;           letter = "?"; }

        int size = 20;

        // Shadow
        g2.setColor(new Color(0, 0, 0, 70));
        g2.fillOval(cx - size / 2 + 3, cy - size / 2 + 3, size, size);

        g2.setColor(color);
        g2.fillOval(cx - size / 2, cy - size / 2, size, size);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawOval(cx - size / 2, cy - size / 2, size, size);
        g2.setStroke(new BasicStroke(1f));
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(letter, cx - fm.stringWidth(letter) / 2, cy + 4);
    }

    // ── Intersection drawing ────────────────────────────────

    private void drawIntersection(Graphics2D g2, Intersection inter, int x, int y, boolean clickable) {
        int r = INTER_RADIUS;

        // Clickable glow during CHOOSE_DESTINATION
        if (clickable) {
            g2.setColor(new Color(255, 255, 100, 70));
            g2.fillOval(x - r - 8, y - r - 8, (r + 8) * 2, (r + 8) * 2);
            g2.setColor(new Color(255, 220, 50, 140));
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(x - r - 5, y - r - 5, (r + 5) * 2, (r + 5) * 2);
            g2.setStroke(new BasicStroke(1f));
        }

        // Circle
        g2.setColor(new Color(50, 55, 65));
        g2.fillOval(x - r, y - r, r * 2, r * 2);
        g2.setColor(new Color(140, 155, 185));
        g2.setStroke(new BasicStroke(2.5f));
        g2.drawOval(x - r, y - r, r * 2, r * 2);
        g2.setStroke(new BasicStroke(1f));

        // Building label + color
        String label = ""; Color lc = new Color(200, 210, 225);
        var b = inter.getBuilding();
        if (b instanceof Garage)   { label = "Garage";  lc = new Color(255, 180, 80); }
        if (b instanceof Home)     { label = "Home";    lc = new Color(120, 220, 130); }
        if (b instanceof WorkPlace){ label = "Work";    lc = new Color(100, 170, 255); }
        if (b instanceof BusStop)  { label = "BusStop"; lc = new Color(220, 130, 220); }

        // ID inside
        g2.setColor(new Color(190, 200, 220));
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        FontMetrics fm = g2.getFontMetrics();
        String idStr = "" + inter.getId();
        g2.drawString(idStr, x - fm.stringWidth(idStr) / 2, y + 5);

        // Label below
        if (!label.isEmpty()) {
            g2.setColor(lc);
            g2.setFont(new Font("SansSerif", Font.BOLD, 10));
            fm = g2.getFontMetrics();
            g2.drawString(label, x - fm.stringWidth(label) / 2, y + r + 14);
        }
    }

    // ── Legend ───────────────────────────────────────────────

    private void drawLegend(Graphics2D g2) {
        int x = 14, y = getHeight() - 22;
        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        Object[][] items = {
            {new Color(180,185,195), "Clear"}, {new Color(200,200,255), "Snow"},
            {new Color(90,210,240), "Ice"}, {new Color(200,50,50), "Accident"},
            {new Color(255,150,30), "Plow"}, {new Color(40,190,60), "Bus"},
            {new Color(60,100,220), "Car"},
        };
        for (Object[] item : items) {
            g2.setColor((Color) item[0]);
            g2.fillRoundRect(x, y - 9, 12, 12, 3, 3);
            g2.setColor(new Color(40, 80, 30));
            g2.drawString((String) item[1], x + 14, y + 1);
            x += 62;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(720, 600);
    }
}
