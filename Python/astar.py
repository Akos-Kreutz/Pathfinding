from board import Board
from node import Node
from node import Types

# The Main class for A* pathfinding.
# Contains all the logic required to generate the path.
class AStar:

  """Sets the reference Board to ensure that the functions can work properly.

  :param board_handler: The Board on which the path is calculated.
  """
  def __init__(self, board_handler):
    self.board_handler = board_handler

  """Calculates the path to the target using the A* algorithm, then returns it. If there is not path, returns an list array.

  :rtype: A List.
  """
  def calculate_path(self):
    path = list()
    open_nodes = list()
    closed_nodes = list()

    # References for the start and destination node for easier use.
    starting_node = self.board_handler.start_node
    destination_node = self.board_handler.destination_node

    open_nodes.append(starting_node)

    while len(open_nodes) > 0:
      # Selects the node with the lowest F value from the open node list, then checks it's neighbours.
      current_node = self.get_node_with_lowest_f_value(open_nodes)

      closed_nodes.append(current_node)
      open_nodes.remove(current_node)

      # If the closed node list contains the destination, then the path is completed.
      if closed_nodes.count(destination_node) > 0:

        # Backtracking and adding the steps to the array.
        while not current_node.parent == None:
          path.append(current_node)
          current_node = current_node.parent

        break
      
      # Checks each neighbour of the node.
      for neighbour in current_node.neighbours:

        # Filtering out nodes that are not fit for path.
        if not neighbour.type == Types.WALL:

          if closed_nodes.count(neighbour) > 0:
            continue
          
          neighbour.g = self.get_g_value(current_node) + neighbour.cost
          neighbour.h = self.get_h_value(neighbour)
          neighbour.f = self.get_f_value(neighbour)

          # If it's not in the open list, add it and set the parent.
          # Else check if the F value is better with the current node, if it is then set the it's parent to the current node.
          if open_nodes.count(neighbour) == 0:
            neighbour.parent = current_node
            open_nodes.append(neighbour)
          elif self.get_f_value(neighbour) > self.get_f_value_with_g(neighbour, current_node.g):
            neighbour.parent = current_node

      for node in closed_nodes:
        if not node == starting_node and not node == destination_node:
          node.type = Types.CHECKED

    return path

  """Searches for the node with the lowest total cost in the node list.

  :param nodes: List of nodes.
  :rtype: A :Node:`Classifier <Classifier>`
  """
  def get_node_with_lowest_f_value(self, nodes):
    chosen_node = None

    for node in nodes:
      if chosen_node == None or self.get_f_value(node) < self.get_f_value(chosen_node):
        chosen_node = node

    return chosen_node

  """This is used to calculate the F value of a node by using the G value of another node.
  
  :param node: The node that used as a basis for the G value.
  :rtype: An Integer.
  """
  def get_f_value_with_g(self, node, g_value):
    return node.h + g_value

  """Calculates the total cost of a node.

  :param node: The node for which the value is calculated.
  :rtype: An Integer.
  """
  def get_f_value(self, node):
    return node.h + node.g

  """Calculates the heuristic distance between the node and the starting node.

  :param node: The node for which the value is calculated.
  :rtype: An Integer.
  """
  def get_g_value(self, node):
    return self.get_heuristic_distance(self.board_handler.start_node, node)
  
  """Calculates the heuristic distance between the node and the destination node.

  :param node: The node for which the value is calculated.
  :rtype: An Integer.
  """
  def get_h_value(self, node):
    return self.get_heuristic_distance(self.board_handler.destination_node, node)

  """Calculates the heuristic distance between two nodes.

  :param node1:
  :param node2:
  :rtype: An Integer.
  """
  def get_heuristic_distance(self, node1, node2):
    return (abs(node1.x - node2.x) + abs(node1.y - node2.y))

