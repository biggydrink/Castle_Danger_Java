package com.andrew;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Interface {

    public static Scanner userScanner = new Scanner(System.in);

    protected static World theWorld = new World();
    protected static LinkedList<Player> playerList = new LinkedList<Player>();
    protected static LinkedList<Room> roomList = theWorld.createRooms();
    protected static HashMap<String,Weapon> weaponMap = theWorld.createWeapons();
    protected static HashMap<String,Armor> armorMap = theWorld.createArmor();
    protected static HashMap<String,Mob> mobMap = theWorld.createMobs();
    //protected static Room currentRoom;
    protected static Player player;
    protected static UserInterface UI = new UserInterface();
    protected static String cmd;
    protected static String args;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void main(String[] args) {

        // Database db = new Database();
        // CDClient myFirstClient = new CDClient();
        // myFirstClient.connectToServer();

        // Create player,
        player = createPlayer();
        player.setCurrentRoom(roomList.get(0));

        // Commands
        setting();
        prompt();
        while (player.getHP() > 0) {
            inputCommand();
            prompt();
        }

    }


    /** User selects a name for their character, character created and returned */
    static protected Player createPlayer() {
        String newName;
        System.out.println("What is your name, Mr/Ms Hero?");
        newName = userScanner.nextLine();
        Player newPlayerChar = new Player(newName);
        System.out.println("Welcome to Castle Danger, " + newName);

        playerList.push(newPlayerChar);

        return newPlayerChar;
    }

    /** Wait for input from player */
    static public void inputCommand() {
        /* Implementation of inputCommand() using commandMap was inspired by the top response in this stack exchange question:
        http://stackoverflow.com/questions/4480334/how-to-call-a-method-stored-in-a-hashmap-java
        */

        String[] cmdWithArgs = parseInput();
        cmd = cmdWithArgs[0].toLowerCase();
        args = cmdWithArgs[1].toLowerCase();

        // TODO add a help command
        if (cmd.trim().equals("")) {
            System.out.println("");
        } else {
            try {
                System.out.println("");
                UI.commandMap.get(cmd).runCommand(args);
            } catch (NullPointerException npe) {
                System.out.println("Wait, what?");
            }
        }
    }

    /** Takes input from the user and parses it into the first word (the command) and the rest (the arguments) */
    static public String[] parseInput() {
        String input;
        String[] listInput;
        String inputCmd;
        String inputArgs = "";
        Scanner scanner = new Scanner(System.in);

        // Get input from user
        System.out.print(" ");
        input = scanner.nextLine();

        // Parse input to command + arg
        listInput = input.split(" ");
        inputCmd = listInput[0];
        if (listInput.length > 1) {
            for (int i = 1; i < listInput.length; ++i) {
                if (i > 1) inputArgs += " ";
                inputArgs += listInput[i];
            }
        }

        String cmdWithArgs[] = {inputCmd,inputArgs};
        return cmdWithArgs;
    }

    /** Display main game prompt */
    static public void prompt() {
        // <[room name]: [hp]/[maxhp]HP : [exits]>
        String prompt;
        double percentHP;
        percentHP = player.getMaxHP() / player.getHP();


        prompt = "<" + player.currentRoom.name + " : ";
        prompt += getHPColor() + player.getHP() + "/" + player.getMaxHP() + "HP" + ANSI_RESET + " : ";
        prompt += ANSI_CYAN + player.currentRoom.getExits() + ANSI_RESET + ">";

        System.out.print(prompt);
        //System.out.print("<" + player.currentRoom.name + " : " + player.getHP() + "/" + player.getMaxHP() + "HP : " + player.currentRoom.getExits() + ">");
    }

    /** Show description of item/player */
    static public void look(String name) {

        try {

            if (mobMap.containsKey(name)) {
                System.out.println(mobMap.get(name).getSetting());
            } else if (armorMap.containsKey(name)) {
                System.out.println(armorMap.get(name).getSetting());
            } else if (weaponMap.containsKey(name)) {
                System.out.println(weaponMap.get(name).getSetting());
            } else {
                System.out.println("You don't see that here");
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

    }

    /** Display currentRoom description */
    static public void setting() {
        System.out.println(ANSI_BLACK + player.currentRoom.description + ANSI_RESET);

        if (!player.currentRoom.mobList.isEmpty()) {
            System.out.print(ANSI_RED + player.currentRoom.showMobs() + ANSI_RESET);
        }
        if (!player.currentRoom.itemList.isEmpty()) {
            System.out.print(ANSI_YELLOW + player.currentRoom.showItems() + ANSI_RESET);
        }
    }

    /** Display player's stats */
    static public void stats() {
        System.out.println("Player stats() called");
        System.out.println(ANSI_BLACK + player.getDescription() + ANSI_RESET);
        System.out.println("Name: " + ANSI_BLACK + player.getName() + ANSI_RESET);
        System.out.println("HP: " + getHPColor() + player.getHP() + "/" + player.getMaxHP() + ANSI_RESET);
        System.out.println("Attack: " + ANSI_BLACK + player.getAttack() + ANSI_RESET);
        System.out.println("Defense: " + ANSI_BLACK +  player.getDefense() + ANSI_RESET);
    }

    static private String getHPColor() {
        double percentHP = (double)player.getHP() / (double)player.getMaxHP();

        if (percentHP < 0.5) {
            return ANSI_RED;
        } else if (percentHP >= 0.5 && percentHP < .8) {
            return ANSI_YELLOW;
        } else if (percentHP >= .8) {
            return ANSI_GREEN;
        }
        return ANSI_RESET;

    }



    private static class UserInterface {

        interface Command {
            void runCommand(Object argument);
        }

        HashMap<String, Command> commandMap = new HashMap<String,Command>();

        public UserInterface() {
            createCommandMap();

        }

        public void createCommandMap() {

            commandMap.put("look", new Command() {
                public void runCommand(Object args) {
                    if (args.equals("")) {
                        setting();
                    } else {
                        look((String)args);
                    }
                }
            });

            commandMap.put("l", new Command() {
                public void runCommand(Object args) {
                    if (args.equals("")) {
                        setting();
                    } else {
                        look((String)args);
                    }
                }
            });

            commandMap.put("attack", new Command() {
                public void runCommand(Object args) {
                    //if (args.equals("")) {
                    //    System.out.println("Attack who?");
                    //} else if (player.currentRoom.mobList.contains(args)) {
                    player.initiateAttack(player.currentRoom.mobList.get(0));
                    //} else {
                    //    System.out.println("You don't see that here");
                    //}
                }
            });

            commandMap.put("sc", new Command() {
                public void runCommand(Object args) { stats(); }
            });

            commandMap.put("n", new Command() {
                public void runCommand(Object args) { player.goNorth(); setting(); }
            });
            commandMap.put("s", new Command() {
                public void runCommand(Object args) { player.goSouth(); setting(); }
            });
            commandMap.put("e", new Command() {
                public void runCommand(Object args) { player.goEast(); setting(); }
            });
            commandMap.put("w", new Command() {
                public void runCommand(Object args) { player.goWest(); setting(); }
            });

        }

    }
}
