package com.pathfinding.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * The Main class for Dijkstras pathfinding.
 * Contains all the logic required to generate the path.
 */
public class Dijkstras extends Pathfinding {

  public Dijkstras(Board boardHandler){
    super(boardHandler);
  }

  /**
   * Calculates the path to the target using the Dijkstras algorithm, then returns it.
   * @return The calculated path.
   */
  @Override
  public Path getPath(){
    Stack<Node> path = new Stack<Node>();

    //Creating the open and closed node list.
    Queue<Node> openNodes = new LinkedList<>();
    ArrayList<Node> closedNodes = new ArrayList<Node>();

    //References for the start and destination node for easier use.
    Node startNode = boardHandler.getStartingNode();
    Node destinationNode = boardHandler.getDestinationNode();

    openNodes.add(startNode);

    do {

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

          if (closedNodes.contains(neighbour)) {
            continue;
          }

          //Calculates the new cost based on the "distance" between the two nodes.
          int newCost = currentNode.getDistance() + neighbour.getCost();

          //If the node is not checked yet, or the new cost is better than the current, queues the node.
          if (!openNodes.contains(neighbour) || newCost < currentNode.getDistance()) {
            neighbour.setParent(currentNode);
            openNodes.add(neighbour);
            neighbour.setDistance(newCost);
          }
        }
      }
    } while (!openNodes.isEmpty());

    return new Path(path, closedNodes);
  }
}
