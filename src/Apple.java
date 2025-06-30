import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Apple {
    private int x, y;
    private Random random;

    public Apple(int maxScreenColumns, int maxScreenRows) {
        random = new Random();
        spawn(maxScreenColumns, maxScreenRows);
    }

    public void spawn(int maxScreenColumns, int maxScreenRows) {
        x = random.nextInt(maxScreenColumns);
        y = random.nextInt(maxScreenRows);
    }

    public void draw(Graphics graphics, int tileSize) {
        graphics.setColor(Color.RED);
        graphics.fillOval(x * tileSize, y * tileSize, tileSize, tileSize);
    }

    // Check if the snake ate the apple (if the snake's head is on the apple's position).
    public boolean isEaten(Snake snake) {
        int[] snakeHead = snake.getHead();
        return (snakeHead[0] == x) && (snakeHead[1] == y);
    }
}
