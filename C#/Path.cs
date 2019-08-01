using System.Collections.Generic;

/// <summary>
/// The Main class for the Path, created by the pathfinding algorithm.
/// </summary>
namespace Pathfinding {

  class Path {

    public Stack<Node> steps {get; private set;} // The individual steps from which the path is built up.
    public List<Node> closedNodes {get; private set;} // The nodes checked by the algorithm.
    public int cost {get; private set;} // The total cost of the steps.

    /// <summary>
    /// Sets all the values to make sure the path is usable.
    /// </summary>
    /// <param name="steps">The building blocks of the path.</param>
    /// <param name="closedNodes">The nodes that were checked during the pathfinding.</param>
    public Path(Stack<Node> steps, List<Node> closedNodes){
      this.steps = steps;
      this.closedNodes = closedNodes;
      this.cost = GetTheTotalCostOfThePath(steps);
    }
    
    /// <summary>
    /// Sums the cost of each step in the steps stack.
    /// </summary>
    /// <param name="steps">The list of steps.</param>
    /// <returns>The sum of each node cost.</returns>
    private int GetTheTotalCostOfThePath(Stack<Node> steps){
      int cost = 0;

      foreach(Node step in steps){
        cost += step.cost;
      }

      return cost;
    }
  }
}