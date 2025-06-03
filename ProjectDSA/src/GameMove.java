/**
 * Class to store information of a move in the game
 */
public class GameMove {
    private int x;
    private int y;
    private int clickType; // 1: left click, 3: right click (1 time), 6: right click (2 times)
    private long timestamp;

    public GameMove(int x, int y, int clickType, long timestamp) {
        this.x = x;
        this.y = y;
        this.clickType = clickType;
        this.timestamp = timestamp;
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getClickType() { return clickType; }
    public long getTimestamp() { return timestamp; }

    // Setters
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setClickType(int clickType) { this.clickType = clickType; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}