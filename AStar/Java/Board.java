package com.pathfinding.astar;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * The Main class for the Board, which contains all the board related functions.
 */
public class Board {
  private int width; //The width of the board.
  private int height; //The height of the board.
  private Node[][] nodes; //Matrix that holds all the nodes, which is essentially the board itself.
  private Node startingNode; //Reference for the starting node
  private Node  destinationNode; //Reference for the destination node

  private Random rng = new Random(); //The Random Number Generator used during the board creation.

  /**
   * Instantiates the node matrix with the given dimensions, then fills it up with wall and floor nodes.
   * @param width The width of the board.
   * @param height The height of the board.
   * {@link #nodes}
   * {@link #GetNeighbours(Node)}
   */
  public void GenerateBoard(int width, int height){
    this.width = width;
    this.height = height;

    this.nodes = new Node[height][width];

    //Iterates through the matrix
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){
        Node.Types type;

        //If the node is on the edge, then it becomes a wall.
        //Else there is a 10% chache for it to become a wall, otherwise it will be a floor node.
        if(i == 0 || j == 0 || i == height - 1|| j == width - 1){
          type = Node.Types.Wall;
        } else {
          if(rng.nextInt((100 - 1) + 1) + 1 < 15){
            type = Node.Types.Wall;
          } else {
            type = Node.Types.Floor;
          }
        }

        nodes[i][j] = new Node(type,j,i);
      }
    }

    //Iterates through the nodes and set their neighbours.
    //This needs to be done after the previous iteration to ensure there is no null node.
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){
        nodes[i][j].setNeighbours(GetNeighbours(nodes[i][j]));
      }
    }
  }

  /**
   * Generates a string from the node matrix and adds marking numbers to it, then writes it to the console.
   */
  public void DrawBoard(){
    //Using StringBuilder for better performance as the board size can change.
    StringBuilder board = new StringBuilder();
    StringBuilder horizontalNumberRow = new StringBuilder((width * 2) + 2);

    //Iterates through the board and appends their values to the StringBuilder.
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){

        if(i == 0){
          horizontalNumberRow.append(j + " ");
        }
        if(j == 0){
          board.append(i + " ");
        }

        board.append(nodes[i][j]);

        if(i != width - 1 || j != height - 1){
          board.append(" ");
        }
      }
      board.append(System.lineSeparator());
    }

    String horizontalNumberString = horizontalNumberRow.insert(0, " ").toString();
    System.out.println(horizontalNumberRow.toString() + System.lineSeparator() + board.toString());
  }

  /**
   * Checks if is there a node at the coordinates and if it's a floor.
   * @param x The X coordinate of the node.
   * @param y The Y coordinate of the node.
   * @return If the coordinates are within the bound of the board size and if the node is a floor.
   * {@link #isCoordinateWithinBounds(int, int)}
   */
  public boolean isCoordinateAvailable(int x, int y){

    if (isCoordinateWithinBounds(x,y)){
      return nodes[y][x].getType().equals(Node.Types.Floor);
    }

    return false;
  }

  /**
   * Checks if the given coordinates are within bounds of the Board size.
   * @param x The X coordinate of the node.
   * @param y The Y coordinate of the node.
   * @return If the coordinates are within the bound of the board size.
   */
  private boolean isCoordinateWithinBounds(int x, int y){
    return x < this.width && x >= 0 && y < this.height && y >= 0;
  }

  /**
   * Sets the node to the given type.
   * @param x The X coordinate of the node.
   * @param y The Y coordinate of the node.
   * @param type The type to which the node should be set.
   */
  private void setNode(int x, int y, Node.Types type){
    nodes[y][x].setType(type);
  }

  /**
   * Set the node type to Start and set the startingNode reference to node.
   * @param x The X coordinate of the node.
   * @param y The Y coordinate of the node.
   * {@link #setNode(int, int, Node.Types)}
   */
  public void setStartingNode(int x, int y){
    setNode(x,y, Node.Types.Start);
    startingNode = nodes[y][x];
  }

  /**
   * Set the node type to Destination and set the destinationNode reference to node.
   * @param x The X coordinate of the node.
   * @param y The Y coordinate of the node.
   * {@link #setNode(int, int, Node.Types)}
   */
  public void setDestinationNode(int x, int y){
    setNode(x,y, Node.Types.Destination);
    destinationNode = nodes[y][x];
  }

  /**
   * Set the node type to Path.
   * @param x The X coordinate of the node.
   * @param y The Y coordinate of the node.
   * {@link #setNode(int, int, Node.Types)}
   */
  public void setPathNode(int x, int y){
    setNode(x,y, Node.Types.Path);
  }

  /**
   * Iterates through the path and if the node type is floor marks it.
   * @param path The path from start to destination.
   */
  public void markPath(Stack<Node> path){
    while(path.size() > 0){

      Node currentNode = path.pop();

      if(currentNode.getType().equals(Node.Types.Floor)){
        setPathNode(currentNode.getX(), currentNode.getY());
      }
    }
  }

  /**
   * Runs a 4 connected node search for the node and saves the neighbours to the list.
   * @param node The node that stands in the middle point of the search.
   * @return A list of neighbours.
   */
  private ArrayList<Node> GetNeighbours(Node node){
    ArrayList<Node> neighbours = new ArrayList<Node>();

    for (int i = node.getX() - 1; i <= node.getX() + 1; i++) {
      for (int j = node.getY() - 1; j <= node.getY() + 1; j++) {

        //This check ensures that the list will not contains the node and that there will be no null node.
        if((i == node.getX() || j == node.getY()) && !(i == node.getX() && j == node.getY()) && isCoordinateWithinBounds(i,j)){
          neighbours.add(nodes[j][i]);
        }
      }
    }

    return neighbours;
  }

  /**
   * Returns the width of the board.
   * @return The width of the board.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Sets the width of the board;
   * @param width The desired width for the board.
   */
  private void setWidth(int width) {
    this.width = width;
  }

  /**
   * Returns the height of the board.
   * @return The height of the board.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Sets the height of the board;
   * @param height The desired height for the board.
   */
  private void setHeight(int height) {
    this.height = height;
  }

  /**
   * Returns the node matrix, which is essentially the board itself.
   * @return The node matrix.
   */
  public Node[][] getNodes() {
    return nodes;
  }

  /**
   * Sets the node matrix.
   * @param nodes The node matrix, which represents the board.
   */
  private void setNodes(Node[][] nodes) {
    this.nodes = nodes;
  }

  /**
   * Returns the starting node.
   * @return The starting node.
   */
  public Node getStartingNode() {
    return startingNode;
  }

  /**
   * Sets the starting node.
   * @param startingNode The desired starting node.
   */
  private void setStartingNode(Node startingNode) {
    this.startingNode = startingNode;
  }

  /**
   * Returns the destination node.
   * @return The destination node.
   */
  public Node getDestinationNode() {
    return destinationNode;
  }

  /**
   * Sets the destination node.
   * @param destinationNode The desired destination node.
   */
  private void setDestinationNode(Node destinationNode) {
    this.destinationNode = destinationNode;
  }
}
