import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {

    ArrayList<Platform> platforms = new ArrayList<>();

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        platforms.add(new Platform(250, 350, 200, 20));
        platforms.add(new Platform(500, 250, 150, 20));
        platforms.add(new Platform(100, 180, 120, 20));
        platforms.add(new Platform(900, 350, 200, 20));
        platforms.add(new Platform(1200, 250, 200, 20));
        platforms.add(new Platform(1500, 300, 200, 20));
        platforms.add(new Platform(1900, 200, 200, 20));
        platforms.add(new Platform(2200, 450, 200, 20));
        platforms.add(new Platform(2500, 390, 200, 20));

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
    int groundY = 500;

    // Camera
    int cameraX = 0;

    double velocityY = 0;
    double gravity = 0.5;

    boolean onGround = false;
    boolean leftPressed;
    boolean rightPressed;
    boolean upPressed;
    boolean downPressed;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(playerX - cameraX, playerY, 50, 50);

        g.fillRect(0, groundY, 800, 100);

        for (Platform platform : platforms) {

            g.fillRect(
                    platform.x - cameraX,
                    platform.y,
                    platform.width,
                    platform.height);

        }
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

        if (key == KeyEvent.VK_SPACE && onGround) {

            velocityY = -14;

            onGround = false;
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

        onGround = false;

        if (leftPressed) {
            playerX -= 5;
        }

        if (rightPressed) {
            playerX += 5;
        }

        velocityY += gravity;

        playerY += (int) velocityY;

        if (playerY + 50 >= groundY) {

            playerY = groundY - 50;

            velocityY = 0;

            onGround = true;
        }

        for (Platform platform : platforms) {

            if (playerX + 50 > platform.x &&
                    playerX < platform.x + platform.width &&
                    playerY + 50 >= platform.y &&
                    playerY + 50 <= platform.y + 20 &&
                    velocityY > 0) {

                playerY = platform.y - 50;

                velocityY = 0;

                onGround = true;
            }
        }

        repaint();

        cameraX = playerX - 400;
    }
}