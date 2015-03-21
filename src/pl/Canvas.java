package pl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel {
	public static InputListener input;
	public static Map map;
	public static BufferedImage cursorImage;
	public static BufferedImage loadScreen;
	public static BufferedImage[] healthBar;
	public static BufferedImage[] laserColor;
	public static BufferedImage compass;
	public static BufferedImage arrow;
	public static long startTime;
	public static void main(String[] args) throws InterruptedException, IOException {
		System.out.println("[INFO] Pixill is Launching");
		Global.frame = new JFrame("Pixill");
		Global.frame.setSize(Global.frameWidth,Global.frameHeight);
		Global.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Global.frame.setVisible(true);
		Global.canvas = new Canvas();
		Global.frame.add(Global.canvas);
		Global.frame.setResizable(false);
		startTime = System.currentTimeMillis();
		init();
		System.out.println("[INFO] Pixill Finished Loading\n");
		Global.loading=false;
		while(Global.gameOn){
			if(!Global.loading)
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
		Global.player = new Player();
		Global.frame.addMouseListener(Global.player);
		loadScreen = ImageIO.read(new File("res/gui/loading.png"));
		cursorImage = ImageIO.read(new File("res/Crosshair1.png"));
		Global.frame.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,new Point(0,0),"Crosshair"));
		map = new Map(Global.level);
		Global.frame.addMouseListener(map);
		healthBar=new BufferedImage[6];
		healthBar[0]=ImageIO.read(new File("res/gui/UI0.png"));
		healthBar[1]=ImageIO.read(new File("res/gui/UI1.png"));
		healthBar[2]=ImageIO.read(new File("res/gui/UI2.png"));
		healthBar[3]=ImageIO.read(new File("res/gui/UI3.png"));
		healthBar[4]=ImageIO.read(new File("res/gui/UI4.png"));
		healthBar[5]=ImageIO.read(new File("res/gui/UI5.png"));
		laserColor=new BufferedImage[3];
		laserColor[0]=ImageIO.read(new File("res/gui/RedUI.png"));
		laserColor[1]=ImageIO.read(new File("res/gui/GreenUI.png"));
		laserColor[2]=ImageIO.read(new File("res/gui/BlueUI.png"));
		arrow = ImageIO.read(new File("res/gui/Arrow.png"));
		compass = ImageIO.read(new File("res/gui/Compass.png"));
	}
	public static void update(){
		//System.out.println(Global.enemies.size()+Global.spawners.size()+Global.projectiles.size());
		//System.out.println("Walls: "+Global.walls.size()+"\nEnemies: "+Global.enemies.size()+"\nSpawners: "+Global.spawners.size());
		//System.out.println(map.x+", "+map.y+": :"+Global.portal.x+","+Global.portal.y);
		Global.camera.update();
		if(Global.level>0)
			Global.player.update();
		else if(Global.level==-1){
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Global.loading=true;
			Global.level++;
			Canvas.map=new Map(Global.level);
			Global.loading=false;
		}
		else if(Global.level==5){
			//nothin'
		}
		if(Global.player.hitBox.intersects(Global.portal.hitBox)){
			Global.loading=true;
			Global.level++;
			Canvas.map=new Map(Global.level);
			Global.loading=false;
		}
		for(int i=0;i < Global.walls.size();i++) {
			Global.walls.get(i).update();
		}
		for(int i=0;i<Global.projectiles.size();i++){
			if(Global.projectiles.get(i).outOfBounds() || Global.projectiles.get(i).remove) {
				Global.projectiles.remove(i);
			}
			else{
				Global.projectiles.get(i).update();
			}
		}
		for(int i=0;i < Global.spawners.size();i++) {
			if(Global.spawners.get(i).dead)
				Global.spawners.remove(i);
			else
				Global.spawners.get(i).update();
		}
		for(int i=0;i<Global.enemies.size();i++){
			if(Global.enemies.get(i).dead){
				Global.enemies.remove(i);
				try {
					Camera.playSound("res/sound/Mob/MobDeath.wav");
				} catch (UnsupportedAudioFileException | IOException
						| LineUnavailableException e) {
					e.printStackTrace();
				}
			}
			else
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
			System.out.println("[BAD] Graphic Rendering is Lagging");
		
		//Actually Drawing Stuff
		super.paintComponent(g);
		repaint();
		if(Global.loading){
			g.drawImage(loadScreen,0,-15,null);
		}
		else{
			map.paint(g);
			for(int i = 0; i < Global.spawners.size();i++) {
				Global.spawners.get(i).paint(g);
			}
			if(Global.level>0&&Global.level!=5)
				Global.player.paint(g);
			for(int i=0;i<Global.projectiles.size();i++){
				Global.projectiles.get(i).paint(g);
			}
			for(int i=0;i<Global.enemies.size();i++){
				Global.enemies.get(i).paint(g);
			}
			Global.camera.paintEffect(g);
			if(Global.level>0&&Global.level!=5){
				g.drawImage(healthBar[Global.player.hp],0,0,null);
				switch(Global.player.color){
				case 'R':
					g.drawImage(laserColor[0],0,0,null);
					break;
				case 'G':
					g.drawImage(laserColor[1],0,0,null);
					break;
				case 'B':
					g.drawImage(laserColor[2],0,0,null);
					break;
				}
				g.drawImage(compass,909,23,null);
				double dx = (Global.portal.x+128-Global.player.getCenterX());
				if(dx==0)
					dx+=.0001;
				double compassTheta=Math.atan((Global.portal.y+128-Global.player.getCenterY())/dx);
				if(Global.portal.x<Global.player.getCenterX())
					compassTheta+=Math.PI;
				AffineTransform at = AffineTransform.getRotateInstance(compassTheta,arrow.getWidth()/2,arrow.getHeight()/2);
				AffineTransformOp atp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
				g.drawImage(atp.filter(arrow,null),896,0,null);
			}
		}
	}
}
