package com.andrew;


public class Item {
    protected String name;
    protected String description;
    protected String setting;

    // make a good constructor
    // could have global list - run a method that could create all the weapons and then add them to the list

    public Item(String name, String description, String setting) {
        this.name = name;
        this.description = description;
        this.setting = setting;
    }

    public String getSetting() { return setting; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
