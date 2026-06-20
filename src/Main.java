import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame("Pixel Adventure");

        window.setSize(800, 600);

        window.setResizable(false);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(new GamePanel());

        window.setLocationRelativeTo(null);

        window.setVisible(true);
    }
}