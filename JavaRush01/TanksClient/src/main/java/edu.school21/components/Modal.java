package edu.school21.components;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Modal {

    int textSize = 0;

    public Modal(int shoot, int hit, int miss, Pane r, int w, int h, int ts) {
        this.textSize = ts;
        Rectangle modal = new Rectangle(0, 0, 500, 500);
        modal.setTranslateX(542 / 2);
        modal.setTranslateY(542 / 2);
        modal.setFill(Color.WHITE);
        r.getChildren().add(modal);
        r.getChildren().add(prepareText("Shoots: " + shoot, w / 3, (h / 8) * 3 - ts, Color.YELLOW));
        r.getChildren().add(prepareText("Hits: " + hit, w / 3, h / 2 - ts, Color.GREEN));
        r.getChildren().add(prepareText("Misses: " + miss, w / 3, (h / 8 * 5) - ts, Color.RED));
    }

    private Text prepareText(String msg, int w, int h, Color fill) {
        Text s = new Text();
        s.setX(w);
        s.setY(h);
        s.setFont(Font.font("Abyssinica SIL", FontWeight.BOLD, FontPosture.REGULAR, textSize));
        s.setFill(fill);
        s.setStroke(Color.BLACK);
        s.setStrokeWidth(1);
        s.setText(msg);

        return s;
    }
}
