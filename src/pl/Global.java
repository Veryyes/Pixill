package pl;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Global {
	//Global Variables to make managing stuff easier
	public static final int FPS = 30;
	public static final double FRAMESKIP = 1000d/FPS;
	public static JFrame frame;
	public static Canvas canvas;
	public static boolean gameOn=true;
	public static int frameWidth = 1024;
	public static int frameHeight = 640;
	public static int level = 0;	//0 = title screen;
	public static int pressedKeys=0;
}
