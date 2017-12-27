package pacman.entries.pacman;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Queue;

import pacman.controllers.Controller;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE> {

	public MOVE getMove(Game game, long timeDue) {
		MOVE bestMove = null;
		Queue<Game> GameStates = new LinkedList<Game>();
		EnumMap<GHOST, MOVE> ghostMoves = new StarterGhosts().getMove();

		for (MOVE move : game.getPossibleMoves(game.getPacmanCurrentNodeIndex())) {
			Game copy = game.copy();
			copy.advanceGame(move, ghostMoves);
			GameStates.add(copy);
		}

		Game tempGameCurrent = game.copy();
		while (!GameStates.isEmpty()) {
			Game tempGame = GameStates.poll();
			if (tempGame.equals(getBetterState(tempGameCurrent, tempGame))) {
				tempGameCurrent = tempGame;
				bestMove = tempGameCurrent.getPacmanLastMoveMade();
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

// public Game getBetterState(Game copy, Game tempGame) {
// int PacmanPosition_copy = copy.getPacmanCurrentNodeIndex();
// int PacmanPosition_tempGame = tempGame.getPacmanCurrentNodeIndex();
// int Score_copy = 0;
// int Score_tempGame = 0;
//
// for (GHOST ghost : GHOST.values()) {
// int GhostPosition_copy = copy.getGhostCurrentNodeIndex(ghost);
// int GhostPosition_tempGame = tempGame.getGhostCurrentNodeIndex(ghost);
//
// if (copy.isGhostEdible(ghost)) {
// if (copy.getShortestPathDistance(PacmanPosition_copy, GhostPosition_copy) <
// Min_Distance_To) {
// Score_copy += 2;
// }
// } else {
// if (copy.getShortestPathDistance(PacmanPosition_copy, GhostPosition_copy) >
// Min_Distance_Away) {
// Score_copy += 2;
// }
// }
//
// if (tempGame.isGhostEdible(ghost)) {
// if (tempGame.getShortestPathDistance(PacmanPosition_tempGame,
// GhostPosition_tempGame) < Min_Distance_To) {
// Score_tempGame += 2;
// }
// } else {
// if (tempGame.getShortestPathDistance(PacmanPosition_tempGame,
// GhostPosition_tempGame) > Min_Distance_Away) {
// Score_tempGame += 2;
// }
// }
// }
//
// int[] PowerPills_copy = copy.getActivePowerPillsIndices();
// int[] Pills_copy = copy.getActivePillsIndices();
// int[] PowerPills_tempGame = tempGame.getActivePowerPillsIndices();
// int[] Pills_tempGame = tempGame.getActivePillsIndices();
//
// for (int i = 0; i < PowerPills_copy.length; i++) {
// if (copy.getShortestPathDistance(PacmanPosition_copy, PowerPills_copy[i]) <
// Min_Distance_To) {
// Score_copy += 1;
// }
// }
//
// for (int i = 0; i < PowerPills_tempGame.length; i++) {
// if (copy.getShortestPathDistance(PacmanPosition_tempGame,
// PowerPills_tempGame[i]) < Min_Distance_To) {
// Score_tempGame += 1;
// }
// }
//
// for (int i = 0; i < Pills_copy.length; i++) {
// if (copy.getShortestPathDistance(PacmanPosition_copy, Pills_copy[i]) <
// Min_Distance_To) {
// Score_copy += 1;
// }
// }
//
// for (int i = 0; i < Pills_tempGame.length; i++) {
// if (copy.getShortestPathDistance(PacmanPosition_tempGame, Pills_tempGame[i])
// < Min_Distance_To) {
// Score_tempGame += 1;
// }
// }
//
// if (Score_copy > Score_tempGame)
// return copy;
// else
// return tempGame;
// }