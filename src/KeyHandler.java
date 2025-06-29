import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {
    public boolean up, down, left, right;
    private GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();

        if ((code == KeyEvent.VK_W) || (code == KeyEvent.VK_UP) && !down) {
            up = true;
            down = left = right = false;
        } else if ((code == KeyEvent.VK_S) || (code == KeyEvent.VK_DOWN) && !up) {
            down = true;
            up = left = right = false;
        } else if ((code == KeyEvent.VK_A) || (code == KeyEvent.VK_LEFT) && !right) {
            left = true;
            up = down = right = false;
        } else if ((code == KeyEvent.VK_D) || (code == KeyEvent.VK_RIGHT) && !left) {
            right = true;
            up = down = left = false;
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gamePanel.isGameOver()) {
                gamePanel.restartGame();
            }
        }
    }
}
