require './input'
require './board'
require './astar'

# The Main Program Loop.
class Main

  ##
  # These variables can only be read outside of this class.
  # +board_handler+:: Board instance.
  # +path_handler+:: Pathfinding instance.
  # +input_handler+:: Handles the user input.
  # +width+:: The width of the board.
  # +height+:: The height of the board.
  attr_reader :board_handler, :path_handler, :input_handler, :height, :width

  @board_handler = Board.new
  @path_handler = AStar.new(@board_handler)
  @input_handler = Input.new(@board_handler, @input_handler)

  ##
  # Starts an infinite loop which always generates a new board.
  # 1. Ask the user for the board dimension.
  # 2. Generates the board.
  # 3. Ask the user for the starting node.
  # 4. Ask the user for the destination node.
  # 5. Marks the path (if there is a path).
  # 6. Starts again
  loop do
    @input_handler.procces_input('help')
    puts 'Press any key to start.'
    @input_handler.procces_input(gets.chomp)

    loop do
      @width = @input_handler.get_numeric('Please provide a width for the board (a whole number between 4 & 101).')

      break if @board_handler.check_board_dimension(@width)
    end

    loop do
      @height = @input_handler.get_numeric('Please provide a height for the board (a whole number between 4 & 101).')

      break if @board_handler.check_board_dimension(@height)
    end

    @board_handler.generate_board(@width, @height)

    loop do
      @board_handler.draw_board

      x = @input_handler.get_numeric('Please type the start column number.')
      y = @input_handler.get_numeric('Please type the start row number.')

      if @board_handler.coordinate_available?(x, y)
        @board_handler.set_starting_node(x, y)
        break
      end
    end

    loop do
      @board_handler.draw_board

      x = @input_handler.get_numeric('Please type the destination column number.')
      y = @input_handler.get_numeric('Please type the destination row number.')

      if @board_handler.coordinate_available?(x, y)
        @board_handler.set_destination_node(x, y)
        break
      end
    end

    puts 'Press any key to start the pathfinding.'
    @input_handler.procces_input(gets.chomp)

    path = @path_handler.calculate_path

    if path.empty?
      puts 'No path found.'
    else
      @board_handler.mark_path(path)
      @board_handler.draw_board
    end

    @input_handler.procces_input(gets.chomp)
  end
end