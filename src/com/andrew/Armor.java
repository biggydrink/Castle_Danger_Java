package com.andrew;


public class Armor extends Item {

    int defense;
    int hp;
    String equipPlacement;


    public Armor(String name, String description, String setting, int defense, int hp, String equipPlacement) {
        super(name,description,setting);
        this.defense = defense;
        this.hp = hp;
        this.equipPlacement = equipPlacement;
    }

    public int getDefense() { return defense; }
    public int getHP() { return hp; }
    public String getEquipPlacement() { return equipPlacement; }
}
