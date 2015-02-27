package pl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Map {
	BufferedImage background;
	public static float x;
	public static float y;
	public static Map level1;
	public Map(int level) {
		x=0;
		y=0;
		switch(level){
		case 0://This should be the menu screen;
			try{
				background = ImageIO.read(new File("res/maps/Test Map.png"));
			}catch(Exception e){
				System.out.println("[SEVERE] Could not find file Test Map.png");
			}
			break;
		case 1:
			
			break;
		}
	}
	public void update(){
		
	}
	public void paint(Graphics g){
		g.drawImage(background, (int)x, (int)y, null);
	}
	public void mapLoader(){
		
	}
}
