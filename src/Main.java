import test.ScriptRunner;
import controller.GameController;
import view.GameFrame;

public class Main {
    public static void main(String[] args){
        // Starting with a fancy Ascii art :D
        System.out.println("""
                                  _____  _____
                                  \\\\\\\\\\\\/ ___/___________________
                                   \\\\\\\\/ /                 _____/__________________________
                ________________    \\\\/ /              _____/.'.'.'.'.'.'.'.'.'.'.'.'_'_'_/
                \\_____        \\__    / / CSAP CSAPAT _____/.'.'.'.'.'.'.'.'.'.'.'.'.'_'_/
                    \\__________\\__  / /        _____/_'_'_'_'_'_'_'_'_'_'_'_'_'_'_/
                                \\_ / /__________/
                                 \\/____/\\\\\\\\\\\\
                                      \\\\\\\\\\\\
                                       ------
                """);

        // Check for --cli flag to use the old ScriptRunner mode
        boolean cliMode = false;
        for (String arg : args) {
            if ("--cli".equals(arg)) {
                cliMode = true;
                break;
            }
        }

        if (cliMode) {
            System.out.println("Type ScriptRunner commands on stdin. Use 'exit' to quit.");
            ScriptRunner runner = new ScriptRunner();
            runner.runFromStdIn();
        } else {
            // Launch Swing GUI
            javax.swing.SwingUtilities.invokeLater(() -> {
                GameController controller = new GameController();
                GameFrame frame = new GameFrame(controller);
                controller.setFrame(frame);
                frame.setVisible(true);
            });
        }
    }
}
