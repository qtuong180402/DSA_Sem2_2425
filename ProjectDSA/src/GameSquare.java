import javax.swing.*;
import java.net.URL;

/**
 * This class provides a representation of a single game square.
 * The class is abstract, and should be extended to provide domain
 * specific functionality.
 */
public abstract class GameSquare extends JButton
{
    /** The x co-ordinate of this square. **/
    protected int xLocation;

    /** The y co-ordinate of this square. **/
    protected int yLocation;

    /** The GameBoard upon which this GameSquare resides. **/
    protected GameBoard board;

    /**
     * Create a new GameSquare, which can be placed on a GameBoard.
     * @param x the x co-ordinate of this square on the game board.
     * @param y the y co-ordinate of this square on the game board.
     * @param filename file name.
     * @param board the GameBoard upon which this square resides.
     */
    public GameSquare(int x, int y, URL filename, GameBoard board)
    {
        super(new ImageIcon(filename));

        this.board = board;
        xLocation = x;
        yLocation = y;
    }

    /**
     * Change the image displayed by this square to the given bitmap.
     * @param filename the filename of the image to display.
     */
    public void setImage(URL filename)
    {
        this.setIcon(new ImageIcon(filename));
    }

    /**
     * A method that is invoked when a user clicks on this square.
     */
    public abstract void clicked();
}