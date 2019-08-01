package com.pathfinding.common;


import java.util.ArrayList;

/**
 * The Main class for the node which is the building block of the board.
 */
public class Node {

  /**
   * The  node types, every type has it's own representation on the board.
   */
  public enum Types {
    Wall ("X"), Floor ("-"), Start ("S"), Destination ("D"), Path ("*"), Checked("~");

    private final String mark;

    Types(String mark){
      this.mark = mark;
    }

    @Override
    public String toString() {
      return mark;
    }
  }

  private Types type; //The type of the node.
  private Node parent; //Parent node, used for backtracking the path.
  private ArrayList<Node> neighbours; //The neighbours of the node.

  private int x; //The X position of the node.
  private int y; //The Y position of the node.
  private int cost; //The extra cost of the node.

  private int G; //The distance between the node and the starting node.
  private int H;//The heuristic distance between the node and the destination node.
  private int F;//The total cost of a node
  private int distance = 1; //The distance of the node.

  /**
   * This constructor ensures that the node always has a valid position and type.
   * @param type The type of the node.
   * @param x The X coordinate of the node.
   * @param y The Y coordinate of the node.
   */
  public Node(Types type, int x, int y, int cost){
    setType(type);
    setX(x);
    setY(y);
    setCost(cost);
  }

  /**
   * Returns the Type mark.
   * @return The Board representation of the node.
   */
  @Override
  public String toString(){
    return type.toString();
  }

  /**
   * Returns the type of the node.
   * @return The type of the node.
   */
  public Types getType() {
    return type;
  }

  /**
   * Sets the type of the node.
   * @param type The type to which the Node type will be set.
   */
  public void setType(Types type) {
    this.type = type;
  }

  /**
   * Returns the parent node of this node.
   * @return The parent node.
   */
  public Node getParent() {
    return parent;
  }

  /**
   * Sets the parent node for this node.
   * @param parent The parent node.
   */
  public void setParent(Node parent) {
    this.parent = parent;
  }

  /**
   * Returns the neighbour nodes of this node.
   * @return An ArrayList which contains all the neighbouring nodes.
   */
  public ArrayList<Node> getNeighbours() {
    return neighbours;
  }

  /**
   * Sets the neighbour list.
   * @param neighbours The list of the neighbours for this node.
   */
  public void setNeighbours(ArrayList<Node> neighbours) {
    this.neighbours = neighbours;
  }

  /**
   * @return Returns the X coordinate of the node.
   */
  public int getX() {
    return x;
  }

  /**
   * Sets the X coordinate of the Node.
   * @param x The column number of the Node.
   */
  private void setX(int x) {
    this.x = x;
  }

  /**
   * @return Returns the Y coordinate of the node.
   */
  public int getY() {
    return y;
  }

  /**
   * Sets the Y coordinate of the Node.
   * @param y The row number of the Node.
   */
  private void setY(int y) {
    this.y = y;
  }

  /**
   * @return The extra cost of the node.
   */
  public int getCost() {
    return cost;
  }

  /**
   * Sets the extra cost of the node.
   * @param cost The extra cost of the node.
   */
  private void setCost(int cost) {
    this.cost = cost;
  }

  /**
   * Returns the heuristic distance between the node and the starting node.
   * @return The G value of the node.
   */
  public int getG() {
    return G;
  }

  /**
   * Sets the heuristic distance between the node and the starting node.
   * @param g The G value for the node.
   */
  public void setG(int g) {
    G = g;
  }

  /**
   * Returns the heuristic distance between the node and the destination node.
   * @return The H value of the node.
   */
  public int getH() {
    return H;
  }

  /**
   * Sets the heuristic distance between the node and the destination node.
   * @param h The H value for the node.
   */
  public void setH(int h) {
    H = h;
  }

  /**
   * Returns the total cost of the node.
   * @return The F value of the node.
   */
  public int getF() {
    return F;
  }

  /**
   * Sets the total cost of the node.
   * @param f The F value for the node.
   */
  public void setF(int f) {
    F = f;
  }

  /**
   * Returns the distance value of the node.
   * @return The Distance of the node.
   */
  public int getDistance() {
    return distance;
  }

  /**
   * Sets the distance value of the node.
   * @param distance The desired distance value.
   */
  public void setDistance(int distance) {
    this.distance = distance;
  }
}
