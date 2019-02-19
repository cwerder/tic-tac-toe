import java.util.Scanner;


public class main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("The squares are numbered as follows:");
		System.out.println("1|2|3\n-+-+-\n4|5|6\n-+-+-\n7|8|9\n");
		
		System.out.print("Who should start? 1=you 2=computer ");
		int temp = scanner.nextInt();
		
		TicTacToeState s = new TicTacToeState();
		s.player = Square.X;
		if(temp == 1) {//temp should only ever be 1 or 2 indicating which player should move first, for this part, it also takes the player move
			s.playerToMove = Square.O; 
		} else {
			s.playerToMove = Square.X;
		}
		
		System.out.println("Press 'y' to enable pruning. Press any other key to disable pruning.");
		scanner.nextLine();
		String y = scanner.nextLine();
		boolean pruningEnabled = false;
		if (y.equals("y")) {
			pruningEnabled = true;
		}
		
		do {
			if(s.playerToMove == Square.X) { // computer is to move
			//	s.getNumberOfPlacements++;
				s = (TicTacToeState) s.getResult(MiniMax.MinimaxDecision(s, pruningEnabled));//there wasn't originally s
			} else { // you are to move
			//	System.out.print("Which square do you want to set? (1--9) ");
				do {//this just checks for valid input
				//	System.out.print("Invalid input");
					System.out.print("Which square do you want to set? (1--9) ");
					temp = scanner.nextInt();//here temp is set to what position you want to move
				//	break;
				}
				while((temp < 1 || temp > 9) || s.field[temp-1] != Square.EMPTY);//this just checks for valid input
				TicTacToeAction a = new TicTacToeAction(Square.O, temp - 1);//temp-1 just re-orientates position in index 1 to index 0
			//	s.getNumberOfPlacements++;
				s = (TicTacToeState)s.getResult(a);//this was originally just a
			}
			s.print();
		}
		while(!s.isTerminal());
		
		if(s.getUtility() > 0) {
			System.out.println("You lost.");
		} else if(s.getUtility() < 0) {
			System.out.println("You win.");
		} else {
			System.out.println("Draw.");
		}
		
		scanner.close();
	}

}
