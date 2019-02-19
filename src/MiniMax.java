import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A class that implements the MiniMax algorithm.
 */
public class MiniMax {
	private static int numberOfStates; /**< counter to measure the number of iterations / states. */
	private static boolean usePruning;
	
	/**
	 * Start procedure of the MiniMax algorithm.
	 * @param state The state where the MiniMax algorithm starts searching
	 * @param usePruning Whether to use alpha-beta-pruning
	 * @return An optimal action to be taken at this point.
	 */
	public static Action MinimaxDecision(TicTacToeState state, boolean usePruning) {
		MiniMax.usePruning = usePruning;
		numberOfStates = 0;
		
		/* TODO
		 * Implement the minimax decision routine. Iterate over all possible actions
		 * and evaluate their utilities invoking MinValue(). Return the action that
		 * generates the highest utility.
		 * You can just return the first or the last best action, however it makes
		 * the algorithm way more interesting if you determine all best actions
		 * and then select one of them randomly.
		 */
		TicTacToeState stateWeStarted = new TicTacToeState(state);
		HashMap<Integer, Square> stateRemembrance = createTranspositionTable(stateWeStarted);
		Action bestAction = null;
		boolean executedOnce = false;
		float trackGreatest = Float.NEGATIVE_INFINITY;
		for (Action a : stateWeStarted.getActions()) {
			stateWeStarted.playerToMove = Square.X;
			if (!executedOnce) {
				executedOnce = true;
				bestAction = a;
			}
			float temp = MinValue(stateWeStarted.getResult(a), Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
			if (trackGreatest < temp) {
				trackGreatest = temp;
				bestAction = a;
			}
			stateWeStarted.getNumberOfPlacements--;
			stateWeStarted.clear(stateRemembrance);//every time you have to reinitialize for new state
		}
		
		System.out.println("State space size: " + numberOfStates);
		return bestAction;
	//	return null;
	}
	
	/**
	 * @param state The current state to be evaluated
	 * @param alpha The current value for alpha
	 * @param beta The current value for beta
	 * @return The maximum of the utilites invoking MinValue, or the utility of the state if it is a leaf.
	 */
	private static float MaxValue(TicTacToeState state, float alpha, float beta) {
		++numberOfStates;
		
		/*
		 * TODO implement the MaxValue procedure according to the textbook:
		 * 
		 * function Max-Value(state, alpha, beta) return a utility value
		 *   if TERMINAL-TEST(state) then return UTILITY(state)
		 *   v <- -infinity
		 *   for each a in ACTIONS(State) do
		 *     v <- max(v, MIN-VALUE(RESULT(state, a), alpha, beta))
		 *     if MiniMax.usePruning then
		 *       if v >= beta then return v
		 *       alpha <- max(alpha, v)
		 *   return v
		 *   
		 *   The pseudo code is slightly changed to be able to reuse the
		 *   code for alpha-beta-pruning.
		 */
		TicTacToeState stateWeStarted = new TicTacToeState(state);
		HashMap<Integer, Square> stateRemembrance = createTranspositionTable(stateWeStarted);
		if (state.isTerminal()) {
			return state.getUtility();
		}
		Float v = Float.NEGATIVE_INFINITY;
		stateWeStarted.player = stateWeStarted.playerToMove;
		for (Action a : stateWeStarted.getActions()) {
			v = Math.max(v, MinValue(stateWeStarted.getResult(a), alpha, beta));
			stateWeStarted.playerToMove = stateWeStarted.player;
			if (MiniMax.usePruning) {
				if (v >= beta) {
					return v;
				}
			}
			stateWeStarted.getNumberOfPlacements--;
			alpha = Math.max(alpha, v);
			stateWeStarted.clear(stateRemembrance);
		}
		
		return v;
	}
	
	/**
	 * @param state The current state to be evaluated
	 * @param alpha The current value for alpha
	 * @param beta The current value for beta
	 * @return The minimum of the utilites invoking MaxValue, or the utility of the state if it is a leaf.
	 */
	private static float MinValue(TicTacToeState state, float alpha, float beta) {
		++numberOfStates;

		/*
		 * TODO implement the MaxValue procedure according to the textbook:
		 * 
		 * function Min-Value(state, alpha, beta) return a utility value
		 *   if TERMINAL-TEST(state) then return UTILITY(state)
		 *   v <- +infinity
		 *   for each a in ACTIONS(State) do
		 *     v <- min(v, MAX-VALUE(RESULT(state, a), alpha, beta))
		 *     if MiniMax.usePruning then
		 *       if v <= alpha then return v
		 *       beta <- min(beta, v)
		 *   return v
		 *   
		 *   The pseudo code is slightly changed to be able to reuse the
		 *   code for alpha-beta-pruning.
		 */
		TicTacToeState stateWeStarted = new TicTacToeState(state);
		HashMap<Integer, Square> stateRemembrance = createTranspositionTable(stateWeStarted);
		if (state.isTerminal()) {
			return state.getUtility();
		}
		float v = Float.POSITIVE_INFINITY;
		stateWeStarted.player = stateWeStarted.playerToMove;
		for (Action a : stateWeStarted.getActions()) {//maybe try forcing action to stay as same type
			v = Math.min(v, MaxValue(stateWeStarted.getResult(a), alpha, beta));
			stateWeStarted.playerToMove = stateWeStarted.player;
			if (MiniMax.usePruning) {
				if (v <= alpha) {
					return v;
				}
			}
			stateWeStarted.getNumberOfPlacements--;
			beta = Math.min(beta, v);
			stateWeStarted.clear(stateRemembrance);
		}

		return v;
	}
	
	public static HashMap<Integer, Square> createTranspositionTable(TicTacToeState stateWeStarted) {
		HashMap<Integer, Square> stateRemembrance = new HashMap<Integer, Square>();
		for (int i = 0; i < stateWeStarted.field.length; i++) {
			if (stateWeStarted.field[i] != Square.EMPTY) {
				stateRemembrance.put(i, stateWeStarted.field[i]);//this establishes what we had originally
			}
		}
		return stateRemembrance;
	}
}
