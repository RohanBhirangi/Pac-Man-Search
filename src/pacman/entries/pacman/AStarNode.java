package pacman.entries.pacman;

import pacman.game.Game;
import pacman.game.Constants.MOVE;

public class AStarNode {
	public Game GameState;
	public MOVE move;
	public Boolean visited;
	public double g;
	public double h;

	public AStarNode(Game GameState, MOVE move, Boolean visited, double g, double h) {
		this.GameState = GameState;
		this.move = move;
		this.visited = visited;
		this.g = g;
		this.h = h;
	}
}
