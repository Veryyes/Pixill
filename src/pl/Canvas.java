package pl;

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

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel {
	public static Camera camera;
	public static InputListener input;
	public static Map map;
	public static Player player;
	public static int theta=2;
	public static BufferedImage cursorImage;
	public static void main(String[] args) throws InterruptedException, IOException {
		Global.frame = new JFrame("Pixill");
		Global.frame.setSize(Global.frameWidth,Global.frameHeight);
		Global.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Global.frame.setVisible(true);
		Global.canvas = new Canvas();
		Global.frame.add(Global.canvas);
		long startTime = System.currentTimeMillis();
		init();
		while(Global.gameOn){
			update();
			startTime+=Global.FRAMESKIP;
			long sleepTime =startTime-System.currentTimeMillis();
			if(sleepTime>=0){
				Thread.sleep(sleepTime);
			}
		}
	}
	public static void init() throws IOException{
		camera = new Camera();
		input = new InputListener();
		Global.frame.addKeyListener(input);
		map = new Map(Global.level);
		player = new Player();
		cursorImage = ImageIO.read(new File("res/crosshair.png"));
		Global.frame.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursorImage,new Point(0,0),"Crosshair"));
	}
	public static void update(){
		camera.update();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		repaint();
		map.paint(g, camera.xShift, camera.yShift);
		player.paint(g);
		
	}
	
}
