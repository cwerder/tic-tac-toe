
/**
 *  
 */
public class TicTacToeAction implements Action {
	Square player; // The player to move (determines the square to be set)
	int position; // The position to be modified in the range [0..9)
	
	public TicTacToeAction(Square square, int position) {
		this.player = square;
		this.position = position;
	}

	public Square getPlayer() {
		return player;
	}

	public void setPlayer(Square player) {
		this.player = player;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
