package pl;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel {
	static Camera camera;
	static InputListener input;
	static Map map;
	static Player player;
	static int theta=2;
	public static void main(String[] args) throws InterruptedException {
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
	public static void init(){
		camera = new Camera();
		input = new InputListener();
		Global.frame.addKeyListener(input);
		map = new Map(Global.level);
		player = new Player();
	}
	public static void update(){
		camera.update();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		repaint();
		//map.paint(g, camera.xShift, camera.yShift);
		player.paint(g);
		/*
		try {
			BufferedImage image = ImageIO.read(new File("res/Test Tile.png"));
			AffineTransform tx = AffineTransform.getRotateInstance(theta,image.getWidth()/2,image.getHeight());
			AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(op.filter(image, null),0,0,null);
			g.drawImage(image, 0, 0, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Graphics2D temp = (Graphics2D) map.background.getGraphics();
		//temp.rotate(theta);
		//temp.drawImage(map.background, 0, 0, null);
		/*
		temp.drawImage(map.background,0,0,null);
		temp.dispose();
		AffineTransform tx = new AffineTransform();
		tx.rotate(theta,map.background.getWidth()/2,map.background.getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		map.background = op.filter(map.background,null);
		g.drawImage(map.background,0,0,null);
		*/
		
	}
	
}
