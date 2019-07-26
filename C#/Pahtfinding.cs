using System.Collections.Generic;
using System.Text.RegularExpressions;
using Pathfinding;

namespace Pathfinding {

  abstract class Pathfinding { 

    protected Board boardHandler {get; private set;} //The Board on which the algorithm searches the path.

    /// <summary>
    /// Sets the reference Board to ensure that the functions can work properly.
    /// </summary>
    /// <param name="boardHandler">The Board on which the path is calculated.</param>
    public Pathfinding(Board boardHandler){
      this.boardHandler = boardHandler;
    }
    
    public abstract Stack<Node> GetPath();

    public override string ToString() {
      return Regex.Replace(this.GetType().Name, @"((?<=\p{Ll})\p{Lu})|((?!\A)\p{Lu}(?>\p{Ll}))", " $0");
    }
  }
}