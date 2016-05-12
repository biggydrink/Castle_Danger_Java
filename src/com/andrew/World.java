package com.andrew;

import java.util.HashMap;
import java.util.LinkedList;


public class World {

    public World() {

    }

    protected LinkedList<Room> createRooms() {
        LinkedList<Room> roomList = new LinkedList<>();

        Room starter = new Room("Deep Dark Forest","You are at the edge of a deep, dark forest to the north, a swampy swamp to the east, a volcano to the\nsouth, and a big fancy-looking palace to the west.");
        roomList.add(starter);

        Room darkForest0 = new Room("Deeper Into the Deep Dark Forest","You're moving deeper into the forest. It looks like the trail leads north and south.","ladybug","longsword");
        Room darkForest1 = new Room("Even Deeper Into the Deep Dark Forest","Continuing on the trail deeper into the forest. It's starting to get a little\ndarker, and the trees are looking a little bigger","ladybug","longsword");
        Room darkForest2 = new Room("So Deep Into the Deep Dark Forest","This forest really is deep!","ladybug","longsword");
        Room darkForest3 = new Room("Pretty Deep Now Into the Deep Dark Forest","It's starting to get dark now too.","ladybug","longsword");
        Room darkForest4 = new Room("Definitely Very Deep Now Into the Deep Dark Forest","You're quite deep into the forest now. The trees are so massive\nand so thick that it's pretty dark. You think you can see a little bit of a clearing ahead to the north though.","ladybug","longsword");
        Room darkForest5 = new Room("A Clearing in the Deep Dark Forest","You've found a clearing way into the forest. Looks like something big might have\nknocked down these trees.","giant ladybug","");
        roomList.add(darkForest0); roomList.add(darkForest1); roomList.add(darkForest2); roomList.add(darkForest3); roomList.add(darkForest4); roomList.add(darkForest5);

        Room swampyPlace0 = new Room("Entering the Swampy Swamp","You're entering a murky swamp. There is a path though--looks like it's taking you further\neast.","wisp","");
        Room swampyPlace1 = new Room("The Swampy Swamp","Further into the swampy swamp. There are lots of mosquitoes around!","wisp","");
        Room swampyPlace2 = new Room("The Swampy Swamp","You continue to trudge through the swampy swamp. It's pretty... swampy.","wisp","");
        Room swampyPlace3 = new Room("The Swampy Swamp","You find yourself in yet more of the swampy swampy swamp.","wisp","");
        Room swampyPlace4 = new Room("The Swampy Swamp","You're starting to get sick of the muck in this swampy swamp. But, there you are, in the thick of it","wisp","");
        Room swampyPlace5 = new Room("The Swampy Swamp","Swampy swamp swamp swamp, swampy swampy swamp swampy, swampy swampy swampy swamp. Swamp.","wisp","");
        Room swampyPlace6 = new Room("The Swampy Swamp","FINALLY, it looks like you might be coming to a clearing up ahead. Either that, or it's the swampiest\narea you've found yet!","wisp","");
        Room swampyPlace7 = new Room("The Swampiest Swamp","Foiled again! Actually this is the swampiest area you've come across yet!","swamp thing","");
        roomList.add(swampyPlace0); roomList.add(swampyPlace1); roomList.add(swampyPlace2); roomList.add(swampyPlace2); roomList.add(swampyPlace3); roomList.add(swampyPlace4); roomList.add(swampyPlace5); roomList.add(swampyPlace6); roomList.add(swampyPlace7);

        Room volcanoArea0 = new Room("Entering the Volcano","You've entered the realm of the volcano. It's quite a blasted landscape, just long fields of almost\nnothing but black, jagged rock.","golem","");
        Room volcanoArea1 = new Room("The Volcano","Your footsteps make a crunching sound as you walk across the cooled lava landscape.","golem","");
        Room volcanoArea2 = new Room("The Volcano","Your footsteps make a crunching sound as you walk across the cooled lava landscape.","golem","");
        Room volcanoArea3 = new Room("The Volcano","Your footsteps make a crunching sound as you walk across the cooled lava landscape.","golem","");
        Room volcanoArea4 = new Room("The Volcano","Your footsteps make a crunching sound as you walk across the cooled lava landscape. It's starting to get\npretty hot.","golem","");
        Room volcanoArea5 = new Room("The Volcano","The rock isn't so cool anymore, and you're starting to see pockets of lava around. It's REALLY hot\nnow, you're not sure if you can go much farther. There's a cave further to the south.","golem","");
        Room volcanoArea6 = new Room("The Volcano","Inside the cave there's treasure everywhere, rubies and gold and all the wealth you can imagine. Too bad\nthere's no money in this game.","dragon","");
        roomList.add(volcanoArea0); roomList.add(volcanoArea1); roomList.add(volcanoArea2); roomList.add(volcanoArea3); roomList.add(volcanoArea4); roomList.add(volcanoArea5); roomList.add(volcanoArea6);

        Room fancyPalace0 = new Room("Entering the Fancy Palace","As you walk into the palace, you become acutely aware of your sweaty, unkempt nature. I mean,\nit's understandable, but really, you DO look out of place. You try to wipe your feet on the welcome mat.","guard","");
        Room fancyPalace1 = new Room("The Fancy Palace","You find yourself distracted by the giant tapestries and other fancy furnishings in this place. It's\nreally quite... fancy.","guard","");
        Room fancyPalace2 = new Room("The Fancy Palace","You find yourself distracted by the giant tapestries and other fancy furnishings in this place. It's\nreally quite... fancy.","guard","");
        Room fancyPalace3 = new Room("The Fancy Palace","You find yourself distracted by the giant tapestries and other fancy furnishings in this place. It's\nreally quite... fancy.","guard","");
        Room fancyPalace4 = new Room("The Fancy Palace","You find yourself distracted by the giant tapestries and other fancy furnishings in this place. It's\nreally quite... fancy.","guard","");
        Room fancyPalace5 = new Room("The Fancy Palace","You didn't think it was possible, but this place starts to get MORE fancy (and pretentious).","guard","");
        Room fancyPalace6 = new Room("The Fancy Palace","It's getting really opulent in here. Somebody apparently thinks they are a pretty big deal.","guard","");
        Room fancyPalace7 = new Room("The Fancy Palace","The fanciest doors you've ever seen are straight ahead of you.","guard","");
        Room fancyPalace8 = new Room("The Fancy Palace","You've reached the throme room of the Fancy Palace. This is the fanciest fancy pants place you've ever\nseen or even heard of.","evil king","");
        roomList.add(fancyPalace0); roomList.add(fancyPalace1); roomList.add(fancyPalace2); roomList.add(fancyPalace3); roomList.add(fancyPalace4); roomList.add(fancyPalace5); roomList.add(fancyPalace6); roomList.add(fancyPalace7); roomList.add(fancyPalace8);

        starter.setNorth(darkForest0); starter.setSouth(volcanoArea0); starter.setEast(swampyPlace0); starter.setWest(fancyPalace0);

        darkForest0.setSouth(starter); darkForest0.setNorth(darkForest1);
        darkForest1.setSouth(darkForest0); darkForest1.setNorth(darkForest2);
        darkForest2.setSouth(darkForest1); darkForest2.setNorth(darkForest3);
        darkForest3.setSouth(darkForest2); darkForest3.setNorth(darkForest4);
        darkForest4.setSouth(darkForest3); darkForest4.setNorth(darkForest5);
        darkForest5.setSouth(darkForest4);

        swampyPlace0.setWest(starter); swampyPlace0.setEast(swampyPlace1);
        swampyPlace1.setWest(swampyPlace0); swampyPlace1.setEast(swampyPlace2);
        swampyPlace2.setWest(swampyPlace1); swampyPlace2.setEast(swampyPlace3);
        swampyPlace3.setWest(swampyPlace2); swampyPlace3.setEast(swampyPlace4);
        swampyPlace4.setWest(swampyPlace3); swampyPlace4.setEast(swampyPlace5);
        swampyPlace5.setWest(swampyPlace4); swampyPlace5.setEast(swampyPlace6);
        swampyPlace6.setWest(swampyPlace5); swampyPlace6.setEast(swampyPlace7);
        swampyPlace7.setWest(swampyPlace6);

        volcanoArea0.setNorth(starter); volcanoArea0.setSouth(volcanoArea1);
        volcanoArea1.setNorth(volcanoArea0); volcanoArea1.setSouth(volcanoArea2);
        volcanoArea2.setNorth(volcanoArea1); volcanoArea2.setSouth(volcanoArea3);
        volcanoArea3.setNorth(volcanoArea2); volcanoArea3.setSouth(volcanoArea4);
        volcanoArea4.setNorth(volcanoArea3); volcanoArea4.setSouth(volcanoArea5);
        volcanoArea5.setNorth(volcanoArea4); volcanoArea5.setSouth(volcanoArea6);
        volcanoArea6.setNorth(volcanoArea5);

        fancyPalace0.setEast(starter); fancyPalace0.setWest(fancyPalace1);
        fancyPalace1.setEast(fancyPalace0); fancyPalace1.setWest(fancyPalace2);
        fancyPalace2.setEast(fancyPalace1); fancyPalace2.setWest(fancyPalace3);
        fancyPalace3.setEast(fancyPalace2); fancyPalace3.setWest(fancyPalace4);
        fancyPalace4.setEast(fancyPalace3); fancyPalace4.setWest(fancyPalace5);
        fancyPalace5.setEast(fancyPalace4); fancyPalace5.setWest(fancyPalace6);
        fancyPalace6.setEast(fancyPalace5); fancyPalace6.setWest(fancyPalace7);
        fancyPalace7.setEast(fancyPalace6); fancyPalace7.setWest(fancyPalace8);
        fancyPalace8.setEast(fancyPalace7);

        return roomList;

    }

    protected HashMap<String,Mob> createMobs() {

        HashMap<String,Mob> mobMap = new HashMap<>();


        Mob ladybug = new Mob("Ladybug","A cute ladybug with little red spots.","There is a ladybug here",20,5,0);
        mobMap.put(ladybug.getName().toLowerCase(),ladybug);
        mobMap.put("bug",ladybug);

        Mob superLadybug = new Mob("Giant Ladybug","This ladybug is HUGE, with a gaping mouth that looks like it wants to eat you for lunch.","Whoa, a giant ladybug is here!! How did it get so huge? Why does it have a sword?!?",50,8,2,"longsword","polkadot shirt","polkadot pants");
        mobMap.put(superLadybug.getName().toLowerCase(),superLadybug);
        mobMap.put("super",superLadybug);
        mobMap.put("giant",superLadybug);

        Mob willowisp = new Mob("Wisp","You see nothing more than a floating blue flame.","A ghostly will'o wisp is floating here",50,10,1);
        mobMap.put(willowisp.getName().toLowerCase(),willowisp);
        mobMap.put("flame",willowisp);
        mobMap.put("willo",willowisp);
        mobMap.put("willo wisp",willowisp);
        mobMap.put("will o wisp",willowisp);

        Mob swampThing = new Mob("Swamp Thing","Looks just like the monster from the movie, cool!","The Swamp Thing is here!",100,12,5,"green sword","oozy shirt","oozy pants");
        mobMap.put(swampThing.getName().toLowerCase(),swampThing);
        mobMap.put("swamp",swampThing);
        mobMap.put("thing",swampThing);

        Mob volcanoGolem = new Mob("Golem","Looks like magma and rock come to life","A volcano golem is here",75,8,10);
        mobMap.put(volcanoGolem.getName().toLowerCase(),volcanoGolem);
        mobMap.put("volcano",volcanoGolem);

        Mob blackDragon = new Mob("Black Dragon","A huge, black dragon!","A... a black dragon is here!",200,22,15,"black sword","charred leather vest","charred leather pants");
        mobMap.put(blackDragon.getName().toLowerCase(),blackDragon);
        mobMap.put("black",blackDragon);
        mobMap.put("dragon",blackDragon);

        Mob foppishGuard = new Mob("Guard","This guard looks a liiiitle bit too concerned with his appearance","A foppish palace guard is here, fixing his hair in the mirror",125,20,15,"longsword","","");
        mobMap.put(foppishGuard.getName().toLowerCase(),foppishGuard);
        mobMap.put("foppish",foppishGuard);
        mobMap.put("palace guard",foppishGuard);
        mobMap.put("foppish guard",foppishGuard);

        Mob evilKing = new Mob("Evil King","","The Corrupt and Nasty King (of destruction-ness and killing) is here, waiting for you to FINISH HIM",300,35,20,"shiny broadsword","shiny breastplate","shiny legplates");
        mobMap.put(evilKing.getName().toLowerCase(),evilKing);
        mobMap.put("king",evilKing);
        mobMap.put("corrupt king",evilKing);
        mobMap.put("nasty king",evilKing);

        return mobMap;
    }

    protected HashMap<String,Equipment> createEquipment() {

        // Equipment constructor arguments: (String name, String description, String setting, int attack, int defense, int hp, String equipPlacement)

        HashMap<String,Equipment> equipmentMap = new HashMap<String,Equipment>();

        Equipment balloonSword = new Equipment("balloon Animal Sword","It's a \"sword\" made out of those skinny balloons that balloon animals are made out of. You really should probably find something else to use if you can..","A balloon animal sword is lying here",1,0,0,"Weapon");
        equipmentMap.put(balloonSword.getName().toLowerCase(),balloonSword);
        equipmentMap.put("balloon",balloonSword);
        equipmentMap.put("balloon sword",balloonSword);

        Equipment longSword = new Equipment("Longsword","Nice, an actual sword, with a blade and everything!","A regular longsword is lying here",4,0,0,"Weapon");
        equipmentMap.put(longSword.getName().toLowerCase(),longSword);
        equipmentMap.put("long",longSword);

        Equipment greenSword = new Equipment("Green Sword","There's no reason why a green sword would be better than a longsword, but it is","A nice green sword is lying here",7,0,0,"Weapon");
        equipmentMap.put(greenSword.getName().toLowerCase(),greenSword);
        equipmentMap.put("green",greenSword);
        equipmentMap.put("greensword",greenSword);

        Equipment blackSword = new Equipment("Blackened Sword","This sword didn't used to be black, but it's previous owner was a 5-packs-a-day smoker","A smokey looking, blackened sword is lying here",11,0,0,"Weapon");
        equipmentMap.put(blackSword.getName().toLowerCase(),blackSword);
        equipmentMap.put("black",blackSword);
        equipmentMap.put("blackened",blackSword);
        equipmentMap.put("black sword",blackSword);

        Equipment shinyBroadsword = new Equipment("Shiny Broadsword","This broadsword looks like it's been polished kind of an unreasonable amount","A very shiny broadsword lies here",15,0,0,"Weapon");
        equipmentMap.put(shinyBroadsword.getName().toLowerCase(),shinyBroadsword);
        equipmentMap.put("shiny",shinyBroadsword);
        equipmentMap.put("broad",shinyBroadsword);

        Equipment righteousSword = new Equipment("Righteous Sword","Righteous, dude","Duuuude, a Righteous Sword is on the floor",100,100,100,"Weapon");
        equipmentMap.put(righteousSword.getName().toLowerCase(),righteousSword);


        // CREATE ARMOR START

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


        Equipment shabbyShirt = new Equipment("Shabby Shirt","A pretty shabby shirt. Should probably shrug this off shoon. Soon.","A shabby shirt lies crumpled on the floor",0,1,0,"Body");
        equipmentMap.put("shabby",shabbyShirt);
        equipmentMap.put("shabby shirt",shabbyShirt);

        Equipment flimsyPants = new Equipment("Flimsy Pants","Looks like you might want to avoid windy areas if you want these to stay on","A flimsy looking pair of pants is on the floor",0,1,0,"Legs");
        equipmentMap.put("flimsy",flimsyPants);
        equipmentMap.put("flimsy pants",flimsyPants);

        Equipment polkadotShirt = new Equipment("Polkadot Shirt","Polkadots! On your shirt! Neat!","Polkadot shirt! In the dirt!",0,2,5,"Body");
        equipmentMap.put("polkadot shirt",polkadotShirt);
        equipmentMap.put("polka",polkadotShirt);

        Equipment polkadotPants = new Equipment("Polkadot Pants","Polkadots! On your pants! Great!","Polkadot pants! On the floor!",0,2,5,"Legs");
        equipmentMap.put("polkadot pants",polkadotPants);

        Equipment oozyShirt = new Equipment("Oozy Shirt","Oozes like a swamp.","An oozy shirt is lying in a puddle on the floor",0,5,0,"Body");
        equipmentMap.put("oozy shirt",oozyShirt);
        equipmentMap.put("puddle",oozyShirt);

        Equipment oozyPants = new Equipment("Oozy Pants","These are getting everything they touch a little slimy","Some oozy pants are here in a puddle of ooze",0,4,0,"Legs");
        equipmentMap.put("ooze",oozyPants);
        equipmentMap.put("oozy pants",oozyPants);

        Equipment charredLeatherVest = new Equipment("Charred Leather Vest","Somehow this vest stayed together despite being clearly burned","A charred, crusty leather vest is here",0,7,15,"Body");
        equipmentMap.put(charredLeatherVest.getName().toLowerCase(),charredLeatherVest);
        equipmentMap.put("charred vest",charredLeatherVest);
        equipmentMap.put("vest",charredLeatherVest);
        equipmentMap.put("burned",charredLeatherVest);

        Equipment charredLeatherPants = new Equipment("Charred Leather Pants","The char makes these pants pretty stiff","A charred pair of leather pants lies on the ground",0,5,10,"Legs");
        equipmentMap.put("charred pants",charredLeatherPants);
        equipmentMap.put("charred leather pants",charredLeatherPants);

        Equipment shinyNewBreastplate = new Equipment("Shiny Breastplate","This armor doubles as a festive mirror!","A shiny reflective breastplate is here",0,10,5,"Body");
        equipmentMap.put("shiny breastplate",shinyNewBreastplate);
        equipmentMap.put("breastplate",shinyNewBreastplate);
        equipmentMap.put("reflective",shinyNewBreastplate);

        Equipment shinyNewLegplates = new Equipment("Shiny Legplates","So shiny they make you nervous about wearing them outside","Some super shiny legplates are on the floor here",0,8,3,"Legs");
        equipmentMap.put("legplates",shinyNewLegplates);
        equipmentMap.put("shiny legplates",shinyNewLegplates);


        return equipmentMap;
    }


    /*
    weapon
    body
    legs

    head
    feet
    hands
    waist
    ring1
    ring2
    off_hand
    wrist
     */

    /*
    protected HashMap<String,Equipment> createArmor() {

        HashMap<String,Equipment> armorMap = new HashMap<String,Equipment>();

        // Equipment constructor arguments: (String name, String description, String setting, int attack, int defense, int hp, String equipPlacement)

        Equipment shabbyShirt = new Equipment("Shabby Shirt","A pretty shabby shirt. Should probably shrug this off shoon. Soon.","A shabby shirt lies crumpled on the floor",0,1,0,"Body");
        armorMap.put("shabby",shabbyShirt);
        armorMap.put("shabby shirt",shabbyShirt);

        Equipment flimsyPants = new Equipment("Flimsy Pants","Looks like you might want to avoid windy areas if you want these to stay on","A flimsy looking pair of pants is on the floor",0,1,0,"Legs");
        armorMap.put("flimsy",flimsyPants);
        armorMap.put("flimsy pants",flimsyPants);

        Equipment polkadotShirt = new Equipment("Polkadot Shirt","Polkadots! On your shirt! Neat!","Polkadot shirt! In the dirt!",0,2,5,"Body");
        armorMap.put("polkadot shirt",polkadotShirt);
        armorMap.put("polka",polkadotShirt);

        Equipment polkadotPants = new Equipment("Polkadot Pants","Polkadots! On your pants! Great!","Polkadot pants! On the floor!",0,2,5,"Legs");
        armorMap.put("polkadot pants",polkadotPants);

        Equipment oozyShirt = new Equipment("Oozy Shirt","Oozes like a swamp.","An oozy shirt is lying in a puddle on the floor",0,5,0,"Body");
        armorMap.put("oozy shirt",oozyShirt);
        armorMap.put("puddle",oozyShirt);

        Equipment oozyPants = new Equipment("Oozy Pants","These are getting everything they touch a little slimy","Some oozy pants are here in a puddle of ooze",0,4,0,"Legs");
        armorMap.put("ooze",oozyPants);
        armorMap.put("oozy pants",oozyPants);

        Equipment charredLeatherVest = new Equipment("Charred Leather Vest","Somehow this vest stayed together despite being clearly burned","A charred, crusty leather vest is here",0,7,15,"Body");
        armorMap.put(charredLeatherVest.getName().toLowerCase(),charredLeatherVest);
        armorMap.put("charred vest",charredLeatherVest);
        armorMap.put("vest",charredLeatherVest);
        armorMap.put("burned",charredLeatherVest);

        Equipment charredLeatherPants = new Equipment("Charred Leather Pants","The char makes these pants pretty stiff","A charred pair of leather pants lies on the ground",0,5,10,"Legs");
        armorMap.put("charred pants",charredLeatherPants);
        armorMap.put("charred leather pants",charredLeatherPants);

        Equipment shinyNewBreastplate = new Equipment("Shiny Breastplate","This armor doubles as a festive mirror!","A shiny reflective breastplate is here",0,10,5,"Body");
        armorMap.put("shiny breastplate",shinyNewBreastplate);
        armorMap.put("breastplate",shinyNewBreastplate);
        armorMap.put("reflective",shinyNewBreastplate);

        Equipment shinyNewLegplates = new Equipment("Shiny Legplates","So shiny they make you nervous about wearing them outside","Some super shiny legplates are on the floor here",0,8,3,"Legs");
        armorMap.put("legplates",shinyNewLegplates);
        armorMap.put("shiny legplates",shinyNewLegplates);

        return armorMap;
    }
*/
}
