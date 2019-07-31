package com.pathfinding.common;

import java.util.HashMap;
import java.util.Map;

/**
 * The Main Program Loop.
 */
public class Main {

  private static Board boardHandler = new Board(); //Board instance
  private final static HashMap<Integer, Pathfinding> pathHandlers;

  static {
    pathHandlers = new HashMap<Integer, Pathfinding>();
    pathHandlers.put(0, new DepthFirst(boardHandler));
    pathHandlers.put(1, new BreadthFirst(boardHandler));
    pathHandlers.put(2, new Dijkstras(boardHandler));
    pathHandlers.put(3, new Astar(boardHandler));
  }

  public static void main(String[] args) {
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

      Path path = getPathfindingMethod().getPath();

      if(path.getSteps().size() == 0){
        System.out.println("No Path found.");
      } else {
        boardHandler.markCheckedNodes(path);
        boardHandler.markPath(path);
        boardHandler.drawBoard();
        System.out.println(getStatistics(path));
      }

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
                "~ : Nodes that were checked, but not part of the path." + System.lineSeparator() + "Commands" + System.lineSeparator() + "Type exit to close the application.");
      }

      return input;
    }

  /**
   * Lists the pathfinding methods and waits for the user to choose one.
   * @return The chosen pathfinding method.
   */
  private static Pathfinding getPathfindingMethod(){
      int number = -1;

      System.out.println("Please choose a pathfinding method.");

      for (Map.Entry<Integer, Pathfinding> entry : pathHandlers.entrySet()){
        System.out.println(entry.getKey() + " " + entry.getValue());
      }

      do {
        String input = System.console().readLine();

        while(!isItANumber(processInput(input))){
          System.out.println("Please try again.");
          input = System.console().readLine();
        }

        number = Integer.parseInt(input);

        if((number < pathHandlers.size() && number >= 0)) {
          break;
        } else {
          System.out.println("Please try again.");
        }
      } while(true);

      return pathHandlers.get(number);
    }

    /**
     * Generates the statistics of a given path, then returns the formated string.
     * @param path The path from which the statistics are genarated.
     * @return A String which contains the path statistics.
     */
    private static String getStatistics(Path path){
      StringBuilder sb = new StringBuilder();
      sb.append("Total cost of the board: " + boardHandler.getBoardCost() + System.lineSeparator());
      sb.append("Number of checked nodes: " + path.getClosedNodes().size() + System.lineSeparator());
      sb.append("Number of steps: " + path.getSteps().size() + System.lineSeparator());
      sb.append("Total cost of the path: " + path.getCost() + System.lineSeparator());
      sb.append("Path: " + System.lineSeparator());

      for (Node step : path.getSteps()){
        sb.append("X " + step.getX() + " Y: " + step.getY() + " Cost: " + step.getCost() + System.lineSeparator());
      }

      return sb.toString();
    }
}
