package pl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.imageio.ImageIO;

public class Map {
	BufferedImage background;
	public static float x;
	public static float y;
	public static Map level1;
	public boolean[][] data;
	public Map(int level) {
		x=0;
		y=0;
		data = new boolean[128][128];
		switch(level){
		case 0://This should be the menu screen;
			try{
				background = ImageIO.read(new File("res/maps/Test Map.png"));
			}catch(Exception e){
				System.out.println("[SEVERE] Could not find file Test Map.png");
			}
			break;
		case 1:
			try {
				BufferedReader br = new BufferedReader(new FileReader("res/maps/a_map..txt"));
				System.out.println(br.readLine());
			} catch (Exception e) {
				System.out.println("[SEVERE] Could not find file res/maps/a_map..txt");
			}
			
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
