package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A Singleton class for logging method calls, returns,
 * and handling user input during the Prototype testing phase
 */
public class Prototype {
    private static Prototype instance = null;
    private boolean isLogging = false; //%@

    private final Scanner scanner;

    private Prototype() {
        this.scanner = new Scanner(System.in);
    }


    public void setLogging(boolean value) {
        this.isLogging = value;
    }
    
    /**
     * Returns the only instance of the singleton class
     * * @return The singleton instance of the Prototype class
     */
    public static Prototype getInstance() {
        if (instance == null) {
            instance = new Prototype();
        }
        return instance;
    }

    /**
     * Logs the entry of a method call
     * Start every tracked method by calling this function!
     * @param obj        The object executing the method (usually 'this').
     * @param methodName The name of the method being called.
     */
    public void changed(String objectName, String attribute , String oldValue, String newValue) {
        if (isLogging) {
            System.out.println(objectName + " " + attribute + " " + oldValue + " ::: " + newValue + "\n");
        }
    }


}
