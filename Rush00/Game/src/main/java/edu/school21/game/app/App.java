package edu.school21.game.app;

import java.util.Scanner;

import com.beust.jcommander.JCommander;

public class App {
    public static void main(String[] args) {
        boolean exit = false;
        String message = "";
        Scanner input = new Scanner(System.in);
        String inputString;
        ArgumentReader parsedArgs = new ArgumentReader();
        JCommander.newBuilder().addObject(parsedArgs).build().parse(args);
        try {
            parsedArgs.checkSpace();
        } catch (IllegalParametersException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        Map map = new Map(parsedArgs.getSize(), parsedArgs.getEnemiesCount(), parsedArgs.getWallsCount(),
                parsedArgs.getProfile());
        while (!exit) {
            map.print(message);
            message = "";
            System.out.print("-> ");
            inputString = input.nextLine();
            switch (inputString) {
                case "s":
                    message = map.moveDownward();
                    break;
                case "w":
                    message = map.moveForward();
                    break;
                case "a":
                    message = map.moveLeft();
                    break;
                case "d":
                    message = map.moveRight();
                    break;
                case "8":
                    exit = true;
                    break;
                case "9":
                    map.gameOver();
                    break;
                default:
                    message = "Invalid input: " + inputString;
                    break;
            }
        }
        input.close();
    }
}
