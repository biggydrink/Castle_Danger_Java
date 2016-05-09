package com.andrew;

import java.util.HashMap;
import java.util.Scanner;

interface Command {
    void runCommand();
}

public class UserInterface {


    HashMap<String, Command> commandMap = new HashMap<String,Command>();
    Player player;

    public UserInterface(Player player) {
        this.player = player;
        createCommandMap();
    }

    public void createCommandMap() {


        commandMap.put("LOOK", new Command() {
            public void runCommand() { setting(); }
        });

        commandMap.put("L", new Command() {
            public void runCommand() { setting(); }
        });

        commandMap.put("SC", new Command() {
            public void runCommand() { player.stats(); }
        });

        commandMap.put("N", new Command() {
            public void runCommand() { player.goNorth(); }
        });
        commandMap.put("S", new Command() {
            public void runCommand() { player.goSouth(); }
        });
        commandMap.put("E", new Command() {
            public void runCommand() { player.goEast(); }
        });
        commandMap.put("W", new Command() {
            public void runCommand() { player.goWest(); }
        });

    }

    /** Wait for input from player */
    public void inputCommand() {
        /* Implementation of inputCommand() using commandMap was inspired by the top response in this stack exchange question:
        http://stackoverflow.com/questions/4480334/how-to-call-a-method-stored-in-a-hashmap-java
        */

        String[] cmdWithArgs = parseInput();
        String cmd = cmdWithArgs[0].toUpperCase();
        String args = cmdWithArgs[1].toUpperCase();

        // TODO add a help command
        if (cmd.trim().equals("")) {
            prompt();
        } else {
            try {
                commandMap.get(cmd).runCommand();
            } catch (NullPointerException npe) {
                System.out.println("Wait, what?");
            }
        }



    }

    /** Takes input from the user and parses it into the first word (the command) and the rest (the arguments) */
    public String[] parseInput() {
        String input;
        String[] listInput;
        String cmd;
        String args = "";
        Scanner scanner = new Scanner(System.in);

        // Get input from user
        System.out.print(" ");
        input = scanner.nextLine();

        // Parse input to command + arg
        listInput = input.split(" ");
        cmd = listInput[0];
        if (listInput.length > 1) {
            for (int i = 1; i < listInput.length; ++i) {
                if (i > 1) args += " ";
                args += listInput[i];
            }
        }

        String cmdWithArgs[] = {cmd,args};
        return cmdWithArgs;


    }

    /** Display main game prompt */
    public void prompt() {
        // <[room name]: [hp]/[maxhp]HP : [exits]>
        System.out.print("<" + player.currentRoom.name + " : " + player.getHP() + "/" + player.getMaxHP() + "HP : " + player.currentRoom.getExits() + ">");
    }

    /** Show description of item/player */
    public void look(String name) {
        System.out.println("UI look(args) called");
    }

    /** Display currentRoom description */
    public void setting() {
        System.out.println(player.currentRoom.description);
    }


}
