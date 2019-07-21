package com.pathfinding.astar;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The Main class for A* pathfinding.
 * Contains all the logic required to generate the path.
 */
public class AStar {
  private Board boardHandler; //The Board on which the algorithm searches the path.

  /**
   * Sets the reference Board to ensure that the functions can work properly.
   * @param boardHandler The Board on which the path is calculated.
   */
  public AStar(Board boardHandler){
    this.boardHandler = boardHandler;
  }

  /**
   * Calculates the path to the target using the A* algorithm, then returns it. If there is not path, returns an empty stack.
   * @return
   */
  public Stack<Node> getPath(){
    Stack<Node> path = new Stack<Node>();

    //Creating the open and closed node list.
    ArrayList<Node> openNodes = new ArrayList<Node>();
    ArrayList<Node> closedNodes = new ArrayList<Node>();

    //References for the start and destination node for easier use.
    Node startNode = boardHandler.getStartingNode();
    Node destinationNode = boardHandler.getDestinationNode();

    openNodes.add(startNode);

    do {

      //Selects the node with the lowest F value from the open node list, then checks it's neighbours.
      Node currentTile = GetTileWithLowestFValue(openNodes);

      closedNodes.add(currentTile);
      openNodes.remove(currentTile);

      //If the closed node list contains the destination, then the path is completed.
      if (closedNodes.contains(destinationNode)) {

        //Backtracking and adding the steps to the stack.
        while (currentTile.getParent() != null) {
          path.push(currentTile);
          currentTile = currentTile.getParent();
        }
        break;
      }

      //Checks each neighbour of the node.
      for(Node neighbour: currentTile.getNeighbours()){

        //Filtering out nodes that are not fit for path.
        if (!neighbour.getType().equals(Node.Types.Wall)) {

          if (closedNodes.contains(neighbour)) {
            continue;
          }

          neighbour.setG(GetGValue(currentTile)+ neighbour.getCost());
          neighbour.setH(GetHValue(neighbour));
          neighbour.setF(GetFValue(neighbour));

          //If it's not in the open list, add it and set the parent.
          //Else check if the F value is better with the current node, if it is then set the it's parent to the current node.
          if (!openNodes.contains(neighbour)) {
            neighbour.setParent(currentTile);
            openNodes.add(neighbour);
          }
          else {
            if(GetFValue(neighbour) > GetFValueWithG(neighbour, currentTile.getG())){
              neighbour.setParent(currentTile);
            }
          }
        }
      }
    } while (openNodes.size() > 0);

    return path;
  }

  /**
   * Searches for the node with the lowest total cost in the node list.
   * @param nodes List of nodes.
   * @return The node with the lowest total cost.
   */
  private Node GetTileWithLowestFValue(ArrayList<Node> nodes){
    Node chosenNode = null;

    for(Node openNode: nodes) {
      if (chosenNode == null || GetFValue(openNode) < GetFValue(chosenNode)){
        chosenNode = openNode;
      }
    }

    return chosenNode;
  }

  /**
   * This is used to calculate the F value of a node by using the G value of another node.
   * @param node The node that used as a basis for the G value.
   * @param gValue >The G value of another node.
   * @return
   */
  private int GetFValueWithG(Node node, int gValue) {
    return node.getH() + gValue;
  }

  /**
   * Calculates the total cost of a node.
   * @param node The node for which the value is calculated.
   * @return The sum of the G and H value.
   */
  private int GetFValue(Node node){
    return node.getH() + node.getG();
  }

  /**
   * Calculates the heuristic distance between the node and the starting node.
   * @param node The node which position is used for the calculation.
   * @return The absolute distance between the starting and the given node.
   * {@link #GetHeuristicDistance(Node, Node)}
   */
  private int GetGValue(Node node) {
    return GetHeuristicDistance(boardHandler.getStartingNode(), node);
  }

  /**
   * Calculates the heuristic distance between the node and the destination node.
   * @param node The node which position is used for the calculation.
   * @return The absolute distance between the destination and the given node.
   * {@link #GetHeuristicDistance(Node, Node)}
   */
  private int GetHValue(Node node) {
    return GetHeuristicDistance(boardHandler.getDestinationNode(), node);
  }

  /**
   * Calculates the heuristic distance between two nodes.
   * @param node1
   * @param node2
   * @return The absolute distance between two nodes.
   */
  private int GetHeuristicDistance(Node node1, Node node2){
    return Math.abs(node1.getX() - node2.getX() + Math.abs(node1.getY() - node2.getY()));
  }

  /**
   * Returns the Board on which the search algorithm runs.
   * @return The Board used by the algorithm.
   */
  public Board getBoardHandler() {
    return boardHandler;
  }

  /**
   * Sets the board for the search algorithm.
   * @param boardHandler The desired board to use.
   */
  private void setBoardHandler(Board boardHandler) {
    this.boardHandler = boardHandler;
  }
}
