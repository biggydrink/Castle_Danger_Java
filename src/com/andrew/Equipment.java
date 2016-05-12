package com.andrew;

/** Items that are able to be equipped by mobs/players, and influence their stats */
public class Equipment extends Item {

    int attack;
    int defense;
    int hp;
    String equipPlacement;


    public Equipment(String name, String description, String setting, int attack, int defense, int hp, String equipPlacement) {
        super(name,description,setting);
        this.defense = defense;
        this.hp = hp;
        this.equipPlacement = equipPlacement;
        this.attack = attack;
    }

    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getHP() { return hp; }
    public String getEquipPlacement() { return equipPlacement; }
}
