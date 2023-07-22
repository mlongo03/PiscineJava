package edu.Roma42.printer.logic;
import  edu.Roma42.printer.app.Img;

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

		if (args.length != 2) {
			System.out.println("Input wrong");
			return ;
		}

		if (args[0].length() != 1 || args[1].length() != 1) {
			System.out.println("Input wrong");
			return ;
		}

		Img imgbmp = new Img();

		try {
			imgbmp.seeBMPImage("./target/resources/it.bmp", args[0].toCharArray()[0], args[1].toCharArray()[0]);
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
