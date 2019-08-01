# C# Implementation

## Introduction
The goal of this project was to create an easy to understand implementation of the different pathfinding algorithms in C#.
To make the project more user friendly I created a simple CLI BoardGame in which you can try out the algorithm.

## Things that can be improved
As the main focus of this project was to implement the pathfinding algorithm (and not to create a game) the whole Demo part could be improved. 
Example: 
1. Baking the node data once, after the start and destination node is selected would be much better, but I stuck with a more common logic as it's more flexible.

## Example Scenario
```
Symbol Description
- : floor node.
X : wall node.
S : starting node.
D : destination node.
* : path node.
~ : Nodes that were checked, but not part of the path.
Commands
Type exit to close the application.
Press any key to start.

  0 1 2 3 4 5 6 7 8 9 
0 X X X X X X X X X X 
1 X - - - X - - - - X 
2 X - - - - - - X - X
3 X - - X - - - - - X
4 X - - - - - - X - X
5 X - - - - - - - - X
6 X X - X - - - - - X
7 X - - - X - - - - X
8 X - - - X - - - - X
9 X X X X X X X X X X

Please type the starting column number.
1
Please type the starting row number.
1
  0 1 2 3 4 5 6 7 8 9 
0 X X X X X X X X X X
1 X S - - X - - - - X
2 X - - - - - - X - X
3 X - - X - - - - - X
4 X - - - - - - X - X
5 X - - - - - - - - X
6 X X - X - - - - - X
7 X - - - X - - - - X
8 X - - - X - - - - X
9 X X X X X X X X X X

  0 1 2 3 4 5 6 7 8 9
0 X X X X X X X X X X
1 X S - - X - - - - X
2 X - - - - - - X - X
3 X - - X - - - - - X
4 X - - - - - - X - X
5 X - - - - - - - - X
6 X X - X - - - - - X
7 X - - - X - - - - X
8 X - - - X - - - - X
9 X X X X X X X X X X

Please type the destination column number.
5
Please type the destination row number.
5
  0 1 2 3 4 5 6 7 8 9 
0 X X X X X X X X X X
1 X S - - X - - - - X
2 X - - - - - - X - X
3 X - - X - - - - - X
4 X - - - - - - X - X
5 X - - - - D - - - X
6 X X - X - - - - - X
7 X - - - X - - - - X
8 X - - - X - - - - X
9 X X X X X X X X X X

Please choose a pathfinding method.
0 : Depth First
1 : Breadth First
2 : Dijkstras
3 : Astar
1
  0 1 2 3 4 5 6 7 8 9 
0 X X X X X X X X X X
1 X S * * X ~ ~ - - X
2 X ~ ~ * * * ~ X - X
3 X ~ ~ X ~ * ~ - - X
4 X ~ ~ ~ ~ * - X - X
5 X ~ ~ ~ ~ D - - - X
6 X X ~ X ~ - - - - X
7 X ~ ~ ~ X - - - - X
8 X - ~ - X - - - - X
9 X X X X X X X X X X

Total cost of the board: 512
Number of checked nodes: 32
Number of steps: 8
Total cost of the path: 44
Path:
X: 2 Y: 1 Cost: 2
X: 3 Y: 1 Cost: 6
X: 3 Y: 2 Cost: 9
X: 4 Y: 2 Cost: 3
X: 5 Y: 2 Cost: 4
X: 5 Y: 3 Cost: 6
X: 5 Y: 4 Cost: 6
X: 5 Y: 5 Cost: 8
```

## Example outputs
```
  0 1 2 3 4 5 6 7 8 9
0 X X X X X X X X X X
1 X ~ ~ ~ ~ X D * - X
2 X X ~ ~ ~ ~ X * - X
3 X S X X * * * * - X
4 X * * * * ~ ~ - X X
5 X - - X X - X - - X
6 X - - - - - - - - X
7 X - - - - - - - X X
8 X - X - - - - - X X
9 X X X X X X X X X X

  0 1 2 3 4 5 6 7 8 9 
0 X X X X X X X X X X
1 X - X X ~ ~ ~ ~ D X
2 X - - - ~ ~ X ~ * X
3 X - X - ~ ~ ~ ~ * X
4 X - - - ~ ~ ~ X * X
5 X - - - S * * * * X
6 X - - - X - - - - X
7 X - - - - X - - - X
8 X - - - X - - X - X
9 X X X X X X X X X X
```