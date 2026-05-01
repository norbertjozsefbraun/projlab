import test.ScriptRunner;

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

        System.out.println("Type ScriptRunner commands on stdin. Use 'exit' to quit.");
        ScriptRunner runner = new ScriptRunner();
        runner.runFromStdIn();
    }
}
