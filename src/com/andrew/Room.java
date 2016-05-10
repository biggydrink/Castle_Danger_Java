package com.andrew;

import java.util.LinkedList;

public class Room {

    //TODO look into how trees work, use that to implement room lists and connections

    String name;
    String description;
    Room north;
    Room south;
    Room east;
    Room west;
    LinkedList<Mob> mobList;
    LinkedList<Item> itemList;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;

        mobList = new LinkedList<Mob>();
        itemList = new LinkedList<Item>();
    }

    public void setNorth(Room northRoom) {
        this.north = northRoom;
    }
    public void setSouth(Room southRoom) { this.south = southRoom; }
    public void setEast(Room eastRoom) { this.east = eastRoom; }
    public void setWest(Room westRoom) { this.west = westRoom; }

    public String getExits() {
        String exits = "";

        if (north != null) exits += "N";
        if (south != null) exits += "S";
        if (east != null) exits += "E";
        if (west != null) exits += "W";

        if (exits.equals("")) exits = "No exits!";

        return exits;
    }

    public String showMobs() {
        String mobSettings = "";
        for (Mob mob : mobList) {
            mobSettings += mob.getSetting() + "\n";
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
