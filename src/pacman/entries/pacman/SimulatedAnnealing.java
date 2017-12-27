package pacman.entries.pacman;

import java.util.EnumMap;
import java.util.Random;

import pacman.controllers.Controller;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class SimulatedAnnealing extends Controller<MOVE> {

	public MOVE getMove(Game game, long timeDue) {
		EnumMap<GHOST, MOVE> ghostMoves = new StarterGhosts().getMove();
		// set intial temperature
		double T = 10000;
		// define cooling rate
		double coolingRate = 0.003;

		// get initial state
		Node current = new Node(game.copy(), game.getPacmanLastMoveMade(), false);

		while (T > 1) {
			// get random successor state
			Node next = getRandomState(current.GameState, ghostMoves);
			int E = getStateValue(next.GameState) - getStateValue(current.GameState);
			// choose state if good
			if (E > 0) {
				current = next;
			} else {
				// choose bad state with probability e^(E/T)
				if (Math.exp(E / T) >= Math.random())
					current = next;
			}
			T *= 1 - coolingRate;
		}

		return current.move;
	}

	public Node getRandomState(Game game, EnumMap<GHOST, MOVE> ghostMoves) {
		MOVE[] moves = game.getPossibleMoves(game.getPacmanCurrentNodeIndex());
		Node copy = new Node(game.copy(), moves[new Random().nextInt(moves.length)], false);
		copy.GameState.advanceGame(moves[new Random().nextInt(moves.length)], ghostMoves);
		return copy;

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