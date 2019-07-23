from board import Board
from astar import AStar

# The main class for input handling.
class Input:

  """Sets the board and path handler.

  :param board_handler: The Board on which the path is calculated.
  :param path_handler: Pathfinding instance.
  """
  def __init__(self, board_handler, path_handler):
    self.board_handler = board_handler
    self.path_handler = path_handler

  """Checks the user input for commands.

  :param input: The input that the user has provided.
  :rtype: The same as the input type.
  """
  def procces_input(self, input):

    if input.lower() == "exit":
      quit(0)
    elif input.lower() == "help": 
      print("Symbol Description\n- : floor node.\nX : wall node.\nS : starting node.\nD : destination node.\n* : path node.\nCommands\nType exit to close the application.")

    return input

  """Displays a guide text for the user and evaluates if the input fits the requirements. Repeats until a valid response is not given, then returns it as an integer.

  :param text: The guide text, which ask the input from the user.
  :rtype: An Integer.
  """
  def get_numeric(self, text):
    number = ""

    while(not self.is_integer(number)):
      number = self.procces_input(input(text + "\n"))

    return int(number)

  """ Checks if the given text can be converted into an integer.

  :param text: The text which the method tries to convert.
  :rtype: A Boolean.
  """
  def is_integer(self, text):
    try:
      int(text)
    except ValueError:
        return False
    
    return True