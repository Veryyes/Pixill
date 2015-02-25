package pl;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel {
	public static Camera camera;
	public static InputListener input;
	public static Map map;
	public static Player player;
	public static BufferedImage cursorImage;
	public static long startTime;
	public static void main(String[] args) throws InterruptedException, IOException {
		System.out.println("[INFO] Pixill is Launching");
		Global.frame = new JFrame("Pixill");
		Global.frame.setSize(Global.frameWidth,Global.frameHeight);
		Global.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Global.frame.setVisible(true);
		Global.canvas = new Canvas();
		Global.frame.add(Global.canvas);
		startTime = System.currentTimeMillis();
		init();
		System.out.println("[INFO] Pixill Finished Loading");
		while(Global.gameOn){
			update();
			startTime+=Global.FRAMESKIP;
			long sleepTime =startTime-System.currentTimeMillis();
			if(sleepTime>=0)
				Thread.sleep(sleepTime);
			else
				System.out.println("[BAD] We Are Lagging");
		}
	}
	public static void init() throws IOException{
		camera = new Camera();
		input = new InputListener();
		Global.frame.addKeyListener(input);
		Global.projectiles = new ArrayList<Projectile>();
		map = new Map(Global.level);
		player = new Player();
		Global.frame.addMouseListener(player);
		cursorImage = ImageIO.read(new File("res/crosshair.png"));
	//	Global.frame.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,new Point(0,0),"Crosshair"));
		BufferedImage tile = ImageIO.read(new File("res/Test Tile.png"));
	//	System.out.println((map.background.getSubimage(0, 0, 128, 128)).equals(map.background.getSubimage(0, 0, 128,128)));
	}
	public static void update(){
		camera.update();
		//camera.setCurrentEffect(new Color(255,0,0,60));
		for(int i=0;i<Global.projectiles.size();i++){
			if(Global.projectiles.get(i).outOfBounds())
				Global.projectiles.remove(i);
			else{
				Global.projectiles.get(i).update();
			}
		}
	}
	public void paintComponent(Graphics g){
		//Making the graphics have a set fps value; Not sure if this matches up w/ update() though
		startTime+=Global.FRAMESKIP;
		long sleepTime =startTime-System.currentTimeMillis();
		if(sleepTime>=0)
			try {Thread.sleep(sleepTime);}
			 catch (InterruptedException e) {e.printStackTrace();}
		else
			System.out.println("[BAD] Graphic Rendering is Lagging");
		//Actually Drawing Stuff
		super.paintComponent(g);
		repaint();
		//g.drawImage((map.background.getSubimage(0, 0, 128,128)),0,0,null);
		map.paint(g, camera.xShift, camera.yShift);
		player.paint(g);
		for(int i=0;i<Global.projectiles.size();i++){
			Global.projectiles.get(i).paint(g);
		}
		camera.paintEffect(g); //Do this last to apply an effect on top of the screen;
	}
}
