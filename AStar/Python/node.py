from enum import Enum

# The Main class for the node which is the building block of the board.
class Node:
  """This constructor ensures that the node always has a valid position and type.

  :param type: The type of the node.
  :param x The X position of the node.
  :param y The Y position of the node.
  """
  def __init__(self, type, x, y):
    self.y = y
    self.x = x
    self.type = type

    self.cost = 0 # The cost of the node.
    self.g = 0 # The distance between the node and the starting node.
    self.h = 0 # The heuristic distance between the node and the destination node.
    self.f = 0 # The total cost of a node.
    self.parent = None # Parent node, used for backtracking the path.
    self.neighbours = list() # The neighbours of the node.

  def __str__(self):
      return self.type.value

class Types(Enum):
  FLOOR = "-"
  WALL = "X"
  START = "S"
  DESTINATION = "D"
  PATH = "*"

