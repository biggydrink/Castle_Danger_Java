# Castle_Danger_Java
Java port and extension of Python class project Castle Danger, a text-based RPG.


Known bugs:

1. When quitting the game, the player's info and equipment are saved in the database, but not the player's inventory

2. Old players saved info is not currently loaded when they log in - they are still given a new character


Cool features:

1. Using global object lists to allow easy references to objects using Strings

2. The way players enter and run commands

Future development:

1. When a player dies, the game currently ends. Would like to give the player the choice to start over at the starting room (with 
  all equipment still intact, or to quit the game (and automatically save the character's info).

2. Multiplayer features - allowing multiple people to log in and play with their own characters simultaneously. 
  This would involve:
    1. Moving and retooling some of the game's main classes

    2. Extending the use of the Client and Server classes 
      (Server class is in Castle_Danger_Java_Server (https://github.com/biggydrink/Castle_Danger_Java_Server)

    3. Adding a protocol class to the Server program to facilitate communication between the two programs

    4. Possibly multithreading, if using only one connection socket/thread is too slow
