import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener {

    ArrayList<Platform> platforms = new ArrayList<>();   // Platform Array
    ArrayList<Enemy> enemies = new ArrayList<>();  // Enemy Array
    ArrayList<Coin> coins = new ArrayList<>();  // Coins Array

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);


        // Platforms
        platforms.add(new Platform(250, 350, 200, 20));
        platforms.add(new Platform(500, 250, 150, 20));
        platforms.add(new Platform(100, 180, 120, 20));
        platforms.add(new Platform(900, 350, 200, 20));
        platforms.add(new Platform(1200, 250, 200, 20));
        platforms.add(new Platform(1500, 300, 200, 20));
        platforms.add(new Platform(1900, 200, 200, 20));
        platforms.add(new Platform(2200, 450, 200, 20));
        platforms.add(new Platform(2500, 390, 200, 20));

        // Enemies
        enemies.add(new Enemy(300, 310, 250, 450));
        enemies.add(new Enemy(300, 310, 250, 450));
        enemies.add(new Enemy(900, 310, 850, 1100));
        enemies.add(new Enemy(1500, 260, 1450, 1650));

        // Coins
        coins.add(new Coin(300, 300));
        coins.add(new Coin(600, 200));
        coins.add(new Coin(1000, 200));
        coins.add(new Coin(1400, 250));
        coins.add(new Coin(1800, 200));

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

    // Score Counter
    int score = 0;

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

        g.drawString("Score: " + score, 20, 20); // Scoreboard

        // Platform Painting
        for (Platform platform : platforms) {

            g.fillRect(
                    platform.x - cameraX,
                    platform.y,
                    platform.width,
                    platform.height);

        }

        // Enemy Painting
        for (Enemy enemy : enemies) {

            g.fillRect(
                    enemy.x - cameraX,
                    enemy.y,
                    enemy.width,
                    enemy.height);

        }

        // Coin Painting
        for (Coin coin : coins) {

            if (!coin.collected) {

                g.fillOval(
                        coin.x - cameraX,
                        coin.y,
                        20,
                        20);

            }
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

        for (Enemy enemy : enemies) {
            enemy.update();
        }

        // Enemy Collision Detection
        for (Enemy enemy : enemies) {

            if (playerX < enemy.x + enemy.width &&
                    playerX + 50 > enemy.x &&
                    playerY < enemy.y + enemy.height &&
                    playerY + 50 > enemy.y) {

                playerX = 100;
                playerY = 100;

                velocityY = 0;
            }
        }


        // Coin Collections Mechanism
        for (Coin coin : coins) {

            if (!coin.collected &&
                    playerX < coin.x + 20 &&
                    playerX + 50 > coin.x &&
                    playerY < coin.y + 20 &&
                    playerY + 50 > coin.y) {

                coin.collected = true;

                score++;
            }
        }

        repaint();

        cameraX = playerX - 400;
    }
}