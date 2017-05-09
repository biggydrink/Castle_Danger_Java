package com.andrew;

import java.util.Random;

/** Extension of Mob, used for player characters */
public class Player extends Mob {

    /* Class Mob has the following data fields:
        String name;
        int maxHP;
        int hp;
        int attack;
        int defense;
        ALREADY INITIALIZED HashMap<String,Item> mobInventoryMap;
        ALREADY INITIALIZED HashMap<String,Item> mobEquipmentMap;
        String description;
         */

    private Random playerRand;
    public int playerID;
    protected String password;

    public Player(String name) {

        super(name,"","",100,1,1,"player");
        playerRand = new Random();
        this.name = name;
        this.description = "Behold, " + this.name + ". An elite player in this game!";
        this.setting = name + " is standing here.";
        this.maxHP = playerRand.nextInt(10) + 150; // random max hp between 150-160
        this.hp = this.maxHP;
        this.attack = playerRand.nextInt(2) + 20; // random attack between 20 and 22
        this.defense = playerRand.nextInt(2) + 15; // random defense between 15 and 17

    }

    protected String getPassword() { return password; }
    protected int getID() { return playerID; }

    protected void setPlayerID(int id) { playerID = id; }
    protected void setPassword(String pw) { password = pw; }

    /*
    // Comment out to have players drop all eq and inv on death
    @Override
    public void die() {
        // Does nothing
    }
    */


    /** Movement commands for Player include warnings if unable to proceed */
    @Override
    public boolean goNorth() {
        if (currentRoom.north != null) {
            setCurrentRoom(currentRoom.north);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean goSouth() {
        if (currentRoom.south != null) {
            setCurrentRoom(currentRoom.south);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean goEast() {
        if (currentRoom.east != null) {
            setCurrentRoom(currentRoom.east);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean goWest() {
        if (currentRoom.west != null) {
            setCurrentRoom(currentRoom.west);
            return true;
        } else {
            return false;
        }
    }

}
