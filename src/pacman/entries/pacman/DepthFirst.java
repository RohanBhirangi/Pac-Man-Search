package pacman.entries.pacman;

import java.util.EnumMap;
import java.util.Stack;

import pacman.controllers.Controller;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class DepthFirst extends Controller<MOVE> {

	public MOVE getMove(Game game, long timeDue) {
		MOVE bestMove = null;
		Stack<Node> stack = new Stack<Node>();
		EnumMap<GHOST, MOVE> ghostMoves = new StarterGhosts().getMove();
		int i = 0;

		stack.push(new Node(game.copy(), game.getPacmanLastMoveMade(), false));

		while (!stack.isEmpty()) {
			i++;
			Node tempGame = stack.pop();
			for (MOVE move : tempGame.GameState.getPossibleMoves(tempGame.GameState.getPacmanCurrentNodeIndex())) {
				Node copy = new Node(tempGame.GameState.copy(), move, false);
				copy.GameState.advanceGame(move, ghostMoves);
				if (!copy.visited) {
					copy.visited = true;
					stack.push(copy);
					if (getStateValue(copy.GameState) > getStateValue(tempGame.GameState)) {
						tempGame = copy;
						bestMove = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
								copy.GameState.getPacmanCurrentNodeIndex(), DM.PATH);
					}
				}
			}
			if (i == 5) {
				i = 0;
				break;
			}
		}

		return bestMove;
	}

	public int getStateValue(Game game) {
		int PacmanPosition = game.getPacmanCurrentNodeIndex();
		int minDist = Integer.MAX_VALUE;
		int GhostPosition;

		for (GHOST ghost : GHOST.values()) {
			GhostPosition = game.getGhostCurrentNodeIndex(ghost);
			if (game.getShortestPathDistance(PacmanPosition, GhostPosition) < minDist) {
				minDist = game.getShortestPathDistance(PacmanPosition, GhostPosition);
			}
		}
		return minDist;
	}
}