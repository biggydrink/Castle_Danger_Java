package com.andrew;

import java.util.HashMap;

public class Mob {

    protected String name;
    protected String description;
    protected String setting;
    protected int maxHP;
    protected int hp;
    protected int attack;
    protected int defense;
    protected Room currentRoom;
    protected HashMap<String,Item> inventory;
    protected HashMap<String,Item> equipment;


    /** Construct that includes attack and defense, used for npcs/monsters */
    public Mob(String name, String description, String setting, int maxHP, int attack, int defense) {
        this.name = name;
        this.description = description;
        this.setting = setting;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.attack = attack;
        this.defense = defense;

        inventory = new HashMap<String,Item>();
        equipment = new HashMap<String,Item>();
    }

    public String getName() {
        return name;
    }
    public String getDescription() { return description; }
    public String getSetting() { return setting; }
    public int getMaxHP() { return maxHP; }
    public int getHP() { return hp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }

    public void setMaxHP(int newMax) {maxHP = newMax; }
    public void setHP(int newHP) {hp = newHP; }


    /** begins fight between this mob and another mob (can be monster or player character) */
    public void initiateAttack(Mob monster) {
        System.out.println("Mob initiateAttack() called");
    }

    /** Check if an item is in a Mob's inventory */
    public boolean isInInventory(String itemName) {
        System.out.println("Mob isInInventory() called");

        return false;
    }

    /** Check if an item is equipped by a Mob */
    public boolean isEquipped(String itemName) {
        System.out.println("Mob isEquipped() callled");

        return false;
    }

    /** Show currently equipped items */
    public void equip() {
        System.out.println("Mob equip (no args) called");
    }

    /** Equip selected item */
    public void equip(String itemName) {
        System.out.println("Mob equip(arg) called");
    }

    /** Unequips selected item */
    public void unequip(String itemName) {
        System.out.println("Mob unequip() called");
    }

    /** Remove an item from inventory */
    public void drop(String itemName) {
        System.out.println("Mob drop() called");
        //TODO item should be added to a room's item list
    }

    /** Use an item (if applicable) */
    public void use(String itemName) {
        System.out.println("Mob use() called");
    }



    public void goNorth() {
        if (currentRoom.north != null) {
            currentRoom.mobList.remove(this);
            currentRoom = currentRoom.north;
            currentRoom.mobList.add(this);
        }
    }
    public void goSouth() {
        if (currentRoom.north != null) {
            currentRoom.mobList.remove(this);
            currentRoom = currentRoom.north;
            currentRoom.mobList.add(this);
        }
    }
    public void goEast() {
        if (currentRoom.north != null) {
            currentRoom.mobList.remove(this);
            currentRoom = currentRoom.north;
            currentRoom.mobList.add(this);
        }
    }
    public void goWest() {
        if (currentRoom.north != null) {
            currentRoom.mobList.remove(this);
            currentRoom = currentRoom.north;
            currentRoom.mobList.add(this);
        }
    }

    public void setCurrentRoom(Room newRoom) {
        if (currentRoom != null && !currentRoom.mobList.isEmpty()) {
            currentRoom.mobList.remove(this);
        }

        newRoom.mobList.add(this);

        currentRoom = newRoom;
    }

}
