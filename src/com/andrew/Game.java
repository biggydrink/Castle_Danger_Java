package com.andrew;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;

public class Game {

    public Database db;
    protected World theWorld; // Makes game object info easily accessible
    protected Player player;
    protected LinkedList<Player> playerList;
    protected HashMap<String,Equipment> equipmentMap;
    protected HashMap<String,Mob> mobMap;
    protected LinkedList<Room> roomList;

    // Timer in this program doesn't control the game, but helps keep it more "alive" - i.e. if you've killed off
    // all the monsters, they'll be repopulated every so often, you slowly restore health between battles, etc
    protected static Timer timer;
    protected static GameClock clockTick;


    public Game() {
        playerList = new LinkedList<Player>();
        theWorld = new World();
        equipmentMap = theWorld.createEquipment();
        mobMap = theWorld.createMobs();
        roomList = theWorld.createRooms();


        // Set up game timer
        timer = new Timer();
        clockTick = new GameClock();
        timer.scheduleAtFixedRate(clockTick,0,clockTick.tickLength); // Sets timer's schedule to clockInterval's milliseconds

        // Set up client / connection
        // CDClient myFirstClient = new CDClient();
        // myFirstClient.connectToServer();

    }


    /** Allows inputs and shows game UI while player is alive */
    public void runGame(Player player) {
        while (player.getHP() > 0) {
            GameInterface.inputCommand();
            GameInterface.prompt();
        }
    }

    /** New character creation. Creates the character and gives them some basic equipment. User chooses a password
     * and the character is added to the database */
    protected Player createPlayer(String name) {
        String password = GameInterface.createPassword();
        Player player = new Player(name);

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

        playerList.push(player);

        return player;
    }

    /** If player has logged in before, loads their character from the database */
    protected Player loadPlayer(String name, int id) {
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

    /** Quitting the game */
    protected void bye() {
        db.savePlayer(player);
        System.out.println("Bye!");
    }

}