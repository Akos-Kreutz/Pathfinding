require './board'
require './astar'

##
# The Main Program Loop.
class Input

  ##
  # These variables can only be read outside of this class.
  # +board_handler+:: Board instance.
  # +path_handler+:: Pathfinding instance.
  attr_reader :board_handler, :path_handler

  ##
  # Sets the board and path handler.
  def initialize(board_handler, path_handler)
    @board_handler = board_handler
    @path_handler = path_handler
  end

  ##
  # Checks the user input for commands.
  # Params:
  # +input+:: The input that the user has provided.
  def procces_input(input)
    case input.to_s.downcase
    when 'exit'
      exit 0
    when 'help'
      puts "Symbol Description\n- : floor node.\nX : wall node.\nS : starting node.\nD : destination node.\n* : path node.\n? : Nodes that were checked, but not part of the path.\nCommands\nType exit to close the application."
    else
      input
    end
  end

  ##
  # Displays a guide text for the user and evaluates if the input fits the requirements. Repeats until a valid response is not given, then returns it as an integer.
  # Params:
  # +text+:: The guide text, which ask the input from the user.
  def get_numeric(text)
    puts text

    puts 'Please try again.' until /\A[-+]?\d+\z/ === number = procces_input(gets.chomp)

    number.to_i
  end
end
