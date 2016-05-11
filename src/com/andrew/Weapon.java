package com.andrew;


public class Weapon extends Item {


    String equipPlacement;
    int attack;

    public Weapon(String name, String description, String setting, int attack, String equipPlacement) {
        super(name,description,setting);
        this.attack = attack;
        this.equipPlacement = equipPlacement;
    }

    public int getAttack() { return attack; }
    public String getEquipPlacement() { return equipPlacement; }
}
