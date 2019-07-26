package com.pathfinding.astar;

import java.util.Stack;

/**
 * The Main Program Loop.
 */
public class Main {

  private static Board boardHandler; //Board instance
  private static AStar pathHandler; //Pathfinding instance

  public static void main(String[] args) {
    boardHandler = new Board();
    pathHandler = new AStar(boardHandler);

    //Starts an infinite loop which always generates a new board.
    while (true) {
      processInput("help");
      System.out.println("Press any key to start.");
      processInput(System.console().readLine());

      boardHandler.generateBoard(10, 10);

      mainLoop();
    }
  }

    /**
     * Ask the user to set the start and destination node, the calculates the path.
     * {@link #processInput(String)}
     */
    private static void mainLoop(){
      int x, y;

      do {
        boardHandler.drawBoard();

        x = getCoordinate("Please type the start column number.");
        y = getCoordinate("Please type the start row number.");

      } while (!boardHandler.isCoordinateAvailable(x,y));

      boardHandler.setStartingNode(x,y);
      boardHandler.drawBoard();

      do {
        boardHandler.drawBoard();

        x = getCoordinate("Please type the destination column number.");
        y = getCoordinate("Please type the destination row number.");

      } while (!boardHandler.isCoordinateAvailable(x,y));

      boardHandler.setDestinationNode(x,y);
      boardHandler.drawBoard();

      System.out.println("Press any key to start the pathfinding.");
      processInput(System.console().readLine());

      Stack<Node> path = pathHandler.getPath();

      if(path.size() == 0){
        System.out.println("No Path found.");
      } else {
        System.out.println("Path found.");
      }

      boardHandler.markPath(path);
      boardHandler.drawBoard();

      processInput(System.console().readLine());
    }

   /**
   * Checks if the string is a number, if it is, sets the number via reference.
   * @param input The input string which needs to be evaluated.
   * @return If the string is a number.
   */
    private static boolean isItANumber(String input){
      try {
        Integer.parseInt(input);
      }
      catch (Exception e) {
        return false;
      }
      return true;
    }

  /**
   * Displays a guide text for the user and evaluates if the input fits the requirements. Repeats until a valid response is not given.
   * @param text The guide text, which ask the input from the user.
   * @return The required coordinate.
   */
    private static int getCoordinate(String text){
      System.out.println(text);

      String input = System.console().readLine();

      while(!isItANumber(processInput(input))){
        System.out.println("Please try again.");
        input = System.console().readLine();
      }

      return Integer.parseInt(input);
    }

  /**
   * Checks the user input for commands.
   * @param input The input that the user has provided.
   * @return The input string.
   */
    private static String processInput(String input){

      if(input.toLowerCase().equals("exit")){
        System.exit(0);
      }
      else if(input.toLowerCase().equals("help")){
        System.out.println("Symbol Description" + System.lineSeparator() + "- : floor node." + System.lineSeparator() + "X : wall node." + System.lineSeparator() +
                "S : starting node." + System.lineSeparator() + "D : destination node." + System.lineSeparator() + "* : path node." + System.lineSeparator() +
                "? : Nodes that were checked, but not part of the path." + System.lineSeparator() + "Commands" + System.lineSeparator() + "Type exit to close the application.");
      }

      return input;
    }
}
