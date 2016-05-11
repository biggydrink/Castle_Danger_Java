package com.andrew;

import java.util.HashMap;

public class Mob {

    protected String name;
    protected String description;
    protected String setting;
    protected int maxHP;
    protected int hp;
    protected int attack;
    protected int defense;
    protected boolean canMove;
    protected Room currentRoom;
    protected HashMap<String,Item> inventory;
    protected HashMap<String,Item> equipment;


    /** Construct that includes attack and defense, used for npcs/monsters */
    public Mob(String name, String description, String setting, int maxHP, int attack, int defense) {
        this.name = name;
        this.description = description;
        this.setting = setting;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.attack = attack;
        this.defense = defense;

        canMove = true;
        inventory = new HashMap<String,Item>();
        equipment = new HashMap<String,Item>();
    }

    public String getName() {
        return name;
    }
    public String getDescription() { return description; }
    public String getSetting() { return setting; }
    public int getMaxHP() { return maxHP; }
    public int getHP() { return hp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }

    public void setMaxHP(int newMax) {maxHP = newMax; }
    public void setHP(int newHP) {hp = newHP; }


    /** begins fight between this mob and another mob (can be monster or player character) */
    public void initiateAttack(Mob monster) {

        canMove = false;
        monster.canMove = false;

        int myAttackVal = attack - monster.getDefense();
            if (myAttackVal < 0) myAttackVal = 0;
        int monsterAttackVal = monster.getAttack() - defense;
            if (monsterAttackVal < 0) monsterAttackVal = 0;

        if (myAttackVal == 0 && monsterAttackVal == 0) {
            System.out.println("Looks like neither of you would be interested in a fight");
        } else {
            while (monster.getHP() > 0 && this.getHP() > 0) {
                monster.setHP(monster.getHP() - myAttackVal);
                hp = hp - monsterAttackVal;

                System.out.println(Interface.ANSI_RED + "You hit " + monster.getName() + " " + getAttackSTR(myAttackVal) + Interface.ANSI_RESET);
                System.out.println(Interface.ANSI_PURPLE + monster.getName() + " hits YOU " + getAttackSTR(monsterAttackVal) + Interface.ANSI_RESET);

                if (monster.getHP() <= 0) {
                    monster.die();
                    canMove = true;
                }
                if (hp <= 0) {
                    die();
                }
            }
        }

    }

    public void die() {
        System.out.println(name + " has died");
        currentRoom.removeMob(this);
    }

    private String getAttackSTR(int attackVal) {

        if (attackVal > 18) {
            return Interface.ANSI_RED + "HARD!!";
        } else if (attackVal > 14) {
            return "omg so hard";
        } else if (attackVal > 12) {
            return "... ouch!";
        } else if (attackVal > 10) {
            return "pretty hard";
        } else if (attackVal > 7) {
            return "kinda hard";
        } else if (attackVal > 5) {
            return "kinda";
        } else if (attackVal > 0) {
            return "a little bit";
        } else {
            return "not hard at all";
        }
    }

    public void gainItem(String itemName) {
        if (currentRoom.roomItemMap.containsKey(itemName)) {
            if (Interface.weaponMap.containsKey(itemName)) { // is a weapon
                Weapon mapWeap = (Weapon)currentRoom.roomItemMap.get(itemName);
                Weapon newWeap = new Weapon(mapWeap.getName(),mapWeap.getDescription(),mapWeap.getSetting(),mapWeap.getAttack(),mapWeap.getEquipPlacement());
                inventory.put(newWeap.getName(),newWeap);
                currentRoom.removeItem(mapWeap);
            } else if (Interface.armorMap.containsKey(itemName)) { // is a piece of armor
                Armor mapArmor = (Armor) currentRoom.roomItemMap.get(itemName);
                Armor newArmor = new Armor(mapArmor.getName(), mapArmor.getDescription(), mapArmor.getSetting(), mapArmor.getDefense(), mapArmor.getHP(), mapArmor.getEquipPlacement());
                inventory.put(newArmor.getName(),newArmor);
                currentRoom.removeItem(mapArmor);
            }
        }
    }

    /** Check if an item is in a Mob's inventory */
    public boolean isInInventory(String itemName) {
        if (inventory.containsKey(itemName)) {
            return true;
        }

        return false;
    }

    /** Check if an item is equipped by a Mob */
    public boolean isEquipped(String itemName) {
        if (equipment.containsKey(itemName)) {
            return true;
        }

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



    public void goNorth() {
        if (currentRoom.north != null) {
            setCurrentRoom(currentRoom.north);
        }
    }
    public void goSouth() {
        if (currentRoom.north != null) {
            setCurrentRoom(currentRoom.south);
        }
    }
    public void goEast() {
        if (currentRoom.north != null) {
            setCurrentRoom(currentRoom.east);
        }
    }
    public void goWest() {
        if (currentRoom.north != null) {
            setCurrentRoom(currentRoom.west);
        }
    }

    public void setCurrentRoom(Room newRoom) {
        if (currentRoom != null && !currentRoom.mobList.isEmpty()) {
            currentRoom.removeMob(this);
        }

        newRoom.addMob(this);
        currentRoom = newRoom;
    }

}
