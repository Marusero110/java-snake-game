import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
    // Screen settings
    final int tileSize = 25;
    final int maxScreenColumns = 24;
    final int maxScreenRows = 24;
    final int screenWidth = tileSize * maxScreenColumns;
    final int screenHeight = tileSize * maxScreenRows;

    // Game loop
    Timer timer;
    final int framesPerSecond = 10;

    KeyHandler keyHandler;
    private Snake snake;
    private Apple apple;

    // Game state
    private boolean gameOver;
    private int score;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        keyHandler = new KeyHandler(this);
        this.addKeyListener(keyHandler);

        snake = new Snake(5, 5);
        apple = new Apple(maxScreenColumns, maxScreenRows);

        // Initialize game state.
        gameOver = false;
        score = 0;

        // Start game loop.
        timer = new Timer(1000 / framesPerSecond, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        drawGrid(graphics);
        snake.draw(graphics, tileSize);
        apple.draw(graphics, tileSize);
        drawScore(graphics);

        if (gameOver) {
            drawGameOver(graphics);
        }
    }

    public void drawGrid(Graphics graphics) {
        graphics.setColor(Color.DARK_GRAY);

        // Vertical lines
        for (int i = 0; i <= maxScreenColumns; i++) {
            int x = i * tileSize;
            graphics.drawLine(x, 0, x, screenHeight);
        }

        // Horizontal lines
        for (int i = 0; i <= maxScreenRows; i++) {
            int y = i * tileSize;
            graphics.drawLine(0, y, screenWidth, y);
        }
    }

    public void drawScore(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial", Font.PLAIN, 24));
        graphics.drawString("Score: " + score, 20, 40);
    }

    public void drawGameOver(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Arial", Font.BOLD, 48));
        String gameOverText = "GAME OVER";
        int gameOverTextWidth = graphics.getFontMetrics().stringWidth(gameOverText);
        graphics.drawString(gameOverText, (screenWidth - gameOverTextWidth) / 2, screenHeight / 2);

        graphics.setFont(new Font("Arial", Font.PLAIN, 24));
        graphics.setColor(Color.WHITE);
        String restartText = "Press Enter to restart";
        int restartTextWidth = graphics.getFontMetrics().stringWidth(restartText);
        graphics.drawString(restartText, (screenWidth - restartTextWidth) / 2, (screenHeight / 2) + 30);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void restartGame() {
        // Reset the game state.
        snake = new Snake(5, 5);
        apple = new Apple(maxScreenColumns, maxScreenRows);
        score = 0;
        gameOver = false;

        // Restart the game loop.
        timer.start();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (gameOver) {
            return;
        }

        snake.update(keyHandler);

        if (snake.checkCollision(maxScreenColumns, maxScreenRows)) {
            gameOver = true;
            timer.stop();
            repaint();
            return;
        }

        if (apple.isEaten(snake)) {
            snake.grow();
            apple.spawn(maxScreenColumns, maxScreenRows);
            score++;
        }

        repaint();
    }
}
