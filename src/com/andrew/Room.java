package com.andrew;

import java.util.HashMap;
import java.util.LinkedList;

public class Room {

    //TODO look into how trees work, use that to implement room lists and connections

    String name;
    String description;
    Room north;
    Room south;
    Room east;
    Room west;
    LinkedList<String> defaultMobTypes;
    LinkedList<Mob> mobList;
    HashMap<String,Mob> roomMobMap;
    LinkedList<Item> itemList;
    HashMap<String,Item> roomItemMap;

    public Room(String name, String description, String defaultMob) {
        this.name = name;
        this.description = description;

        mobList = new LinkedList<Mob>();
        roomMobMap = new HashMap<String,Mob>();
        itemList = new LinkedList<Item>();
        roomItemMap = new HashMap<String,Item>();

        defaultMobTypes = new LinkedList<>();
        defaultMobTypes.add(defaultMob);
        for (String mobName : defaultMobTypes) {
            createMob(mobName);
        }
    }

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

    public void createMob(String name) {
        //addMob(Interface.mobMap.get(name));
        Mob mapMob = Interface.mobMap.get(name);
        Mob newMob = new Mob(mapMob.getName(),mapMob.getDescription(),mapMob.getSetting(),mapMob.getMaxHP(),mapMob.getAttack(),mapMob.getDefense());
        newMob.setCurrentRoom(this);
    }

    public String getExits() {
        String exits = "";

        if (north != null) exits += "N";
        if (south != null) exits += "S";
        if (east != null) exits += "E";
        if (west != null) exits += "W";

        if (exits.equals("")) exits = "No exits!";

        return exits;
    }

    public void addMob(Mob mob) {
        mobList.add(mob);
        roomMobMap.put(mob.getName().toLowerCase(),mob);
    }

    public void removeMob(Mob mob) {
        mobList.remove(mob);
        roomMobMap.remove(mob.getName().toLowerCase());
    }

    public void addItem(Item item) {
        itemList.add(item);
        roomItemMap.put(item.getName().toLowerCase(),item);
    }

    public void removeItem(Item item) {
        itemList.remove(item);
        roomItemMap.remove(item.getName().toLowerCase());
    }

    public String showMobs(Player player) {
        String mobSettings = "";
        for (Mob mob : mobList) {
            if (!mob.getName().equals(player.getName())) {
                mobSettings += mob.getSetting() + "\n";
            }

        }

        return mobSettings;
    }

    public String showItems() {
        String itemSettings = "";
        for (Item item : itemList) {
            itemSettings += item.getSetting() + "\n";
        }

        return itemSettings;
    }

}
