package com.andrew;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TimerTask;

/** Tasks to be run every tick, tick timer set in Interface.java.
 * Typically:
 *  increase all player's HP by 25% each tick
 *  create all room default mobs/items every 10 ticks
 *
 * Tick default is 1 minute
 */
public class GameClock extends TimerTask {

    /*
    protected static LinkedList<Player> playerList;
    protected static HashMap<String,Equipment> equipmentMap;
    protected static HashMap<String,Mob> mobMap;
    protected static LinkedList<Room> roomList;


*/
    private long counter;


    public GameClock() {
        /*
        playerList = Interface.playerList;
        equipmentMap = Interface.equipmentMap;
        HashMap<String,Mob> mobMap = Interface.mobMap;
        LinkedList<Room> roomList = Interface.roomList;

        */

        counter = 0;
    }

    @Override
    public void run() {
        // This method is called every clock tick

        ++ counter;

        for (Player player : Interface.playerList) {
            player.setHP(player.getHP() + (int)(player.getHP() * .25));
            if (player.getHP() > player.getMaxHP()) {
                player.setHP(player.getMaxHP());
            }
        }


        if (counter % 5 == 0) {

            for (Room room : Interface.roomList) {
                // Create default items and mobs if they're no longer in the room
                for (String defaultMob : room.defaultMobTypes) {
                    if (!room.mobIsInRoom(defaultMob)) { // mob is no longer in room's roomMobList
                        room.createMob(defaultMob);
                    }
                }
                for (String defaultItem : room.defaultItemTypes) {
                    if (!room.itemIsInRoom(defaultItem)) { // item is no logner in room's roomItemList
                        room.createItem(defaultItem);
                    }
                }
            }
        }


    }
}
