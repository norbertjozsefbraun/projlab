package view;

import java.awt.*;
import javax.swing.*;

import controller.GameController;
import model.entities.DirectionType;

/**
 * Button bar for game actions.
 */
public class ControlPanel extends JPanel {

    public ControlPanel(GameController controller) {
        setBackground(new Color(40, 75, 35));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 6));
        setBorder(BorderFactory.createEmptyBorder(2, 6, 8, 6));

        Color btnColor = new Color(60, 160, 80);

        JButton newGameBtn = btn("New Game", btnColor);
        newGameBtn.addActionListener(e -> controller.newGame());
        add(newGameBtn);

        JButton roundBtn = btn("New Round", btnColor);
        roundBtn.addActionListener(e -> controller.newRound());
        add(roundBtn);

        JButton rollBtn = btn("Roll Dice", btnColor);
        rollBtn.addActionListener(e -> controller.rollDice());
        add(rollBtn);

        add(Box.createHorizontalStrut(10));

        add(btn("Left",  btnColor, e -> controller.setDirection(DirectionType.LE)));
        add(btn("Ahead", btnColor, e -> controller.setDirection(DirectionType.AH)));
        add(btn("Right", btnColor, e -> controller.setDirection(DirectionType.RI)));

        add(Box.createHorizontalStrut(10));

        JButton stepBtn = btn("Step", btnColor);
        stepBtn.addActionListener(e -> controller.step());
        add(stepBtn);
    }

    private JButton btn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFocusPainted(false); b.setOpaque(true);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker(), 1),
                BorderFactory.createEmptyBorder(5, 11, 5, 11)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private JButton btn(String text, Color bg, java.awt.event.ActionListener al) {
        JButton b = btn(text, bg);
        b.addActionListener(al);
        return b;
    }
}
