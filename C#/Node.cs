﻿using System;
using System.Collections.Generic;

namespace Pathfinding {

  /// <summary>
  /// The Main class for the node which is the building block of the board.
  /// </summary>
  class Node {
    public enum Types {Wall, Floor, Start, Destination, Path, Checked} //Node Types

    public Types type {get; set;} //The type of the node.
    public Node parent {get; set;} //Parent node, used for backtracking the path.
    public List<Node> neighbours {get; set;} //The neighbours of the node.

    public int x {get; private set;} //The X position of the node.
    public int y {get; private set;} //The Y position of the node.

    public int G {get; set;} //The distance between the node and the starting node.
    public int H {get; set;} //The heuristic distance between the node and the destination node.
    public int F {get; set;} //The total cost of a node
    public int Distance {get; set;} //The distance of the node.
    public int cost {get; set;} //The cost of the node.

    /// <summary>
    /// This constructor ensures that the node always has a valid position and type.
    /// </summary>
    /// <param name="type">The type of the Node.</param>
    /// <param name="x">The X coordinate of the Node.</param>
    /// <param name="y">The Y coordinate of the Node.</param>
    public Node(Types type, int x, int y, int cost){
      this.type = type;
      this.x = x;
      this.y = y;
      this.cost = cost;
      this.Distance = 1;
    }

    /// <summary>
    /// The string representation of a node depends on it's type
    /// </summary>
    /// <returns>
    /// The string representation of the node.
    /// </returns>
    public override string ToString(){
      if(type.Equals(Types.Wall)){
        return "X";
      } 
      else if(type.Equals(Types.Start)){
        return "S";
      } 
      else if(type.Equals(Types.Destination)){
        return "D";
      } 
      else if(type.Equals(Types.Path)){
        return "*";
      } 
      else if(type.Equals(Types.Checked)){
        return "~";
      } 
      else {
        return "-";
      }
    }
  }
}
