package view;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import controller.GameController;
import model.core.Game;
import model.core.Session;
import model.entities.SnowPlow;
import model.entities.Vehicle;
import model.items.*;

/**
 * Right-side panel showing balance, shop (buy heads & resources),
 * active head display, and owned heads with swap buttons.
 * Clean, minimal design.
 */
public class SidePanel extends JPanel {

    private static final Color BG = new Color(248, 248, 248);
    private static final Color SECTION_BG = new Color(255, 255, 255);
    private static final Color TEXT_COLOR = new Color(60, 60, 60);
    private static final Color SECONDARY = new Color(120, 120, 120);
    private static final Color BORDER_COLOR = new Color(220, 220, 220);
    private static final Color BTN_BG = new Color(235, 235, 235);
    private static final Color BTN_HOVER = new Color(225, 225, 225);
    private static final Color ACTIVE_COLOR = new Color(80, 140, 80);

    private final GameController controller;

    private JLabel balanceValueLabel;
    private JPanel headsShopPanel;
    private JPanel resourcesShopPanel;
    private JLabel activeHeadLabel;
    private JPanel ownedHeadsPanel;

    public SidePanel(GameController controller) {
        this.controller = controller;
        setBackground(BG);
        setPreferredSize(new Dimension(230, 0));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        buildUI();
    }

    private void buildUI() {
        // ── Balance ─────────────────────────────────
        JPanel balancePanel = sectionPanel("Balance");
        balanceValueLabel = new JLabel("0");
        balanceValueLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        balanceValueLabel.setForeground(TEXT_COLOR);
        balanceValueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        balancePanel.add(balanceValueLabel);

        JButton addFundsBtn = makeButton("+100 Funds");
        addFundsBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        addFundsBtn.addActionListener(e -> controller.addFunds(100));
        balancePanel.add(Box.createVerticalStrut(4));
        balancePanel.add(addFundsBtn);
        add(balancePanel);
        add(Box.createVerticalStrut(6));

        // ── Buy Heads ───────────────────────────────
        JPanel buyHeadsSection = sectionPanel("Buy Heads");
        headsShopPanel = new JPanel();
        headsShopPanel.setLayout(new BoxLayout(headsShopPanel, BoxLayout.Y_AXIS));
        headsShopPanel.setOpaque(false);
        headsShopPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        addHeadShopButton(headsShopPanel, "Sweeper", 50);
        addHeadShopButton(headsShopPanel, "Blower", 80);
        addHeadShopButton(headsShopPanel, "IceCracker", 70);
        addHeadShopButton(headsShopPanel, "Salter", 100);
        addHeadShopButton(headsShopPanel, "GravelSpreader", 80);
        addHeadShopButton(headsShopPanel, "Dragon", 150);

        buyHeadsSection.add(headsShopPanel);
        add(buyHeadsSection);
        add(Box.createVerticalStrut(6));

        // ── Buy Resources ───────────────────────────
        JPanel buyResSection = sectionPanel("Buy Resources");
        resourcesShopPanel = new JPanel();
        resourcesShopPanel.setLayout(new BoxLayout(resourcesShopPanel, BoxLayout.Y_AXIS));
        resourcesShopPanel.setOpaque(false);
        resourcesShopPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        addResourceShopButton(resourcesShopPanel, "Salt x20", "Salt", 10, 20);
        addResourceShopButton(resourcesShopPanel, "Gravel x20", "Gravel", 10, 20);
        addResourceShopButton(resourcesShopPanel, "Biokerosene x20", "Biokerosene", 15, 20);

        buyResSection.add(resourcesShopPanel);
        add(buyResSection);
        add(Box.createVerticalStrut(6));

        // ── Active Head ─────────────────────────────
        JPanel activeSection = sectionPanel("Active Head");
        activeHeadLabel = new JLabel("N/A");
        activeHeadLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        activeHeadLabel.setForeground(ACTIVE_COLOR);
        activeHeadLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        activeSection.add(activeHeadLabel);
        add(activeSection);
        add(Box.createVerticalStrut(6));

        // ── Owned Heads ─────────────────────────────
        JPanel ownedSection = sectionPanel("Owned Heads");
        ownedHeadsPanel = new JPanel();
        ownedHeadsPanel.setLayout(new BoxLayout(ownedHeadsPanel, BoxLayout.Y_AXIS));
        ownedHeadsPanel.setOpaque(false);
        ownedHeadsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ownedSection.add(ownedHeadsPanel);
        add(ownedSection);

        add(Box.createVerticalGlue());
    }

    // ── Refresh ─────────────────────────────────────

    public void refresh() {
        Game game = Session.getInstance().getGame();
        if (game == null) {
            balanceValueLabel.setText("0");
            activeHeadLabel.setText("N/A");
            ownedHeadsPanel.removeAll();
            ownedHeadsPanel.revalidate();
            ownedHeadsPanel.repaint();
            return;
        }

        balanceValueLabel.setText(String.valueOf(game.getShop().getBalance()));

        // Find the snowplow
        SnowPlow plow = null;
        for (Vehicle v : game.getVehicles()) {
            if (v instanceof SnowPlow) {
                plow = (SnowPlow) v;
                break;
            }
        }

        if (plow != null) {
            Head active = plow.getActiveHead();
            activeHeadLabel.setText(active != null ? active.getClass().getSimpleName() : "None");

            // Rebuild owned heads list
            ownedHeadsPanel.removeAll();
            List<Head> heads = plow.getHeads();
            if (heads != null) {
                for (Head h : heads) {
                    JPanel row = new JPanel(new BorderLayout(4, 0));
                    row.setOpaque(false);
                    row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
                    row.setAlignmentX(Component.LEFT_ALIGNMENT);

                    String name = h.getClass().getSimpleName();
                    boolean isActive = (h == active);

                    JLabel nameLabel = new JLabel(name);
                    nameLabel.setForeground(isActive ? ACTIVE_COLOR : TEXT_COLOR);
                    nameLabel.setFont(new Font("SansSerif", isActive ? Font.BOLD : Font.PLAIN, 11));
                    row.add(nameLabel, BorderLayout.CENTER);

                    if (!isActive) {
                        JButton swapBtn = makeSmallButton("Activate");
                        final Head headRef = h;
                        swapBtn.addActionListener(e -> controller.changeHead(headRef));
                        row.add(swapBtn, BorderLayout.EAST);
                    } else {
                        JLabel activeTag = new JLabel("(active)");
                        activeTag.setForeground(ACTIVE_COLOR);
                        activeTag.setFont(new Font("SansSerif", Font.ITALIC, 9));
                        row.add(activeTag, BorderLayout.EAST);
                    }

                    ownedHeadsPanel.add(row);
                    ownedHeadsPanel.add(Box.createVerticalStrut(2));
                }
            }
            ownedHeadsPanel.revalidate();
            ownedHeadsPanel.repaint();
        } else {
            activeHeadLabel.setText("N/A");
            ownedHeadsPanel.removeAll();
            ownedHeadsPanel.revalidate();
            ownedHeadsPanel.repaint();
        }
    }

    // ── Helper builders ─────────────────────────────

    private void addHeadShopButton(JPanel parent, String name, int price) {
        JButton btn = makeButton(name + " ($" + price + ")");
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        btn.addActionListener(e -> controller.buyHead(name, price));
        parent.add(btn);
        parent.add(Box.createVerticalStrut(2));
    }

    private void addResourceShopButton(JPanel parent, String label, String type, int unitPrice, int amount) {
        JButton btn = makeButton(label + " ($" + unitPrice + ")");
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        btn.addActionListener(e -> controller.buyResource(type, unitPrice, amount));
        parent.add(btn);
        parent.add(Box.createVerticalStrut(2));
    }

    private JPanel sectionPanel(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(SECTION_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        lbl.setForeground(SECONDARY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(lbl);
        p.add(Box.createVerticalStrut(4));
        return p;
    }

    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(BTN_BG);
        b.setForeground(TEXT_COLOR);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setFont(new Font("SansSerif", Font.PLAIN, 10));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JButton makeSmallButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(BTN_BG);
        b.setForeground(TEXT_COLOR);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setFont(new Font("SansSerif", Font.PLAIN, 9));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(2, 6, 2, 6)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}
