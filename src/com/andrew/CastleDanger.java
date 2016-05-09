package com.andrew;

import java.util.LinkedList;

public class CastleDanger {


    public static void main(String[] args) {

        // Database db = new Database();
        // CDClient myFirstClient = new CDClient();
        // myFirstClient.connectToServer();
        World theWorld = new World();

        // Create player,
        Player player = theWorld.createPlayer();
        player.setCurrentRoom(theWorld.getRoomList().get(0));
        // Create UI object
        UserInterface UI = new UserInterface(player);

        // Commands
        UI.setting();
        UI.prompt();
        while (player.getHP() > 0) {
            UI.inputCommand();
            UI.prompt();
        }

    }
}
