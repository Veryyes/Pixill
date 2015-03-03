package pl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {
	BufferedImage background;
	public static float x;
	public static float y;
	public static Map level1;
	public String[][] data;
	public static BufferedImage tile;
	public Map(int level){
		x=0;
		y=0;
		data = new String[128][128];
		try {
			tile = ImageIO.read(new File("res/maps/Tile.png"));
		} catch (IOException e1) {
			System.out.println("[SEVERE] Could not find file res/maps/Tile.png");
		}
		System.out.println("[INFO] Loading Map");
		switch(level){
		/*case 0://This should be the menu screen;
			try{
				background = ImageIO.read(new File("res/maps/Test Map.png"));
			}catch(Exception e){
				System.out.println("[SEVERE] Could not find file Test Map.png");
			}
			break;*/
		case 0:
			try {
				data = mapLoader("res/maps/Level_1.txt");
				y=-40*128;
				x=-21*128;
			} catch (Exception e) {
				System.out.println("[SEVERE] Could not find file res/maps/Level_1.txt");
			}
			
			break;
		}
		System.out.println("[INFO] Done Loading Map");
	}
	public void update(){
		
	}
	public void paint(Graphics g){
		g.drawImage(background, (int)x, (int)y, null);
		g.setColor(Color.BLACK);
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[0].length;j++){
				if((i*128+y>-128)&&(i*128+y<Global.frameHeight)&&(j*128+x>-128d)&&(j*128+x<Global.frameWidth)){
					if(data[i][j].equals("1"))
						g.drawImage(tile,(int)(j*128+x),(int)(i*128+y),null);
					else
						g.fillRect((int)(j*128+x),(int)(i*128+y),128,128);
				}
			}
		}
	}
	public String[][] mapLoader(String filename) throws IOException{
		//Reading the File
		BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
		int item;
		String data = "";
		while((item=br.read())!=-1){
			data+=(char)item;
		}
		//Removing ","
		data=data.trim();
		String[] data1 = data.split(",");
		String data2="";
		for(String s : data1){
			data2+=s;
		}
		//Removing "\n"
		char[] data3 = data2.toCharArray();
		String data4 = "";
		for(char c:data3){
			if(c!='\n')
				data4+=c;
		}
		//Shoving it in a 2D array
		char[] data5=data4.toCharArray();
		String[][] map = new String[128][128];
		for(int i = 0;i<map.length;i++){
			for(int j=0;j<map[0].length;j++){
				map[i][j]=""+data5[j+i*129];
			}
		}
		return map;
	}
}
