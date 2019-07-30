using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace Pathfinding {

  /// <summary>
  /// The Main class for Depth First pathfinding.
  /// Contains all the logic required to generate the path.
  /// </summary>
  class DepthFirst : Pathfinding{

    public DepthFirst(Board boardHandler) : base(boardHandler) {}

    /// <summary>
    /// Calculates the path to the target using the Depth First algorithm, then returns it.
    /// </summary>
    public override Path GetPath(){
      Stack<Node> path = new Stack<Node>();

      //Creating the open and closed node list.
      Stack<Node> openNodes = new Stack<Node>();
      List<Node> closedNodes = new List<Node>();

      //References for the start and destination node for easier use.
      Node startNode = boardHandler.startingNode;
      Node destinationNode = boardHandler.destinationNode;

      openNodes.Push(startNode);

      while (openNodes.Count > 0) {
        
        //Selects the next node to check.
        Node currentNode = openNodes.Pop();
        if(!closedNodes.Contains(currentNode)){ closedNodes.Add(currentNode); }

        //If the current node is the destination node, then the path is complete.
        if (currentNode == destinationNode) {

          //Backtracking and adding the steps to the stack.
          while (currentNode != startNode) {
            path.Push(currentNode);
            currentNode = currentNode.parent;
          }

          break;
        }

        //Checks each neighbour of the node.
        foreach (Node neighbour in currentNode.neighbours) {

          //Filtering out nodes that are not fit for path.
          if (!neighbour.type.Equals(Node.Types.Wall)) {

            if (!closedNodes.Contains(neighbour)) {
              neighbour.parent = currentNode;
              openNodes.Push(neighbour);
            }
          }
        }
      }

      return new Path(path, closedNodes);
    }
  }
}
