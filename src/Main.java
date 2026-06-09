import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();

        GamePanel panel = new GamePanel();

        window.add(panel);

        window.setTitle("My Platformer");
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        window.setVisible(true);
    }
}