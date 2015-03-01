package pl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel {
	public static InputListener input;
	public static Map map;
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
		Global.camera = new Camera();
		input = new InputListener();
		Global.frame.addKeyListener(input);
		/*
		Global.projectiles = new ArrayList<Projectile>();
		Global.enemies = new ArrayList<Enemy>();
		Global.walls = new ArrayList<Wall>();
		Global.spawners = new ArrayList<Spawner>();
		*/
		map = new Map(Global.level);
		Global.player = new Player();
		Global.frame.addMouseListener(Global.player);
	//	cursorImage = ImageIO.read(new File("res/crosshair.png"));
	//	Global.frame.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,new Point(0,0),"Crosshair"));
		//Global.enemies.add(new Crawler(700,500,'B'));
		Global.spawners.add(new Spawner(700,500,'B'));
		
	}
	public static void update(){
		Global.camera.update();
		//camera.setCurrentEffect(new Color(255,0,0,60));2
		Global.player.update();
		for(int i=0;i<Global.projectiles.size();i++){
			if(Global.projectiles.get(i).outOfBounds() || Global.projectiles.get(i).remove) {
				Global.projectiles.remove(i);
			}
			else{
				Global.projectiles.get(i).update();
			}
		}
		for(int i=0;i < Global.spawners.size();i++) {
			Global.spawners.get(i).update();
		}
		for(int i=0;i<Global.enemies.size();i++){
			Global.enemies.get(i).update();
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
			System.err.println("[BAD] Graphic Rendering is Lagging");
		//Actually Drawing Stuff
		super.paintComponent(g);
		repaint();
		try {
			map.paint(g);
		}
		catch (Exception e) {
			System.err.println("Map paint: "+e);
		}
		try {
			for(int i=0;i<Global.projectiles.size();i++){
				Global.projectiles.get(i).paint(g);
			}
		}
		catch(Exception e) {
			System.err.println("Projectile paint: "+e);
		}
		for(int i = 0; i < Global.spawners.size();i++) {
			Global.spawners.get(i).paint(g);
		}
		Global.player.paint(g);
		
		for(int i=0;i<Global.enemies.size();i++){
			Global.enemies.get(i).paint(g);
		}
		
		Global.camera.paintEffect(g); //Do this last to apply an effect on top of the screen;
	}
}
