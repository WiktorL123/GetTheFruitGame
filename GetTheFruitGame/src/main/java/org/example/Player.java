package org.example;


import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;

public class Player{
    public int positionX=50;
    public int positionY=50;
    private int direction;
    public int width=25;
    public int height=25;
    public int moveDistance=10;


    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public Player(int gridSize) {
        positionX = (gridSize * Game.DOT_SIZE - width) / 2;  // Początkowa pozycja na dole w środku planszy
        positionY = gridSize * Game.DOT_SIZE - height;

        }


        public void changeDirection(int key) {
        if ((key == KeyEvent.VK_LEFT) && (direction != KeyEvent.VK_RIGHT)) {
            direction = -1;
        }
        if ((key == KeyEvent.VK_RIGHT) && (direction != KeyEvent.VK_LEFT)) {
            direction = 1;
        }

    }
    public void move() {
       positionX+=direction*moveDistance;

    }
    public void draw(Graphics g){
            g.setColor(Color.RED);
            g.fillRect(positionX, positionY, width , height);
    }
}
