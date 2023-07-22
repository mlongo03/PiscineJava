package edu.Roma42.printer.app;
import edu.Roma42.printer.logic.*;

import java.util.*;
import java.io.*;
import java.lang.Object;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;
import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import com.diogonunes.jcolor.Ansi;

public class Img {

	public Img() {
	}

	public void seeBMPImage(String BMPFileName, String black, String white) throws IOException {
		BufferedImage image = ImageIO.read(new FileInputStream(BMPFileName));
		// Attribute Attribute_black = (Attribute.WHITE_BACK());
		// Attribute Attribute_white = (Attribute.BLACK_BACK());
		Attribute Attribute_black = Check_Color(black);
		Attribute Attribute_white = Check_Color(white);
		String[][] array2D = new String[image.getHeight()][image.getWidth()];

		for (int xPixel = 0; xPixel < image.getHeight(); xPixel++)
		{
			for (int yPixel = 0; yPixel < image.getWidth(); yPixel++)
			{
				int color = image.getRGB(yPixel, xPixel);
				if (color==Color.BLACK.getRGB()) {
        			array2D[xPixel][yPixel] = Ansi.colorize(" ", Attribute_black);
				} else {
        			array2D[xPixel][yPixel] = Ansi.colorize(" ", Attribute_white);
				}
			}
		}

		for (int x = 0; x < array2D.length; x++)
		{
			for (int y = 0; y < array2D[x].length; y++)
			{
				System.out.print(array2D[x][y]);
			}
			System.out.println();
		}
	}

	public Attribute Check_Color(String color) {
		Attribute attr = Attribute.WHITE_BACK();

		if (color.equals("red")) {
			attr = Attribute.RED_BACK();
		}
		if (color.equals("green")) {
			attr = Attribute.GREEN_BACK();
		}
		if (color.equals("white")) {
			attr = Attribute.WHITE_BACK();
		}
		if (color.equals("black")) {
			attr = Attribute.BLACK_BACK();
		}
		if (color.equals("yellow")) {
			attr = Attribute.YELLOW_BACK();
		}
		if (color.equals("cyan")) {
			attr = Attribute.CYAN_BACK();
		}
		if (color.equals("blue")) {
			attr = Attribute.BLUE_BACK();
		}

		return attr;
	}
}
