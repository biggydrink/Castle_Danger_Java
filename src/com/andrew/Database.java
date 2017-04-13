package com.andrew;

import java.sql.*;
import java.util.LinkedList;

/** All database info
 *  Includes methods for creating and updating tables */
public class Database {

    private static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/"; //TODO make this not local host
    private static String DB_NAME = "castle_danger";
    private static String USER = "root";
    private static String PASS = "sharks8";

    private static Statement statement = null;
    private static Connection conn = null;
    private static ResultSet rs = null;

    private static String PLAYER_TABLE_NAME = "players";
    private static String INVENTORY_TABLE_NAME = "inventory";
    private static String EQUIPPED_TABLE_NAME = "equipment";

    public Database() {
        setup();
    }

    private boolean setup() {

        try {
            // load driver
            try {
                String Driver = "com.mysql.jdbc.Driver";
                Class.forName(Driver);
            } catch (ClassNotFoundException cnfe){
                System.out.println("No database drivers found");
                System.out.println(cnfe);
                return false;
            }

            conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME,USER,PASS);

            // Type Scroll Sensitive since there will be multiple users connected and constant changes to the tables
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // Create DB if doesn't exist
            //String createDBQuery = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + ";";
            //statement.executeUpdate(createDBQuery);

            // Create tables if they don't already exist
            if (!tableExists(PLAYER_TABLE_NAME)) {
                createPlayerTable();
            }

            if (!tableExists(INVENTORY_TABLE_NAME)) {
                createInventoryTable();
            }

            if (!tableExists(EQUIPPED_TABLE_NAME)) {
                createEquippedTable();
            }

            return true;
        } catch (SQLException sqle) {
            System.out.println("Error setting up conn and statement");
            System.out.println(sqle);
            sqle.printStackTrace();
            return false;
        }
    }

    /** Checks if selected table already exists */
    private boolean tableExists(String tableName) {

        try {

            if (rs != null) rs.close();

            String checkTableExistsQuery = "SHOW TABLES LIKE '" + tableName + "'";
            rs = statement.executeQuery(checkTableExistsQuery);
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException sqle) {
            System.out.println("Error checking if " + tableName + " exists");
            System.out.println(sqle);
            sqle.printStackTrace();
            return false;
        }
    }

    /** Creates players table */
    private void createPlayerTable() {
        String createTableQuery = "CREATE TABLE " + PLAYER_TABLE_NAME + " (" +
                "playerID INT NOT NULL AUTO_INCREMENT PRIMARY KEY" +
                ", name VARCHAR(25) UNIQUE NOT NULL" +
                ", password VARCHAR(30) NOT NULL" +
                ", maxhp INT NOT NULL" +
                ", hp INT NOT NULL" +
                ", attack INT NOT NULL" +
                ", defense INT NOT NULL" +
                ", description VARCHAR(500) NOT NULL" +
                ", setting VARCHAR(500) NOT NULL" +
                ", currentRoom INT NOT NULL" +
            ");";

        try {
            statement.executeUpdate(createTableQuery);
        } catch (SQLException sqle) {
            System.out.println("Could not create players table");
            System.out.println(sqle);
            sqle.printStackTrace();
        }
    }

    /** Creates inventory table */
    private void createInventoryTable() {

        String createTableQuery = "CREATE TABLE " + INVENTORY_TABLE_NAME + " (" +
                "inv_key INT AUTO_INCREMENT PRIMARY KEY" +
                ", playerID INT NOT NULL" +
                ", name VARCHAR(25) NOT NULL" +
                ", equipmentPlacement VARCHAR(20)" +
            ");";

        try {
            statement.executeUpdate(createTableQuery);
        } catch (SQLException sqle) {
            System.out.println("Could not create inventory table");
            System.out.println(sqle);
            sqle.printStackTrace();
        }
    }

    /** Creates equipment table */
    private void createEquippedTable() {
        String createTableQuery = "CREATE TABLE " + EQUIPPED_TABLE_NAME + " (" +
                "eq_key INT AUTO_INCREMENT PRIMARY KEY" +
                ", playerID INT NOT NULL" +
                ", eqWeapon VARCHAR(50) NOT NULL" +
                ", eqBody VARCHAR(50) NOT NULL" +
                ", eqLegs VARCHAR(50) NOT NULL" +
                ");";

        try {
            statement.executeUpdate(createTableQuery);
        } catch (SQLException sqle) {
            System.out.println("Could not create equipment table");
            System.out.println(sqle);
            sqle.printStackTrace();
        }
    }

    /** Adds new player to players table */
    public void addNewPlayer(Player player) {
        String name = player.getName();
        String password = player.getPassword();
        int maxHP = player.getMaxHP();
        int hp = player.getHP();
        int attack = player.getAttack();
        int defense = player.getDefense();
        String description = player.getDescription();
        String setting = player.getSetting();
        int room = GameInterface.roomList.indexOf(player.currentRoom);

        String updateQuery = "INSERT INTO " + PLAYER_TABLE_NAME + "(name,password,maxhp,hp,attack,defense,description,setting,currentroom)" +
                " VALUES ('" +
                name +
                "','" + password +
                "'," + maxHP +
                "," + hp +
                "," + attack +
                "," + defense +
                ",'" + description +
                "','" + setting +
                "'," + room +
                ");";


        try {
            statement.execute(updateQuery);
            //statement.execute(addPlayerInvQuery);
        } catch (SQLException sqle) {
            System.out.println("SQL Exception: " + sqle);
        }
    }

    /** Adds new player's equipment (even if nothing) equipment table */
    public void addNewPlayerEQ(Player player) {
        String eqWeapon = "";
        String eqBody = "";
        String eqLegs = "";


        if (player.mobEquipmentMap.get("Weapon") != null) {
            eqWeapon = player.mobEquipmentMap.get("Weapon").getName();
        }
        if (player.mobEquipmentMap.get("Body") != null) {
            eqBody = player.mobEquipmentMap.get("Body").getName();
        }
        if (player.mobEquipmentMap.get("Legs") != null) {
            eqLegs = player.mobEquipmentMap.get("Legs").getName();
        }

        String addPlayerEQQuery = "INSERT INTO " + EQUIPPED_TABLE_NAME + "(playerid, eqWeapon, eqBody, eqLegs)" +
                " VALUES (" +
                getID(player.name) +
                ",'" + eqWeapon +
                "','" + eqBody +
                "','" + eqLegs +
                "');";

        try {
            statement.execute(addPlayerEQQuery);
        } catch (SQLException sqle) {
            System.out.println("SQL Exception: " + sqle);
        }
    }

    /** Adds new player's inventory (even if nothing) to the inventory table */
    public void savePlayerInv(Player player) {

        int id = player.getID();

        deletePlayerInv(player);

        if (player.mobInventoryMap.size() == 0) {
            String addPlayerInvQuery = "INSERT INTO " + INVENTORY_TABLE_NAME + "(playerID, name, equipmentPlacement)" +
                    " VALUES (" +
                    id +
                    ", ''" +
                    ", ''" +
                    ");";

            try {
                statement.execute(addPlayerInvQuery);
            } catch (SQLException sqle) {
                System.out.println("SQL Exception: " + sqle);
            }

        } else {
            for (String itemName : player.mobInventoryMap.keySet()) {

                String eqPlacement = "";

                try {
                    Equipment invItem = (Equipment)player.mobInventoryMap.get(itemName);
                    eqPlacement = invItem.getEquipPlacement();
                } catch (Exception e) {
                    System.out.println("Problem casting inventory as Equipment:");
                    System.out.println(e);
                }


                String addPlayerInvQuery = "INSERT INTO " + INVENTORY_TABLE_NAME + "(playerID, name, equipmentPlacement)" +
                        " VALUES (" +
                        id +
                        ", '" + itemName +
                        "', '" + eqPlacement +
                        "');";

                try {
                    statement.execute(addPlayerInvQuery);
                } catch (SQLException sqle) {
                    System.out.println("SQL Exception: " + sqle);
                }
            }
        }


    }

    /** Deletes player's inventory from inventory table */
    public void deletePlayerInv(Player player) {
        int id = player.getID();

        String deleteQuery = "DELETE FROM " + INVENTORY_TABLE_NAME + " WHERE" +
                " playerID = " + id +
                ";";

        try {
            statement.execute(deleteQuery);
        } catch (SQLException sqle) {
            System.out.println("SQL Exception while deleting player inventory:");
            System.out.println(sqle);
        }

    }

    /** Updates character info */
    public void savePlayer(Player player) {


        //TODO may not want to save player's current hp, or maybe not save if below 0

        // Get player data
        String name = player.getName();
        int id = player.getID();
        String eqWeapon = "";
        String eqBody = "";
        String eqLegs = "";


        if (player.mobEquipmentMap.get("Weapon") != null) {
            eqWeapon = player.mobEquipmentMap.get("Weapon").getName();
        }
        if (player.mobEquipmentMap.get("Body") != null) {
            eqBody = player.mobEquipmentMap.get("Body").getName();
        }
        if (player.mobEquipmentMap.get("Legs") != null) {
            eqLegs = player.mobEquipmentMap.get("Legs").getName();
        }
        int maxHP = player.getMaxHP();// - GameInterface.equipmentMap.get(eqWeapon).getHP() - GameInterface.equipmentMap.get(eqBody).getHP() - GameInterface.equipmentMap.get(eqLegs).getHP();
        int hp = player.getHP();
        int attack = player.getAttack();// - GameInterface.equipmentMap.get(eqWeapon).getAttack() - GameInterface.equipmentMap.get(eqBody).getAttack() - GameInterface.equipmentMap.get(eqLegs).getAttack();
        int defense = player.getDefense();// - GameInterface.equipmentMap.get(eqWeapon).getDefense() - GameInterface.equipmentMap.get(eqBody).getDefense() - GameInterface.equipmentMap.get(eqLegs).getDefense();
        String description = player.getDescription();
        String setting = player.getSetting();
        int room = GameInterface.roomList.indexOf(player.currentRoom);

        // Save
        savePlayerEquipment(player);
        savePlayerInv(player); // Saving inventory not currently supported

        String saveQuery = "UPDATE " + PLAYER_TABLE_NAME +
                " SET maxhp = " + maxHP +
                ",hp = " + hp +
                ",attack = " + attack +
                ",description = '" + description +
                "', setting = '" + setting +
                "', currentroom = " + room +
                " WHERE playerID = " + id + ";";

        try {
            statement.executeUpdate(saveQuery);
        } catch (SQLException sqle) {
            System.out.println("playersave Exception: " + sqle);
        }
    }

    /** Updates characters equipment */
    public void savePlayerEquipment(Player player) {
        int id = player.getID();
        // HP, attack, defense are all calculated minus the bonuses from equipment

        String eqWeapon = "";
        String eqBody = "";
        String eqLegs = "";


        if (player.mobEquipmentMap.get("Weapon") != null) {
            eqWeapon = player.mobEquipmentMap.get("Weapon").getName();
        }
        if (player.mobEquipmentMap.get("Body") != null) {
            eqBody = player.mobEquipmentMap.get("Body").getName();
        }
        if (player.mobEquipmentMap.get("Legs") != null) {
            eqLegs = player.mobEquipmentMap.get("Legs").getName();
        }

        String addPlayerEQQuery = "UPDATE " + EQUIPPED_TABLE_NAME +
                " SET eqWeapon = '" + eqWeapon +
                "',eqBody = '" + eqBody +
                "',eqlegs = '" + eqLegs +
                "' WHERE playerID = " + id + ";";

        try {
            statement.executeUpdate(addPlayerEQQuery);
        } catch (SQLException sqle) {
            System.out.println("eq Exception " + sqle);
        }
    }


    /** Get character's playerID (from players table) based on name */
    public int getID(String name) {
        String getIDQuery = "SELECT playerID FROM players WHERE name = '" + name + "';";
        int id = 0;

        try {
            if (rs != null) rs.close();
            rs = statement.executeQuery(getIDQuery);
            while (rs.next()) {
                id = rs.getInt("playerID");
            }
        } catch (SQLException sqle) {
            System.out.println("SQL Exception: " + sqle);
        }

        return id;
    }

    /** Verifies id/password combination for logging in */
    public boolean checkPassword(int id, String password) {
        String checkPWQuery = "SELECT password FROM players WHERE playerid = " + id + ";";
        String newPW = "";

        try {
            if (rs != null) rs.close();
            rs = statement.executeQuery(checkPWQuery);
            while (rs.next()) {
                newPW = rs.getString("password");
            }
        } catch (SQLException sqle) {
            System.out.println("SQL Exception: " + sqle);
        }

        if (password.equals(newPW)) {
            return true;
        }

        return false;
    }

    /** Gets player info for loading saved character */
    public void loadPlayer(int id) {
        String loadPlayerQuery = "SELECT name, maxhp, hp, attack, defense, description, setting, currentroom" +
                " FROM " + PLAYER_TABLE_NAME +
                " WHERE playerID = " + id +
                ";";

        try {
            if (rs != null) rs.close();
            rs = statement.executeQuery(loadPlayerQuery);
        } catch (SQLException sqle) {
            System.out.println("Exception: " + sqle);
        }
    }

    /** Gets player equipment information for loading saved chracter */
    public String[] loadPlayerEQ(int id) {
        String[] eqArray = new String[3];
        // Fill with blanks to avoid null pointers
        for (int i = 0; i < eqArray.length; ++i) {
            eqArray[i] = "";
        }

        String loadPlayerEQQuery = "SELECT eqWeapon, eqBody, eqLegs" +
                " FROM " + EQUIPPED_TABLE_NAME +
                " WHERE playerID = " + id +
                ";";

        try {
            if (rs != null) rs.close();
            rs = statement.executeQuery(loadPlayerEQQuery);
            rs.next();
            eqArray[0] = rs.getString("eqWeapon");
            eqArray[1] = rs.getString("eqBody");
            eqArray[2] = rs.getString("eqLegs");

        } catch (SQLException sqle) {
            System.out.println("Exception: " + sqle);
        }

        return eqArray;
    }

    public LinkedList<String> loadPlayerInv(int id) {
        LinkedList<String> playerInvLoadList = new LinkedList<>();

        String getInvSelectQuery = "SELECT name FROM " + INVENTORY_TABLE_NAME +
                " WHERE playerid = " + id +
                ";";

        try {
            if (rs != null) rs.close();
            rs = statement.executeQuery(getInvSelectQuery);
            while (rs.next()) {
                playerInvLoadList.add(rs.getString(1));
            }
        } catch (SQLException sqle) {
            System.out.println("SQL Exception while loading player inventory:");
            System.out.println(sqle);
        }

        return playerInvLoadList;

    }

}
