package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Game extends JPanel implements ActionListener {
    private int score;
    private Point fruit;
    private Random random;
    private Timer timer;
    private boolean inGame;
    private boolean gameOver;
    private Player player;
    public static final int DOT_SIZE = 20;
    public static final int GRID_SIZE = 20;
    public static final int MAX_DOTS = 400;
    public static final int DELAY = 140;
    private JButton restartButton;
    public Game() {
        fruit = new Point();
        inGame = true;
        player = new Player(GRID_SIZE);
        score = 0;
        random = new Random();
        setPreferredSize(new Dimension(GRID_SIZE * DOT_SIZE, GRID_SIZE * DOT_SIZE));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                player.changeDirection(key);
            }
        });
        restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> restartGame()); // Dodanie obsługi zdarzenia kliknięcia
        add(restartButton); // Dodanie przycisku do panelu gry
        initGame();
    }

    public void initGame() {
        createFruit();
        inGame = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void createFruit() {
        int x = random.nextInt(GRID_SIZE);
        int y = random.nextInt(GRID_SIZE)/2;
        fruit = new Point(x, y);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            player.move();
           moveFruit();
            checkCollision();
            checkFruit();
            repaint();
        }
    }

    private void checkCollision() {

        if (player.positionX < 0)
            player.setPositionX(0);
        else if (player.positionX + player.width > getWidth())
            player.setPositionX(getWidth() - player.width);
        else if (!inGame) {
            gameOver=true;
        }


    }

        @Override
        protected void paintComponent (Graphics g){
            super.paintComponent(g);
            player.draw(g);
            drawFruit(g);
            if(!inGame)
                gameOver(g);
            drawScore(g);
            if (inGame)
                restartButton.setVisible(false);
            else
                restartButton.setVisible(true);


        }
       private boolean checkFruit(){
        if(fruit==null) return false;
        int playerX=player.getPositionX()/DOT_SIZE;
        int playerY=player.getPositionY()/DOT_SIZE;

        if (playerX==fruit.x&&playerY==fruit.y) {
            score++;
            createFruit();
            return true;
        }
            return false;
        }

        private void moveFruit(){
             if(fruit!=null){
                 int speed=1;
                 fruit.y+=speed;
         }
             if (fruit.y*DOT_SIZE>=getHeight()) {
                 inGame=false;
                 timer.stop();
             }

             else if (checkFruit())
                 createFruit();



        }

        private void  drawFruit(Graphics g){
        if (fruit!=null){
            g.setColor(Color.BLUE);
            g.fillOval(fruit.x*DOT_SIZE, fruit.y*DOT_SIZE, DOT_SIZE, DOT_SIZE);
        }
        }

        private void drawScore(Graphics g){
        String msg="Your score: "+score;
        Font font = new Font("Arial", Font.PLAIN, 16);
        g.setColor(Color.GREEN);
        g.setFont(font);
        g.drawString(msg, 10, 20);
        }
        private void gameOver(Graphics g){
        String msg= "YOU  LOSE!";
            Font font = new Font("Arial", Font.PLAIN, 16);
            g.setColor(Color.red);
            g.setFont(font);
            g.drawString(msg, 20, 50);
        }

    private void restartGame() {
        score = 0; // Zresetowanie wyniku
        player.setPositionX(50); // Przywrócenie gracza na początkową pozycję
        createFruit(); // Wygenerowanie nowego owocu

        inGame = true; // Ustawienie gry na stan początkowy
        timer.start(); // Uruchom ponownie timer gry, jeśli go masz
    }


}