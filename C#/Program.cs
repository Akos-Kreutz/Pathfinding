using System;
using System.Collections.Generic;

namespace Pathfinding {

  /// <summary>
  /// The Main Program Loop.
  /// </summary>
  class Program {
    private readonly static Board BoardHandler = new Board(); //Board instance
    private readonly static Dictionary<int, Pathfinding> PathHandlers = new Dictionary<int, Pathfinding>(){
      {0, new Astar(BoardHandler)},
      {1, new BreadthFirst(BoardHandler)}
    }; //The Pathfinder Algorithms with their assigned index.

    /// <summary>
    /// Instantiates the handlers and starts an infinite loop, which can be only escaped through user input.
    /// </summary>
    /// <param name="args">CLI Arguments</param>
    /// <seealso cref="Program.ProcessInput(string)"/>
    static void Main(string[] args) {

      //Starts an infinite loop which always generates a new board.
      while(true){
        System.Console.Clear();

        ProcessInput("help");
        System.Console.WriteLine("Press any key to start.");
        ProcessInput(System.Console.ReadLine());

        BoardHandler.GenerateBoard(10,10);
        
        MainLoop();
      }
    }

    /// <summary>
    /// Ask the user to set the start and destination node, the calculates the path.
    /// </summary>
    /// <seealso cref="Program.SetCoordinates(out int, out int, string)"/>
    /// <seealso cref="Program.ProcessInput(string)"/>
    private static void MainLoop(){
      int x, y;

      SetCoordinates(out x, out y, "starting");

      BoardHandler.SetStartingNode(x,y);
      BoardHandler.DrawBoard();

      SetCoordinates(out x, out y, "destination");

      BoardHandler.SetDestinationNode(x,y);
      BoardHandler.DrawBoard();

      Stack<Node> path = GetPathfindingMethod().GetPath();

      if(path.Count == 0){
        System.Console.WriteLine("No Path found.");
      } else {
        BoardHandler.MarkPath(path);
        BoardHandler.DrawBoard();
      }

      ProcessInput(System.Console.ReadLine());
    }

    /// <summary>
    /// Checks if the string is a number, if it is, sets the number via reference.
    /// </summary>
    /// <param name="input">The input string which needs to be evaluated.</param>
    /// <param name="number">The reference number which requires the value.</param>
    /// <returns>
    /// If the string is a number.
    /// </returns>
    private static bool IsItANumber(string input, out int number){
      return int.TryParse(input, out number);
    }

    /// <summary>
    /// Ask the user until a correct X and Y coordinate is provided.
    /// </summary>
    /// <param name="x">The X coordinate reference.</param>
    /// <param name="y">The Y coordinate reference.</param>
    /// <param name="name">The name of which node type requires the values.</param>
    /// <seealso cref="Program.SetCoordinate(string, out int)"/>
    private static void SetCoordinates(out int x, out int y, string name){
      do {
        BoardHandler.DrawBoard();

        SetCoordinate(string.Format("Please type the {0} column number.", name), out x);
        SetCoordinate(string.Format("Please type the {0} row number.", name), out y);

      } while (!BoardHandler.IsCoordinateAvailable(x,y));
    }

    /// <summary>
    /// Displays a guide text for the user and evaluates if the input fits the requirements. Repeats until a valid response is not given.
    /// </summary>
    /// <param name="text">The guide text, which ask the input from the user.</param>
    /// <param name="coordinate">The required coordinate.</param>
    private static void SetCoordinate(string text, out int coordinate){
      System.Console.WriteLine(text);

      while(!IsItANumber(ProcessInput(System.Console.ReadLine()), out coordinate)){
        System.Console.WriteLine("Please try again.");
      }
    }

    /// <summary>
    /// Checks the user input for commands.
    /// </summary>
    /// <param name="input">The input that the user has provided.</param>
    /// <returns>
    /// The input string.
    /// </returns>
    private static string ProcessInput(string input){

      if(input.ToLower().Equals("exit")){
        Environment.Exit(0);
      } 
      else if(input.ToLower().Equals("help")){
        System.Console.WriteLine("Symbol Description{0}- : floor node.{0}X : wall node.{0}S : starting node.{0}D : destination node.{0}* : path node.{0}~ : Nodes that were checked, but not part of the path.{0}Commands{0}Type exit to close the application.", Environment.NewLine);
      }

      return input;
    }

    /// <summary>
    /// Lists the pathfinding methods and waits for the user to choose one.
    /// </summary>
    /// <returns>
    /// The chosen pathfinding method.
    /// </returns>
    private static Pathfinding GetPathfindingMethod(){
      int number = -1;

      System.Console.WriteLine("Please choose a pathfinding method.");

      foreach(KeyValuePair<int, Pathfinding> pair in PathHandlers){
        System.Console.WriteLine("{0} : {1}", pair.Key, pair.Value);
      }

      while(!IsItANumber(ProcessInput(System.Console.ReadLine()), out number) || !(number < PathHandlers.Count && number >= 0)) {
        System.Console.WriteLine("Please try again.");
      }

      return PathHandlers[number];
    }
  }
}
