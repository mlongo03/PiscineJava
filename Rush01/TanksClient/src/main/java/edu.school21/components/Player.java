package edu.school21.components;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
    private final Image deadImg;
    private boolean dead = false;
    private boolean start = false;
    private int screenSize;
    private int life;
    private boolean token = false;
    private double vel = 0;

    public Player(int x, int y, int w, int h, Image img, int screenSize, Image deadImg) {
        super(w, h);
        setFill(new ImagePattern(img));
        setTranslateX(x);
        setTranslateY(y);
        this.life = 100;
        this.screenSize = screenSize - w;
        this.deadImg = deadImg;
    }

    public void moveTo(double x) {
        if (start) {
            setTranslateX(x);
        }
    }

    public void move() {
        if (start) {
            setTranslateX((getTranslateX() + vel));
            if (getTranslateX() > screenSize)
                setTranslateX(screenSize);
        }
    }

    public void hit() {
        life -= 5;
        if (life <= 0) {
            dead = true;
            start = false;
            setFill(new ImagePattern(deadImg));
        }
    }

    public boolean getStart() {
        return (start);
    }

    public boolean isDead() {
        return (dead);
    }

    public int getLife() {
        return (life);
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setToken(boolean token) {
        this.token = token;
    }

    public boolean getToken() {
        return (this.token);
    }

    public void setVel(double vel) {
        this.vel = vel;
    }

}
