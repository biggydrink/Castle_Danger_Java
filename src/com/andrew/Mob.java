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
    protected HashMap<String,Item> mobInventoryMap;
    protected HashMap<String,Item> mobEquipmentMap;
    private String[] equipAreas = {"Weapon","Body","Legs"};


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
        mobInventoryMap = new HashMap<String,Item>();
        mobEquipmentMap = new HashMap<String,Item>();
        mobEquipmentMap.put("Body",null);
        mobEquipmentMap.put("Legs",null);
        mobEquipmentMap.put("Weapon",null);

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
    public void setDefense(int newDefense) { defense = newDefense; }
    public void setAttack(int newAttack) { attack = newAttack; }


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

    public boolean gainItem(String itemName) {
        if (currentRoom.itemIsInRoom(itemName)) {
            if (Interface.weaponMap.containsKey(itemName) || Interface.armorMap.containsKey(itemName)) { // is a weapon or item
                Equipment mapWeap = (Equipment)currentRoom.roomItemMap.get(itemName);
                Equipment newWeap = new Equipment(mapWeap.getName(),mapWeap.getDescription(),mapWeap.getSetting(),mapWeap.getAttack(),mapWeap.getDefense(),mapWeap.getHP(),mapWeap.getEquipPlacement());
                mobInventoryMap.put(newWeap.getName().toLowerCase(),newWeap);
                currentRoom.removeItem(mapWeap);
                return true;
            }
        }

        return false;
    }



    /** Check if an item is in a Mob's inventory */
    public boolean isInInventory(String itemName) {
        if (mobInventoryMap.containsKey(itemName.toLowerCase())) {
            return true;
        }

        return false;
    }

    /** Check if an item is equipped by a Mob */
    public boolean isEquipped(String itemName) {

        for (String equipPlace : equipAreas) {
            if (mobEquipmentMap.get(equipPlace) != null) {
                String check = mobEquipmentMap.get(equipPlace).getName();
                if (check.equalsIgnoreCase(itemName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getInventoryString() {
        String invString = "";

        for (String itemName : mobInventoryMap.keySet()) {
            invString += (itemName + "\n");
        }

        return invString;
    }

    /** Show currently equipped items */
    public String getEquipmentString() {
        String viewEquipment = "";

        for (String equipPlace : equipAreas) {
            viewEquipment += (equipPlace + "\t\t");
            if (mobEquipmentMap.get(equipPlace) != null) {
                viewEquipment += mobEquipmentMap.get(equipPlace).getName();
            } else {
                viewEquipment += "[Nothing]";
            }
            viewEquipment += "\n";
        }

        return viewEquipment;

    }

    /** Equip selected item */
    public boolean equip(String itemName) {

        if (isInInventory(itemName)) {
            if (Interface.armorMap.get(itemName) != null || Interface.weaponMap.get(itemName) != null) { // It's a piece of armor or a weapon
                Equipment toEquip = (Equipment) mobInventoryMap.get(itemName);
                mobEquipmentMap.put(toEquip.getEquipPlacement(),toEquip);
                setAttack(attack += toEquip.getAttack());
                setDefense(defense + toEquip.getDefense());
                setMaxHP(maxHP + toEquip.getHP());
                setHP(hp + toEquip.getHP());
            }
            mobInventoryMap.remove(itemName);
            return true;
        }

        return false;
    }

    /** Unequips selected item */
    public boolean unequip(String itemName) {

        if (isEquipped(itemName)) {
            Equipment toUnEquip = null;
            if (Interface.armorMap.get(itemName) != null) {
                toUnEquip = Interface.armorMap.get(itemName);
            } else if (Interface.weaponMap.get(itemName) != null) {
                toUnEquip = Interface.weaponMap.get(itemName);
            }
                mobEquipmentMap.put(toUnEquip.getEquipPlacement(),null);
                setAttack(attack - toUnEquip.getAttack());
                setDefense(defense - toUnEquip.getDefense());
                setMaxHP(maxHP - toUnEquip.getHP());
                setHP(hp - toUnEquip.getHP());
                mobInventoryMap.put(itemName.toLowerCase(), toUnEquip);
                return true;
        }
        return false;
    }

    /** Remove an item from inventory */
    public boolean drop(String itemName) {

        if (isInInventory(itemName)) {
            Item itemToDrop = mobInventoryMap.get(itemName);
            currentRoom.addItem(itemToDrop);
            mobInventoryMap.remove(itemName);
            return true;
        }

        return false;

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
