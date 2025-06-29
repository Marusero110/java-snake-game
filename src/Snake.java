import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Snake {
    private LinkedList<int[]> body;
    private int direction;
    private int[] head;
    private boolean growing = false;

    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;

    public Snake(int initialX, int initialY) {
        body = new LinkedList<>();
        head = new int[] { initialX, initialY };
        body.addFirst(head);

        direction = RIGHT;
    }

    public void update(KeyHandler keyHandler) {
        if (keyHandler.up && (direction != DOWN)) {
            direction = UP;
        } else if (keyHandler.down && (direction != UP)) {
            direction = DOWN;
        } else if (keyHandler.left && (direction != RIGHT)) {
            direction = LEFT;
        } else if (keyHandler.right && (direction != LEFT)) {
            direction = RIGHT;
        }

        // Move the snake's head in the current direction.
        int newHeadX = head[0];
        int newHeadY = head[1];

        switch (direction) {
            case UP:
                newHeadY--;
                break;
            case RIGHT:
                newHeadX++;
                break;
            case DOWN:
                newHeadY++;
                break;
            case LEFT:
                newHeadX--;
                break;
        }

        int[] newHead = { newHeadX, newHeadY };
        body.addFirst(newHead);
        head = newHead;

        if (!growing) {
            body.removeLast();
        } else {
            growing = false; // Reset growth flag.
        }
    }

    public void grow() {
        growing = true;
    }

    public void draw(Graphics graphics, int tileSize) {
        graphics.setColor(Color.GREEN);
        for (int[] segment : body) {
            graphics.fillRect(segment[0] * tileSize, segment[1] * tileSize, tileSize, tileSize);
        }
    }

    public boolean checkCollision(int maxScreenColumns, int maxScreenRows) {
        // Check wall collision (out of bounds).
        if ((head[0] < 0) || (head[0] >= maxScreenColumns) || (head[1] < 0) || (head[1] >= maxScreenRows)) {
            return true;
        }

        // Check self-collision (head collides with any body part).
        for (int i = 1; i < body.size(); i++) {
            if ((head[0] == body.get(i)[0]) && (head[1] == body.get(i)[1])) {
                return true;
            }
        }

        return false;
    }

    public int[] getHead() {
        return head;
    }
}
