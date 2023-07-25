package edu.school21.game.app;

import org.w3c.dom.Attr;

import com.diogonunes.jcolor.Attribute;

public class Tile {
    private Attribute color;
    private String charString;

    Tile() {
        color = Attribute.BLACK_BACK();
        charString = "";
    }

    public String getCharString() {
        return charString;
    }

    public Attribute getColor() {
        return color;
    }

    public void setColor(Attribute color) {
        this.color = color;
    }

    public void setCharString(String charString) {
        this.charString = charString;
    }

    public void setColorFromString(String strColor) {
        switch (strColor.toUpperCase()) {
            case "YELLOW":
                color = Attribute.YELLOW_BACK();
                break;
            case "RED":
                color = Attribute.RED_BACK();
                break;
            case "MAGENTA":
                color = Attribute.MAGENTA_BACK();
                break;
            case "CYAN":
                color = Attribute.CYAN_BACK();
                break;
            case "BLUE":
                color = Attribute.BLUE_BACK();
                break;
            case "GREEN":
                color = Attribute.GREEN_BACK();
                break;
            case "WHITE":
                color = Attribute.WHITE_BACK();
                break;
            case "BLACK":
                color = Attribute.BLACK_BACK();
                break;
            default:
                // error
                break;
        }
    }
}
