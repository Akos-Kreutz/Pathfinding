using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace Pathfinding {

  /// <summary>
  /// The Main class for Breadth First pathfinding.
  /// Contains all the logic required to generate the path.
  /// </summary>
  class BreadthFirst : Pathfinding{

    public BreadthFirst(Board boardHandler) : base(boardHandler) {}

    /// <summary>
    /// Calculates the path to the target using the Breadth First algorithm, then returns it. If there is not path, returns an empty stack.
    /// </summary>
    public override Stack<Node> GetPath(){
      Stack<Node> path = new Stack<Node>();

      //Creating the open and closed node list.
      Queue<Node> openNodes = new Queue<Node>();
      List<Node> closedNodes = new List<Node>();

      //References for the start and destination node for easier use.
      Node startNode = boardHandler.startingNode;
      Node destinationNode = boardHandler.destinationNode;

      openNodes.Enqueue(startNode);

      while (openNodes.Count > 0) {
        
        //Selects the next node to check.
        Node currentNode = openNodes.Dequeue();
        closedNodes.Add(currentNode);

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
              openNodes.Enqueue(neighbour);
            }
          }
        }
      }

      //Marks all the checked nodes.
      foreach (Node node in closedNodes) {
        if(!node.Equals(destinationNode) && !node.Equals(startNode)){
          node.type = Node.Types.Checked;
        }
      }

      return path;
    }
  }
}
