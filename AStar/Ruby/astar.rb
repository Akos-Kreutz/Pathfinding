require './node'

##
# The Main class for A* pathfinding.
# Contains all the logic required to generate the path.
class AStar

  ##
  # These variables can only be read outside of this class.
  # +board_handler+:: The Board on which the algorithm searches the path.
  attr_reader :board_handler

  ##
  # Sets the reference Board to ensure that the functions can work properly.
  # Params:
  # +board_handler+:: The Board on which the path is calculated.
  def initialize(board_handler)
    @board_handler = board_handler
  end

  ##
  # Calculates the path to the target using the A* algorithm, then returns it. If there is not path, returns an empty array.
  def calculate_path
    path = []
    open_nodes = []
    closed_nodes = []

    # References for the start and destination node for easier use.
    starting_node = @board_handler.start_node
    destination_node = @board_handler.destination_node

    open_nodes << starting_node

    until open_nodes.empty?
      # Selects the node with the lowest F value from the open node list, then checks it's neighbours.
      current_node = get_node_with_lowest_f_value(open_nodes)

      closed_nodes << current_node
      open_nodes.delete(current_node)

      # If the closed node list contains the destination, then the path is completed.
      if closed_nodes.include? destination_node

        # Backtracking and adding the steps to the array.
        until current_node.parent.nil?
          path << current_node
          current_node = current_node.parent
        end

        break
      end

      # Checks each neighbour of the node.
      current_node.neighbours.each do |neighbour|

        # Filtering out nodes that are not fit for path.
        next unless neighbour.type != :wall

        next if closed_nodes.include? neighbour

        neighbour.g = get_g_value(current_node) + neighbour.cost
        neighbour.h = get_h_value(neighbour)
        neighbour.f = get_f_value(neighbour)

        # If it's not in the open list, add it and set the parent.
        # Else check if the F value is better with the current node, if it is then set the it's parent to the current node.
        if !open_nodes.include? neighbour
          neighbour.parent = current_node
          open_nodes << neighbour
        else
          neighbour.parent = current_node if get_f_value(neighbour) > get_f_value_with_g(neighbour, current_node.g)
        end
      end

    end

    path
  end

  private

  ##
  # Searches for the node with the lowest total cost in the node list.
  # Params:
  # +nodes+:: List of nodes.
  def get_node_with_lowest_f_value(nodes)
    chosen_node = nil

    nodes.each do |node|
      chosen_node = node if chosen_node.nil? || get_f_value(node) < get_f_value(chosen_node)
    end

    chosen_node
  end

  ##
  # This is used to calculate the F value of a node by using the G value of another node.
  # Params:
  # +node+:: The node that used as a basis for the G value.
  # +g_value+:: The G value of another node.
  def get_f_value_with_g(node, g_value)
    node.h + g_value
  end

  ##
  # Calculates the total cost of a node.
  # Params:
  # +node+:: The node for which the value is calculated.
  def get_f_value(node)
    node.h + node.g
  end

  ##
  # Calculates the heuristic distance between the node and the starting node.
  # Params:
  # +node+:: The node for which the value is calculated.
  def get_g_value(node)
    get_heuristic_distance(@board_handler.start_node, node)
  end

  ##
  # Calculates the heuristic distance between the node and the destination node.
  # Params:
  # +node+:: The node for which the value is calculated.
  def get_h_value(node)
    get_heuristic_distance(@board_handler.destination_node, node)
  end

  ##
  # Calculates the heuristic distance between two nodes.
  # Params:
  # +node1+::
  # +node2+::
  def get_heuristic_distance(node1, node2)
    ((node1.x - node2.x).abs + (node1.y - node2.y).abs)
  end
end
