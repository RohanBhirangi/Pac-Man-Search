package pacman.entries.pacman;

import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Node {
	public Game GameState;
	public MOVE move;
	public Boolean visited;

	public Node(Game GameState, MOVE move, Boolean visited) {
		this.GameState = GameState;
		this.move = move;
		this.visited = visited;
	}
}
