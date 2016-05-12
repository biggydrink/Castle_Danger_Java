package com.andrew;

import java.util.TimerTask;

/** Tasks to be run every tick, tick timer set in Interface.java.
 * Typically:
 *  increase all player's HP by 25% each tick
 *  create all room default mobs/items every 10 ticks
 *
 * Recommended tick time is 1 minute, but can be increased for presentation purposes.
 * Use Interface.clockInterval to change
 */
public class GameClock extends TimerTask {

    private long counter;

    public GameClock() {
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

        // Populate game again every x number of ticks
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

            // Future plans to also pre-emptively save all players
        }


    }
}
