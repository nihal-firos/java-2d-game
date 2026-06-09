import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener {

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        Timer timer = new Timer(16, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                updateGame();

            }
        });

        timer.start();
    }


    int playerX = 100;
    int playerY = 100;

    boolean leftPressed;
    boolean rightPressed;
    boolean upPressed;
    boolean downPressed;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(playerX, playerY, 80, 80);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }

        if (key == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }

        if (key == KeyEvent.VK_UP) {
            upPressed = true;
        }

        if (key == KeyEvent.VK_DOWN) {
            downPressed = true;
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }

        if (key == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }

        if (key == KeyEvent.VK_UP) {
            upPressed = false;
        }

        if (key == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void updateGame() {

        if (leftPressed) {
            playerX -= 8;
        }

        if (rightPressed) {
            playerX += 8;
        }

        if (upPressed) {
            playerY -= 8;
        }

        if (downPressed) {
            playerY += 8;
        }

        repaint();
    }
}