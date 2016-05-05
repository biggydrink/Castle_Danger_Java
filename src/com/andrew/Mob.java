package com.andrew;

import java.util.HashMap;

abstract public class Mob {

    protected String name;
    protected int maxHP;
    protected int hp;
    protected int attack;
    protected int defense;
    protected HashMap<String,Item> inventory;
    protected HashMap<String,Item> equipped;
    protected String description;


    public Mob() {
        System.out.println("Mob initializer");

        inventory = new HashMap<String,Item>();
        equipped = new HashMap<String,Item>();
    }

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

}
