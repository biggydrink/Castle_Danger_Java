package com.andrew;

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


    BALLOONSWORD("BALLOONSWORD","balloon Animal Sword","It's a \"sword\" made out of those skinny balloons that balloon animals are made out of. You really should probably find something else to use if you can..","A balloon animal sword is lying here",1,0,0,"Weapon"),
    LONGSWORD("LONGSWORD","Longsword","Nice, an actual sword, with a blade and everything!","A regular longsword is lying here",8,0,0,"Weapon"),
    GREENSWORD("GREENSWORD","Green Sword","There's no reason why a green sword would be better than a longsword, but it is","A nice green sword is lying here",11,0,0,"Weapon"),
    BLACKSWORD("BLACKSWORD","Blackened Sword","This sword didn't used to be black, but it's previous owner was a 5-packs-a-day smoker","A smokey looking, blackened sword is lying here",15,0,0,"Weapon"),
    SHINYBROADSWORD("SHINYBROADSWORD","Shiny Broadsword","This broadsword looks like it's been polished kind of an unreasonable amount","A very shiny broadsword lies here",20,0,0,"Weapon"),
    RIGHTEOUSSWORD("RIGHTEOUSSWORD","Righteous Sword","Righteous, dude","Duuuude, a Righteous Sword is on the floor",100,100,100,"Weapon"),
    // CREATE WEAPONS END
    // CREATE ARMOR START
    SHABBYSHIRT("SHABBYSHIRT","Shabby Shirt","A pretty shabby shirt. Should probably shrug this off shoon. Soon.","A shabby shirt lies crumpled on the floor",0,1,0,"Body"),
    FLIMSYPANTS("FLIMSYPANTS","Flimsy Pants","Looks like you might want to avoid windy areas if you want these to stay on","A flimsy looking pair of pants is on the floor",0,1,0,"Legs"),
    POLKADOTSHIRT("POLKADOTSHIRT","Polkadot Shirt","Polkadots! On your shirt! Neat!","Polkadot shirt! In the dirt!",0,2,5,"Body"),
    POLKADOTPANTS("POLKADOTPANTS","Polkadot Pants","Polkadots! On your pants! Great!","Polkadot pants! On the floor!",0,2,5,"Legs"),
    OOZYSHIRT("OOZYSHIRT","Oozy Shirt","Oozes like a swamp.","An oozy shirt is lying in a puddle on the floor",0,5,0,"Body"),
    OOZYPANTS("OOZYPANTS","Oozy Pants","These are getting everything they touch a little slimy","Some oozy pants are here in a puddle of ooze",0,4,0,"Legs"),
    CHARREDLEATHERVEST("CHARREDLEATHERVEST","Charred Leather Vest","Somehow this vest stayed together despite being clearly burned","A charred, crusty leather vest is here",0,7,15,"Body"),
    CHARREDLEATHERPANTS("CHARREDLEATHERPANTS","Charred Leather Pants","The char makes these pants pretty stiff","A charred pair of leather pants lies on the ground",0,5,10,"Legs"),
    SHINYNEWBREASTPLATE("SHINYNEWBREASTPLATE","Shiny Breastplate","This armor doubles as a festive mirror!","A shiny reflective breastplate is here",0,10,5,"Body"),
    SHINYNEWLEGPLATES("SHINYNEWLEGPLATES","Shiny Legplates","So shiny they make you nervous about wearing them outside","Some super shiny legplates are on the floor here",0,8,3,"Legs");


    // Variables
    protected String variableName;
    protected String name;
    protected String description;
    protected String setting;
    int attack;
    int defense;
    int hp;
    String equipPlacement;


    Eqpmt(String variableName, String name, String description, String setting, int attack, int defense, int hp, String equipPlacement) {
        this.variableName = variableName;
        this.name = name;
        this.description = description;
        this.setting = setting;
        this.defense = defense;
        this.hp = hp;
        this.equipPlacement = equipPlacement;
        this.attack = attack;
    }

    public String getSetting() { return setting; }
    public String getVariableName() {return variableName; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getHP() { return hp; }
    public String getEquipPlacement() { return equipPlacement; }

    // Enum.valueOf() throws:
    //      an IllegalArgumentException if itemName is not a valid enum
    //      a NullPointerException if itemName is null

    /** returns true if the string corresponds to an enum constant */
    // TODO make sure this method is used in all places where global equipmentMap used to be checked
    public static boolean enumExists(String enumName) {
        for (Eqpmt eq : values()) {
            if (enumName.equals(eq.variableName)) {
                return true;
            }
        }
        return false;
    }

}