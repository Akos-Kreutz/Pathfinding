##
# The Main class for the node which is the building block of the board.

class Node

  ##
  # These variables can only be read outside of this class.
  # +x+:: The X position of the node.
  # +y+:: The Y position of the node.
  # +cost+:: The cost of the node.
  attr_reader :x, :y, :cost

  ##
  # These variables can be both read and changed outside of this class.
  # +type+:: The type of the node.
  # +parent:: Parent node, used for backtracking the path.
  # +neighbours+:: The neighbours of the node.
  # +g+:: The distance between the node and the starting node.
  # +h+:: The heuristic distance between the node and the destination node.
  # +f+:: The total cost of a node
  attr_accessor :type, :parent, :neighbours, :g, :h, :f

  ##
  # This constructor ensures that the node always has a valid position and type.
  # Params:
  # +type+:: The type of the node.
  # +x+:: The X position of the node.
  # +y+:: The Y position of the node.
  def initialize(type, x, y)
    @x = x
    @y = y
    @type = type

    @cost = 0
    @g = 0
    @h = 0
    @f = 0
  end

  ##
  # Returns the string representation of a node which depends on it's type.
  def to_s
    case @type
    when :wall
      'X'
    when :start
      'S'
    when :destination
      'D'
    when :path
      '*'
    when :checked
      '?'
    else
      '-'
    end
  end

end
