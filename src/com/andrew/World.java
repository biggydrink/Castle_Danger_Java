package com.andrew;

import java.util.LinkedList;
import java.util.Scanner;


public class World {

    LinkedList<Player> playerList;
    static Scanner mainScanner = new Scanner(System.in);

    public World() {
        playerList = new LinkedList<Player>();
        Player firstPlayer = createPlayer();
        UserInterface UI = new UserInterface(firstPlayer);

        // Rooms
        LinkedList<Room> roomList = createRooms();
        firstPlayer.setCurrentRoom(roomList.get(0));

        // Commands
        UI.setting();
        UI.prompt();
        while (firstPlayer.getHP() > 0) {
            UI.inputCommand();
            UI.prompt();
        }

    }

    /** User selects a name for their character, character created and returned */
    protected Player createPlayer() {
        String newName;
        System.out.println("What is your name, Mr/Ms Hero?");
        newName = mainScanner.nextLine();
        Player newPlayerChar = new Player(newName);
        System.out.println("Welcome to Castle Danger, " + newName);

        playerList.push(newPlayerChar);

        return newPlayerChar;
    }

    private LinkedList<Room> createRooms() {
        LinkedList<Room> roomList = new LinkedList<>();

        Room starter = new Room("Deep Dark Forest","You are at the edge of a deep, dark forest to the north, a swampy swamp to the east, a volcano to the\nsouth, and a big fancy-looking palace to the west.");
        roomList.add(starter);

        Room darkForest0 = new Room("Deeper Into the Deep Dark Forest","You're moving deeper into the forest. It looks like the trail leads north and south.");
        Room darkForest1 = new Room("Even Deeper Into the Deep Dark Forest","Continuing on the trail deeper into the forest. It's starting to get a little\n darker, and the trees are looking a little bigger");
        Room darkForest2 = new Room("So Deep Into the Deep Dark Forest","This forest really is deep!");
        Room darkForest3 = new Room("Pretty Deep Now Into the Deep Dark Forest","It's starting to get dark now too.");
        Room darkForest4 = new Room("Definitely Very Deep Now Into the Deep Dark Forest","You're quite deep into the forest now. The trees are so massive\nand so thick that it's pretty dark. You think you can see a little bit of a clearing ahead to the north though.");
        Room darkForest5 = new Room("A Clearing in the Deep Dark Forest","You've found a clearing way into the forest. Looks like something big might have\nknocked down these trees.");
        roomList.add(darkForest0); roomList.add(darkForest1); roomList.add(darkForest2); roomList.add(darkForest3); roomList.add(darkForest4); roomList.add(darkForest5);

        Room swampyPlace0 = new Room("Entering the Swampy Swamp","You're entering a murky swamp. There is a path though--looks like it's taking you further\neast.");
        Room swampyPlace1 = new Room("The Swampy Swamp","Further into the swampy swamp. There are lots of mosquitoes around!");
        Room swampyPlace2 = new Room("The Swampy Swamp","You continue to trudge through the swampy swamp. It's pretty... swampy.");
        Room swampyPlace3 = new Room("The Swampy Swamp","You find yourself in yet more of the swampy swampy swamp.");
        Room swampyPlace4 = new Room("The Swampy Swamp","You're starting to get sick of the muck in this swampy swamp. But, there you are, in the thick of it");
        Room swampyPlace5 = new Room("The Swampy Swamp","Swampy swamp swamp swamp, swampy swampy swamp swampy, swampy swampy swampy swamp. Swamp.");
        Room swampyPlace6 = new Room("The Swampy Swamp","FINALLY, it looks like you might be coming to a clearing up ahead. Either that, or it's the swampiest\narea you've found yet!");
        Room swampyPlace7 = new Room("The Swampiest Swamp","Foiled again! Actually this is the swampiest area you've come across yet!");
        roomList.add(swampyPlace0); roomList.add(swampyPlace1); roomList.add(swampyPlace2); roomList.add(swampyPlace2); roomList.add(swampyPlace3); roomList.add(swampyPlace4); roomList.add(swampyPlace5); roomList.add(swampyPlace6); roomList.add(swampyPlace7);

        Room volcanoArea0 = new Room("Entering the Volcano","You've entered the realm of the volcano. It's quite a blasted landscape, just long fields of almost\nnothing but black, jagged rock.");
        Room volcanoArea1 = new Room("The Volcano","Your footsteps make a crunching sound as you walk across the cooled lava landscape.");
        Room volcanoArea2 = new Room("The Volcano","Your footsteps make a crunching sound as you walk across the cooled lava landscape.");
        Room volcanoArea3 = new Room("The Volcano","Your footsteps make a crunching sound as you walk across the cooled lava landscape.");
        Room volcanoArea4 = new Room("The Volcano","Your footsteps make a crunching sound as you walk across the cooled lava landscape. It's starting to get\npretty hot.");
        Room volcanoArea5 = new Room("The Volcano","It's REALLY hot now, you're not sure if you can go much farther. There's a cave further to the south.");
        Room volcanoArea6 = new Room("The Volcano","Inside the cave there's treasure everywhere, rubies and gold and all the wealth you can imagine. Too bad\nthere's no money in this game.");
        roomList.add(volcanoArea0); roomList.add(volcanoArea1); roomList.add(volcanoArea2); roomList.add(volcanoArea3); roomList.add(volcanoArea4); roomList.add(volcanoArea5); roomList.add(volcanoArea6);

        Room fancyPalace0 = new Room("Entering the Fancy Palace","As you walk into the palace, you become acutely aware of your sweaty, unkempt nature. I mean,\nit's understandable, but really, you DO look out of place. You try to wipe your feet on the welcome mat.");
        Room fancyPalace1 = new Room("The Fancy Palace","You find yourself distracted by the giant tapestries and other fancy furnishings in this place. It's\nreally quite... fancy.");
        Room fancyPalace2 = new Room("The Fancy Palace","You find yourself distracted by the giant tapestries and other fancy furnishings in this place. It's\nreally quite... fancy.");
        Room fancyPalace3 = new Room("The Fancy Palace","You find yourself distracted by the giant tapestries and other fancy furnishings in this place. It's\nreally quite... fancy.");
        Room fancyPalace4 = new Room("The Fancy Palace","You find yourself distracted by the giant tapestries and other fancy furnishings in this place. It's\nreally quite... fancy.");
        Room fancyPalace5 = new Room("The Fancy Palace","You didn't think it was possible, but this place starts to get MORE fancy (and pretentious).");
        Room fancyPalace6 = new Room("The Fancy Palace","It's getting really opulent in here. Somebody apparently thinks they are a pretty big deal.");
        Room fancyPalace7 = new Room("The Fancy Palace","The fanciest doors you've ever seen are straight ahead of you.");
        Room fancyPalace8 = new Room("The Fancy Palace","You've reached the throme room of the Fancy Palace. This is the fanciest fancy pants place you've ever\nseen or even heard of.");
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


}
