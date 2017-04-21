package com.andrew;

import java.util.TimerTask;

/** Tasks to be run every tick, tick timer set in GameInterface.java.
 * Typically:
 *  increase all player's HP by playerGainHpPercent each tick
 *  create all room default mobs/items every tickCounter ticks
 *
 * Recommended tick time is 1 minute, but can be increased for presentation purposes.
 * Use GameInterface.clockInterval to change
 */
public class GameClock extends TimerTask {

    private long counter;
    private double playerGainHpPercent;
    private int tickLimit;
    protected long tickLength;

    public GameClock() {

        counter = 0;
        playerGainHpPercent = 0.07;
        tickLength = 10000; //milliseconds, 1000 = 1 second
        tickLimit = 2;
    }

    @Override
    public void run() {
        // This method is called every clock tick (see tickLength for timing)
        ++ counter;

        for (Player player : GameInterface.playerList) {
            player.setHP(player.getHP() + (int)(player.getHP() * playerGainHpPercent));
        }

        // Populate game again every tickCounter number of ticks
        if (counter % tickLimit == 0) {
            counter = 0;

            for (Room room : GameInterface.roomList) {
                // Create default items and mobs if they're no longer in the room
                for (String defaultMob : room.defaultMobTypes) {
                    if (!room.mobIsInRoom(defaultMob)) { // mob is no longer in room's roomMobList
                        room.createMob(defaultMob);
                    }
                }
                for (String defaultItem : room.defaultItemTypes) {
                    if (!room.itemIsInRoom(defaultItem)) { // item is no longer in room's roomItemList
                        room.createItem(defaultItem);
                    }
                }
            }
        }


    }
}
