import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class SmartSquare extends GameSquare implements MouseListener, TimeChecker
{
    /** The bomb existence of this square. **/
    private boolean thisSquareHasBomb;

    /** User sets a red flag on this square. **/
    private boolean guessThisSquareIsBomb;

    /** The traversal state of this square. **/
    private boolean thisSquareHasTraversed;

    /** The x co-ordinate of this square. **/
    private int xLocation;

    /** The y co-ordinate of this square. **/
    private int yLocation;

    /** The y co-ordinate of this square. **/
    private long startTime;

    /**
     * Create a new SmartSquare instance, which can be placed on a GameBoard.
     * @param x the x co-ordinate of this square on the game board.
     * @param y the y co-ordinate of this square on the game board.
     * @param board the GameBoard upon which this square resides.
     */
    public SmartSquare(int x, int y, GameBoard board)
    {
        // Paint this square as gray block when initialization.
        super(x, y, SmartSquare.class.getResource("/images/block.png"), board);

        // Assign coordinates to this square.
        xLocation = x;
        yLocation = y;

        // Initialize attributes
        thisSquareHasBomb = false;
        thisSquareHasTraversed = false;
        guessThisSquareIsBomb = false;
        startTime = 0;

        // add right mouse listener
        addMouseListener(this);
    }

    /**
     * Set bomb existence of the SmartSquare instance as a given result.
     * @param result the bomb existence of this SmartSquare instance.
     */
    protected void setBombExist(boolean result)
    {
        thisSquareHasBomb = result;
    }

    /**
     * Return bomb existence of the SmartSquare instance.
     * @return the bomb existence of this SmartSquare instance.
     */
    protected boolean getBombExist()
    {
        return thisSquareHasBomb;
    }

    /**
     * Return traversal state of the SmartSquare instance.
     * @return the traversal state of this SmartSquare instance.
     */
    protected boolean getTraverse()
    {
        return thisSquareHasTraversed;
    }

    /**
     * Set traversal state of the SmartSquare instance as a given result.
     * @param result the traversal state of this SmartSquare instance.
     */
    protected void setTraverse(boolean result)
    {
        thisSquareHasTraversed = result;
    }

    /**
     * Return a boolean value whether user sets a red flag in this square or not.
     * @return the state whether this square has been marked as a bomb or not.
     */
    protected boolean getGuessThisSquareIsBomb()
    {
        return guessThisSquareIsBomb;
    }

    /**
     * Set the start time of the game.
     * @param time the time presented as milliseconds.
     */
    protected void setStartTime(long time)
    {
        startTime = time;
    }

    /**
     * Return the game start time.
     * @return the time presented as milliseconds.
     */
    protected long getStartTime()
    {
        return startTime;
    }

    /**
     * An implementation method of abstract method (from GameSquare).
     * Once get click event, detect bombs and expand into empty space.
     * @throws LineUnavailableException
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    public void clicked()
    {
        if (board.getReplayManager() != null) {
            board.getReplayManager().recordMove(xLocation, yLocation, 1); // 1 = left click
        }

        try {
            openSound(".//src//pick_block.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        CheckSquare cq = new CheckSquare(board);

        guessThisSquareIsBomb = false;

        if(thisSquareHasBomb)
        {
            /*
             * If this square contains a bomb, show the bomb image.
             * Then display the selection window
             */
            setImage(SmartSquare.class.getResource("/images/bombReveal.png"));
            try {
                openSound(".//src//images_Error.wav");
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            long costTime = System.currentTimeMillis() - ((SmartSquare) board.getSquareAt(0, 0)).getStartTime();
            window1("You used " + TimeChecker.calculateTime(costTime) +". Do you want to continue playing?", "Game Over",
                    new ImageIcon(SmartSquare.class.getResource("/images/failFace.png")));
        } else{
            thisSquareHasTraversed = false;
            /*
             * If this square doesn't contain a bomb, calculate its surrounding bombs.
             * If this square has zero bombs in its surrounding squares,
             * expanding into empty space until the surrounding of the space has at least one bomb
             * or the space touches the window's boundary.
             */
            cq.countBomb(xLocation, yLocation);

            if (cq.isSuccess()) {
                long costTime = System.currentTimeMillis() - ((SmartSquare) board.getSquareAt(0, 0)).getStartTime();
                cq.showBomb(xLocation, yLocation);
                window("You win this game in " + TimeChecker.calculateTime(costTime) +
                                "! Do you want to try again?","Congratulations",
                        new ImageIcon(SmartSquare.class.getResource("/images/passFace.jpg")));
            }
        }
    }

    /**
     * Method to not show popup when replay
     */
    public void replayClicked() {
        try {
            openSound(".//src//pick_block.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        CheckSquare cq = new CheckSquare(board);
        guessThisSquareIsBomb = false;

        if(thisSquareHasBomb) {
            // Show only bomb image, no popup
            setImage(SmartSquare.class.getResource("/images/bombReveal.png"));
            try {
                openSound(".//src//images_Error.wav");
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
            cq.showBomb(xLocation, yLocation);
        } else {
            thisSquareHasTraversed = false;
            cq.countBomb(xLocation, yLocation);

            if (cq.isSuccess()) {
                // Show only bomb image, no popup
                cq.showBomb(xLocation, yLocation);
            }
        }
    }

    /**
     * A method to achieve pop-up window.
     * @param msg the message to display on the window.
     * @param title the title string for the window.
     * @param img the icon.
     */
    public void window(String msg, String title, Icon img)
    {

        int choose = JOptionPane.showConfirmDialog(board, msg, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,img);

        if (choose == JOptionPane.YES_OPTION)
        {
            new Menu("Mine sweeper");
        }

        // Close this window after user making a choice
        board.dispose();
    }

    public void window1(String msg, String title, Icon img)
    {
    CheckSquare cq = new CheckSquare(board);
    int choose = JOptionPane.showConfirmDialog(board, msg, title,
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,img);

    if (choose == JOptionPane.YES_OPTION)
    {
        setImage(SmartSquare.class.getResource("/images/block.png"));
    } else {
        cq.showBomb(xLocation, yLocation);
        int menuChoose = JOptionPane.showConfirmDialog(
            board,
            "Do you want to go back to the menu?\nOr click No to see a small suprise ;3",
            "Continute :>",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            new ImageIcon(SmartSquare.class.getResource("/images/passFace.jpg"))
        );
        if (menuChoose == JOptionPane.YES_OPTION) {
            new Menu("Mine sweeper");
            board.dispose();
        } else {
            try {
                Desktop desk = Desktop.getDesktop();
                desk.browse(new URI("https://youtu.be/dQw4w9WgXcQ"));
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
            board.dispose();
        }
    }
}


    /**
     * An implementation method to respond right-click events.
     * @param e the event when user clicks on this square.
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
        // If user right-click on this square.
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            int clickCount = e.getClickCount();

            // Show red flag.
            if (clickCount == 1)
            {
                setImage(SmartSquare.class.getResource("/images/redFlag.png"));
                guessThisSquareIsBomb = true;

                if (board.getReplayManager() != null) {
                    board.getReplayManager().recordMove(xLocation, yLocation, 3); // 3 = right click 1 time
                }
            }

            // Show question mark.
            if (clickCount == 2)
            {
                setImage(SmartSquare.class.getResource("/images/questionMark.png"));
                guessThisSquareIsBomb = false;

                if (board.getReplayManager() != null) {
                    board.getReplayManager().recordMove(xLocation, yLocation, 6); // 6 = right click 2 times
                }
            }
        }
    }

    /**
     * Set guess bomb status from replay
     */
    protected void setGuessThisSquareIsBomb(boolean guess) {
        guessThisSquareIsBomb = guess;
    }

    // The following mouse events are not going to be handled in this class.
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public static void openSound(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(path);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }
}