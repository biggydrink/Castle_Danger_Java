package com.andrew;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Timer;

// Additions
// Allow 'g all' command
// Allow look command to be used in directions
    // Would have to add text to the Room object
// save all players every tickLimit
// Reformat World's room array
// Add some enums?
    // Equipment slots
        // Under Equipment class AND maybe Mob class?
    // NSEW in rooms
// When you try move in a direction you can't go, the current room setting is still displayed, which makes you think maybe you DID move



public class GameInterface {

    public static Scanner userScanner = new Scanner(System.in);

    public static Database db = new Database();

    protected static World theWorld = new World(); // Use this to make game object info easily accessible

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

    protected static HashMap<String,String> commandShortcuts = new HashMap<>();

    // Timer in this program doesn't control the game, but helps keep it more "alive" - i.e. if you've killed off
    // all the monsters, they'll be repopulated every so often, you slowly restore health between battles, etc
    protected static Timer timer;
    protected static GameClock clockTick;
    //protected static long clockInterval = 10000; // milliseconds, 1000 = 1 second

    public static void main(String[] args) {

        String loginName = "";
        int id;
        boolean quit = false;
        int userChoice;

        // Set up client / connection
        // CDClient myFirstClient = new CDClient();
        // myFirstClient.connectToServer();

        // Set up game timer
        timer = new Timer();
        clockTick = new GameClock();
        timer.scheduleAtFixedRate(clockTick,0,clockTick.tickLength); // Sets timer's schedule to clockInterval's milliseconds

        // Command input setup
        populateShortcuts();

        while (!quit) {
            // Logging in
            System.out.println("Welcome to Castle Danger! Enter your name:");
            loginName = userScanner.nextLine();
            // Check for ID - if new player, getID() returns 0. Otherwise ID used to load player stats and inv/equipment
            id = db.getID(loginName);
            if (id == 0) {
                player = createPlayer(loginName);
            } else {
                login(loginName,id);
            }

            // View location and prompt when game begins
            setting();
            prompt();

            // Main game loop
            runGame(player); // Loops until player's HP < 0

            System.out.println("You have died!");
            System.out.println("(1). Login");
            System.out.println("(2). Quit");

            userChoice = getPositiveIntInput(1,2);
            if (userChoice == 2) {
                quit = true;
            }

        }

        bye();

    }

    /** Allows inputs and shows game UI while player is alive */
    static public void runGame(Player player) {
        while (player.getHP() > 0) {
            inputCommand();
            prompt();
        }
    }

    /** GameInterface for logging in the user. If user selects a name that hasn't been logged yet, a new player is created,
     * otherwise the player's character is loaded from the database.
     * @param name - player's login name
     * @param id - player's characters database ID. Taken from the db in main()
     */
    static private void login(String name, int id) {
        System.out.println("Welcome back! Please enter your password:");
        String password = userScanner.nextLine();
        if (db.checkPassword(id,password)) {
            player = loadPlayer(name, id);
        } else {
            System.out.println("Incorrect password");
            System.exit(-1);
        }

    }

    /** New character creation. Creates the character and gives them some basic equipment. User chooses a password
     * and the character is added to the database */
    static protected Player createPlayer(String name) {
        String password = "";

        System.out.println("Looks like you're new - please create a password:");
        while (password.length() < 6) {
            // TODO add criteria for passwords (regular expressions or otherwise)
            System.out.println("Please enter at least 6 characters for your pw");
            // TODO add hashing for passwords
            password = userScanner.nextLine();
        }
        player = new Player(name);

        // Initializations
        player.setCurrentRoom(roomList.get(0));
        player.setPassword(password);
        player.equip(equipmentMap.get("polkadot shirt"));
        player.equip(equipmentMap.get("polkadot pants"));
        // Update db
        db.addNewPlayer(player); // add to players table
        player.setPlayerID(db.getID(player.name)); // query db to get ID, used for loading character
        db.addNewPlayerEQ(player); // add new equipment to eq table
        db.savePlayerInv(player); // new character has no inventory, but this enters the player id in the db

        // Welcome & help
        System.out.println("Welcome to Castle Danger, " + name + " !");
        help();

        playerList.push(player);

        return player;
    }

    /** If player has logged in before, loads their character from the database */
    static private Player loadPlayer(String name, int id) {
        Player loadedPlayer = new Player(name);
        db.loadPlayer(id); // not used for anything yet
        loadedPlayer.setPlayerID(id);


        // Load eq
        String[] eqArray = db.loadPlayerEQ(id);
        for (int i = 0; i < eqArray.length; ++i) {
            if (!eqArray[i].equals("")) {
                loadedPlayer.equip(equipmentMap.get(eqArray[i].toLowerCase()));
            }
        }

        // Load inventory
        LinkedList<String> inventoryLoadList = db.loadPlayerInv(id);
        for (String itemName : inventoryLoadList) {
            loadedPlayer.gainItem(itemName);
        }

        loadedPlayer.setCurrentRoom(roomList.get(0));
        playerList.push(loadedPlayer);

        return loadedPlayer;
    }

    /** Displays game commands */
    static public void help() {
        System.out.println("Here are some basic commands to use in the game:");
        System.out.println(ANSI_BLACK + "Command" + padSpace(30 - "Command".length()) + "Use" + ANSI_RESET);
        System.out.println("l / look" + padSpace(30 - "l / look".length()) + "Look at your surroundings");
        System.out.println("l / look [something]" + padSpace(30 - "l / look [something]".length()) + "Look at something or someone");
        System.out.println("n / s / e / w" + padSpace(30-"n / s / e / w".length()) + "Go north / go south / go east / go west");
        System.out.println("i / inv" + padSpace(30-"i / inv".length()) + "See what you have in your inventory");
        System.out.println("eq" + padSpace(30-"eq".length()) + "See what you have equipped");
        System.out.println("eq / uneq [item]" + padSpace(30-"eq / uneq [item]".length()) + "Equip or unequip an item");
        System.out.println("st" + padSpace(30-"st".length()) + "Check your stats");
        System.out.println("g [item]" + padSpace(30-"g [item]".length()) + "Get an item off the floor");
        System.out.println("h monsters" + padSpace(30-"h monsters".length()) + "See the names of the monsters you can attack");
        System.out.println("h items" + padSpace(30-"h items".length()) + "See the names of the items you can pick up");
        System.out.println("h" + padSpace(30-"h".length()) + "Read this help file again");
        System.out.println("quit" + padSpace(30-"quit".length()) + "Quit the game and save your character");
        System.out.println("Enjoy!");
    }
    /** Used for making the help() method display text nicely */
    static private String padSpace(int numSpaces) {
        String spaces = "";
        for (int i = 0; i < numSpaces; ++i) {
            spaces += " ";
        }
        return spaces;
    }

    /** Display main game prompt with current room name, health, and exits */
    static public void prompt() {
        // <[room name]: [hp]/[maxhp]HP : [exits]>
        String prompt;

        prompt = ANSI_PURPLE + "<" + player.currentRoom.name + " : ";
        prompt += getHPColor() + player.getHP() + "/" + player.getMaxHP() + "HP" + ANSI_RESET + " : ";
        prompt += ANSI_CYAN + player.currentRoom.getExits() + ANSI_RESET + ">";

        System.out.print(prompt);
    }


    /** Wait for input from player, parse the text, and run the appropriate command */
    static protected void inputCommand() {
        /* Implementation of inputCommand() using commandMap was inspired by the top response in this stack exchange question:
        http://stackoverflow.com/questions/4480334/how-to-call-a-method-stored-in-a-hashmap-java
        */

        String[] cmdWithArgs = parseInput();
        cmd = cmdWithArgs[0].toLowerCase();
        args = cmdWithArgs[1].toLowerCase();

        if (commandShortcuts.containsKey(cmd)) {
            cmd = commandShortcuts.get(cmd);
        }

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
    static private String[] parseInput() {
        String input;
        String[] listInput;
        String inputCmd;
        String inputArgs = "";
        //Scanner scanner = new Scanner(System.in);

        // Get input from user
        System.out.print(" ");
        input = userScanner.nextLine();

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


    /** Returns appropriate ANSI color for player's current HP. Used in prompt() and stats() */
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

    /** Reads user input, only allows int options between lower and upper bound */
    public static int getPositiveIntInput(int lowerBound, int upperBound) {
        String userInput;
        int userNumber = -1;

        while ((userNumber < 0) || (userNumber < lowerBound || userNumber > upperBound)) {
            try {
                userInput = userScanner.nextLine();
                userNumber = Integer.parseInt(userInput);
                if ((userNumber < 0) || (userNumber < lowerBound || userNumber > upperBound)) {
                    System.out.println("Please enter a number between " + lowerBound + " and " + upperBound);
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a number");
            }
        }

        return userNumber;
    }

    /** Player command to quit the game */
    static private void quit() { //foox
        System.out.println("Are you sure? Type quit again, or press enter to continue");
        String response = userScanner.nextLine();
        if (response.equalsIgnoreCase("quit")) {
            bye();
        }
    }

    /** Quitting the game */
    static private void bye() {
        db.savePlayer(player);
        System.out.println("Bye!");
        System.exit(0);
    }

    /** Populate dictionary with shorthand commands, keyed to actual command methods */
    static private void populateShortcuts() {
        commandShortcuts.put("l","look");
        commandShortcuts.put("n","north");
        commandShortcuts.put("s","south");
        commandShortcuts.put("e","east");
        commandShortcuts.put("w","west");
        commandShortcuts.put("g","get");
        commandShortcuts.put("eq","equipment");
        commandShortcuts.put("equip","equipment");
        commandShortcuts.put("uneq","unequip");
        commandShortcuts.put("h","help");
        commandShortcuts.put("i","inventory");
        commandShortcuts.put("inv","inventory");
        commandShortcuts.put("stat","status");
    }

    /** Holds commandMap, which takes commands from inputCommand() and returns appropriate methods
     *  Implementation inspired by top comment on the below stackoverflow question:
     *  http://stackoverflow.com/questions/4480334/how-to-call-a-method-stored-in-a-hashmap-java*/
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

            commandMap.put("help",new Command() {
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

            commandMap.put("quit", new Command() {
                public void runCommand(String args) {
                    quit();
                }
            });

            commandMap.put("drop",new Command() {
                public void runCommand(String args) {
                    if (args.equals("")) {
                        System.out.println("Drop what?");
                    } else if (args.equalsIgnoreCase("all")) {

                        int limit = player.mobInventoryList.size();
                        for (int i = 0; i < limit; ++i) {
                            Item itemToDrop = player.mobInventoryList.get(0);
                            if (player.drop(itemToDrop.getName())) {
                                System.out.println("You drop your " + itemToDrop.getName());
                            }
                        }

                    } else if (player.drop(args)) {
                        System.out.println("You drop your " + args);
                    } else {
                        System.out.println("You don't have that");
                    }
                }
            });

            commandMap.put("inventory", new Command() {
                public void runCommand(String args) {
                    viewInventory(player);
                }
            });

            commandMap.put("equipment", new Command() {
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

            commandMap.put("unequip", new Command() {
                public void runCommand(String args) {
                    if (player.unequip(args)) {
                        System.out.println("You unequip your " + args);
                    } else {
                        System.out.println("Unequip what?");
                    }
                }
            });

            commandMap.put("get", new Command() {
                public void runCommand(String args) {
                    String notHere = "";
                    if (args.equalsIgnoreCase("all")) {
                        int limit = player.currentRoom.itemList.size();
                        for (int i = 0; i < limit; ++i) {
                            Item item = player.currentRoom.itemList.get(0);
                            if (player.gainItemInRoom(item.getName())) {
                                System.out.println("You get a " + item.getName());
                            }
                        }
                    } else if (player.gainItemInRoom(args)) {
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

            commandMap.put("status", new Command() {
                public void runCommand(String args) { stats(); }
            });

            commandMap.put("north", new Command() {
                public void runCommand(String args) {
                    if (player.goNorth()) {
                        setting();
                    } else {
                        System.out.println("You can't go that way.");
                    }
                }
            });
            commandMap.put("south", new Command() {
                public void runCommand(String args) {
                    if (player.goSouth()) {
                        setting();
                    } else {
                        System.out.println("You can't go that way.");
                    }
                }
            });
            commandMap.put("east", new Command() {
                public void runCommand(String args) {
                    if (player.goEast()) {
                        setting();
                    } else {
                        System.out.println("You can't go that way.");
                    }
                }
            });
            commandMap.put("west", new Command() {
                public void runCommand(String args) {
                    if (player.goWest()) {
                        setting();
                    } else {
                        System.out.println("You can't go that way.");
                    }
                }
            });
        }
    }

    /** Display currentRoom description */
    static public void setting() {
        System.out.println(ANSI_BLACK + player.currentRoom.description + ANSI_RESET);

        if (player.currentRoom.mobList.size() > 1) { // current player is always in room, so list is always at least 1
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

    /** Show player's inventory */
    static private void viewInventory(Player player) {
        System.out.println("You are carrying: ");
        if (player.mobInventoryMap.isEmpty()) {
            System.out.println("...nothing!");
        } else {
            System.out.println(player.getInventoryString());

        }
    }

    /** Show description of item/player/monster */
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
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

    }

    /** Displays the names of items in the current room so that the player can easily target them */
    static private void helpItems() {
        System.out.println("The names of the items you can get in this room are: ");
        for (String item : player.currentRoom.roomItemMap.keySet()) {
            System.out.println(ANSI_YELLOW + item + ANSI_RESET);
        }
    }
    /** Displays the names of monsters/players in the current room so that the player can easily target them */
    static private void helpMonsters() {
        System.out.println("The names of the monsters you can attack in this room are: ");
        if (player.currentRoom.roomMobMap.size() == 1) {
            System.out.println("There are no monsters in this room.");
        }
        for (String monsterName : player.currentRoom.roomMobMap.keySet()) {
            if (!monsterName.equalsIgnoreCase(player.getName())) {
                System.out.println(ANSI_RED + monsterName + ANSI_RESET);
            }
        }
    }

}