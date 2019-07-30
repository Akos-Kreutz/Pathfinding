using System.Collections.Generic;

namespace Pathfinding {

  class Path {

    public Stack<Node> steps {get; private set;}
    public List<Node> closedNodes {get; private set;}
    public int cost {get; private set;}

    public Path(Stack<Node> steps, List<Node> closedNodes){
      this.steps = steps;
      this.closedNodes = closedNodes;
      this.cost = GetTheTotalCostOfThePath(steps);
    }
    
    private int GetTheTotalCostOfThePath(Stack<Node> steps){
      int cost = 0;

      foreach(Node step in steps){
        cost += step.cost;
      }

      return cost;
    }
  }
}