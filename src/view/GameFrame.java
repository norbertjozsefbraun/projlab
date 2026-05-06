package view;

import java.awt.*;
import javax.swing.*;

import controller.GameController;
import controller.GameController.TurnPhase;

/**
 * Main application window with a prominent instruction banner at the top.
 */
public class GameFrame extends JFrame {

    private final MapPanel mapPanel;
    private final ControlPanel controlPanel;
    private final StatusPanel statusPanel;
    private final JLabel instructionLabel;
    private final GameController controller;

    public GameFrame(GameController controller) {
        super("Snow Plow — CSAP CSAPAT");
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(50, 100, 40));

        // ── Top: instruction banner + buttons ──────────────
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(40, 75, 35));

        // Instruction banner
        instructionLabel = new JLabel("  Click \"New Game\" to start  ", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setOpaque(true);
        instructionLabel.setBackground(new Color(40, 75, 35));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 6, 20));
        instructionLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        topPanel.add(instructionLabel);

        controlPanel = new ControlPanel(controller);
        topPanel.add(controlPanel);

        // ── Center: map ────────────────────────────────────
        mapPanel = new MapPanel(controller);
        JScrollPane scrollPane = new JScrollPane(mapPanel);
        scrollPane.getViewport().setBackground(new Color(76, 140, 60));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // ── Bottom: status bar ─────────────────────────────
        statusPanel = new StatusPanel(controller);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        setSize(780, 760);
        setLocationRelativeTo(null);
    }

    /** Refresh all panels + instruction banner from current model state. */
    public void refresh() {
        // Update instruction banner
        String text = controller.getInstructionText();
        instructionLabel.setText("  " + text + "  ");

        TurnPhase phase = controller.getPhase();
        Color bg = switch (phase) {
            case CHOOSE_DESTINATION -> new Color(120, 100, 20);
            case ROLL_DICE -> new Color(140, 110, 20);
            case MOVING -> new Color(160, 70, 30);
            case NPC_MOVING -> new Color(80, 100, 150);
            case ROUND_COMPLETE -> new Color(30, 80, 50);
        };
        instructionLabel.setBackground(bg);

        statusPanel.refresh();
        mapPanel.repaint();
    }
}
