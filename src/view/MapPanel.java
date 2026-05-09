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
 * Renders the game world as a clean graph visualization.
 * Intersections = nodes (circles), Roads = edges (lines with field dots).
 */
public class MapPanel extends JPanel {

    private static final int NODE_RADIUS = 24;
    private static final int CLICK_RADIUS = 32;
    private static final int FIELD_DOT = 8;
    private static final Color BG_COLOR = new Color(250, 250, 250);
    private static final Color EDGE_COLOR = new Color(180, 180, 180);
    private static final Color NODE_FILL = new Color(240, 240, 240);
    private static final Color NODE_STROKE = new Color(80, 80, 80);
    private static final Color NODE_TEXT = new Color(50, 50, 50);
    private static final Color LABEL_COLOR = new Color(120, 120, 120);
    private static final Color FIELD_CLEAR = new Color(210, 210, 210);
    private static final Color FIELD_SNOW = new Color(160, 170, 190);
    private static final Color FIELD_ICE = new Color(140, 190, 210);
    private static final Color FIELD_BLOCKED = new Color(120, 125, 135);
    private static final Color FIELD_ACCIDENT = new Color(180, 80, 80);
    private static final Color HIGHLIGHT_COLOR = new Color(100, 130, 200, 80);

    private final GameController controller;

    public MapPanel(GameController controller) {
        this.controller = controller;
        setBackground(BG_COLOR);

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
            g2.setColor(LABEL_COLOR);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 16));
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

        // Draw edges (roads)
        for (Road road : world.getRoads()) {
            Intersection intA = road.getDestinationA();
            Intersection intB = road.getDestinationB();
            if (intA == null || intB == null) continue;
            int[] posA = positions.get(intA.getId());
            int[] posB = positions.get(intB.getId());
            if (posA == null || posB == null) continue;
            drawEdge(g2, road, posA, posB, fieldPositions);
        }

        // Draw nodes (intersections)
        boolean choosing = controller.getPhase() == TurnPhase.CHOOSE_DESTINATION;
        for (Intersection inter : world.getIntersections()) {
            int[] pos = positions.get(inter.getId());
            if (pos == null) continue;
            drawNode(g2, inter, pos[0], pos[1], choosing);
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

    // ── Edge (Road) drawing ─────────────────────────────────

    private void drawEdge(Graphics2D g2, Road road, int[] posA, int[] posB,
                          Map<Integer, double[]> fieldPositions) {
        double ax = posA[0], ay = posA[1], bx = posB[0], by = posB[1];
        double dx = bx - ax, dy = by - ay;
        double len = Math.sqrt(dx * dx + dy * dy);
        if (len == 0) return;
        double ux = dx / len, uy = dy / len;
        double px = -uy, py = ux;

        double margin = NODE_RADIUS + 6;
        double startT = margin / len, endT = 1.0 - margin / len;

        int nB = road.getLanesToB() != null ? road.getLanesToB().size() : 0;
        int nA = road.getLanesToA() != null ? road.getLanesToA().size() : 0;
        int totalLanes = nB + nA;

        // Draw the edge line(s)
        float laneSpacing = 12f;
        float totalWidth = (totalLanes - 1) * laneSpacing;

        // Draw each lane as a thin line
        int laneIndex = 0;

        // Lanes to B
        if (road.getLanesToB() != null) {
            for (int li = 0; li < nB; li++) {
                float offset = -totalWidth / 2f + laneIndex * laneSpacing;
                double x1 = ax + ux * len * startT + px * offset;
                double y1 = ay + uy * len * startT + py * offset;
                double x2 = ax + ux * len * endT + px * offset;
                double y2 = ay + uy * len * endT + py * offset;

                g2.setColor(EDGE_COLOR);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

                // Draw field dots along this lane
                drawLaneFieldDots(g2, road.getLanesToB().get(li), ax, ay, ux, uy, px, py,
                        startT, endT, len, offset, fieldPositions, false);

                laneIndex++;
            }
        }

        // Lanes to A
        if (road.getLanesToA() != null) {
            for (int li = 0; li < nA; li++) {
                float offset = -totalWidth / 2f + laneIndex * laneSpacing;
                double x1 = ax + ux * len * startT + px * offset;
                double y1 = ay + uy * len * startT + py * offset;
                double x2 = ax + ux * len * endT + px * offset;
                double y2 = ay + uy * len * endT + py * offset;

                g2.setColor(EDGE_COLOR);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

                drawLaneFieldDots(g2, road.getLanesToA().get(li), ax, ay, ux, uy, px, py,
                        startT, endT, len, offset, fieldPositions, true);

                laneIndex++;
            }
        }

        g2.setStroke(new BasicStroke(1f));

        // Road name label at midpoint
        double mx = (ax + bx) / 2, my = (ay + by) / 2;
        g2.setColor(LABEL_COLOR);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
        FontMetrics fm = g2.getFontMetrics();
        String name = road.getName() != null ? road.getName() : "";
        double labelOffset = totalWidth / 2f + 12;
        g2.drawString(name,
                (int)(mx + px * labelOffset - fm.stringWidth(name) / 2.0),
                (int)(my + py * labelOffset + 4));
    }

    private void drawLaneFieldDots(Graphics2D g2, Lane lane,
                                    double ax, double ay, double ux, double uy,
                                    double px, double py, double startT, double endT,
                                    double roadLen, double perpOffset,
                                    Map<Integer, double[]> fieldPositions,
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
            drawFieldDot(g2, field, (int) fx, (int) fy);
        }
    }

    private void drawFieldDot(Graphics2D g2, Field field, int cx, int cy) {
        int snow = field.getSurface().getSnowThickness();
        boolean ice = field.getSurface().getIsIce();
        boolean accident = field.getAccidentTimer() > 0;

        Color dotColor;
        if (accident)         dotColor = FIELD_ACCIDENT;
        else if (snow >= 35)  dotColor = FIELD_BLOCKED;
        else if (ice)         dotColor = FIELD_ICE;
        else if (snow > 0)    dotColor = FIELD_SNOW;
        else                  dotColor = FIELD_CLEAR;

        int r = FIELD_DOT / 2;
        g2.setColor(dotColor);
        g2.fillOval(cx - r, cy - r, FIELD_DOT, FIELD_DOT);
        g2.setColor(NODE_STROKE);
        g2.drawOval(cx - r, cy - r, FIELD_DOT, FIELD_DOT);

        // Show snow number if present
        if (snow > 0) {
            g2.setColor(NODE_TEXT);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 7));
            FontMetrics fm = g2.getFontMetrics();
            String s = String.valueOf(snow);
            g2.drawString(s, cx - fm.stringWidth(s) / 2, cy - r - 2);
        }
    }

    // ── Vehicle drawing ─────────────────────────────────────

    private void drawVehicle(Graphics2D g2, Vehicle v, int cx, int cy) {
        Color color; String letter;
        if (v instanceof SnowPlow)  { color = new Color(220, 140, 40);  letter = "P"; }
        else if (v instanceof Bus)  { color = new Color(60, 160, 80);   letter = "B"; }
        else if (v instanceof Car)  { color = new Color(80, 120, 200);  letter = "C"; }
        else                        { color = new Color(150, 150, 150); letter = "?"; }

        int size = 18;

        // Light shadow
        g2.setColor(new Color(0, 0, 0, 30));
        g2.fillOval(cx - size / 2 + 2, cy - size / 2 + 2, size, size);

        g2.setColor(color);
        g2.fillOval(cx - size / 2, cy - size / 2, size, size);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawOval(cx - size / 2, cy - size / 2, size, size);
        g2.setStroke(new BasicStroke(1f));
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(letter, cx - fm.stringWidth(letter) / 2, cy + 4);
    }

    // ── Node (Intersection) drawing ─────────────────────────

    private void drawNode(Graphics2D g2, Intersection inter, int x, int y, boolean clickable) {
        int r = NODE_RADIUS;

        // Subtle highlight during CHOOSE_DESTINATION
        if (clickable) {
            g2.setColor(HIGHLIGHT_COLOR);
            g2.fillOval(x - r - 6, y - r - 6, (r + 6) * 2, (r + 6) * 2);
        }

        // Node circle
        g2.setColor(NODE_FILL);
        g2.fillOval(x - r, y - r, r * 2, r * 2);
        g2.setColor(NODE_STROKE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(x - r, y - r, r * 2, r * 2);
        g2.setStroke(new BasicStroke(1f));

        // ID inside
        g2.setColor(NODE_TEXT);
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontMetrics fm = g2.getFontMetrics();
        String idStr = String.valueOf(inter.getId());
        g2.drawString(idStr, x - fm.stringWidth(idStr) / 2, y + 5);

        // Building label below
        String label = "";
        var b = inter.getBuilding();
        if (b instanceof Garage)    label = "Garage";
        if (b instanceof Home)      label = "Home";
        if (b instanceof WorkPlace) label = "Work";
        if (b instanceof BusStop)   label = "BusStop";

        if (!label.isEmpty()) {
            g2.setColor(LABEL_COLOR);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
            fm = g2.getFontMetrics();
            g2.drawString(label, x - fm.stringWidth(label) / 2, y + r + 14);
        }
    }

    // ── Legend ───────────────────────────────────────────────

    private void drawLegend(Graphics2D g2) {
        int x = 14, y = getHeight() - 18;
        g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
        Object[][] items = {
            {FIELD_CLEAR, "Clear"}, {FIELD_SNOW, "Snow"},
            {FIELD_ICE, "Ice"}, {FIELD_ACCIDENT, "Accident"},
            {new Color(220, 140, 40), "Plow"}, {new Color(60, 160, 80), "Bus"},
            {new Color(80, 120, 200), "Car"},
        };
        for (Object[] item : items) {
            g2.setColor((Color) item[0]);
            g2.fillOval(x, y - 8, 10, 10);
            g2.setColor(NODE_STROKE);
            g2.drawOval(x, y - 8, 10, 10);
            g2.setColor(LABEL_COLOR);
            g2.drawString((String) item[1], x + 13, y + 1);
            x += 58;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(720, 600);
    }
}
