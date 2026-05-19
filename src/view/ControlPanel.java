package view;

import java.awt.*;
import javax.swing.*;

import controller.GameController;
import model.entities.DirectionType;

/**
 * Button bar for game actions — clean, flat style.
 */
public class ControlPanel extends JPanel {

    private static final Color BTN_BG = new Color(235, 235, 235);
    private static final Color BTN_FG = new Color(50, 50, 50);
    private static final Color BTN_BORDER = new Color(200, 200, 200);

    public ControlPanel(GameController controller) {
        setBackground(new Color(245, 245, 245));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 6));
        setBorder(BorderFactory.createEmptyBorder(2, 6, 8, 6));

        JButton newGameBtn = btn("New Game");
        newGameBtn.addActionListener(e -> {
            Window owner = SwingUtilities.getWindowAncestor((Component) e.getSource());
            NewGameDialog dlg = new NewGameDialog(owner, controller);
            dlg.setLocationRelativeTo(owner);
            dlg.setVisible(true);
        });
        add(newGameBtn);

        JButton roundBtn = btn("New Round");
        roundBtn.addActionListener(e -> controller.newRound());
        add(roundBtn);

        JButton rollBtn = btn("Roll Dice");
        rollBtn.addActionListener(e -> controller.rollDice());
        add(rollBtn);

        add(Box.createHorizontalStrut(10));

        add(btn("Left",  e -> controller.setDirection(DirectionType.LE)));
        add(btn("Ahead", e -> controller.setDirection(DirectionType.AH)));
        add(btn("Right", e -> controller.setDirection(DirectionType.RI)));

        add(Box.createHorizontalStrut(10));

        JButton stepBtn = btn("Step");
        stepBtn.addActionListener(e -> controller.step());
        add(stepBtn);
    }

    private JButton btn(String text) {
        JButton b = new JButton(text);
        b.setBackground(BTN_BG);
        b.setForeground(BTN_FG);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setFont(new Font("SansSerif", Font.PLAIN, 12));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BTN_BORDER, 1),
                BorderFactory.createEmptyBorder(5, 11, 5, 11)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JButton btn(String text, java.awt.event.ActionListener al) {
        JButton b = btn(text);
        b.addActionListener(al);
        return b;
    }
}
