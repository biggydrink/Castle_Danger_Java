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
        ALREADY INITIALIZED HashMap<String,Item> inventory;
        ALREADY INITIALIZED HashMap<String,Item> equipment;
        String description;
         */

        super(name,"",100,1,1);
        playerRand = new Random();
        this.name = name;
        this.maxHP = playerRand.nextInt(10) + 80; // random max hp between 80 and 90
        this.hp = this.maxHP;
        this.attack = playerRand.nextInt(2) + 8; // random attack between 8 and 10
        this.defense = playerRand.nextInt(2) + 3; // random defense between 3 and 5
        this.description = "Behold, " + this.name + ". An elite player in this game!";
    }

    /** Display player's stats */
    public void stats() {
        System.out.println("Player stats() called");
        System.out.println(getDescription());
        System.out.println("Name: " + getName());
        System.out.println("HP: " + getHP() + "/" + getMaxHP());
        System.out.println("Attack: " + getAttack());
        System.out.println("Defense: " + getDefense());
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
            currentRoom.mobList.remove(this);
            currentRoom = currentRoom.north;
            currentRoom.mobList.add(this);
            System.out.println(currentRoom.description);
        } else {
            System.out.println("You can't go that way!");
        }
    }
    @Override
    public void goSouth() {
        if (currentRoom.south != null) {
            currentRoom.mobList.remove(this);
            currentRoom = currentRoom.south;
            currentRoom.mobList.add(this);
            System.out.println(currentRoom.description);
        } else {
            System.out.println("You can't go that way!");
        }
    }
    @Override
    public void goEast() {
        if (currentRoom.east != null) {
            currentRoom.mobList.remove(this);
            currentRoom = currentRoom.east;
            currentRoom.mobList.add(this);
            System.out.println(currentRoom.description);
        } else {
            System.out.println("You can't go that way!");
        }
    }
    @Override
    public void goWest() {
        if (currentRoom.west != null) {
            currentRoom.mobList.remove(this);
            currentRoom = currentRoom.west;
            currentRoom.mobList.add(this);
            System.out.println(currentRoom.description);
        } else {
            System.out.println("You can't go that way!");
        }
    }

    // HashMap<String,String>





}
