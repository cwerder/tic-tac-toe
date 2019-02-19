import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class that implements a state and the playing logic of the TicTacToe game. 
 */
public class TicTacToeState implements State {
	public Square[] field; /**< The field, consisting of nine squares. First three values correspond to first row, and so on. */
	public Square player; /**< The player, either X or O. */
	public Square playerToMove; /**< The player that is about to move. */
	private float utility; /**< The utility value of this state. Can be 0, 1 (won) or -1 (lost).*/
	public int getNumberOfPlacements;

	/**
	 * Updates the utility value.
	 */
	private void updateUtility() {
		/** TODO
		 * The utility value for the TicTacToe game is defined as follows:
		 * - if player has three marks in a row, it is 1
		 * - if the other player has three marks in a row, it is -1
		 * - otherwise it is 0
		 * Note that "three marks in a row" can actually be a row, a column
		 * or a diagonal. So basically, first find out if there are three
		 * identical values in a row, and if so, check whether the marks belong
		 * to player or not. 
		 */
		if ((field[0] == Square.X && field[3] == Square.X && field[0] == field[6]) || (field[0] == Square.X && field[4] == Square.X && field[4] == field[8]) || 
				(field[0] == Square.X && field[1] == Square.X && field[0] == field[2]) || (field[1] == Square.X && field[4] == Square.X && field[1] == field[7])
				|| (field[2] == Square.X && field[5] == Square.X && field[2] == field[8]) || (field[2] == Square.X && field[4] == Square.X && field[2] == field[6]) 
				|| (field[6] == Square.X && field[7] == Square.X && field[6] == field[8]) || (field[3] == Square.X && field[4] == Square.X && field[3] == field[5])) {
			utility = 1;
		}
		
		
		else if ((field[0] == Square.O && field[3] == Square.O && field[0] == field[6]) || (field[0] == Square.O && field[4] == Square.O && field[4] == field[8]) || 
				(field[0] == Square.O && field[1] == Square.O && field[0] == field[2]) || (field[1] == Square.O && field[4] == Square.O && field[1] == field[7])
				|| (field[2] == Square.O && field[5] == Square.O && field[2] == field[8]) || (field[2] == Square.O && field[4] == Square.O && field[2] == field[6])
				|| (field[6] == Square.O && field[7] == Square.O && field[6] == field[8]) || (field[3] == Square.O && field[4] == Square.O && field[3] == field[5])) {
			utility = -1;
		}
		else {
			utility = 0;
		}
	}
	
	/**
	 * Default constructor.
	 */
	public TicTacToeState() {
		field = new Square[9];
		for(int i = 0; i < 9; ++i) {
			field[i] = Square.EMPTY;
		}
		player = Square.X;
		playerToMove = Square.X;
		utility = 0;
	}
	
	//copy constructor
	public TicTacToeState(TicTacToeState state) {
		field = new Square[9];
		for(int i = 0; i < 9; ++i) {
			field[i] = state.field[i];
		}
		player = state.player;
		playerToMove = state.playerToMove;
		utility = state.getUtility();
		this.getNumberOfPlacements = state.getNumberOfPlacements;
	}
	
	@Override
	public List</*Action*/TicTacToeAction> getActions() {
		/** TODO
		 * For the TicTacToe game, there is one valid action
		 * for each empty square. The action would then consist
		 * of the position of the empty square and the "color" of
		 * the player to move.
		 */
		List<TicTacToeAction> possibleActions = new ArrayList<TicTacToeAction>();
		if (this.playerToMove == Square.X) {
			for (int i = 0; i < 9; i++) {
				if (this.field[i] == Square.EMPTY) {
					possibleActions.add(new TicTacToeAction(Square.X, i));
				}
			}
		}
		else {
			for (int i = 0; i < 9; i++) {
				if (this.field[i] == Square.EMPTY) {
					possibleActions.add(new TicTacToeAction(Square.O, i));
				}
			}
		}
		return possibleActions;
		//return null;
	}

	@Override
	public float getUtility() {
		return utility;
	}

	@Override
	public TicTacToeState getResult(Action action) {//this was just Action action
		/** TODO
		 * Create a new state and copy all the contents of the current state
		 * to the new one (in particular the field and the player). The
		 * player to move must be switched. Then incorporate the action into
		 * the field of the new state. Finally, compute the utility of the new
		 * state using updateUtility().
		 */
	//	TicTacToeState newState = new TicTacToeState();//problem is here with default constructor
		
	/*	for (int i = 0; i < 9; i++) {
			oldState.field[i] = oldState.field[i];
		}*/
		if (action == null) {
			
		}
		else if (this.playerToMove == Square.X) {
			this.field[action.getPosition()] = Square.X;
			this.playerToMove = Square.O;
		}
		else if (this.playerToMove == Square.O) {
			this.field[action.getPosition()] = Square.O;
			this.playerToMove = Square.X;
		}
		getNumberOfPlacements++;
		updateUtility();
		return this;
	}

	@Override
	public boolean isTerminal() {
		/** TODO
		 * Hint: the utility value has specific values if one of
		 * the players has won, which is a terminal state. However,
		 * you will also have to check for terminal states in which
		 * no player has won, which can not be inferred immediately
		 * from the utility value.
		 */
		if (getUtility() == 1 ||  getUtility() == -1 || getNumberOfPlacements==9) {
			return true;
		}
		
		return false;
	}

	@Override
	public void print() {
		String s = "" + field[0] + "|" + field[1] + "|" + field[2] + "\n";
		s += "-+-+-\n";
		s += field[3] + "|" + field[4] + "|" + field[5] + "\n";
		s += "-+-+-\n";
		s += field[6] + "|" + field[7] + "|" + field[8] + "\n";
		System.out.println(s);
	}
	
	public void clear(HashMap<Integer, Square> stateRemembrance) {
		for (int i = 0; i < this.field.length; i++) {
			if (!stateRemembrance.containsKey(i)) {
				this.field[i] = Square.EMPTY;
			}
		}
	}
}
