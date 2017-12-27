package pacman.entries.pacman;

import java.util.ArrayList;
import java.util.Random;

import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class EvolutionStrategy extends Controller<MOVE> {
	private static final MOVE bestMove = MOVE.DOWN;

	public MOVE getMove(Game game, long timeDue) {
		MOVE[] moves = { MOVE.DOWN, MOVE.UP, MOVE.LEFT, MOVE.RIGHT };
		ArrayList<MOVE> population = new ArrayList<MOVE>();
		for (int i = 0; i < 10; i++) {
			population.add(i, moves[new Random().nextInt(moves.length)]);
		}
		
		
		
		
		return bestMove;
	}
}