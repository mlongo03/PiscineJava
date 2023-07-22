package edu.Roma42.printer.logic;
import  edu.Roma42.printer.app.Img;
import  edu.Roma42.printer.app.Args;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.util.*;
import java.io.*;
import java.lang.Object;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;
import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;
import com.diogonunes.jcolor.Ansi;


public class Program {

    public static void main(String[] args) {
//         // Apply color and style to text
//         String coloredText = Ansi.colorize("Hello, World!", Attribute.GREEN_TEXT(), Attribute.BOLD(), Attribute.WHITE_BACK());

//         // Print colored text to the console
//         System.out.println(coloredText);
// }


// 	public static void main(String[] args) {

		Args arg = new Args();
		JCommander.newBuilder()
            .addObject(arg)
            .build()
            .parse(args);
        arg.run();

	}
}

