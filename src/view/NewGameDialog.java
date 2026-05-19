package view;

import java.awt.*;
// no awt event imports needed explicitly
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ChangeListener;

import controller.GameController;

/**
 * Modal dialog to configure a new game: player count, distribution (plow/bus),
 * car count, then player names/types and plow head selection.
 */
public class NewGameDialog extends JDialog {

    private final GameController controller;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);

    // Config controls
    private final JSpinner playerCountSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 16, 1));
    private final JSpinner plowCountSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 16, 1));
    private final JSpinner busCountSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 16, 1));
    private final JSpinner carCountSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 64, 1));
    private final JButton nextBtn = new JButton("Next");

    // Player setup lists (populated after config)
    private final List<JTextField> nameFields = new ArrayList<>();
    private final List<JComboBox<String>> typeCombos = new ArrayList<>();
    private final List<JComboBox<String>> headCombos = new ArrayList<>();

    public NewGameDialog(Window owner, GameController controller) {
        super(owner, "New Game Setup", ModalityType.APPLICATION_MODAL);
        this.controller = controller;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setResizable(false);
    }

    private void initComponents() {
        buildConfigCard();
        buildPlayersCard();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(cards, BorderLayout.CENTER);
    }

    private void buildConfigCard() {
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        p.add(new JLabel("Number of players:"));
        p.add(playerCountSpinner);
        p.add(Box.createVerticalStrut(8));

        p.add(new JLabel("Of which: snowplow drivers:"));
        p.add(plowCountSpinner);
        p.add(Box.createVerticalStrut(8));

        p.add(new JLabel("Of which: bus drivers:"));
        p.add(busCountSpinner);
        p.add(Box.createVerticalStrut(8));

        p.add(new JLabel("Number of cars (NPC):"));
        p.add(carCountSpinner);
        p.add(Box.createVerticalStrut(12));

        JPanel row = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> dispose());
        nextBtn.addActionListener(e -> onConfigNext());
        row.add(cancel);
        row.add(nextBtn);

        p.add(row);

        // validation logic
        ChangeListener validator = e -> validateConfig();
        playerCountSpinner.addChangeListener(validator);
        plowCountSpinner.addChangeListener(validator);
        busCountSpinner.addChangeListener(validator);
        validateConfig();

        cards.add(p, "config");
    }

    private void validateConfig() {
        int pc = (Integer) playerCountSpinner.getValue();
        int pl = (Integer) plowCountSpinner.getValue();
        int bu = (Integer) busCountSpinner.getValue();
        boolean ok = (pl + bu == pc) && pc > 0;
        nextBtn.setEnabled(ok);
    }

    private void buildPlayersCard() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // placeholder center; actual rows created when config confirmed
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setPreferredSize(new Dimension(480, 280));
        p.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = new JButton("Back");
        JButton start = new JButton("Start Game");
        back.addActionListener(e -> cardLayout.show(cards, "config"));
        start.addActionListener(e -> onStart());
        bottom.add(back);
        bottom.add(start);
        p.add(bottom, BorderLayout.SOUTH);

        cards.add(p, "players");
    }

    private void onConfigNext() {
        int pc = (Integer) playerCountSpinner.getValue();
        int pl = (Integer) plowCountSpinner.getValue();

        // build player rows
        nameFields.clear();
        typeCombos.clear();
        headCombos.clear();

        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));

        for (int i = 1; i <= pc; i++) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel lbl = new JLabel("Player " + i + ": ");
            JTextField nameField = new JTextField("Player " + i, 14);
            JComboBox<String> typeCombo = new JComboBox<>(new String[] {"Plow", "Bus"});
            JComboBox<String> headCombo = new JComboBox<>(new String[] {"Sweeper", "IceCracker"});

            // default assignment: first pl are Plow, then Bus
            if (i <= pl) {
                typeCombo.setSelectedItem("Plow");
            } else {
                typeCombo.setSelectedItem("Bus");
            }

            headCombo.setVisible(typeCombo.getSelectedItem().equals("Plow"));

            typeCombo.addActionListener(ev -> headCombo.setVisible(typeCombo.getSelectedItem().equals("Plow")));

            row.add(lbl);
            row.add(nameField);
            row.add(Box.createHorizontalStrut(8));
            row.add(typeCombo);
            row.add(Box.createHorizontalStrut(8));
            row.add(new JLabel("Head:"));
            row.add(headCombo);

            nameFields.add(nameField);
            typeCombos.add(typeCombo);
            headCombos.add(headCombo);

            playersPanel.add(row);
        }

        // replace placeholder list content
        Component comp = cards.getComponent(1); // players card
        JScrollPane sc = (JScrollPane) ((JPanel) comp).getComponent(0);
        JViewport vp = sc.getViewport();
        vp.setView(playersPanel);

        cardLayout.show(cards, "players");
        pack();
    }

    private void onStart() {
        int targetPlow = (Integer) plowCountSpinner.getValue();
        int targetBus = (Integer) busCountSpinner.getValue();
        int carCount = (Integer) carCountSpinner.getValue();

        int pc = nameFields.size();
        int plCount = 0;
        int buCount = 0;
        for (JComboBox<String> cb : typeCombos) {
            if ("Plow".equals(cb.getSelectedItem())) {
                plCount++;
            } else {
                buCount++;
            }
        }

        if (plCount != targetPlow || buCount != targetBus) {
            JOptionPane.showMessageDialog(this,
                    "Selected player types do not match the configured distribution.",
                    "Validation error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Build start command args
        StringBuilder args = new StringBuilder();
        args.append("-pc ").append(pc).append(" ");
        args.append("-cc ").append(carCount).append(" ");

        int plIndex = 1;
        for (int i = 0; i < pc; i++) {
            String name = nameFields.get(i).getText().trim();
            if (name.isEmpty()) name = "Player" + (i+1);
            String type = (String) typeCombos.get(i).getSelectedItem();
            String typeToken = "Plow".equals(type) ? "!plow" : "!bus";
            args.append("-p").append(i+1).append(" \"").append(name.replace("\"","'"))
                    .append("\" ").append(typeToken).append(" ");

            if ("Plow".equals(type)) {
                String head = (String) headCombos.get(i).getSelectedItem();
                String headCode = "Sweeper".equals(head) ? "sw" : "ic";
                args.append("-sh").append(plIndex).append(" ").append(headCode).append(" ");
                plIndex++;
            }
        }

        // Trigger controller to start game via ScriptRunner
        controller.startGameWithScript(args.toString().trim());
        dispose();
    }
}
