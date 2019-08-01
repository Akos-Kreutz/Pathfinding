package com.pathfinding.common;


import java.util.ArrayList;
import java.util.Stack;

/**
 * The Main class for the Path, created by the pathfinding algorithm.
 */
public class Path {

  private Stack<Node> steps; //The individual steps from which the path is built up.
  private ArrayList<Node> closedNodes; //The nodes checked by the algorithm.
  private int cost; //The total cost of the steps.

  /**
   * Sets all the values to make sure the path is usable.
   * @param steps The building blocks of the path.
   * @param closedNodes The nodes that were checked during the pathfinding.
   */
  public Path(Stack<Node> steps, ArrayList<Node> closedNodes) {
    setSteps(steps);
    setClosedNodes(closedNodes);
    setCost(GetTheTotalCostOfThePath(steps));
  }

  /**
   * Sums the cost of each step in the steps stack.
   * @param steps The list of steps.
   * @return The sum of each node cost.
   */
  private int GetTheTotalCostOfThePath(Stack<Node> steps){
    int cost = 0;

    for(Node step : steps){
      cost += step.getCost();
    }

    return cost;
  }

  /**
   * Returns the steps from which the path is made of.
   * @return A Stack of nodes representing the path.
   */
  public Stack<Node> getSteps() {
    return steps;
  }

  /**
   * Sets the step stack.
   * @param steps A stack of nodes.
   */
  private void setSteps(Stack<Node> steps) {
    this.steps = steps;
  }

  /**
   * An Array List of nodes, representing the checked nodes.
   * @return The nodes, which were checked during the path creation.
   */
  public ArrayList<Node> getClosedNodes() {
    return closedNodes;
  }

  /**
   * Sets the Array List of checked nodes.
   * @param closedNodes The nodes checked by the pathfinding algorithm.
   */
  private void setClosedNodes(ArrayList<Node> closedNodes) {
    this.closedNodes = closedNodes;
  }

  /**
   * Returns the total cost of the path.
   * @return The sum of the cost of each node in the steps stack.
   */
  public int getCost() {
    return cost;
  }

  /**
   * Sets the total cost of the path.
   * @param cost The sum of the cost of each node in the steps stack.
   */
  private void setCost(int cost) {
    this.cost = cost;
  }
}
