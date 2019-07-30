using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace Pathfinding {

  /// <summary>
  /// The Main class for Dijkstra's pathfinding.
  /// Contains all the logic required to generate the path.
  /// </summary>
  class Dijkstras : Pathfinding{

    public Dijkstras(Board boardHandler) : base(boardHandler) {}

    /// <summary>
    /// Calculates the path to the target using the Dijkstra's algorithm, then returns it.
    /// </summary>
    public override Path GetPath(){
      Stack<Node> path = new Stack<Node>();

      //Creating the open and closed node list.
      Queue<Node> openNodes = new Queue<Node>();
      List<Node> closedNodes = new List<Node>();

      //References for the start and destination node for easier use.
      Node startNode = boardHandler.startingNode;
      Node destinationNode = boardHandler.destinationNode;

      openNodes.Enqueue(startNode);

      do {
        //Selects the next node from the queue.
        Node currentNode = openNodes.Dequeue();
        if(!closedNodes.Contains(currentNode)){ closedNodes.Add(currentNode); }

        //If the closed node list contains the destination, then the path is completed.
        if (closedNodes.Contains(destinationNode)) {
          //Backtracking and adding the steps to the stack.
          while (currentNode.parent != null) {
            path.Push(currentNode);
            currentNode = currentNode.parent;
          }

          break;
        }

        //Checks each neighbour of the node.
        foreach (Node neighbour in currentNode.neighbours) {

          //Filtering out nodes that are not fit for path.
          if (!neighbour.type.Equals(Node.Types.Wall)) {

            if (closedNodes.Contains(neighbour)) {
              continue;
            }

            //Calculates the new cost based on the "distance" between the two nodes.
            int newCost = currentNode.Distance + neighbour.cost;

            //If the node is not checked yet, or the new cost is better than the current, queues the node.
            if (!openNodes.Contains(neighbour) || newCost < currentNode.Distance) {
              neighbour.parent = currentNode;
              openNodes.Enqueue(neighbour);
              neighbour.Distance = newCost;
            }
          }
        }
      } while (openNodes.Count > 0);

      return new Path(path, closedNodes);
    }
  }
}
