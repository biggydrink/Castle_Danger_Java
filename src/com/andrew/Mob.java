package com.andrew;

import java.util.HashMap;

/** In-game monsters, aka mobs. Some details (having non-players able to move and hold inventory, etc) not yet
 * implemented but included for future development.
 *
 */
public class Mob {

    protected String name;
    protected String description;
    protected String setting;
    protected int maxHP;
    protected int hp;
    protected int attack;
    protected int defense;
    protected String defaultWeapon;
    protected String defaultBody;
    protected String defaultLegs;
    protected boolean canMove;
    protected Room currentRoom;
    protected HashMap<String,Item> mobInventoryMap;
    protected HashMap<String,Equipment> mobEquipmentMap;
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
        this.defaultWeapon = "";
        this.defaultBody = "";
        this.defaultLegs = "";

        canMove = true;
        mobInventoryMap = new HashMap<String,Item>();
        mobEquipmentMap = new HashMap<String,Equipment>();
        mobEquipmentMap.put("Body",null);
        mobEquipmentMap.put("Legs",null);
        mobEquipmentMap.put("Weapon",null);

    }

    /** Construct that includes attack and defense, used for npcs/monsters */
    public Mob(String name, String description, String setting, int maxHP, int attack, int defense, String defaultWeapon, String defaultBody, String defaultLegs) {
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
        mobInventoryMap = new HashMap<String,Item>();
        mobEquipmentMap = new HashMap<String,Equipment>();

        if (GameInterface.equipmentMap.containsKey(defaultWeapon)) { equip(GameInterface.equipmentMap.get(defaultWeapon)); }
        if (GameInterface.equipmentMap.containsKey(defaultBody)) { equip(GameInterface.equipmentMap.get(defaultBody)); }
        if (GameInterface.equipmentMap.containsKey(defaultLegs)) { equip(GameInterface.equipmentMap.get(defaultLegs)); }

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
    public String getDefaultWeapon() { return defaultWeapon; }
    public String getDefaultBody() { return defaultBody; }
    public String getDefaultLegs() { return defaultLegs; }

    public void setMaxHP(int newMax) {maxHP = newMax; }
    public void setHP(int newHP) {hp = newHP; }
    public void setDefense(int newDefense) { defense = newDefense; }
    public void setAttack(int newAttack) { attack = newAttack; }
    public void setSetting(String newSetting) { setting = newSetting; }


    /** begins fight between this mob and another mob (can be monster or player character) */
    public void initiateAttack(Mob monster) {
        String myOriginalSetting = getSetting();
        String mobOriginalSetting = monster.getSetting();

        setSetting(getName() + " is here, attacking " + monster.getName());
        monster.setSetting(monster.getName() + " is fighting with " + getName());

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
                Item toDrop = mobEquipmentMap.get(eq);
                unequip(toDrop.getName());
                drop(toDrop.getName());
            }
        }

        for (String inv : mobInventoryMap.keySet()) {
            Item toDrop = mobInventoryMap.get(inv);
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
    public boolean gainItemInRoom(String itemName) {
        if (currentRoom.itemIsInRoom(itemName)) {
            if (GameInterface.equipmentMap.containsKey(itemName)) { // is a weapon or item
                Equipment mapEQ = (Equipment)currentRoom.roomItemMap.get(itemName);
                Equipment newEQ = new Equipment(mapEQ.getName(),mapEQ.getDescription(),mapEQ.getSetting(),mapEQ.getAttack(),mapEQ.getDefense(),mapEQ.getHP(),mapEQ.getEquipPlacement());
                mobInventoryMap.put(newEQ.getName().toLowerCase(),newEQ);
                currentRoom.removeItem(mapEQ);
                return true;
            }
        }

        return false;
    }

    public boolean gainItem(String itemName) {
        if (GameInterface.equipmentMap.containsKey(itemName)) { // is a weapon or item
            Equipment mapWeap = GameInterface.equipmentMap.get(itemName);
            Equipment newWeap = new Equipment(mapWeap.getName(),mapWeap.getDescription(),mapWeap.getSetting(),mapWeap.getAttack(),mapWeap.getDefense(),mapWeap.getHP(),mapWeap.getEquipPlacement());
            mobInventoryMap.put(newWeap.getName().toLowerCase(),newWeap);
            return true;
        }

        return false;
    }

    /** Shortcut to equip something. Unlike the other equip, item does not need to be in inventory.
     * Can only be called manually in code */
    public void equip(Equipment item) {
        mobEquipmentMap.put(item.getEquipPlacement(),item);
        setAttack(attack += item.getAttack());
        setDefense(defense + item.getDefense());
        setMaxHP(maxHP + item.getHP());
        setHP(hp + item.getHP());
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
            for (Equipment eq : mobEquipmentMap.values()) {
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

    /** Equip selected item. Must be in inventory */
    public boolean equip(String itemName) {

        if (isInInventory(itemName)) {
            if (GameInterface.equipmentMap.get(itemName) != null) { // It's a piece of armor or a weapon
                Equipment toEquip = (Equipment) mobInventoryMap.get(itemName);
                if (alreadyEquipped(toEquip.getEquipPlacement())) {
                    unequip(mobEquipmentMap.get(toEquip.getEquipPlacement()).getName());
                }
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

        Equipment toUnEquip;

        if (isEquipped(itemName)) {

            toUnEquip = mobEquipmentMap.get(getEquippedItemLocation(itemName));

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

    /** Remove an item from inventory, and places it in the room's inventory list */
    public boolean drop(String itemName) {

        if (isInInventory(itemName)) {
            Item itemToDrop = mobInventoryMap.get(itemName.toLowerCase());
            currentRoom.addItem(itemToDrop);
            mobInventoryMap.remove(itemName.toLowerCase());
            return true;
        }

        return false;

    }

    /** Use an item (if applicable) */
    public void use(String itemName) {
        System.out.println("Mob use() called");
    }


    /** Movement commands, to try going to the current room's north/south/east/west location */
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

    /** Sets current room to any room object */
    public void setCurrentRoom(Room newRoom) {
        if (currentRoom != null && !currentRoom.mobList.isEmpty()) {
            currentRoom.removeMob(this);
        }

        newRoom.addMob(this);
        currentRoom = newRoom;
    }

}
