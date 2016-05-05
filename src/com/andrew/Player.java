package com.andrew;

import java.util.Random;

/** Extension of Mob, used for player characters */
public class Player extends Mob {

    private Random playerRand;

    public Player(String name) {
        /* Abstract class Mob has the following data fields:
        String name;
        int maxHP;
        int hp;
        int attack;
        int defense;
        ALREADY INITIALIZED HashMap<String,Item> inventory;
        ALREADY INITIALIZED HashMap<String,Item> equipped;
        String description;
         */

        super();
        playerRand = new Random();
        this.name = name;
        this.maxHP = playerRand.nextInt(10) + 80; // random max hp between 80 and 90
        this.hp = this.maxHP;
        this.attack = playerRand.nextInt(2) + 8; // random attack between 8 and 10
        this.defense = playerRand.nextInt(2) + 3; // random defense between 3 and 5
        this.description = "Behold, " + this.name + ". An elite player in this game!";


        GameInterface UI = new GameInterface();
    }

    /** Display player's stats */
    public void stats() {
        System.out.println("Player stats() called");
    }


    private class GameInterface {

        Room currentRoom;

        /** Wait for input from player */
        public void inputCommand() {
            System.out.println("UI inputCommand() called");
        }

        /** Display currentRoom description */
        public void setting() {
            System.out.println("UI currentRoom() called");
        }

        /** Display main game prompt */
        public void prompt() {
            System.out.println("UI prompt() called");
        }

        /** Show description of item/player */
        public void look(String name) {
            System.out.println("UI look() called");
        }

        public void goNorth() {
            System.out.println("UI goNorth called");
        }
        public void goSouth() {
            System.out.println("UI goSouth called");
        }
        public void goEast() {
            System.out.println("UI goEast called");
        }
        public void goWest() {
            System.out.println("UI goWest called");
        }

    }
}
