package pl;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Global {
	//Global Variables to make managing stuff easier
	public static final int FPS = 60;
	public static double FRAMESKIP = 1000d/FPS;
	public static JFrame frame;
	public static Canvas canvas;
	public static boolean gameOn=true;
	public static int frameWidth = 1024;
	public static int frameHeight = 640;
	public static int level = 0;	//0 = title screen;
	public static int pressedKeys=0;
	public static Camera camera;
	public static Player player;
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public static ArrayList<Wall> walls = new ArrayList<Wall>();
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public static ArrayList<Spawner> spawners = new ArrayList<Spawner>();
}
