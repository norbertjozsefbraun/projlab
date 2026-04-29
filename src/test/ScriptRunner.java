package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;

/**
 * Reads a test script from a file and executes each line as a command.
 * Each line in the file corresponds to one of the supported game commands.
 */
public class ScriptRunner {

    /**
     * Reads the file at the given path line by line and dispatches each line
     * to the appropriate command handler.
     *
     * @param path the path to the script file
     */
    public void runFromFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.strip();
                if (line.isEmpty()) continue;
                StringTokenizer st = new StringTokenizer(line);
                switch (st.nextToken().toLowerCase()) {
                    case "randomize"   -> randomize();
                    case "derandomize" -> derandomize();
                    case "start"       -> start(st);
                    case "lsh"         -> lsh();
                    case "ch"          -> ch(st);
                    case "roll"        -> roll();
                    case "move"        -> move(st);
                    case "save"        -> save(st);
                    case "ls"          -> ls();
                    case "transaction" -> transaction(st);
                    case "fill"        -> fill(st);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read script: " + e.getMessage());
        }
    }

    private void randomize() {
        // TODO: implement randomize command - NORBI
    }

    private void derandomize() {
        // TODO: implement derandomize command - BAZSI
    }

    private void start(StringTokenizer st) {
        // TODO: implement start command - NORBI
    }

    private void lsh() {
        // TODO: implement lsh command - ZOLI
    }

    private void ch(StringTokenizer st) {
        // TODO: implement ch command - ZOLI
    }

    private void roll() {
        // TODO: implement roll command - KEVE
    }

    private void move(StringTokenizer st) {
        // TODO: implement move command - KEVE BAZSI (ZEKI)
    }

    private void save(StringTokenizer st) {
        // TODO: implement save command - NORBI
    }

    private void ls() {
        // TODO: implement ls command - ZEKI
        // Kiirni az osszes elerheto testcaset konzolra
        // mainbe listabol?
        ArrayList<String> testCases = new ArrayList<>();
        testCases.add("auto-sikeres-lep");
        testCases.add("fejcsere");
        testCases.add("busz-kor-teljesitese");
        testCases.add("hokotro-es-auto-utkozese");
        testCases.add("havazas");
        testCases.add("jegpancel-kialakulasa");
        testCases.add("auto-megcsuszik-a-jegen");
        testCases.add("auto-megakad-a-hoban");
        testCases.add("autok-utkozese");
        testCases.add("auto-buszba-csuszik");
        testCases.add("auto-elindul-a-munkahelyerol");
        testCases.add("auto-megerkezik-a-munkahelyere");
        testCases.add("auto-elindul-otthonrol");
        testCases.add("auto-hazaer");
        testCases.add("jatek-vege");
        testCases.add("gravelspreader-working");
        testCases.add("hoeltakaritas-fuvassal");
        testCases.add("zuzalek-feltoltese");
        testCases.add("sozas");
        testCases.add("langszoro-ujratoltese");
        testCases.add("utszakasz-soprese");
        testCases.add("soszoro-fej-urjatoltese");
        testCases.add("jegtores");
        testCases.add("olvasztas");

        System.out.println("Elerheto tesztesetek:");
        int i = 1;
        for (String testCase : testCases) {
            System.out.println("\t" + i++ + ". " + testCase);
        }
    }

    private void transaction(StringTokenizer st) {
        // TODO: implement transaction command - BAZSI
    }

    private void fill(StringTokenizer st) {
        // TODO: implement fill command - ZOLI
    }
}
