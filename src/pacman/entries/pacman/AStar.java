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

public class AStar extends Controller<MOVE> {

	public MOVE getMove(Game game, long timeDue) {
		MOVE bestMove = null;
		Queue<AStarNode> openSet = new LinkedList<AStarNode>();
		Queue<AStarNode> closedSet = new LinkedList<AStarNode>();
		EnumMap<GHOST, MOVE> ghostMoves = new StarterGhosts().getMove();

		int j = getNearestPowerPillIndex(game);
		openSet.add(new AStarNode(game.copy(), game.getPacmanLastMoveMade(), false, getDistanceFromSource(game, game),
				getHeuristic(game, j)));

		while (!openSet.isEmpty()) {
			AStarNode tempGame = getMinimumNode(openSet);
			closedSet.add(tempGame);
			openSet.poll();
			AStarNode tempcopy = tempGame;

			if (getHeuristic(tempGame.GameState, j) == 0) {
				bestMove = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
						tempGame.GameState.getPacmanCurrentNodeIndex(), DM.PATH);
			}

			for (MOVE move : tempGame.GameState.getPossibleMoves(tempGame.GameState.getPacmanCurrentNodeIndex())) {
				AStarNode copy = null;
				copy = new AStarNode(tempGame.GameState.copy(), move, false, 0, 0);
				copy.g = getDistanceFromSource(tempGame.GameState, copy.GameState);
				copy.h = getHeuristic(copy.GameState, j);
				copy.GameState.advanceGame(move, ghostMoves);

				if (!openSet.contains(copy)) {
					openSet.add(copy);
				} else {
					if ((copy.g + copy.h) < (tempcopy.g + tempcopy.h)) {
						tempcopy = copy;
						bestMove = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
								copy.GameState.getPacmanCurrentNodeIndex(), DM.PATH);
					}
				}
			}
		}
		return bestMove;
	}

	public AStarNode getMinimumNode(Queue<AStarNode> openSet) {
		double f = Double.MAX_VALUE;
		AStarNode goalNode = null;
		for (AStarNode node : openSet) {
			if ((node.g + node.h) < f) {
				goalNode = node;
			}
		}
		return goalNode;
	}

	public int getNearestPowerPillIndex(Game game) {
		int PacmanPosition = game.getPacmanCurrentNodeIndex();
		double minDist = Double.MAX_VALUE;
		int[] powerPills = game.getPowerPillIndices();
		int j = 0;

		for (int i = 0; i < powerPills.length; i++)
			if (game.isPowerPillStillAvailable(i))
				if (game.getDistance(PacmanPosition, powerPills[i], DM.PATH) < minDist) {
					minDist = game.getShortestPathDistance(PacmanPosition, powerPills[i]);
					j = powerPills[i];
				}
		return j;

	}

	public double getDistanceFromSource(Game source, Game dest) {
		return source.getShortestPathDistance(source.getPacmanCurrentNodeIndex(), dest.getPacmanCurrentNodeIndex());
	}

	public double getHeuristic(Game game, int j) {
		return game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), j);
	}
}