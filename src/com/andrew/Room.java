package com.andrew;


import java.util.LinkedList;

public class Room {
    String name;
    String description;
    Room north;
    Room south;
    Room east;
    Room west;
    LinkedList<Mob> mobList;
    LinkedList<Item> itemList;

    public Room() {
        mobList = new LinkedList<Mob>();
        itemList = new LinkedList<Item>();
    }

}
