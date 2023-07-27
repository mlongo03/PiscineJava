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

public class Img {

	public Img() {
	}

	public void seeBMPImage(String BMPFileName, char black, char white) throws IOException {
		BufferedImage image = ImageIO.read(new FileInputStream(BMPFileName));

		int[][] array2D = new int[image.getHeight()][image.getWidth()];

		for (int xPixel = 0; xPixel < image.getHeight(); xPixel++)
		{
			for (int yPixel = 0; yPixel < image.getWidth(); yPixel++)
			{
				int color = image.getRGB(yPixel, xPixel);
				if (color==Color.BLACK.getRGB()) {
					array2D[xPixel][yPixel] = black;
				} else {
					array2D[xPixel][yPixel] = white;
				}
			}
		}

		for (int x = 0; x < array2D.length; x++)
		{
			for (int y = 0; y < array2D[x].length; y++)
			{
				System.out.print((char)array2D[x][y]);
			}
			System.out.println();
		}
	}
}
