package com.andrew;

import java.util.HashMap;
import java.util.LinkedList;

/** In-game monsters, aka mobs. Some details (having non-players able to move and hold inventory, etc) not yet
 * implemented but included for future development.
 * This is also a superclass of Player characters
 */

// Currently all item/equipment-related methods have been changed to have Eqpmt parameters
    // Checks for whether an item is in inventory, etc should be done in GameInterface
    // This will be more clear as we can see the check and its success or failure will result in an obvious
    // output statement - i.e. if (not an item) || (!isInInventory(item)) -> "You don't have that"
        // if (is an item) && (is in inventory) -> drop() or whatever
    // This avoids constant verification of item strings in Mob class
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


    // Inventory should probably just be a list, not a map
    // Equipment should probably just be a map, not a list
        // The equipment map should have default equip slot keys added, with null (or something) as the default value if no other default given
    protected LinkedList<Eqpmt> mobInventoryList;
//    protected HashMap<String,Eqpmt> mobEquipmentMap;
    protected HashMap<EquipSlot,Eqpmt> mobEquipmentMap;
    private String[] equipSlots = {"mainHand","Body","Legs"};


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

        canMove = true;
        mobInventoryList = new LinkedList<Eqpmt>();
        mobEquipmentMap = new HashMap<EquipSlot,Eqpmt>();
        populateEqMap();
        equipDefaults();
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
        mobInventoryList = new LinkedList<Eqpmt>();
        mobEquipmentMap = new HashMap<EquipSlot,Eqpmt>();
        populateEqMap();
        equipDefaults();

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
        mobInventoryList = new LinkedList<Eqpmt>();
        mobEquipmentMap = new HashMap<EquipSlot,Eqpmt>();
        populateEqMap();
        equipDefaults();


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

    /** Populates all eq slots with nulls */
    private void populateEqMap() {
        mobEquipmentMap.put(EquipSlot.MAINHAND,null);
        mobEquipmentMap.put(EquipSlot.BODY,null);
        mobEquipmentMap.put(EquipSlot.LEGS,null);
    }

    /** Equip default equipment.
     * Also adds equipment to the equipmentMap
     */
    private void equipDefaults() {
        equip(defaultWeapon);
        equip(defaultBody);
        equip(defaultLegs);
    }

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

    /** Mob removed from current room, all items in inventory and equipped are dropped*/
    private void die() {
        System.out.println(name + " has died");
        currentRoom.removeMob(this);

        // Unequip everything
        for (Eqpmt equippedItem : mobEquipmentMap.values()) {
            if (equippedItem != null) {
                unEquip(equippedItem);
            }
        }

        // Drop everything
        for (Eqpmt inventoryItem : mobInventoryList) {
            drop(inventoryItem);
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
    public boolean gainItemInRoom(Eqpmt item) {

        if (currentRoom.itemIsInRoom(item)) {
            mobInventoryList.add(item);
            currentRoom.removeItem(item);
            return true;
        }
        return false;
    }

    /** Gain an item, does not need to be in current room */
    public void gainItem(Eqpmt item) {
        mobInventoryList.add(item);
    }

    /** Shortcut to equip something. Unlike the other equip, item does not need to be in inventory. */
    public boolean equip(Eqpmt item) {
        if (item != null) {
            mobEquipmentMap.put(item.getEquipSlot(),item);
            setAttack(attack += item.getAttack());
            setDefense(defense + item.getDefense());
            setMaxHP(maxHP + item.getHP());
            setHP(hp + item.getHP());

            return true;
        }
        return false;
    }


    /** Equip selected item. Must be in inventory */
    public boolean equipFromInv(Eqpmt toEquip) {

        if (isInInventory(toEquip)) {
            EquipSlot eqSlot = toEquip.getEquipSlot();

            // Remove if already equipped
            if (alreadyEquipped(eqSlot)) {
                unEquip(mobEquipmentMap.get(eqSlot));
            }

            // Equip item
            mobEquipmentMap.put(toEquip.getEquipSlot(),toEquip);
            attack += toEquip.getAttack();
            defense += toEquip.getDefense();
            maxHP += toEquip.getHP();
            hp += toEquip.getHP();

            // Remove from inventory
            mobInventoryList.remove(toEquip);

            return true;
        } else {
            return false;
        }
    }

    /** Checks if something is already equipped in the selected slot */
    private boolean alreadyEquipped(EquipSlot eqLoc) {
        if (mobEquipmentMap.get(eqLoc) == null) {
            return false;
        } else {
            return true;
        }
    }

    /** Unequips selected item
     * Item moved to inventory
     * @param toUnEquip Eqpmt to unequip (must already be equipped to unequip)
     */
    public void unEquip(Eqpmt toUnEquip) {

        // Remove from eqMap
        EquipSlot eqSlot = toUnEquip.getEquipSlot();
        mobEquipmentMap.remove(eqSlot);

        // Reduce stats
        attack -= toUnEquip.getAttack();
        defense -= toUnEquip.getDefense();
        maxHP -= toUnEquip.getHP();
        hp -= toUnEquip.getHP();

        // Add to inventory
        mobInventoryList.add(toUnEquip);
    }

    /** Remove an item from inventory, and place it in the room's inventory list */
    public boolean drop(Eqpmt toDrop) {

        if (isInInventory(toDrop)) {
            currentRoom.addItem(toDrop);
            mobInventoryList.remove(toDrop);
            return true;
        }
        return false;
    }

    /** Use an item (if applicable) */
    public void use(String itemName) {
        System.out.println("Mob.use() called");
    }

    /** Check if an item is in a Mob's inventory */
    public boolean isInInventory(String itemName) {

        for (Eqpmt itemInInv : mobInventoryList) {
            if (itemInInv.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }

        return false;
    }

    /** Check if an item is in a Mob's inventory */
    public boolean isInInventory(Eqpmt item) {

        for (Eqpmt itemInInv : mobInventoryList) {
            if (item == itemInInv) {
                return true;
            }
        }

        return false;
    }

    /** Check if an item is equipped by a Mob */
    public boolean isEquipped(Eqpmt toUnEquip) {

        EquipSlot eqSlot = toUnEquip.getEquipSlot();
        if (!(mobEquipmentMap.get(eqSlot) == toUnEquip)) {
            return false;
        }

        return true;
    }

    /** Puts together a String list of items in inventory, shown by the inv command in GameInterface's commandMap */
    public String getInventoryString() {
        String invString = "";

        for (Eqpmt eqInInv : mobInventoryList) {
            invString += (eqInInv.getName() + "\n");
        }

        return invString;
    }

    /** Show currently equipped items */
    public String getEquipmentString() {
        String viewEquipment = "";

        for (String equipPlace : equipSlots) {
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

}
