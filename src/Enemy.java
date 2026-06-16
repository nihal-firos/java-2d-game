public class Enemy {

    int x;
    int y;

    int width = 40;
    int height = 40;

    int speed = 2;

    int leftLimit;
    int rightLimit;

    boolean facingRight = true;

    public Enemy(int x, int y, int leftLimit, int rightLimit) {

        this.x = x;
        this.y = y;

        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }

    public void update() {

        x += speed;

        if (speed > 0) {
            facingRight = true;
        } else {
            facingRight = false;
        }

        if (x <= leftLimit || x >= rightLimit) {
            speed *= -1;
        }
    }
}
