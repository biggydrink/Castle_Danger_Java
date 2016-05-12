package com.andrew;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Timer;

public class Interface {

    //TODO add comments

    public static Scanner userScanner = new Scanner(System.in);

    protected static World theWorld = new World();

    protected static LinkedList<Player> playerList = new LinkedList<>();
    protected static HashMap<String,Equipment> equipmentMap = theWorld.createEquipment();
    protected static HashMap<String,Mob> mobMap = theWorld.createMobs();
    protected static LinkedList<Room> roomList = theWorld.createRooms();

    protected static Player player;
    protected static UserInterface UI = new UserInterface();
    protected static String cmd;
    protected static String args;

    // ANSI values taken from   http://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    protected static Timer timer;
    protected static GameClock clockTick;
    protected static long clockInterval = 30000; // milliseconds, 1000 = 1 second



    public static void main(String[] args) {

        // Database db = new Database();
        // CDClient myFirstClient = new CDClient();
        // myFirstClient.connectToServer();

        // Create player,
        player = createPlayer();
        player.setCurrentRoom(roomList.get(0));
        //player.equip(equipmentMap.get("righteous sword"));
        player.equip(equipmentMap.get("polkadot shirt"));
        player.equip(equipmentMap.get("polkadot pants"));


        // Commands
        setting();
        prompt();

        timer = new Timer();
        clockTick = new GameClock();
        timer.scheduleAtFixedRate(clockTick,0,clockInterval); // Sets timer's schedule to clockInterval's milliseconds

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
        System.out.println("Welcome to Castle Danger, " + newName + " !");
        help();

        playerList.push(newPlayerChar);

        return newPlayerChar;
    }

    static public void help() {
        System.out.println("Here are some basic commands to use in the game:");
        System.out.println(ANSI_BLACK + "Command" + padSpace(30 - "Command".length()) + "Use" + ANSI_RESET);
        System.out.println("l / look" + padSpace(30 - "l / look".length()) + "Look at your surroundings");
        System.out.println("l / look [something]" + padSpace(30 - "l / look [something]".length()) + "Look at something or someone");
        System.out.println("n / s / e / w" + padSpace(30-"n / s / e / w".length()) + "Go north, south, east, or west");
        System.out.println("i / inv" + padSpace(30-"i / inv".length()) + "See what you have in your inventory");
        System.out.println("eq" + padSpace(30-"eq".length()) + "See what you have equipped");
        System.out.println("eq / uneq [item]" + padSpace(30-"eq / uneq [item]".length()) + "Equip or unequip an item");
        System.out.println("st" + padSpace(30-"st".length()) + "Check your stats");
        System.out.println("g [item]" + padSpace(30-"g [item]".length()) + "Get an item off the floor");
        System.out.println("h monsters" + padSpace(30-"h monsters".length()) + "See the technical names of the monsters you can attack");
        System.out.println("h items" + padSpace(30-"h items".length()) + "See the technical names of the items you can pick up");
        System.out.println("h" + padSpace(30-"h".length()) + "Read this help file again");
        System.out.println("Enjoy!");
    }

    static public void helpItems() {
        System.out.println("The names of the items you can get in this room are: ");
        for (String item : player.currentRoom.roomItemMap.keySet()) {
            System.out.println(ANSI_YELLOW + item + ANSI_RESET);
        }
    }

    static public void helpMonsters() {
        System.out.println("The names of the monsters you can attack in this room are: ");
        for (String monsterName : player.currentRoom.roomMobMap.keySet()) {
            if (!monsterName.equalsIgnoreCase(player.getName())) {
                System.out.println(ANSI_RED + monsterName + ANSI_RESET);
            }
        }
    }

    static public String padSpace(int numSpaces) {
        String spaces = "";
        for (int i = 0; i < numSpaces; ++i) {
            spaces += " ";
        }

        return spaces;
    }

    /** Wait for input from player */
    static public void inputCommand() {
        /* Implementation of inputCommand() using commandMap was inspired by the top response in this stack exchange question:
        http://stackoverflow.com/questions/4480334/how-to-call-a-method-stored-in-a-hashmap-java
        */

        String[] cmdWithArgs = parseInput();
        cmd = cmdWithArgs[0].toLowerCase();
        args = cmdWithArgs[1].toLowerCase();

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

            if (player.currentRoom.roomMobMap.containsKey(name)) {
                Mob viewingMob = player.currentRoom.roomMobMap.get(name);
                System.out.println(viewingMob.getDescription());
                System.out.println(viewingMob.getInventoryString());
                System.out.println(viewingMob.getEquipmentString());

            } else if (player.currentRoom.roomItemMap.containsKey(name)) {
                Item viewingItem = player.currentRoom.roomItemMap.get(name);
                System.out.println(viewingItem.getDescription());
            } else if (player.isInInventory(name)) {
                Item viewingItem = player.mobInventoryMap.get(name);
                System.out.println(viewingItem.getDescription());
            } else if (player.isEquipped(name)) {
                //Item viewingItem = player.mobEquipmentMap.get(name);
                String eqLoc = player.getEquippedItemLocation(name);
                Item viewingItem = player.mobEquipmentMap.get(eqLoc);
                System.out.println(viewingItem.getDescription());
            } else {
                System.out.println("You don't see that here");
            }

            //TODO see if this exception isn't necessary
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

    }

    /** Display currentRoom description */
    static public void setting() {
        System.out.println(ANSI_BLACK + player.currentRoom.description + ANSI_RESET);

        if (!player.currentRoom.mobList.isEmpty()) {
            System.out.print(ANSI_RED + player.currentRoom.showMobs(player) + ANSI_RESET);
        }
        if (!player.currentRoom.itemList.isEmpty()) {
            System.out.print(ANSI_YELLOW + player.currentRoom.showItems() + ANSI_RESET);
        }
    }

    /** Display player's stats */
    static public void stats() {
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

    static private void viewInventory(Player player) {
        System.out.println("You are carrying: ");
        if (player.mobInventoryMap.isEmpty()) {
            System.out.println("...nothing!");
        } else {
            System.out.println(player.getInventoryString());

        }
    }

    static private void viewEquipment(Player player) {
        System.out.println("You are wearing: ");

    }



    private static class UserInterface {

        interface Command {
            void runCommand(String argument);
        }

        HashMap<String, Command> commandMap = new HashMap<String,Command>();

        public UserInterface() {
            createCommandMap();

        }

        public void createCommandMap() {

            commandMap.put("look", new Command() {
                public void runCommand(String args) {
                    if (args.equals("")) {
                        setting();
                    } else {
                        look((String)args);
                    }
                }
            });

            commandMap.put("l", new Command() {
                public void runCommand(String args) {
                    if (args.equals("")) {
                        setting();
                    } else {
                        look((String)args);
                    }
                }
            });

            commandMap.put("h",new Command() {
                public void runCommand(String args) {
                    if (args.equals("items")) {
                        helpItems();
                    } else if (args.equals("monsters")) {
                        helpMonsters();
                    } else {
                        help();
                    }

                }
            });

            commandMap.put("drop",new Command() {
                public void runCommand(String args) {
                    if (player.drop(args)) {
                        System.out.println("You drop your " + args);
                    } else {
                        System.out.println("Drop what?");
                    }
                }
            });

            commandMap.put("i", new Command() {
                public void runCommand(String args) {
                    viewInventory(player);
                }
            });
            commandMap.put("inv", new Command() {
                public void runCommand(String args) {
                    viewInventory(player);
                }
            });

            commandMap.put("eq", new Command() {
                public void runCommand(String args) {
                    if (args.equals("")) {
                        System.out.println("You are wearing: ");
                        System.out.println(player.getEquipmentString());
                    } else {
                        if (player.equip(args)) {
                            System.out.println("You equip your " + args);
                        } else {
                            System.out.println("Equip what?");
                        }
                    }

                }
            });

            commandMap.put("uneq", new Command() {
                public void runCommand(String args) {
                    if (player.unequip(args)) {
                        System.out.println("You unequip your " + args);
                    } else {
                        System.out.println("Unequip what?");
                    }
                }
            });

            commandMap.put("g", new Command() {
                public void runCommand(String args) {

                    String notHere = "";
                    if (player.gainItem(args)) {
                        System.out.println("You get a " + args);
                    } else {
                        if (!args.equals("")) {
                            notHere = " That isn't here";
                        }
                        System.out.println("Get what?" + notHere);

                    }
                }
            });

            commandMap.put("attack", new Command() {
                public void runCommand(String args) {

                    if (args.equals("")) {
                        if (player.currentRoom.roomMobMap.size() == 1) {
                            System.out.println("You're the only one here!");
                        } else {
                            System.out.println("Attack who?");
                        }
                    } else if (args.toLowerCase().equals(player.getName().toLowerCase())) {
                        System.out.println("You can't attack yourself!");
                    } else if (!player.currentRoom.roomMobMap.containsKey(args)) {
                        System.out.println("Attack who?");
                    } else {
                        player.initiateAttack(player.currentRoom.roomMobMap.get(args));
                    }
                }
            });

            commandMap.put("st", new Command() {
                public void runCommand(String args) { stats(); }
            });

            commandMap.put("n", new Command() {
                public void runCommand(String args) { player.goNorth(); setting(); }
            });
            commandMap.put("s", new Command() {
                public void runCommand(String args) { player.goSouth(); setting(); }
            });
            commandMap.put("e", new Command() {
                public void runCommand(String args) { player.goEast(); setting(); }
            });
            commandMap.put("w", new Command() {
                public void runCommand(String args) { player.goWest(); setting(); }
            });

        }

    }
}
