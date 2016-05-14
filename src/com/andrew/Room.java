package com.andrew;

import java.util.HashMap;
import java.util.LinkedList;

/** Game locations
 * Created and connected together in World.java
 * Can hold items and mobs. Default items and mobs are included for easy population of the game.
 */
public class Room {

    String name;
    String description;
    Room north;
    Room south;
    Room east;
    Room west;
    LinkedList<String> defaultMobTypes;
    LinkedList<Mob> mobList;
    HashMap<String,Mob> roomMobMap;
    LinkedList<String> defaultItemTypes;
    LinkedList<Item> itemList;
    HashMap<String,Item> roomItemMap;

    /** Room constructor with default mob/item types */
    public Room(String name, String description, String defaultMob, String defaultItem) {
        this.name = name;
        this.description = description;

        mobList = new LinkedList<Mob>();
        roomMobMap = new HashMap<String,Mob>();
        itemList = new LinkedList<Item>();
        roomItemMap = new HashMap<String,Item>();
        defaultItemTypes = new LinkedList<>();
        defaultItemTypes.add(defaultItem);
        for (String itemName : defaultItemTypes) {
            createItem(itemName);
        }

        defaultMobTypes = new LinkedList<>();
        defaultMobTypes.add(defaultMob);
        for (String mobName : defaultMobTypes) {
            createMob(mobName);
        }
    }

    /** Room constructor with no default mob/item types */
    public Room(String name, String description) {
        this.name = name;
        this.description = description;

        mobList = new LinkedList<Mob>();
        roomMobMap = new HashMap<String,Mob>();
        itemList = new LinkedList<Item>();
        roomItemMap = new HashMap<String,Item>();
    }

    public void setNorth(Room northRoom) {
        this.north = northRoom;
    }
    public void setSouth(Room southRoom) { this.south = southRoom; }
    public void setEast(Room eastRoom) { this.east = eastRoom; }
    public void setWest(Room westRoom) { this.west = westRoom; }

    /** Check if an item is in the room or not */
    public boolean itemIsInRoom(String itemName) {
        if (roomItemMap.containsKey(itemName)) {
            return true;
        }
        return false;
    }
    /** Check if a mob is in the room or not */
    public boolean mobIsInRoom(String mobName) {
        if (roomMobMap.containsKey(mobName)) {
            return true;
        }
        return false;
    }

    /** Create an item, usually the room's default */
    protected void createItem(String name) {
        if (name.equals("")) {
            return;
        }
        Equipment mapItem = GameInterface.equipmentMap.get(name);
        Equipment newItem = new Equipment(mapItem.getName(),mapItem.getDescription(),mapItem.getSetting(),mapItem.getAttack(),mapItem.getDefense(), mapItem.getHP(),mapItem.getEquipPlacement());
        addItem(newItem);
    }

    /** Create a mob, usually the room's default */
    protected void createMob(String name) {

        if (name.equals("")) {
            return;
        }
        Mob mapMob = GameInterface.mobMap.get(name);
        Mob newMob = new Mob(mapMob.getName(),mapMob.getDescription(),mapMob.getSetting(),mapMob.getMaxHP(),mapMob.getAttack(),mapMob.getDefense(),mapMob.getDefaultWeapon(),mapMob.getDefaultBody(),mapMob.getDefaultLegs());
        newMob.setCurrentRoom(this);
    }

    /** Returns a string of available exits, N for North, etc */
    protected String getExits() {
        String exits = "";

        if (north != null) exits += "N";
        if (south != null) exits += "S";
        if (east != null) exits += "E";
        if (west != null) exits += "W";

        if (exits.equals("")) exits = "No exits!";

        return exits;
    }

    /** Add a mob to room */
    protected void addMob(Mob mob) {
        mobList.add(mob);
        roomMobMap.put(mob.getName().toLowerCase(),mob);
    }

    /** Remove a mob from room */
    protected void removeMob(Mob mob) {
        mobList.remove(mob);
        roomMobMap.remove(mob.getName().toLowerCase());
    }

    /** Add an item to room */
    protected void addItem(Item item) {
        itemList.add(item);
        roomItemMap.put(item.getName().toLowerCase(),item);
    }

    /** Remove an item from room */
    protected void removeItem(Item item) {
        itemList.remove(item);
        roomItemMap.remove(item.getName().toLowerCase());
    }

    /** Return a string of all items in the room. Runs in the GameInterface.setting() method */
    protected String showMobs(Player player) {
        String mobSettings = "";
        for (Mob mob : mobList) {
            if (!mob.getName().equals(player.getName())) {
                mobSettings += mob.getSetting() + "\n";
            }
        }
        return mobSettings;
    }

    /** Return a string of all monsters/players in the room. Runs in the GameInterface.setting() method */
    protected String showItems() {
        String itemSettings = "";
        for (Item item : itemList) {
            itemSettings += item.getSetting() + "\n";
        }

        return itemSettings;
    }

}
