package com.andrew;


import java.sql.*;

public class Database {

    /*
    CREATE DATABASE castle_danger;
    CREATE TABLE players (
        player_key INT AUTO_INCREMENT PRIMARY KEY
        name VARCHAR(25) UNIQUE NOT NULL
        maxhp INT NOT NULL
        hp INT NOT NULL
        attack INT NOT NULL
        defense INT NOT NULL
        description VARCHAR(500) NOT NULL
        currentRoom VARCHAR(50) NOT NULL
    );
    CREATE DATABASE inventory (
        inv_key INT AUTO_INCREMENT PRIMARY KEY
        player_key INTFOREIGN KEY NOT NULL
        name VARCHAR(25) NOT NULL
        description  VARCHAR(500) NOT NULL
        defense INT
        hp INT
        attack INT
        equipPlacement VARCHAR(20)
    );
    // Could just add a column to inv "Equipped" ?
    CREATE DATABASE equipment (
        eq_key INT AUTO_INCREMENT PRIMARY KEY
        player_key INTFOREIGN KEY NOT NULL
        name VARCHAR(25) NOT NULL
        description  VARCHAR(500) NOT NULL
        defense INT
        hp INT
        attack INT
        equipPlacement VARCHAR(20)
    );
     */

    private static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/"; //TODO make this not local host
    private static String DB_NAME = "castle_danger";
    private static String USER = "root";
    private static String PASS = "4ig00K1MS$J$!1";

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

        // CREATE DATABASE IF NOT EXISTS dbname;

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
            String createDBQuery = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + ";";
            statement.executeUpdate(createDBQuery);

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

    private void createPlayerTable() {
        /*
        CREATE TABLE players (
                player_key INT AUTO_INCREMENT PRIMARY KEY
                name VARCHAR(25) UNIQUE NOT NULL
                maxhp INT NOT NULL
                hp INT NOT NULL
                attack INT NOT NULL
                defense INT NOT NULL
                description VARCHAR(500) NOT NULL
                currentRoom VARCHAR(50) NOT NULL
        );
        */

        String createTableQuery = "CREATE TABLE " + PLAYER_TABLE_NAME + " (" +
                "player_key INT NOT NULL AUTO_INCREMENT PRIMARY KEY" +
                ", name VARCHAR(25) UNIQUE NOT NULL" +
                ", maxhp INT NOT NULL" +
                ", hp INT NOT NULL" +
                ", attack INT NOT NULL" +
                ", defense INT NOT NULL" +
                ", description VARCHAR(500) NOT NULL" +
                ", currentRoom VARCHAR(50) NOT NULL" +
            ");";

        try {
            statement.executeUpdate(createTableQuery);
        } catch (SQLException sqle) {
            System.out.println("Could not create players table");
            System.out.println(sqle);
            sqle.printStackTrace();
        }
    }

    private void createInventoryTable() {
        /*
        CREATE DATABASE inventory (
                inv_key INT AUTO_INCREMENT PRIMARY KEY
                player_key INTFOREIGN KEY NOT NULL
                name VARCHAR(25) NOT NULL
                description  VARCHAR(500) NOT NULL
                defense INT
                hp INT
                attack INT
                equipPlacement VARCHAR(20)
        );
         */

        String createTableQuery = "CREATE TABLE " + INVENTORY_TABLE_NAME + " (" +
                "inv_key INT AUTO_INCREMENT PRIMARY KEY" +
                ", player_key INT NOT NULL" +
                ", name VARCHAR(25) NOT NULL" +
                ", description VARCHAR(500) NOT NULL" +
                ", defense INT" +
                ", hp INT" +
                ", attack INT" +
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

    private void createEquippedTable() {
        /*
        CREATE DATABASE equipment (
                eq_key INT AUTO_INCREMENT PRIMARY KEY
                player_key INTFOREIGN KEY NOT NULL
                name VARCHAR(25) NOT NULL
                description  VARCHAR(500) NOT NULL
                defense INT
                hp INT
                attack INT
                equipPlacement VARCHAR(20)
        );
         */
        String createTableQuery = "CREATE TABLE " + EQUIPPED_TABLE_NAME + " (" +
                "eq_key INT AUTO_INCREMENT PRIMARY KEY" +
                ", player_key INT NOT NULL" +
                ", name VARCHAR(25) NOT NULL" +
                ", description VARCHAR(500) NOT NULL" +
                ", defense INT" +
                ", hp INT" +
                ", attack INT" +
                ", equipmentPlacement VARCHAR(20)" +
                ");";

        try {
            statement.executeUpdate(createTableQuery);
        } catch (SQLException sqle) {
            System.out.println("Could not create equipment table");
            System.out.println(sqle);
            sqle.printStackTrace();
        }
    }

}
