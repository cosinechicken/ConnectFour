package contestants;

import connectFour.Grid;

public class BChenStatSamp implements connectFour.Player
{
	private int next;
	private int movesNum = 5000;
	@Override
	public int getMoveColumn(Grid g)
	{
		next = g.getNextPlayer();
		int bestMove = 0;
		int bestVal = 0;
		int movesMade = 0;
		for (int i = 0; i < g.getCols(); i++) {
			int bestCurVal = 2*movesNum;
			if (g.isColumnFull(i)) {
				continue;
			}
			g.makeMove(i);
			movesMade++;
			if (g.getWinningPlayer() == next) {
				return i;
			}
			if (g.getWinningPlayer() == -1) {
				bestCurVal = movesNum;
				if (bestCurVal >= bestVal) {
					bestVal = bestCurVal;
					bestMove = i;
				}
				continue;
			}
			for (int j = 0; j < g.getCols(); j++) {
				int curVal = 0;
				if (g.isColumnFull(j)) {
					continue;
				}
				g.makeMove(j);
				movesMade++;
				if (g.getWinningPlayer() == 3 - next) {
					continue;
				}
				if (g.getWinningPlayer() == -1) {
					curVal = movesNum;
					if (curVal <= bestCurVal) {
						bestCurVal = curVal;
					}
					continue;
				}
				for (int k = 0; k < movesNum; k++)
				{
					
					int winningPlayer = 0;
					connectFour.Player player1 = new SimplePlayer();
					connectFour.Player player2 = new SimplePlayer();
					while (winningPlayer == 0)
					{
						g.makeMove(player1.getMoveColumn(g));
						winningPlayer = g.getWinningPlayer();
						movesMade++;
						if (winningPlayer == 0)
						{
							g.makeMove(player2.getMoveColumn(g));
							winningPlayer = g.getWinningPlayer();
							movesMade++;
						}
					}
					if (winningPlayer == next) {
						curVal += 2;
					} else if (winningPlayer == -1) {
						curVal += 1;
					}
					while (movesMade != 2) {
						g.undo();
						movesMade--;
					}
				}
				if (curVal <= bestCurVal) {
					bestCurVal = curVal;
					
				}
				while (movesMade != 1) {
					g.undo();
					movesMade--;
				}
			}
			if (bestCurVal >= bestVal) {
				bestVal = bestCurVal;
				bestMove = i;
			}
			while (movesMade != 0) {
				g.undo();
				movesMade--;
			}
		}
		System.out.println(bestVal);
		return bestMove;
	}

	@Override
	public String getPlayerName()
	{
		return "Brandon Chen's Statistic Sampling Algorithm";
	}
	
}
