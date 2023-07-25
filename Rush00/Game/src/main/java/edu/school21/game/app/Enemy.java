package edu.school21.game.app;

public class Enemy {
    int xPos;
    int yPos;

    Enemy() {
        xPos = 0;
        yPos = 0;
    }

    Enemy(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
}
