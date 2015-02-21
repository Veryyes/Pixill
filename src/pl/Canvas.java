package pl;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel {
	static JFrame frame;
	static Canvas canvas;
	static boolean gameOn=false;
	static Camera camera;
	static InputListener input;
	static Map map;
	public static void main(String[] args) throws InterruptedException {
		frame = new JFrame("Pixill");
		frame.setSize(Global.frameWidth,Global.frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		canvas = new Canvas();
		frame.add(canvas);
		long startTime = System.currentTimeMillis();
		init();
		while(gameOn){
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
		frame.addKeyListener(input);
		map = new Map(Global.level);
	}
	public static void update(){
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		repaint();
		map.paint(g, camera.xShift, camera.yShift);
	}
	
}
