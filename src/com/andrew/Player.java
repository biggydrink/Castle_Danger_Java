package com.andrew;

import java.util.Random;

/** Extension of Mob, used for player characters */
public class Player extends Mob {

    private Random playerRand;

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
        this.attack = playerRand.nextInt(2) + 8; // random attack between 8 and 10
        this.defense = playerRand.nextInt(2) + 3; // random defense between 3 and 5

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
