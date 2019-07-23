require './node'

##
# The Main class for the Board, which contains all the board related functions.
class Board

  ##
  # These variables can only be read outside of this class.
  # +width+:: The width of the board.
  # +height+:: The height of the board.
  # +nodes+:: Matrix that holds all the nodes, which is essentially the board itself.
  # +start_node+:: Reference for the starting node.
  # +destination_node+:: Reference for the destination node.
  attr_reader :width, :height, :nodes, :start_node, :destination_node

  ##
  # Instantiates the node matrix with the given dimensions, then fills it up with wall and floor nodes.
  # Params:
  # +width+:: The width of the board.
  # +height+:: The height of the board.
  def generate_board(width, height)
    @width = width
    @height = height
    @nodes = Array.new(height) { Array.new(width) }

    # Iterates through the matrix
    (0..height - 1).each do |i|
      (0..width - 1).each do |j|

        # If the node is on the edge, then it becomes a wall.
        # Else there is a 10% chache for it to become a wall, otherwise it will be a floor node.
        type = if i == 0 || j == 0 || i == height - 1 || j == width - 1
                 :wall
               else
                 if rand(1..100) < 15
                   :wall
                 else
                   :floor
                 end
               end

        @nodes[i][j] = Node.new(type, j, i)
      end
    end

    # Iterates through the nodes and set their neighbours.
    # This needs to be done after the previous iteration to ensure there is no null node.
    @nodes.each do |columns|
      columns.each do |node|
        add_neighbours(node)
      end
    end
  end

  ##
  # Generates a string from the node matrix and adds marking numbers to it, then writes it to the console.
  def draw_board
    # The biggest expected character numbers.
    max_column_number_length = (@width - 1).to_s.size
    max_row_number_length = (@height - 1).to_s.size

    # The output and the horizontal row strings are kept separately.
    output = Array.new(height)
    horizontal_number_row = Array.new(max_column_number_length, '')

    # Indenting the rows, so the sliced number will aligne when printed to the console.
    (0..max_column_number_length - 1).each do |index|
      horizontal_number_row[index] = ''.rjust((10**index) * 2, ' ').prepend(' ')
      horizontal_number_row[index].prepend('  ') if index > 0
    end

    # Iterating through the node matrix to create the final output. The formating will also be done in this loop.
    (0..@height - 1).each do |i|
      row = ''
      (0..@width - 1).each do |j|
        if i == 0
          (0..j.to_s.size - 1).each do |k|
            horizontal_number_row[k] << j.to_s.split('')[k] << ' '
          end
        end

        output[i] = i.to_s.rjust(max_row_number_length, ' ') if j == 0

        row << @nodes[i][j].to_s.prepend(' ')
      end

      output[i] << row
    end

    puts horizontal_number_row.join("\n")
    puts output.join("\n")
  end

  ##
  # Checks if is there a node at the coordinates and if it's a floor.
  # Params:
  # +x+:: The X coordinate of the node.
  # +y+:: The Y coordinate of the node.
  def coordinate_available?(x, y)
    @nodes[y][x].type == :floor if coordinate_within_bounds?(x, y)
  end

  ##
  # Set the node type to Start and set the start_node reference to node.
  # Params:
  # +x+:: The X coordinate of the node.
  # +y+:: The Y coordinate of the node.
  def set_starting_node(x, y)
    set_node(x, y, :start)
    @start_node = @nodes[y][x]
  end

  ##
  # Set the node type to Destination and set the destination_node reference to node.
  # Params:
  # +x+:: The X coordinate of the node.
  # +y+:: The Y coordinate of the node.
  def set_destination_node(x, y)
    set_node(x, y, :destination)
    @destination_node = @nodes[y][x]
  end

  ##
  # Set the node type to Path.
  # Params:
  # +x+:: The X coordinate of the node.
  # +y+:: The Y coordinate of the node.
  def set_path_node(x, y)
    set_node(x, y, :path)
  end

  ##
  # Iterates through the path and if the node type is floor marks it.
  # Params:
  # +path+:: The path from start to destination.
  def mark_path(path)
    path.each do |step|
      set_path_node(step.x, step.y) if step.type == :floor
    end
  end

  ##
  # Check if the board dimension fits the criteria.
  # Params:
  # +value+:: The supposed board dimension.
  def check_board_dimension(value)
    value >= 4 && value <= 101
  end

  private

  ##
  # Sets the node to the given type.
  # Params:
  # +x+:: The X coordinate of the node.
  # +y+:: The Y coordinate of the node.
  # +type+:: The type to which the node should be set.
  def set_node(x, y, type)
    @nodes[y][x].type = type
  end

  ##
  # Checks if the given coordinates are within bounds of the Board size.
  # Params:
  # +x+:: The X coordinate of the node.
  # +y+:: The Y coordinate of the node.
  def coordinate_within_bounds?(x, y)
    x < @width && x >= 0 && y < @height && y >= 0
  end

  ##
  # Runs a 4 connected node search for the node and sets the neighbours for the node.
  # Params:
  # +node+:: The node that stands in the middle point of the search.
  def add_neighbours(node)
    neighbours = []
    counter = 0

    ((node.x - 1)..(node.x + 1)).each do |i|
      ((node.y - 1)..(node.y + 1)).each do |j|

        # This check ensures that the list will not contains the node and that there will be no null node.
        if (i == node.x || j == node.y) && !(i == node.x && j == node.y) && coordinate_within_bounds?(i, j)
          neighbours[counter] = @nodes[j][i]
          counter += 1
        end
      end
    end

    node.neighbours = neighbours
  end
end
