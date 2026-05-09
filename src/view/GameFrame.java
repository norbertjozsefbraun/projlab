package view;

import java.awt.*;
import javax.swing.*;

import controller.GameController;
import controller.GameController.TurnPhase;

/**
 * Main application window — clean, minimal graph-style layout.
 */
public class GameFrame extends JFrame {

    private final MapPanel mapPanel;
    private final ControlPanel controlPanel;
    private final SidePanel sidePanel;
    private final JLabel instructionLabel;
    private final GameController controller;

    public GameFrame(GameController controller) {
        super("Snow Plow — CSAP CSAPAT");
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(250, 250, 250));

        // ── Top: instruction banner + buttons ──────────────
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(245, 245, 245));
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));

        // Instruction banner
        instructionLabel = new JLabel("  Click \"New Game\" to start  ", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        instructionLabel.setForeground(new Color(60, 60, 60));
        instructionLabel.setOpaque(true);
        instructionLabel.setBackground(new Color(245, 245, 245));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 6, 20));
        instructionLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        topPanel.add(instructionLabel);

        controlPanel = new ControlPanel(controller);
        topPanel.add(controlPanel);

        // ── Center: map ────────────────────────────────────
        mapPanel = new MapPanel(controller);
        JScrollPane scrollPane = new JScrollPane(mapPanel);
        scrollPane.getViewport().setBackground(new Color(250, 250, 250));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // ── Right: side panel (shop + heads) ───────────────
        sidePanel = new SidePanel(controller);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);

        setSize(1020, 760);
        setLocationRelativeTo(null);
    }

    /** Refresh all panels + instruction banner from current model state. */
    public void refresh() {
        // Update instruction banner
        String text = controller.getInstructionText();
        instructionLabel.setText("  " + text + "  ");

        TurnPhase phase = controller.getPhase();
        Color fg = switch (phase) {
            case CHOOSE_DESTINATION -> new Color(100, 100, 60);
            case ROLL_DICE -> new Color(100, 80, 40);
            case MOVING -> new Color(120, 60, 40);
            case ROUND_COMPLETE -> new Color(60, 60, 60);
        };
        instructionLabel.setForeground(fg);

        sidePanel.refresh();
        mapPanel.repaint();
    }
}
