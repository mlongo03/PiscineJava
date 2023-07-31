package edu.school21.components;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Bullet extends Rectangle {

    private int screenSize;

    public Bullet(int x, int y, int w, int h, Image img, int screenSize) {
        super(w, h);
        setFill(new ImagePattern(img));
        setTranslateX(x);
        setTranslateY(y);
        this.screenSize = screenSize;
    }

    public void moveUp() {
        setTranslateY((getTranslateY() - 5));
    }

    public void moveDown() {
        setTranslateY((getTranslateY() + 5));
        if (getTranslateY() > screenSize)
            setTranslateY(screenSize);
    }
}
