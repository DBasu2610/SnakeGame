import java.awt.*; //AWT stands for Abstract Window Toolkit.
                   // It is a set of APIs used by Java programs to create graphical user interfaces 
                   //(GUIs) and interact with them
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener {
    // It is used to group other components together and manage their layout.
    private class Tile {
        int x;
        int y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    int boardHeight;
    int boardWidth;
    int tileSize = 25;
    Tile head;
    ArrayList<Tile> body;
    Tile food;
    Random random;
    Timer timer;
    int xVelocity;
    int yVelocity;
    boolean gameOver = false;

    Game(int boardWidth, int boardHeight) {
        this.boardHeight = boardHeight;
        this.boardHeight = boardHeight;

        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true); // this is because we want our snake to follow the keys

        head = new Tile(5, 5);
        body = new ArrayList<Tile>();
        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        xVelocity = 0;
        yVelocity = 0;

        timer = new Timer(100, this);
        timer.start();
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        for (int i = 0; i < boardWidth / tileSize; i++) {
            g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
        }

        g.setColor(Color.YELLOW);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize,true);

        g.setColor(Color.WHITE);
        g.fill3DRect(head.x * tileSize, head.y * tileSize, tileSize, tileSize,true);

        for (int i = 0; i < body.size(); i++) {
            Tile snakeLength = body.get(i);
            g.fill3DRect(snakeLength.x * tileSize, snakeLength.y * tileSize, tileSize, tileSize,true);
        }

        g.setFont(new Font("Arial",Font.PLAIN,16));
        if(gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over: Your Score is " + String.valueOf(body.size()),tileSize-16,tileSize);
        }
        else {
            g.drawString("Score:" + String.valueOf(body.size()), tileSize-16, tileSize);
        }

    }

    public void placeFood() {
        food.x = random.nextInt(24);
        food.y = random.nextInt(24);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;

    }

    public void move() {

        if (collision(head, food)) {
            body.add(new Tile(food.x, food.y));
            placeFood();
        }

        for (int i = body.size() - 1; i >= 0; i--) {
            Tile snakeLength = body.get(i);
            if (i == 0) {
                snakeLength.x = head.x;
                snakeLength.y = head.y;

            } else {
                Tile prevSnakeLength = body.get(i - 1);
                snakeLength.x = prevSnakeLength.x;
                snakeLength.y = prevSnakeLength.y;
            }
        }
        head.x = head.x + xVelocity;
        head.y = head.y + yVelocity;

        for (int i = 0; i < body.size(); i++) {
            Tile snakeLength = body.get(i);

            if (collision(head, snakeLength)) {
                gameOver = true;
            }
        }

        if( head.x*tileSize<0 || head.y*tileSize<0) {
            gameOver = true;
        }

        if( head.x*tileSize>boardHeight || head.y>boardHeight) {
            gameOver = true;
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        move();

        repaint();

        if (gameOver) {
            timer.stop();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) { // to make the snake move up down left right by using keys
        if (e.getKeyCode() == KeyEvent.VK_UP && yVelocity != -1) {
            xVelocity = 0;
            yVelocity = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && yVelocity != 1) { // all the && conditions are given to prevent
                                                                           // the snake from running into its own body
            xVelocity = 0;
            yVelocity = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && xVelocity != 1) {
            xVelocity = -1;
            yVelocity = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && xVelocity != -1) {
            xVelocity = 1;
            yVelocity = 0;
        }
    }

    // we just need to find these methods we dont need to design its body
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
