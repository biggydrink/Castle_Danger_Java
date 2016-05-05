package com.andrew;

import java.util.LinkedList;
import java.util.Scanner;


public class World {

    LinkedList<Player> playerList;
    static Scanner mainScanner = new Scanner(System.in);

    public World() {
        playerList = new LinkedList<Player>();
    }

    protected void createPlayer() {
        String newName;
        System.out.println("What is your name, Mr/Ms Hero?");
        newName = mainScanner.nextLine();
        Player newPlayerChar = new Player(newName);
        System.out.println("Welcome to Castle Danger, " + newName);

        playerList.push(newPlayerChar);

    }
}
