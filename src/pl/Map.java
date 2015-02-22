package pl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Map {
	SparseMatrix map;
	BufferedImage background;
	float x;
	float y;
	public Map(int level) {
		float x=0;
		float y=0;
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
	public void paint(Graphics g, int xShift, int yShift){
		g.drawImage(background, (int)x+xShift, (int)y+yShift, null);
	}

}
