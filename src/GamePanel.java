import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.ArrayList;
import javax.swing.JButton;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Font;

public class GamePanel extends JPanel implements KeyListener {

    ArrayList<Platform> platforms = new ArrayList<>();   // Platform Array
    ArrayList<Enemy> enemies = new ArrayList<>();  // Enemy Array
    ArrayList<Coin> coins = new ArrayList<>();  // Coins Array

    JButton resetButton;

    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);

        setLayout(null);

        // Level Loader
        loadLevel(1);

        // Restart Button
        resetButton = new JButton("Play Again");

        resetButton.setBounds(320, 200, 150, 40);

        resetButton.setVisible(false);

        // Reset Button
        resetButton.addActionListener(e -> {
            resetGame();
        });

        add(resetButton);

        // Sprite Loader
        try {

            // IDLE
            idleSheet = ImageIO.read(
                    new File(
                            "assets/Main Characters/Mask Dude/Idle (32x32).png"));

            int frameCount = idleSheet.getWidth() / 32;

            idleFrames = new BufferedImage[frameCount];

            for (int i = 0; i < frameCount; i++) {

                idleFrames[i] = idleSheet.getSubimage(
                        i * 32,
                        0,
                        32,
                        32);
            }

            // RUN
            runSheet = ImageIO.read(
                    new File(
                            "assets/Main Characters/Mask Dude/Run (32x32).png"));

            int runFrameCount = runSheet.getWidth() / 32;

            runFrames = new BufferedImage[runFrameCount];

            for (int j = 0; j < runFrameCount; j++) {

                runFrames[j] = runSheet.getSubimage(
                        j * 32,
                        0,
                        32,
                        32);
            }
            
            // Jump Sheet
            jumpSheet = ImageIO.read(
                    new File(
                            "assets/Main Characters/Mask Dude/Jump (32x32).png"));

            int jumpFrameCount = jumpSheet.getWidth() / 32;

            jumpFrames = new BufferedImage[jumpFrameCount];

            for (int i = 0; i < jumpFrameCount; i++) {

                jumpFrames[i] = jumpSheet.getSubimage(
                        i * 32,
                        0,
                        32,
                        32);
            }

            // Fall Sheet
            fallSheet = ImageIO.read(
                    new File(
                            "assets/Main Characters/Mask Dude/Fall (32x32).png"));

            int fallFrameCount = fallSheet.getWidth() / 32;

            fallFrames = new BufferedImage[fallFrameCount];

            for (int i = 0; i < fallFrameCount; i++) {

                fallFrames[i] = fallSheet.getSubimage(
                        i * 32,
                        0,
                        32,
                        32);
            }

            // Enemy Sprite Loader
            enemyWalkSheet = ImageIO.read(
                    new File(
                            "assets/Enemies/AngryPig/Walk (36x30).png"));

            int enemyFrameCount = enemyWalkSheet.getWidth() / 36;

            enemyWalkFrames = new BufferedImage[enemyFrameCount];

            for (int i = 0; i < enemyFrameCount; i++) {

                enemyWalkFrames[i] = enemyWalkSheet.getSubimage(
                        i * 36,
                        0,
                        36,
                        30);
            }

            // Terrain Loader
            terrainSheet = ImageIO.read(
                    new File(
                            "assets/Terrain/Terrain (16x16).png"));
                    grassTile = terrainSheet.getSubimage(
                    90,
                    0,
                    54,
                    64);
            
    
                
            backgroundImage = ImageIO.read(
                    new File(
                            "assets/Background/Blue.png"));
            
            System.out.println(
                    backgroundImage.getWidth() +
                            " x " +
                            backgroundImage.getHeight());
            
            // Apple Loader
            appleSheet = ImageIO.read(
                    new File(
                            "assets/Items/Fruits/Apple.png"));

            int appleFrameCount = appleSheet.getWidth() / 32;

            appleFrames = new BufferedImage[appleFrameCount];

            for (int i = 0; i < appleFrameCount; i++) {

                appleFrames[i] = appleSheet.getSubimage(
                        i * 32,
                        0,
                        32,
                        32);
            }

            // Spike Sprite Loader
            spikeSheet = ImageIO.read(
                    new File(
                            "assets/Traps/Spikes/Idle.png"));

            spikeImage = spikeSheet.getSubimage(
                    0,
                    0,
                    16,
                    16);

            // Flag Loader
            flagSheet = ImageIO.read(
                    new File(
                            "assets/Items/Checkpoints/Checkpoint/Checkpoint (Flag Idle)(64x64).png"));

            int flagFrameCount = flagSheet.getWidth() / 64;

            flagFrames = new BufferedImage[flagFrameCount];

            for (int i = 0; i < flagFrameCount; i++) {

                flagFrames[i] = flagSheet.getSubimage(
                        i * 64,
                        0,
                        64,
                        64);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Timer timer = new Timer(16, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
            }
        });

        timer.start();
        
    }

    public void loadLevel(int level) {

        platforms.clear();
        enemies.clear();
        coins.clear();

        final int TILE = 46;

        switch (level) {

            case 1:

                platforms.add(new Platform(250, 350, TILE * 4, 20));
                platforms.add(new Platform(500, 250, TILE * 3, 20));
                platforms.add(new Platform(100, 180, TILE * 3, 20));

                enemies.add(new Enemy(300, 310, 250, 450));

                coins.add(new Coin(300, 300));
                coins.add(new Coin(600, 200));

                goalX = 900;
                goalY = 180;

                break;

            case 2:

                platforms.add(new Platform(250, 350, TILE * 3, 20));
                platforms.add(new Platform(450, 250, TILE * 2, 20));
                platforms.add(new Platform(650, 180, TILE * 2, 20));
                platforms.add(new Platform(900, 250, TILE * 3, 20));

                enemies.add(new Enemy(500, 210, 450, 550));
                enemies.add(new Enemy(950, 210, 900, 1050));

                goalX = 1200;
                goalY = 180;

                break;
        }
    }

    // Respawn Mechanism
    public void respawn() {

        playerX = 100;
        playerY = 100;

        velocityY = 0;

        score = 0;

        for (Coin coin : coins) {
            coin.collected = false;
        }

        for (Enemy enemy : enemies) {
            enemy.alive = true;
        }
    }

    // Levels
    int currentLevel = 1;
    boolean resetLevel = false;
    boolean loadNextLevel = false;

    int playerX = 100;
    int playerY = 100;
    int groundY = 530;

    // Score Counter
    int score = 0;

    // Camera
    int cameraX = 0;

    double velocityY = 0;
    double gravity = 0.5;

    // Goal Flag
    int goalX = 2700;
    int goalY = 326;

    BufferedImage flagSheet;
    BufferedImage[] flagFrames;

    int flagFrame = 0;
    int flagAnimationCounter = 0;

    boolean gameWon = false;

    // Sprite Image
    BufferedImage idleSheet;
    BufferedImage[] idleFrames;
    int currentFrame = 0;
    int animationCounter = 0;

    BufferedImage runSheet;
    BufferedImage[] runFrames;

    boolean facingRight = true;

    BufferedImage jumpSheet;
    BufferedImage fallSheet;

    BufferedImage[] jumpFrames;
    BufferedImage[] fallFrames;

    // Enemy Sprites
    BufferedImage enemyWalkSheet;
    BufferedImage[] enemyWalkFrames;

    int enemyFrame = 0;
    int enemyAnimationCounter = 0;


    // Terrain Sprites
    BufferedImage terrainSheet;
    BufferedImage grassTile;

    BufferedImage backgroundImage;

    // Apple Sprite
    BufferedImage appleSheet;
    BufferedImage[] appleFrames;

    int appleFrame = 0;
    int appleAnimationCounter = 0;

    // Spike Sprite
    BufferedImage spikeSheet;
    BufferedImage spikeImage;

    boolean onGround = false;
    boolean leftPressed;
    boolean rightPressed;
    boolean upPressed;
    boolean downPressed;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < getWidth(); x += 64) {

            for (int y = 0; y < getHeight(); y += 64) {

                g.drawImage(
                        backgroundImage,
                        x,
                        y,
                        64,
                        64,
                        null);
            }
        }

        if (gameWon) {

            Font oldFont = g.getFont();

            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));

            g.drawString("YOU WIN!", 250, 100);

            g.setFont(oldFont);
        }

        if (idleFrames != null) {

            BufferedImage currentSprite;

            if (!onGround) {

                if (velocityY < 0) {

                    currentSprite = jumpFrames[0];

                } else {

                    currentSprite = fallFrames[0];
                }

            } else if (leftPressed || rightPressed) {

                currentSprite = runFrames[currentFrame % runFrames.length];

            } else {

                currentSprite = idleFrames[currentFrame % idleFrames.length];
            }

            if (facingRight) {

                g.drawImage(
                        currentSprite,
                        playerX - cameraX,
                        playerY - 14,
                        64,
                        64,
                        null);

            } else {

                g.drawImage(
                        currentSprite,
                        playerX - cameraX + 64,
                        playerY - 14,
                        -64,
                        64,
                        null);
            }
        }

        // Level Indicator
        g.drawString(
                "Level: " + currentLevel,
                20,
                60);

        // Ground Spike
        for (int x = 0; x < 3000; x += 32) {

            g.drawImage(
                    spikeImage,
                    x - cameraX,
                    groundY,
                    32,
                    32,
                    null);
        }

        g.drawString("Score: " + score, 20, 20); // Scoreboard

        // Goal Flag
        g.drawImage(
                flagFrames[flagFrame],
                goalX - cameraX,
                goalY,
                64,
                64,
                null);

        // Platform Painting
        for (Platform platform : platforms) {

            final int TILE_SPACING = 46;

                int tileCount = platform.width / TILE_SPACING;

                for (int i = 0; i < tileCount; i++) {

                    g.drawImage(
                            grassTile,
                            platform.x - cameraX + i * TILE_SPACING,
                            platform.y,
                            54,
                            64,
                            null);
                }
        }

        // Enemy Painting
        for (Enemy enemy : enemies) {

            if (!enemy.alive) {
                continue;
            }

            if (enemy.facingRight) {

                g.drawImage(
                        enemyWalkFrames[enemyFrame],
                        enemy.x - cameraX + 72,
                        enemy.y - 10,
                        -72,
                        60,
                        null);

            } else {

                g.drawImage(
                        enemyWalkFrames[enemyFrame],
                        enemy.x - cameraX,
                        enemy.y - 10,
                        72,
                        60,
                        null);
            }
        }

        // Coin Painting
        for (Coin coin : coins) {

            if (!coin.collected) {

                g.drawImage(
                        appleFrames[0],
                        coin.x - cameraX,
                        coin.y,
                        32,
                        32,
                        null);

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

        if (gameWon) {
            repaint();
            return;
        }

        onGround = false;

        if (leftPressed) {
            playerX -= 5;
        }

        if (rightPressed) {
            playerX += 5;
        }

        velocityY += gravity;

        playerY += (int) velocityY;

        // Spike Death
        if (playerY + 50 >= groundY) {

            resetLevel = true;
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

            if (enemy.alive) {
                enemy.update();
            }
        }

        // Enemy Collision Detection
        for (Enemy enemy : enemies) {

            if (!enemy.alive) {
                continue;
            }

            if (playerX < enemy.x + enemy.width &&
                    playerX + 50 > enemy.x &&
                    playerY < enemy.y + enemy.height &&
                    playerY + 50 > enemy.y) {

                // Player landed on top
                int playerBottom = playerY + 50;

                if (velocityY > 0 &&
                    playerBottom < enemy.y + 20) {

                    enemy.alive = false;
                    velocityY = -8;
                    score++;

                } else {
                    resetLevel = true;
                }
            }
        }


        // Coin Collections Mechanism
        for (Coin coin : coins) {

            if (!coin.collected &&
                    playerX < coin.x + 32 &&
                    playerX + 50 > coin.x &&
                    playerY < coin.y + 32 &&
                    playerY + 50 > coin.y) {

                coin.collected = true;

                score++;
            }
        }

        // Win Condition
        if (playerX < goalX + 64 &&
                playerX + 50 > goalX &&
                playerY < goalY + 64 &&
                playerY + 50 > goalY) {

            loadNextLevel = true;
        }

        repaint();

        animationCounter++;

        if (animationCounter > 10) {

            currentFrame++;

            if (currentFrame > 1000) {
                currentFrame = 0;
            }

            animationCounter = 0;
        }

        // Sprite Direction
        if (leftPressed) {
            facingRight = false;
        }

        if (rightPressed) {
            facingRight = true;
        }

        // Enemy Animation
        enemyAnimationCounter++;

        if (enemyAnimationCounter > 10) {

            enemyFrame++;

            if (enemyFrame >= enemyWalkFrames.length) {
                enemyFrame = 0;
            }

            enemyAnimationCounter = 0;
        }

        // Apple Animation
        appleAnimationCounter++;

        if (appleAnimationCounter > 10) {

            appleFrame++;

            if (appleFrame >= appleFrames.length) {
                appleFrame = 0;
            }

            appleAnimationCounter = 0;
        }

        // Flag Animation
        flagAnimationCounter++;

        if (flagAnimationCounter > 10) {

            flagFrame++;

            if (flagFrame >= flagFrames.length) {
                flagFrame = 0;
            }

            flagAnimationCounter = 0;
        }

        if (loadNextLevel) {

            loadNextLevel = false;

            currentLevel++;

            if (currentLevel <= 5) {

                loadLevel(currentLevel);

                playerX = 100;
                playerY = 100;

                velocityY = 0;

                cameraX = 0;

            } else {

                gameWon = true;
                resetButton.setVisible(true);
            }
        }

        if (resetLevel) {

            resetLevel = false;

            resetCurrentLevel();
        }

        cameraX = Math.max(0, playerX - 400);
    }

    public void resetGame() {
        
        currentLevel = 1;

        loadLevel(1);

        playerX = 100;
        playerY = 100;

        velocityY = 0;

        score = 0;

        gameWon = false;

        cameraX = 0;

        for (Coin coin : coins) {
            coin.collected = false;
        }

        for (Enemy enemy : enemies) {
            enemy.alive = true;
        }

        resetButton.setVisible(false);
    }

    public void resetCurrentLevel() {

        loadLevel(currentLevel);

        playerX = 100;
        playerY = 100;

        velocityY = 0;

        cameraX = 0;

        score = 0;
    }
}