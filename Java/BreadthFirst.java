package com.pathfinding.common;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Stack;

/**
 * The Main class for Breadth First pathfinding.
 * Contains all the logic required to generate the path.
 */
public class BreadthFirst extends Pathfinding {

  public BreadthFirst(Board boardHandler){
    super(boardHandler);
  }

  /**
   * Calculates the path to the target using the Breadth First algorithm, then returns it.
   * @return The calculated path.
   */
  @Override
  public Path getPath(){
    Stack<Node> path = new Stack<Node>();

    //Creating the open and closed node list.
    Queue<Node> openNodes = new LinkedList<Node>();
    ArrayList<Node> closedNodes = new ArrayList<Node>();

    //References for the start and destination node for easier use.
    Node startNode = boardHandler.getStartingNode();
    Node destinationNode = boardHandler.getDestinationNode();

    openNodes.add(startNode);

    while (!openNodes.isEmpty()) {

      //Selects the next node to check.
      Node currentNode = openNodes.remove();
      if(!closedNodes.contains(currentNode)){ closedNodes.add(currentNode); }

      //If the current node is the destination node, then the path is complete.
      if (currentNode == destinationNode) {

        //Backtracking and adding the steps to the stack.
        while (currentNode != startNode) {
          path.push(currentNode);
          currentNode = currentNode.getParent();
        }
        break;
      }

      //Checks each neighbour of the node.
      for(Node neighbour: currentNode.getNeighbours()){

        //Filtering out nodes that are not fit for path.
        if (!neighbour.getType().equals(Node.Types.Wall)) {

          if (!closedNodes.contains(neighbour)) {
            neighbour.setParent(currentNode);
            openNodes.add(neighbour);
          }
        }
      }
    }

    return new Path(path, closedNodes);
  }
}
