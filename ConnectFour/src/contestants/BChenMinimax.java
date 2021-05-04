package contestants;

import connectFour.Grid;
import connectFour.GridUtilities;

public class BChenMinimax implements connectFour.Player {

	private int next;
	private int numMoves = 8;
	@Override
	public int getMoveColumn(Grid g)
	{
		next = g.getNextPlayer();
		int move = minimaxGetScoreAB(g,numMoves,next,Integer.MIN_VALUE,Integer.MAX_VALUE)[1];
		System.out.println("The AI went to column: " + move);
		return move;
	}

	@Override
	public String getPlayerName()
	{
		// TODO Auto-generated method stub
		return "Brandon's Game Winning Algorithm (yay!!!)";
	}
    // Returns an array of two integers: [0] is the score for this grid, and
    // [1] is the recommended column to move in for this grid. 
    private int[] minimaxGetScoreAB(Grid g, int remainingDepth, int myPlayer, int a, int b)
    {
        // Did this move end the game? If so, score it now based on whether we won.
        if (g.getWinningPlayer() == myPlayer)
        {
            // We won!
            return new int[] { 1000 * (remainingDepth + 1), -1 };
        }
        else if (g.getWinningPlayer() == (3 - myPlayer))
        {
            // They won
            return new int[] { -1000 * (remainingDepth + 1), -1 };
        }
        else if (g.getWinningPlayer() == -1)
        {
            // Game ends in a draw.
            return new int[] { 0, -1 };
        }

        int nextPlayer = g.getNextPlayer();

        // We don't want to go any deeper, so just return the immediate heuristic score
        // for this board
        if (remainingDepth <= 0)
        {
            int score = getHeuristicScore(g);
            return new int[] { score, -1 };
        }

        // Call self recursively for next player's moves' scores
        
        // Is this nextPlayer trying to minimize or maximize the score?  If it's us,
        // maximize.  If opponent, minimize.
        boolean isMax = (nextPlayer == myPlayer);
        int bestMove = -1;
        int bestScore;
        if (isMax)
        {
            bestScore = Integer.MIN_VALUE;
        }
        else
        {
            bestScore = Integer.MAX_VALUE;
        }        

        for (int nextCol = 0; nextCol < g.getCols(); nextCol++)
        {
            if (!g.isColumnFull(nextCol))
            {
                // Apply the move (temporarily) to the grid...
                g.makeMove(nextCol);
                
                // ... and then call ourselves recursively to move down the decision tree
                // and come up with a score                
                int scoreCur = minimaxGetScoreAB(g, remainingDepth - 1, myPlayer, a, b)[0];
                
                // ... and we must remember to UNDO that move now that the call is done.
                g.undo();
                
                // Update bestScore with what the recursive call returned
                if (isMax)
                {
                    if (scoreCur > bestScore)
                    {
                        bestScore = scoreCur;
                        a = scoreCur;
                        bestMove = nextCol;
                        
                    }
                }
                else
                {
                    // minimizing!
                    if (scoreCur < bestScore)
                    {
                        bestScore = scoreCur;
                        b = scoreCur;
                        bestMove = nextCol;
                    }
                }
            }
            if (a >= b) {
            	break;
            }
        }

        // Return the best score (and the recommended move)
        return new int[] { bestScore, bestMove };        
    }

    // Returns an array of two integers: [0] is the score for this grid, and
    // [1] is the recommended column to move in for this grid. 
    private int[] minimaxGetScore(Grid g, int remainingDepth, int myPlayer)
    {
        // Did this move end the game?  If so, score it now based on whether we won.
        if (g.getWinningPlayer() == myPlayer)
        {
            // We won!
            return new int[] { 1000 * (remainingDepth + 1), -1 };
        }
        else if (g.getWinningPlayer() == (3 - myPlayer))
        {
            // They won
            return new int[] { -1000 * (remainingDepth + 1), -1 };
        }
        else if (g.getWinningPlayer() == -1)
        {
            // Game ends in a draw.
            return new int[] { 0, -1 };
        }

        int nextPlayer = g.getNextPlayer();

        // We don't want to go any deeper, so just return the immediate heuristic score
        // for this board
        if (remainingDepth <= 0)
        {
            int score = getHeuristicScore(g);
            return new int[] { score, -1 };
        }

        // Call self recursively for next player's moves' scores
        
        // Is this nextPlayer trying to minimize or maximize the score?  If it's us,
        // maximize.  If opponent, minimize.
        boolean isMax = (nextPlayer == myPlayer);
        int bestMove = -1;
        int bestScore;
        if (isMax)
        {
            bestScore = Integer.MIN_VALUE;
        }
        else
        {
            bestScore = Integer.MAX_VALUE;
        }        

        for (int nextCol = 0; nextCol < g.getCols(); nextCol++)
        {
            if (!g.isColumnFull(nextCol))
            {
                // Apply the move (temporarily) to the grid...
                g.makeMove(nextCol);
                
                // ... and then call ourselves recursively to move down the decision tree
                // and come up with a score                
                int scoreCur = minimaxGetScore(g, remainingDepth - 1, myPlayer)[0];
                
                // ... and we must remember to UNDO that move now that the call is done.
                g.undo();
                
                // Update bestScore with what the recursive call returned
                if (isMax)
                {
                    if (scoreCur > bestScore)
                    {
                        bestScore = scoreCur;
                        bestMove = nextCol;
                    }
                }
                else
                {
                    // minimizing!
                    if (scoreCur < bestScore)
                    {
                        bestScore = scoreCur;
                        bestMove = nextCol;
                    }
                }
            }
        }

        // Return the best score (and the recommended move)
        return new int[] { bestScore, bestMove };        
    }
	private int whoWon (Grid g) {
		int winner = g.getWinningPlayer();
		if (winner == 0) {
			return 0;
		}
		if (winner == next) {
			return 1;
		}
		return -1; 
	}
	private int getHeuristicScore(Grid g) {
		int winner = whoWon(g);
		if (winner != 0) {
			return winner * 1000000;
		}
		int total = 0;
		GridUtilities grid = new GridUtilities(g);
		for (int i = 0; i < g.getRows(); i++) {
			for (int j = 0; j < g.getCols(); j++) {
				if (g.getPlayerAt(i, j) != 0) {
					int change = 10;
					if (g.getPlayerAt(i,j) != next) {
						change = -10;
					}
					if (i > 0 && g.getPlayerAt(i-1,j) == g.getPlayerAt(i,j)) {
						total += change;
					}
					if (i > 0 && j > 0 && g.getPlayerAt(i-1,j-1) == g.getPlayerAt(i,j)) {
						total += change;
					}
					if (j > 0 && g.getPlayerAt(i,j-1) == g.getPlayerAt(i,j)) {
						total += change;
					}
					if (i < g.getRows() - 1 && j > 0 && g.getPlayerAt(i+1,j-1) == g.getPlayerAt(i,j)) {
						total += change;
					}
					total -= (int) (change*(Math.abs((j+0.5-g.getCols()/2.0))));
					int[] arr0 = grid.getLengthAndSpaces(i,j,g.RIGHT);
					int[] arr1 = grid.getLengthAndSpaces(i,j,g.UP);
					int[] arr2 = grid.getLengthAndSpaces(i,j,g.UPLEFT);
					int[] arr3 = grid.getLengthAndSpaces(i,j,g.UPRIGHT);
					total += (change*(arrScore(arr0)+arrScore(arr1)+arrScore(arr2)+arrScore(arr3)));
				}
			}
		}
		
		return total;
	}
	public int arrScore(int[] arr) {
		int sum = arr[0] + arr[1];
		if (sum == 3) {
			return 1;
		}
		if (sum >= 4) {
			return 20;
		}
		return 0;
	}
}
