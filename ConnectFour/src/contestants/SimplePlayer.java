package contestants;

import connectFour.Grid;
import connectFour.Player;

/* 
	----------------------------------------------------------------------------------
	SIMPLE PLAYER

	For step 3 of the ConnectFour doc, you will fill out this class to create
	your first, simple player.  See doc for details.
	----------------------------------------------------------------------------------
*/

public class SimplePlayer implements Player 
{

	@Override
	public int getMoveColumn(Grid g) 
	{
		int i = (int) (Math.random()*g.getCols());
		while (g.isColumnFull(i)) {
			i = (int) (Math.random()*g.getCols());
		}
		return i;
	}

	@Override
	public String getPlayerName() 
	{
		return "My Simple Player";
	}

}
