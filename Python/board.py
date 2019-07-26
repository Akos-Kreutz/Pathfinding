from node import Node
from node import Types
import random

# The Main class for the Board, which contains all the board related functions.
class Board:

  """Instantiates the node matrix with the given dimensions, then fills it up with wall and floor nodes.

  :param width: The width of the board.
  :param height The height of the board.
  """
  def generate_board(self, width, height):
    self.width = width
    self.height = height
    self.nodes = [[0 for x in range(width)] for y in range(height)]

    # Iterates through the matrix
    for i in range(height):
      for j in range(width):

        # If the node is on the edge, then it becomes a wall.
        # Else there is a 10% chache for it to become a wall, otherwise it will be a floor node.
        if i == 0 or j == 0 or i == height - 1 or j == width - 1:
          type = Types.WALL
        else:
          if random.randint(1,101) < 15:
            type = Types.WALL
          else:
            type = Types.FLOOR

        self.nodes[i][j] = Node(type, j, i)

    # Iterates through the nodes and set their neighbours.
    # This needs to be done after the previous iteration to ensure there is no null node.
    for i in range(height):
      for j in range(width):
        self.add_neighbours(self.nodes[i][j])

  """Runs a 4 connected node search for the node and sets the neighbours for the node.

  :param node: The node that stands in the middle point of the search.
  """
  def add_neighbours(self, node):
    for i in range(node.x - 1, node.x + 2):
      for j in range(node.y - 1, node.y + 2):
        if (i == node.x or j == node.y) and not (i == node.x and j == node.y) and self.is_coordinate_within_bounds(i, j):
          node.neighbours.append(self.nodes[j][i])
  
  """Checks if the given coordinates are within bounds of the Board size.

  :param x: The X coordinate of the node.
  :param y: The Y coordinate of the node.
  :rtype: A Boolean.
  """
  def is_coordinate_within_bounds(self, x, y):
    return x < self.width and x >= 0 and y < self.height and y >= 0

  """Checks if the given coordinates are within bounds of the Board size.

  :param x: The X coordinate of the node.
  :param y: The Y coordinate of the node.
  :rtype: A Boolean.
  """
  def is_coordinate_available(self, x, y):
    return self.is_coordinate_within_bounds(x, y) and self.nodes[y][x].type == Types.FLOOR

  """Sets the node to the given type.

  :param type: The type to which the node should be set.
  :param x: The X coordinate of the node.
  :param y: The Y coordinate of the node.
  """
  def __set_node(self, x, y, type):
    self.nodes[y][x].type = type

  """Set the node type to Destination and set the destination_node reference to node.

  :param x: The X coordinate of the node.
  :param y: The Y coordinate of the node.
  """
  def set_destination_node(self, x, y):
    self.__set_node(x, y, Types.DESTINATION)
    self.destination_node = self.nodes[y][x]

  """Set the node type to Start and set the start_node reference to node.

  :param x: The X coordinate of the node.
  :param y: The Y coordinate of the node.
  """
  def set_starting_node(self, x, y):
    self.__set_node(x, y, Types.START)
    self.start_node = self.nodes[y][x]

  """Set the node type to Path.

  :param x: The X coordinate of the node.
  :param y: The Y coordinate of the node.
  """
  def set_path_node(self, x, y):
    self.__set_node(x, y, Types.PATH)

  """Iterates through the path and if the node type is floor marks it.

  :param path: The path from start to destination.
  """
  def mark_path(self, path):
    for step in path:
      if not step == self.start_node and not step == self.destination_node:
        self.set_path_node(step.x, step.y)

  """Generates a string from the node matrix and adds marking numbers to it, then writes it to the console.

  """
  def draw_board(self):
    horizontal_number_row = ""
    board = ""

    for i in range(self.height):
      for j in range(self.width):
        if i == 0:
          horizontal_number_row += '{0} '.format(j)
        if j == 0:
          board += '{0} '.format(i)

        board += str(self.nodes[i][j])

        if not i == self.width - 1 or not j == self.height -1:
          board += " "

      board += "\n"

    print('  {0}{1}{2}'.format(horizontal_number_row, "\n", board))

