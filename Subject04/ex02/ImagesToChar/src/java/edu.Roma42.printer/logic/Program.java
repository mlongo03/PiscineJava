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


public class Program {
	public static void main(String[] args) {

		Args arg = new Args();
		JCommander.newBuilder()
            .addObject(arg)
            .build()
            .parse(args);
        arg.run();

		// try {
		// 	imgbmp.seeBMPImage("./target/resources/it.bmp", args[0].toCharArray()[0], args[1].toCharArray()[0]);
		// }

		// catch (Exception e) {
		// 	System.out.println(e.getMessage());
		// }
	}
}
