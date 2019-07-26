from board import Board
from astar import AStar
from input import Input


board_handler = Board()
path_handler = AStar(board_handler)
input_handler = Input(board_handler, path_handler)

while True:
  x = 0
  y = 0

  input_handler.procces_input('help')
  input_handler.procces_input(input("Press any key to start.\n"))

  board_handler.generate_board(10,10)
  
  while not hasattr(board_handler, 'start_node'):
    board_handler.draw_board()

    x = input_handler.get_numeric("Please type the start column number.")
    y = input_handler.get_numeric("Please type the start row number.")

    if board_handler.is_coordinate_available(x, y):
      board_handler.set_starting_node(x, y)

  while not hasattr(board_handler, 'destination_node'):
    board_handler.draw_board()

    x = input_handler.get_numeric("Please type the destination column number.")
    y = input_handler.get_numeric("Please type the destination row number.")

    if board_handler.is_coordinate_available(x, y):
      board_handler.set_destination_node(x, y)

  board_handler.draw_board()
  input("Press any key to start the pathfinding.")
  path = path_handler.calculate_path()

  if len(path) == 0:
    print("No path found.")
  else:
    board_handler.mark_path(path)
    board_handler.draw_board()

  input()