package com.pathfinding.common;

/**
 *
 */
public abstract class Pathfinding {

  protected Board boardHandler; //The Board on which the algorithm searches the path.

  /**
   * Sets the reference Board to ensure that the functions can work properly.
   * @param boardHandler The Board on which the path is calculated.
   */
  public Pathfinding(Board boardHandler) {
    this.boardHandler = boardHandler;
  }

  /**
   * Return the path calculated by the pathfinding algorithm.
   * @return The Path created by the algorithm.
   */
  public abstract Path getPath();

  /**
   * Returns the Class Name, separated by spaces after every upper case letter.
   * @return The pretty print class name.
   */
  @Override
  public String toString(){
    return this.getClass().getSimpleName().replaceAll("(.)([A-Z0-9]\\w)", "$1 $2");
  }
}
