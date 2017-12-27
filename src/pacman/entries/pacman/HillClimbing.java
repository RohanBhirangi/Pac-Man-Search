package pacman.entries.pacman;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Queue;

import pacman.controllers.Controller;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class HillClimbing extends Controller<MOVE> {

	public MOVE getMove(Game game, long timeDue) {
		MOVE bestMove = null;
		Queue<Node> Queue = new LinkedList<Node>();
		EnumMap<GHOST, MOVE> ghostMoves = new StarterGhosts().getMove();

		// get neighbours/successors of initial state
		for (MOVE move : game.getPossibleMoves(game.getPacmanCurrentNodeIndex())) {
			Node copy = new Node(game.copy(), move, false);
			copy.GameState.advanceGame(move, ghostMoves);
			Queue.add(copy);
		}

		// get initial state
		Node tempGameCurrent = new Node(game.copy(), game.getPacmanLastMoveMade(), false);

		// find highest valued successor state
		while (!Queue.isEmpty()) {
			Node tempGame = Queue.poll();
			if (tempGame.GameState.equals(getBetterState(tempGameCurrent.GameState, tempGame.GameState))) {
				tempGameCurrent = tempGame;
				bestMove = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
						tempGame.GameState.getPacmanCurrentNodeIndex(), DM.PATH);
			}
		}

		return bestMove;
	}

	public Game getBetterState(Game game1, Game game2) {
		int PacmanPosition1 = game1.getPacmanCurrentNodeIndex();
		int PacmanPosition2 = game2.getPacmanCurrentNodeIndex();
		int minDist1 = Integer.MAX_VALUE;
		int minDist2 = Integer.MAX_VALUE;
		int GhostPosition1;
		int GhostPosition2;

		for (GHOST ghost : GHOST.values()) {
			GhostPosition1 = game1.getGhostCurrentNodeIndex(ghost);
			GhostPosition2 = game2.getGhostCurrentNodeIndex(ghost);

			if (game1.getShortestPathDistance(PacmanPosition1, GhostPosition1) < minDist1) {
				minDist1 = game1.getShortestPathDistance(PacmanPosition1, GhostPosition1);
			}

			if (game2.getShortestPathDistance(PacmanPosition2, GhostPosition2) < minDist2) {
				minDist2 = game2.getShortestPathDistance(PacmanPosition2, GhostPosition2);
			}
		}

		if (minDist1 > minDist2) {
			return game1;
		} else {
			return game2;
		}
	}
}