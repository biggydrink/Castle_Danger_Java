package com.andrew;

import java.util.LinkedList;

/**
 * Created by Andrew on 4/25/2017.
 */
public enum Eqpmt {

        /*
    Current equipment slots:
    weapon
    body
    legs

    Potential future equipment slots:
    head
    feet
    hands
    waist
    ring1
    ring2
    off_hand
    wrist
     */


    BALLOONSWORD("BALLOONSWORD","balloon Animal Sword","balloon animal sword,balloon,sword","It's a \"sword\" made out of those skinny balloons that balloon animals are made out of. You really should probably find something else to use if you can..","A balloon animal sword is lying here",1,0,0,EquipSlot.MAINHAND),
    LONGSWORD("LONGSWORD","Longsword","longsword,sword","An actual sword, with a blade and everything!","A regular longsword is lying here",8,0,0,EquipSlot.MAINHAND),
    GREENSWORD("GREENSWORD","Green Sword","green sword,sword","There's no reason why a green sword would be better than a longsword, but it is","A nice green sword is lying here",11,0,0,EquipSlot.MAINHAND),
    BLACKSWORD("BLACKSWORD","Blackened Sword","blackened sword,black sword,black,sword","This sword didn't used to be black, but it's previous owner was a 5-packs-a-day smoker","A smokey looking, blackened sword is lying here",15,0,0,EquipSlot.MAINHAND),
    SHINYBROADSWORD("SHINYBROADSWORD","Shiny Broadsword","shiny broadsword,broadsword,sword","This broadsword looks like it's been polished kind of an unreasonable amount","A very shiny broadsword lies here",20,0,0,EquipSlot.MAINHAND),
    RIGHTEOUSSWORD("RIGHTEOUSSWORD","Righteous Sword","righteous sword,righteous,sword","Righteous, dude","Duuuude, a Righteous Sword is on the floor",100,100,100,EquipSlot.MAINHAND),
    // CREATE WEAPONS END
    // CREATE ARMOR START
    SHABBYSHIRT("SHABBYSHIRT","Shabby Shirt","shabby shirt,shabby","A pretty shabby shirt. Should probably shrug this off shoon. Soon.","A shabby shirt lies crumpled on the floor",0,1,0,EquipSlot.BODY),
    FLIMSYPANTS("FLIMSYPANTS","Flimsy Pants","flimsy pants, flimsy, pants","Looks like you might want to avoid windy areas if you want these to stay on","A flimsy looking pair of pants is on the floor",0,1,0,EquipSlot.LEGS),
    POLKADOTSHIRT("POLKADOTSHIRT","Polkadot Shirt","polkadot shirt,polkadot,shirt","Polkadots! On your shirt! Neat!","Polkadot shirt! In the dirt!",0,2,5,EquipSlot.BODY),
    POLKADOTPANTS("POLKADOTPANTS","Polkadot Pants","polkadot pants,polkadot,pants","Polkadots! On your pants! Great!","Polkadot pants! On the floor!",0,2,5,EquipSlot.LEGS),
    OOZYSHIRT("OOZYSHIRT","Oozy Shirt","oozy shirt,oozy,shirt","Oozes like a swamp.","An oozy shirt is lying in a puddle on the floor",0,5,0,EquipSlot.BODY),
    OOZYPANTS("OOZYPANTS","Oozy Pants","oozy pants,oozy,pants","These are getting everything they touch a little slimy","Some oozy pants are here in a puddle of ooze",0,4,0,EquipSlot.LEGS),
    CHARREDLEATHERVEST("CHARREDLEATHERVEST","Charred Leather Vest","charred leather vest,charred vest,charred,leather,vest","Somehow this vest stayed together despite being clearly burned","A charred, crusty leather vest is here",0,7,15,EquipSlot.BODY),
    CHARREDLEATHERPANTS("CHARREDLEATHERPANTS","Charred Leather Pants","charred leather pants,charred pants,charred,leather,pants","The char makes these pants pretty stiff","A charred pair of leather pants lies on the ground",0,5,10,EquipSlot.LEGS),
    SHINYNEWBREASTPLATE("SHINYNEWBREASTPLATE","Shiny Breastplate","shiny breastplate,shiny,breastplate","This armor doubles as a festive mirror!","A shiny reflective breastplate is here",0,10,5,EquipSlot.BODY),
    SHINYNEWLEGPLATES("SHINYNEWLEGPLATES","Shiny Legplates","shiny legplates,shiny,legplates","So shiny they make you nervous about wearing them outside","Some super shiny legplates are on the floor here",0,8,3,EquipSlot.LEGS);


    // Variables
    protected String variableName;
    protected String name;
    protected String description;
    protected String setting;
    int attack;
    int defense;
    int hp;
    EquipSlot equipSlot;
    protected String searchValues;


    Eqpmt(String variableName, String name, String searchValues, String description, String setting, int attack, int defense, int hp, EquipSlot equipSlot) {
        this.variableName = variableName;
        this.name = name;
        this.description = description;
        this.setting = setting;
        this.defense = defense;
        this.hp = hp;
        this.equipSlot = equipSlot;
        this.attack = attack;
        this.searchValues = searchValues;
    }

    public String getSetting() { return setting; }
    public String getVariableName() {return variableName; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getHP() { return hp; }
    public EquipSlot getEquipSlot() { return equipSlot; }

    // Enum.valueOf() throws:
    //      an IllegalArgumentException if itemName is not a valid enum
    //      a NullPointerException if itemName is null

    /** returns true if the string corresponds to an enum constant */
    public static boolean enumExists(String enumName) {
        for (Eqpmt eq : values()) {
            if (enumName.equals(eq.variableName)) {
                return true;
            }
        }
        return false;
    }

    /** returns true if given arg corresponds to a lookString in at least one Eqpmt */
    public static LinkedList<Eqpmt> searchItems(String query) {
        LinkedList<Eqpmt> results = new LinkedList<Eqpmt>();

        for (Eqpmt eq : values()) {
            for (String descrWord : eq.searchValues.split(",")) {
                if (query.equalsIgnoreCase(descrWord)) {
                    results.add(eq);
                }
            }
        }
        return results;
    }

}