package com.andrew;

import java.util.HashMap;
import java.util.LinkedList;

/** In-game monsters, aka mobs. Some details (having non-players able to move and hold inventory, etc) not yet
 * implemented but included for future development.
 * This is also a superclass of Player characters
 */
public class Mob {

    protected String name;
    protected String description;
    protected String setting;
    protected int maxHP;
    protected int hp;
    protected int attack;
    protected int defense;
    protected Eqpmt defaultWeapon;
    protected Eqpmt defaultBody;
    protected Eqpmt defaultLegs;
    protected boolean canMove;
    protected Room currentRoom;
    protected HashMap<String,Eqpmt> mobInventoryMap;
    protected LinkedList<Eqpmt> mobInventoryList;
    protected HashMap<String,Eqpmt> mobEquipmentMap;
    protected LinkedList<Eqpmt> mobEquipmentList;
    private String[] equipAreas = {"Weapon","Body","Legs"};


    /** Construct that includes attack and defense
     *  Equips no weapon or armor */
    public Mob(String name, String description, String setting, int maxHP, int attack, int defense) {
        this.name = name;
        this.description = description;
        this.setting = setting;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.attack = attack;
        this.defense = defense;
//        this.defaultWeapon = "";
//        this.defaultBody = "";
//        this.defaultLegs = "";

        canMove = true;
        mobInventoryMap = new HashMap<String,Eqpmt>();
        mobInventoryList = new LinkedList<Eqpmt>();
        mobEquipmentMap = new HashMap<String,Eqpmt>();
        mobEquipmentList = new LinkedList<Eqpmt>();

        mobEquipmentMap.put("Body",null);
        mobEquipmentMap.put("Legs",null);
        mobEquipmentMap.put("Weapon",null);

    }

    /** Construct that includes attack and defense, AND includes arguments for default weapon and armor*/
    public Mob(String name, String description, String setting, int maxHP, int attack, int defense, Eqpmt defaultWeapon, Eqpmt defaultBody, Eqpmt defaultLegs) {
        this.name = name;
        this.description = description;
        this.setting = setting;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.defaultWeapon = defaultWeapon;
        this.defaultBody = defaultBody;
        this.defaultLegs = defaultLegs;

        canMove = true;
        mobInventoryMap = new HashMap<String,Eqpmt>();
        mobInventoryList = new LinkedList<Eqpmt>();
        mobEquipmentMap = new HashMap<String,Eqpmt>();
        mobEquipmentList = new LinkedList<Eqpmt>();

//        if (GameInterface.equipmentMap.containsKey(defaultWeapon)) { equip(GameInterface.equipmentMap.get(defaultWeapon)); }
//        if (GameInterface.equipmentMap.containsKey(defaultBody)) { equip(GameInterface.equipmentMap.get(defaultBody)); }
//        if (GameInterface.equipmentMap.containsKey(defaultLegs)) { equip(GameInterface.equipmentMap.get(defaultLegs)); }
        equip(defaultWeapon);
        equip(defaultBody);
        equip(defaultLegs);

    }

    /** Construct that includes attack and defense, AND includes arguments for default weapon, but not armor*/
    public Mob(String name, String description, String setting, int maxHP, int attack, int defense, Eqpmt defaultWeapon) {
        this.name = name;
        this.description = description;
        this.setting = setting;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.defaultWeapon = defaultWeapon;

        canMove = true;
        mobInventoryMap = new HashMap<String,Eqpmt>();
        mobInventoryList = new LinkedList<Eqpmt>();
        mobEquipmentMap = new HashMap<String,Eqpmt>();
        mobEquipmentList = new LinkedList<Eqpmt>();

//        if (GameInterface.equipmentMap.containsKey(defaultWeapon)) { equip(GameInterface.equipmentMap.get(defaultWeapon)); }
//        if (GameInterface.equipmentMap.containsKey(defaultBody)) { equip(GameInterface.equipmentMap.get(defaultBody)); }
//        if (GameInterface.equipmentMap.containsKey(defaultLegs)) { equip(GameInterface.equipmentMap.get(defaultLegs)); }
        equip(defaultWeapon);

    }

    // Getters & Setters
    public String getName() {
        return name;
    }
    public String getDescription() { return description; }
    public String getSetting() { return setting; }
    public int getMaxHP() { return maxHP; }
    public int getHP() { return hp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public Eqpmt getDefaultWeapon() { return defaultWeapon; }
    public Eqpmt getDefaultBody() { return defaultBody; }
    public Eqpmt getDefaultLegs() { return defaultLegs; }

    public void setMaxHP(int newMax) {maxHP = newMax; }
    public void setHP(int newHP) {
        if (newHP > maxHP) {
            hp = maxHP;
        } else {
            hp = newHP;
        }
    }
    public void setDefense(int newDefense) { defense = newDefense; }
    public void setAttack(int newAttack) { attack = newAttack; }
    public void setSetting(String newSetting) { setting = newSetting; }
    public void setDescription(String newDescription) {description = newDescription; }


    /** begins fight between this mob and another mob (can be monster or player character) */
    public void initiateAttack(Mob monster) {
        String myOriginalSetting = getSetting();
        String mobOriginalSetting = monster.getSetting();

        // Reset setting strings for both parties (attacking mob + attacked mob)
        setSetting(getName() + " is here, attacking " + monster.getName());
        monster.setSetting(monster.getName() + " is fighting with " + getName());

        // Can't move rooms while attacking or being attacked
        canMove = false;
        monster.canMove = false;

        // Set attack values
        int myAttackVal = attack - monster.getDefense();
        int monsterAttackVal = monster.getAttack() - defense;

        // Check for (and don't allow) negative attack values
        if (myAttackVal < 0) myAttackVal = 0;
        if (monsterAttackVal < 0) monsterAttackVal = 0;


        if (myAttackVal == 0 && monsterAttackVal == 0) { // Don't allow never-ending attacks
            System.out.println("Looks like neither of you would be interested in a fight");
        } else {
            while (monster.getHP() > 0 && this.getHP() > 0) {
                monster.setHP(monster.getHP() - myAttackVal);
                hp = hp - monsterAttackVal;


                System.out.println(GameInterface.ANSI_RED + "You hit " + monster.getName() + " " + getAttackSTR(myAttackVal) + GameInterface.ANSI_RESET);
                System.out.println(GameInterface.ANSI_PURPLE + monster.getName() + " hits YOU " + getAttackSTR(monsterAttackVal) + GameInterface.ANSI_RESET);
                GameInterface.prompt();
                System.out.println("");

                //Can include the below for debugging or balance help
                //System.out.println("Monster attack: " + monsterAttackVal + " | Monster HP: " + monster.getHP());
                //System.out.println("My attack: " + myAttackVal + " | my HP: " + getHP());


                // Got help from this stackoverflow question regarding how to delay a program
                // Seems to have the potential for errors, but hopefully with such a short sleep time the potential for problematic interruptions is low
                // http://stackoverflow.com/questions/3342651/how-can-i-delay-a-java-program-for-a-few-second
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    System.out.println("Interrupted: " + ie);
                }

                // Handle deaths, ends loop
                if (monster.getHP() <= 0) {
                    monster.die();
                }
                if (hp <= 0) {
                    die();
                }
            }

            canMove = true;
            monster.canMove = true;
            monster.setSetting(mobOriginalSetting);
            setSetting(myOriginalSetting);
        }

    }

    /** Called when the monster's HP goes below 0 - mob removed from current room and drops all its items */
    private void die() {
        System.out.println(name + " has died");
        currentRoom.removeMob(this);

        for (String eq : mobEquipmentMap.keySet()) {
            if (mobEquipmentMap.get(eq) != null) {
                Eqpmt toDrop = mobEquipmentMap.get(eq);
                unequip(toDrop.getName());
                drop(toDrop.getName());
            }
        }

        for (String inv : mobInventoryMap.keySet()) {
            Eqpmt toDrop = mobInventoryMap.get(inv);
            drop(toDrop.getName());
        }

    }

    /** Returns a different string to be used in display attacks, depending on how hard you are hitting (or being hit!) */
    private String getAttackSTR(int attackVal) {

        if (attackVal > 18) {
            return GameInterface.ANSI_RED + "HARD!!";
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

    /** Picks up an item in the room the monster is in */
    public boolean gainItemInRoom(Eqpmt itemName) {
//        itemName = itemName.toLowerCase();
        if (currentRoom.itemIsInRoom(itemName)) {
//            if (GameInterface.equipmentMap.containsKey(itemName)) { // is a weapon or item
//                Equipment mapEQ = (Equipment)currentRoom.roomItemMap.get(itemName);
//                Equipment newEQ = new Equipment(mapEQ.getName(),mapEQ.getDescription(),mapEQ.getSetting(),mapEQ.getAttack(),mapEQ.getDefense(),mapEQ.getHP(),mapEQ.getEquipPlacement());
//                mobInventoryMap.put(newEQ.getName().toLowerCase(),newEQ);
                mobInventoryMap.put(itemName.getName(),itemName);
//                mobInventoryList.add(newEQ);
                mobInventoryList.add(itemName);
//                currentRoom.removeItem(mapEQ);
                currentRoom.removeItem(itemName);
                return true;
            }
//        }

        return false;
    }

    public boolean gainItem(Eqpmt itemName) {
//        if (GameInterface.equipmentMap.containsKey(itemName)) { // is a weapon or item
//            Equipment mapWeap = GameInterface.equipmentMap.get(itemName);
//            Equipment newWeap = new Equipment(mapWeap.getName(),mapWeap.getDescription(),mapWeap.getSetting(),mapWeap.getAttack(),mapWeap.getDefense(),mapWeap.getHP(),mapWeap.getEquipPlacement());
//            mobInventoryMap.put(newWeap.getName().toLowerCase(),newWeap);
            mobInventoryMap.put(itemName.getName(),itemName);
//            mobInventoryList.add(newWeap);
            mobInventoryList.add(itemName);

            return true;
        }

//        return false;
//    }

    /** Shortcut to equip something. Unlike the other equip, item does not need to be in inventory.
     * Can only be called manually in code */
    public void equip(Eqpmt item) {
        if (item != null) {
            mobEquipmentMap.put(item.getEquipPlacement(),item);
            mobEquipmentList.add(item);
            setAttack(attack += item.getAttack());
            setDefense(defense + item.getDefense());
            setMaxHP(maxHP + item.getHP());
            setHP(hp + item.getHP());
        }
    }


    /** Equip selected item. Must be in inventory */
    public boolean equip(String itemName) {

//        if (isInInventory(itemName) && GameInterface.equipmentMap.get(itemName) != null) { // It's a piece of armor or a weapon
            Eqpmt toEquip = mobInventoryMap.get(itemName);

            // Remove if already equipped
            if (alreadyEquipped(toEquip.getEquipPlacement())) {
                unequip(mobEquipmentMap.get(toEquip.getEquipPlacement()).getName());
            }

            // Equip item
            mobEquipmentMap.put(toEquip.getEquipPlacement(),toEquip);
            mobEquipmentList.add(toEquip);
            setAttack(attack += toEquip.getAttack());
            setDefense(defense + toEquip.getDefense());
            setMaxHP(maxHP + toEquip.getHP());
            setHP(hp + toEquip.getHP());

            // Remove from inventory
            mobInventoryMap.remove(itemName);
            mobInventoryList.remove(toEquip);

            return true;
//        } else {
//            return false;
//        }
    }

    /** Checks if something is already equipped in the selected slot */
    private boolean alreadyEquipped(String eqLoc) {
        if (mobEquipmentMap.get(eqLoc) == null) {
            return false;
        } else {
            return true;
        }
    }

    /** Unequips selected item */
    public boolean unequip(String itemName) {

        Eqpmt toUnEquip;

        if (isEquipped(itemName)) {

            toUnEquip = mobEquipmentMap.get(getEquippedItemLocation(itemName));

            mobEquipmentMap.put(toUnEquip.getEquipPlacement(),null);
            mobEquipmentList.remove(toUnEquip);
            setAttack(attack - toUnEquip.getAttack());
            setDefense(defense - toUnEquip.getDefense());
            setMaxHP(maxHP - toUnEquip.getHP());
            setHP(hp - toUnEquip.getHP());
            mobInventoryMap.put(itemName.toLowerCase(), toUnEquip);
            mobInventoryList.add(toUnEquip);
            return true;
        }
        return false;
    }

    /** Remove an item from inventory, and places it in the room's inventory list */
    public boolean drop(String itemName) {

        if (isInInventory(itemName)) {
            Eqpmt itemToDrop = mobInventoryMap.get(itemName.toLowerCase());
            currentRoom.addItem(itemToDrop);
            mobInventoryMap.remove(itemName.toLowerCase());
            mobInventoryList.remove(itemToDrop);
            return true;
        }

        return false;
    }

    /** Use an item (if applicable) */
    public void use(String itemName) {
        System.out.println("Mob use() called");
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

    /** Checks where an item is equipped - as a weapon, on the body, or on the legs */
    public String getEquippedItemLocation(String itemName) {

        if (isEquipped(itemName)) {
            for (Eqpmt eq : mobEquipmentMap.values()) {
                if (eq != null && eq.getName().equalsIgnoreCase(itemName)) {
                    return eq.getEquipPlacement();
                }
            }
        }
        return "";
    }

    /** Puts together a String list of items in inventory, shown by the inv command in GameInterface's commandMap */
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


    /** Movement commands, to try going to the current room's north/south/east/west location */
    public boolean goNorth() {
        if (currentRoom.north != null) {
            setCurrentRoom(currentRoom.north);
            return true;
        } else {
            return false;
        }
    }
    public boolean goSouth() {
        if (currentRoom.north != null) {
            setCurrentRoom(currentRoom.south);
            return true;
        } else {
            return false;
        }
    }
    public boolean goEast() {
        if (currentRoom.north != null) {
            setCurrentRoom(currentRoom.east);
            return true;
        } else {
            return false;
        }
    }
    public boolean goWest() {
        if (currentRoom.north != null) {
            setCurrentRoom(currentRoom.west);
            return true;
        } else {
            return false;
        }
    }

    /** Sets current room to any room object */
    public void setCurrentRoom(Room newRoom) {
        if (currentRoom != null && !currentRoom.mobList.isEmpty()) {
            // removes mob from your current room before adding to a new one
            currentRoom.removeMob(this);
        }

        newRoom.addMob(this);
        currentRoom = newRoom;
    }

    private static class Armory {

        Eqpmt body;
        Eqpmt legs;
        Eqpmt mainHand;

        void setBody(Eqpmt newBody) {
            if (body != null) {
                removeBody();
            }
            body = newBody;
        }

        Eqpmt removeBody() {
            if (body != null) {
                Eqpmt oldBody = body;
                body = null;
                return oldBody;
            } else {
                return null;
            }
        }

        void setLegs(Eqpmt newLegs) {
            if (legs != null) {
                removeLegs();
            }
            legs = newLegs;
        }

        Eqpmt removeLegs() {
            if (legs != null) {
                Eqpmt oldLegs = legs;
                legs = null;
                return oldLegs;
            } else {
                return null;
            }
        }

        void setMainHand(Eqpmt newMainHand) {
            if (mainHand != null) {
                removeMainHand();
            }
            mainHand = newMainHand;
        }

        Eqpmt removeMainHand() {
            if (mainHand != null) {
                Eqpmt oldMainHand = mainHand;
                mainHand = null;
                return oldMainHand;
            } else {
                return null;
            }
        }

        HashMap<String,Eqpmt> removeAll() {
            HashMap<String,Eqpmt> eqMap = new HashMap<String,Eqpmt>();

            eqMap.put("body",removeBody());
            eqMap.put("legs",removeLegs());
            eqMap.put("mainHand",removeMainHand());

            return eqMap;
        }

    }

}
