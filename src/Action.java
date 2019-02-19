/**
 * An interface to be implemented by any action in the context of the MiniMax algorithm. 
 */
public interface Action {
	public void setPlayer(Square player);
	public void setPosition(int position);
	public int getPosition();
	public Square getPlayer();
}
