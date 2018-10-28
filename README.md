# MaxConnect4
Author: Clayton Kramp, CWID: 10664271

Class: CSCI 404 Artificial Intelligence Project 2

Due Date: Thurs, March 22

## Project Details
Project written in java
java version "1.8.0\_144"


## Compilation
To compile cd into src. Then, type in:

`javac maxconnect4.java GameBoard.java AiPlayer.java`

Or simply:

`make`

Code has been tested at the Alamode lab on machine bb136-01 via SSH environment and has compiled and run as expected.
Code has been written, compiled, and tested on a macOS environment

## Running
To run maxconnect4 in interactive mode, type:

`java maxconnect4 interactive [input-file] [human-next/computer-next] [depth]`

To run maxconnect4 in one-move mode, type:

`java maxconnect4 one-move [input-file] [output-file] [depth]`

### maxconnect4.java
The main file, reads in command line args and initializes the board.  Creates the AI player, and runs the game one interactive of one-move mode.

### GameBoard.java
Contains the methods and attributes of the GameBoard.  Has useful methods that can count all the pieces on the board and calculate the scores of each player.

### AiPlayer.java
The file that contains the AI agent equipped with depth-limited alpha-beta pruning search.  Given a depth, it will calculate the best next move.  It has a heuristic function that weighs the score winning moves the highest, and puts weight on 3-in-a-rows and 2-in-a-rows.
