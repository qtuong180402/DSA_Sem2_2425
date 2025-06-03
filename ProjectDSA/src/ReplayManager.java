import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages game replay
 */
public class ReplayManager {
    private List<GameMove> moves;
    private List<BombPosition> bombPositions; // Save the original bomb location
    private GameBoard originalBoard;
    private int boardWidth;
    private int boardHeight;
    private int bombCount;
    private boolean isRecording;
    private boolean isReplaying;
    private long gameStartTime;

    public ReplayManager(GameBoard board, int width, int height, int bombs) {
        this.moves = new ArrayList<>();
        this.bombPositions = new ArrayList<>();
        this.originalBoard = board;
        this.boardWidth = width;
        this.boardHeight = height;
        this.bombCount = bombs;
        this.isRecording = false;
        this.isReplaying = false;
    }

    /**
     * Class to save bomb location
     */
    public static class BombPosition {
        public int x, y;
        public BombPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Save bomb positions from current board
     */
    public void saveBombPositions() {
        bombPositions.clear();
        for (int y = 0; y < boardHeight; y++) {
            for (int x = 0; x < boardWidth; x++) {
                SmartSquare square = (SmartSquare) originalBoard.getSquareAt(x, y);
                if (square != null && square.getBombExist()) {
                    bombPositions.add(new BombPosition(x, y));
                }
            }
        }
    }

    /**
     * Start recording game
     */
    public void startRecording() {
        moves.clear();
        isRecording = true;
        gameStartTime = System.currentTimeMillis();
        // Lưu vị trí bomb ngay khi bắt đầu record
        saveBombPositions();
    }

    /**
     * Stop recording game
     */
    public void stopRecording() {
        isRecording = false;
    }

    /**
     * Add a move to the list
     */
    public void recordMove(int x, int y, int clickType) {
        if (isRecording && !isReplaying) {
            long timestamp = System.currentTimeMillis() - gameStartTime;
            moves.add(new GameMove(x, y, clickType, timestamp));
        }
    }

    /**
     * Replay the game with the original bomb position
     */
    public void startReplay() {
        if (moves.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No replays to play!");
            return;
        }

        isReplaying = true;

        // Create new board to replay
        GameBoard replayBoard = new GameBoard("Minesweeper - Replay", boardWidth, boardHeight);

        // Place bombs in saved locations
        placeSavedBombs(replayBoard);

        // Set start time for replay board
        ((SmartSquare) replayBoard.getSquareAt(0, 0)).setStartTime(System.currentTimeMillis());

        // Create a timer to replay each move
        Timer replayTimer = new Timer(100, null);
        final int[] currentMoveIndex = {0};
        final long[] replayStartTime = {System.currentTimeMillis()};

        replayTimer.addActionListener(e -> {
            if (currentMoveIndex[0] >= moves.size()) {
                replayTimer.stop();
                isReplaying = false;
                JOptionPane.showMessageDialog(replayBoard, "Replay complete");
                return;
            }

            GameMove currentMove = moves.get(currentMoveIndex[0]);
            long elapsedTime = System.currentTimeMillis() - replayStartTime[0];

            // Check to see if it's time to make this move
            if (elapsedTime >= currentMove.getTimestamp()) {
                executeMove(replayBoard, currentMove);
                currentMoveIndex[0]++;
            }
        });

        replayTimer.start();
    }

    /**
     * Place bombs in saved locations instead of random
     */
    private void placeSavedBombs(GameBoard board) {
        for (BombPosition pos : bombPositions) {
            SmartSquare square = (SmartSquare) board.getSquareAt(pos.x, pos.y);
            if (square != null) {
                square.setBombExist(true);
                square.setTraverse(true);
            }
        }
    }

    /**
     * Make a move in the replay
     */
    private void executeMove(GameBoard board, GameMove move) {
        SmartSquare square = (SmartSquare) board.getSquareAt(move.getX(), move.getY());

        if (square != null) {
            // Temporarily turn off recording to avoid overwriting old moves
            boolean wasRecording = isRecording;
            isRecording = false;

            switch (move.getClickType()) {
                case 1: // Left click - CHANGE HERE
                    square.replayClicked(); // Call method for Replay
                    break;
                case 3: // Right click 1 time (flag)
                    square.setImage(SmartSquare.class.getResource("/images/redFlag.png"));
                    square.setGuessThisSquareIsBomb(true);
                    break;
                case 6: // Right click 2 times (question mark)
                    square.setImage(SmartSquare.class.getResource("/images/questionMark.png"));
                    square.setGuessThisSquareIsBomb(false);
                    break;
            }

            // Restore recording state
            isRecording = wasRecording;
        }
    }

    // FUTURE WORK
    /**
     * Save replay to file (future expansion)
     */
    public void saveReplay(String filename) {
        // implement later
    }
    /**
     * Load replay from file (future expansion)
     */
    public void loadReplay(String filename) {
        // implement later
    }

    // Getters
    public boolean isRecording() { return isRecording; }
    public boolean isReplaying() { return isReplaying; }
    public int getMoveCount() { return moves.size(); }
}