package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A Singleton class for logging method calls, returns,
 * and handling user input during the skeleton testing phase
 */
public class Skeleton {
    private static Skeleton instance = null;

    private int callDepth;
    private final Scanner scanner;
    private final Map<Object, String> objectNames;

    private Skeleton() {
        this.callDepth = 0;
        this.scanner = new Scanner(System.in);
        this.objectNames = new HashMap<>();
    }

    /**
     * Returns the only instance of the singleton class
     * * @return The singleton instance of the Skeleton class
     */
    public static Skeleton getInstance() {
        if (instance == null) {
            instance = new Skeleton();
        }
        return instance;
    }

    /**
     * Prints tabs to format output text based on the current call depth
     */
    private void printCallDepth() {
        for (int i = 0; i < callDepth; i++) {
            System.out.print("\t");
        }
    }

    /**
     * Assigns a custom name to an object reference for easier logging
     * Call this when initializing the test objects!
     * @param obj  The object to register
     * @param name The name to associate with the object
     */
    public void ctor(Object obj, String name) {
        objectNames.put(obj, name);
    }

    /**
     * Returns the registered name of an object, or its default class name if not registered
     * @param obj The object to look up
     * @return The name of the object
     */
    public String getObjectName(Object obj) {
        if (obj == null) {
            return "null";
        }
        return objectNames.getOrDefault(obj, obj.getClass().getSimpleName());
    }

    /**
     * Logs the entry of a method call
     * Start every tracked method by calling this function!
     * @param obj        The object executing the method (usually 'this').
     * @param methodName The name of the method being called.
     */
    public void call(Object obj, String methodName) {
        printCallDepth();
        String objName = getObjectName(obj);
        System.out.println(objName + ":" + methodName + "()");
        callDepth++;
    }

    /**
     * Logs the entry of a method call along with its parameters
     * Start every tracked method by calling this function!
     * @param obj        The object executing the method (usually 'this')
     * @param methodName The name of the method being called
     * @param params     A string representation of the parameters passed to the method
     */    public void call(Object obj, String methodName, String params) {
        printCallDepth();
        String objName = getObjectName(obj);
        System.out.println(objName + ":" + methodName + "(" + params + ")");
        callDepth++;
    }

    /**
     * Asks the user to select an option from a provided list
     * @param question The question to ask the user
     * @param options  An array of option
     * @return The number of the selected option
     */
    public int getChoice(String question, String[] options) {
        printCallDepth();
        System.out.println(question + " (1, 2, ...)");

        // Print the options
        for (int i = 0; i < options.length; i++) {
            printCallDepth();
            System.out.println((i + 1) + ". " + options[i]);
        }

        printCallDepth();
        System.out.print("> ");

        // Read input
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);

                // Check if choice is valid
                if (choice >= 1 && choice <= options.length) {
                    return choice;
                } else {
                    printCallDepth();
                    System.out.print("Invalid choice, try again:\n");
                    printCallDepth();
                    System.out.print("> ");
                }
            } catch (NumberFormatException e) {
                printCallDepth();
                System.out.print("Invalid choice, try again:\n");
                printCallDepth();
                System.out.print("> ");
            }
        }
    }

    /**
     * Logs when a void method returns
     * Call this at the end of every tracked void method!
     */
    public void returnMethod() {
        callDepth--;
        printCallDepth();
        System.out.println("return");
    }

    /**
     * Logs when a method returns a specific value
     * Call this at the end of every tracked value-returning method
     * @param type  The data type or class name of the return value
     * @param value The actual returned value/object as a string
     */
    public void returnMethod(String type, String value) {
        callDepth--;
        printCallDepth();
        System.out.println("return " + type + ": " + value);
    }

    /**
     * Resets the singleton class
     */
    public void reset() {
        callDepth = 0;
        objectNames.clear();
    }

}
