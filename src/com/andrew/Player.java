package com.andrew;

import java.util.Random;

//TODO add comments

/** Extension of Mob, used for player characters */
public class Player extends Mob {

    private Random playerRand;
    public int playerID;
    protected String password;

    public Player(String name) {
        /* Abstract class Mob has the following data fields:
        String name;
        int maxHP;
        int hp;
        int attack;
        int defense;
        ALREADY INITIALIZED HashMap<String,Item> mobInventoryMap;
        ALREADY INITIALIZED HashMap<String,Item> mobEquipmentMap;
        String description;
         */

        super(name,"","",100,1,1);
        playerRand = new Random();
        this.name = name;
        this.description = "Behold, " + this.name + ". An elite player in this game!";
        this.setting = name + " is standing here.";
        this.maxHP = playerRand.nextInt(10) + 80; // random max hp between 80 and 90
        this.hp = this.maxHP;
        this.attack = playerRand.nextInt(2) + 10; // random attack between 10 and 12
        this.defense = playerRand.nextInt(2) + 5; // random defense between 5 and 7

    }

    public int getHP() {
        return hp;
    }
    public int getMaxHP() {
        return maxHP;
    }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    protected String getPassword() { return password; }
    protected int getID() { return playerID; }

    protected void setPlayerID(int id) { playerID = id; }
    protected void setPassword(String pw) { password = pw; }

    @Override
    public void goNorth() {
        if (currentRoom.north != null) {
            setCurrentRoom(currentRoom.north);
        } else {
            System.out.println("You can't go that way!");
        }
    }
    @Override
    public void goSouth() {
        if (currentRoom.south != null) {
            setCurrentRoom(currentRoom.south);
        } else {
            System.out.println("You can't go that way!");
        }
    }
    @Override
    public void goEast() {
        if (currentRoom.east != null) {
            setCurrentRoom(currentRoom.east);
        } else {
            System.out.println("You can't go that way!");
        }
    }
    @Override
    public void goWest() {
        if (currentRoom.west != null) {
            setCurrentRoom(currentRoom.west);
        } else {
            System.out.println("You can't go that way!");
        }
    }

}
