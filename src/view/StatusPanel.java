package view;

import java.awt.*;
import javax.swing.*;

import controller.GameController;
import model.core.Game;
import model.core.Session;
import model.entities.Vehicle;

/**
 * Compact status bar at the bottom showing game stats.
 */
public class StatusPanel extends JPanel {

    private final JLabel roundLabel;
    private final JLabel balanceLabel;
    private final JLabel diceLabel;
    private final JLabel vehicleLabel;
    private final GameController controller;

    public StatusPanel(GameController controller) {
        this.controller = controller;
        setBackground(new Color(35, 65, 30));
        setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
        setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));

        roundLabel   = lbl("Round: N/A");
        balanceLabel = lbl("Balance: N/A");
        diceLabel    = lbl("Dice: N/A");
        vehicleLabel = lbl("Vehicle: N/A");

        add(roundLabel); add(balanceLabel); add(diceLabel); add(vehicleLabel);
    }

    public void refresh() {
        Game game = Session.getInstance().getGame();
        if (game == null) {
            roundLabel.setText("Round: N/A"); balanceLabel.setText("Balance: N/A");
            diceLabel.setText("Dice: N/A");   vehicleLabel.setText("Vehicle: N/A");
            return;
        }

        roundLabel.setText("Round: " + game.getRounds());
        balanceLabel.setText("Balance: " + game.getShop().getBalance());
        diceLabel.setText("Dice: " + (controller.getLastDiceRoll() == 0 ? "N/A"
                : controller.getLastDiceRoll()));

        Vehicle v = controller.getCurrentVehicle();
        if (v != null) {
            String dir = v.getDirection() != null ? v.getDirection().name() : "N/A";
            String pos = v.getCurrentField() != null ? "field " + v.getCurrentField().getId()
                    : v.getCurrentBuilding() != null ? v.getCurrentBuilding().getClass().getSimpleName()
                    : "N/A";
            vehicleLabel.setText(v + " | Dir: " + dir + " | At: " + pos);
        } else {
            vehicleLabel.setText("Vehicle: N/A");
        }
    }

    private JLabel lbl(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(new Color(190, 210, 180));
        l.setFont(new Font("SansSerif", Font.PLAIN, 11));
        return l;
    }
}
