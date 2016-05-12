package com.andrew;

/** Basic class for all items in game.
 * Currently Equipment class extends this, and all items in the game are Equipment, but this is still included
 * to help with future development (could have potions, scrolls, rocks, whatever
 */

public class Item {
    protected String name;
    protected String description;
    protected String setting;


    public Item(String name, String description, String setting) {
        this.name = name;
        this.description = description;
        this.setting = setting;
    }

    public String getSetting() { return setting; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
