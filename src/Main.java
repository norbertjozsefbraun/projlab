import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import test.Test;

public class Main {
    public static void println(String s){
        System.out.println(s);
    }

    public static void main(String[] args){
        //Initializing a scanner
        Scanner scanner = new Scanner(System.in);
        //Initializing the input string
        String input = "";
        //Retrieving the first input
        input = scanner.nextLine();
        //Formatting the input
        input = input.strip().toLowerCase();
        //Initialiing the useCases list
        List<String> useCases = new ArrayList<>();
        //Initializing the useCases list's members
        useCases.add("sweeper-clean");
        useCases.add("salter-refill");
        useCases.add("icecracker-working");
        useCases.add("dragon-working");
        useCases.add("cars-collide");
        useCases.add("car-crashes-into-bus");
        useCases.add("car-enters-workplace");
        useCases.add("car-waits-at-workplace-and-leaves");
        useCases.add("car-arrives-home");
        useCases.add("car-leaves-home");
        useCases.add("ice-gets-on-field");
        useCases.add("car-slips-on-ice");
        useCases.add("car-is-blocked");
        useCases.add("snowfall-in-game");
        useCases.add("car-moves");
        useCases.add("snowplow-and-car-collision-to-respawn");
        useCases.add("bus-completing-round");
        useCases.add("swapping-heads");
        useCases.add("game-over");

        //state machine idle-->ls-->idle or idle-->test <someUseCase>-->idle
        while(!input.equals("exit")){
            StringTokenizer st = new StringTokenizer(input);

            //Handling ls command
            if(st.countTokens() == 1 && st.nextToken().equals("ls")){
                for(var currUseCase : useCases){
                    println(currUseCase);
                }
            //Handling test command
            } else if (st.countTokens() == 2 && st.nextToken().equals("test")){
                //Getting the desired useCase
                String testThisRaw = st.nextToken();
                //Executing the right behavior
                switch(testThisRaw){
                    case "sweeper-clean":
                        Test.testSweeper();
                        break;
                    case "salter-refill":
                        Test.testSalterRefill();
                        break;
                    case "icecracker-working":
                        Test.testIceCracker();
                        break;
                    case "dragon-working":
                        Test.testDragon();
                        break;
                    case "cars-collide":
                        Test.testCarsCollide();
                        break;
                    case "car-crashes-into-bus":
                        Test.testCarCrashesIntoBus();
                        break;
                    case "car-enters-workplace":
                        Test.testCarEntersWorkplace();
                        break;
                    case "car-waits-at-workplace-and-leaves":
                        Test.testCarWaitsAtWorkplaceAndLeaves();
                        break;
                    case "car-arrives-home":
                        Test.testCarArrivesHome();
                        break;
                    case "car-leaves-home":
                        Test.testCarLeavesHome();
                        break;
                    case "ice-gets-on-field":
                        Test.testIceGetsOnField();
                        break;
                    case "car-slips-on-ice":
                        Test.testCarSlipsOnIce();
                        break;
                    case "car-is-blocked":
                        Test.testCarIsBlocked();
                        break;
                    case "snowfall-in-game":
                        Test.testSnowfallInGame();
                        break;
                    case "car-moves":
                        Test.testCarMoves();
                        break;
                    case "snowplow-and-car-collision-to-respawn":
                        Test.testSnowPlowAndCarCollision();
                        break;
                    case "bus-completing-round":
                        Test.testBusCompletingRound();
                        break;
                    case "swapping-heads":
                        Test.testSwappingHeads();
                        break;
                    case "game-over":
                        Test.testGameOver();
                        break;
                    default:
                        println("Non-valid command, please try again!");
                        break;
                }
            } else {
                println("Non-valid command, please try again!");
            }

            //Retrieving next Command
            input = scanner.nextLine();
        }

    }
}
