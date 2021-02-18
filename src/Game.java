
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.Timer;

class Fire {

    private int x;
    private int y;

    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

public class Game extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(5, this);
    private int time = 0;
    private int spentBullet = 0;
    private BufferedImage image;
    private ArrayList<Fire> bullet = new ArrayList<Fire>();

    private int firedirY = 10;
    private int ballX = 0; //start point of ball
    private int balldirX = 3;
    private int spaceShipX = 0; //start point of spaceShip
    private int dirSpaceX = 20;

    public boolean checkCollision() {
        for (Fire fire : bullet) {
            if (new Rectangle(fire.getX(), fire.getY(), 10, 20).intersects(new Rectangle(ballX, 0, 20, 20))) {
                return true;
            }
        }
        return false;
    }

    public Game() {

        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        time += 5;
        g.setColor(Color.red);
        g.fillOval(ballX, 0, 20, 20); //drawing ball
        g.drawImage(image, spaceShipX, 490, image.getWidth() / 10, image.getHeight() / 10, this);
        for (Fire fire : bullet) {
            if (fire.getY() < 0) {
                bullet.remove(bullet);
            }
        }
        g.setColor(Color.blue);
        for (Fire fire : bullet) {
            g.fillRect(fire.getX(), fire.getY(), 10, 20);

        }
        if (checkCollision()) {
            timer.stop();
            String message = "Kazandınız \n"
                    + "Harcanan Ateş: " + spentBullet
                    + "\nGeçen Süre: " + time / 1000.0 + " saniye";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        super.repaint(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_LEFT) {
            if (spaceShipX <= 0) {
                spaceShipX = 0;
            } else {
                spaceShipX -= dirSpaceX;
            }
        } else if (c == KeyEvent.VK_RIGHT) {
            if (spaceShipX >= 720) {
                spaceShipX = 720;
            } else {
                spaceShipX += dirSpaceX;
            }
        } else if (c == KeyEvent.VK_CONTROL) {
            bullet.add(new Fire(spaceShipX + 15, 470));
            spentBullet++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (Fire fire : bullet) {
            fire.setY(fire.getY() - firedirY);
        }
        ballX += balldirX;
        if (ballX >= 750) {
            balldirX = -balldirX;
        }
        if (ballX <= 0) {
            balldirX = -balldirX;
        }
        repaint();
    }

}
