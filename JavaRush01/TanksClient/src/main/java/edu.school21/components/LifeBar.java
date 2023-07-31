package edu.school21.components;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class LifeBar extends Region {

    private Rectangle lf;
    private Rectangle bd;

    public LifeBar(int life, int x, int y, Image border, Image fill, Pane r) {
        setTranslateX(x);
        setTranslateY(y);
        bd = new Rectangle(x, y, 140, 24);
        bd.setFill(new ImagePattern(border));
        lf = new Rectangle(x + 5, y + 2, 1.3 * life, 20);
        lf.setFill(new ImagePattern(fill));
        lf.setVisible(false);
        bd.setVisible(false);
        r.getChildren().add(lf);
        r.getChildren().add(bd);
    }

    public void setHp(int newHp) {
        lf.setWidth(1.3 * newHp);
    }

    public void setVisible() {
        lf.setVisible(true);
        bd.setVisible(true);
    }
}
