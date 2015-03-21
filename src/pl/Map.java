package pl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Map implements MouseListener  {
	public static float x;
	public static float y;
	public String[][] data;
	public static BufferedImage tile;
	public static BufferedImage entrance;
	public static BufferedImage exit;
	public static BufferedImage[] menuBack;
	private static float animationCounter;
	public static BufferedImage title;
	public Map(int level){
		x=0;
		y=0;
		data = new String[64][64];
		try {
			tile = ImageIO.read(new File("res/maps/Tile.png"));
		} catch (IOException e1) {
			System.out.println("[SEVERE] Could not find file res/maps/Tile.png");
		}
		try {
			entrance = ImageIO.read(new File("res/maps/Entrance.png"));
		} catch (IOException e2) {
			System.out.println("[SEVERE] Could not find file res/maps/Entrance.png");
		}
		try {
			exit = ImageIO.read(new File("res/maps/Exit.png"));
		} catch (IOException e2) {
			System.out.println("[SEVERE] Could not find file res/maps/Exit.png");
		}
		System.out.println("[INFO] Loading Map - Level: "+Global.level);
		clearEntities();
		switch(level){
		case 0://This should be the menu screen;
			animationCounter=0;
			menuBack = new BufferedImage[5];
			for(int i=0;i<menuBack.length;i++){
				try {
					menuBack[i] = ImageIO.read(new File("res/gui/menu"+i+".png"));
				} catch (IOException e) {
					System.out.println("[SEVERE] Could not find file res/gui/menu"+i+".png");
				}
			}
			try {
				title = ImageIO.read(new File("res/gui/Overlay.png"));
			} catch (IOException e1) {
				System.out.println("[SEVERE] Could not find file res/gui/Overlay.png");
			}
			for(int i=0;i<data.length;i++){
				for(int j=0;j<data[0].length;j++)
					data[i][j]="";
			}
			break;
		case 1:
			try {
				data = mapLoader("res/maps/Level_1.txt");
				//y=-17*128;
				//x=-2*128;
				shiftEntities();


			} catch (Exception e) {
				System.out.println("[SEVERE] Could not find file res/maps/Level_1.txt");
				//e.printStackTrace();
				//System.exit(1);
			}
			break;
		case 2:
			try {
				data = mapLoader("res/maps/Level_2.txt");
				//y=-40*128;
				//x=-21*128;
				shiftEntities();
			} catch (Exception e) {
				System.out.println("[SEVERE] Could not find file res/maps/Level_2.txt");
			}
			break;
		case 3:
			try {
				data = mapLoader("res/maps/Level_3.txt");
				//y=-40*128;
				//x=-21*128;
				shiftEntities();
			} catch (Exception e) {
				System.out.println("[SEVERE] Could not find file res/maps/Level_3.txt");
			}
			break;
		
		case 4:
			try {
				data = mapLoader("res/maps/Level_4.txt");
				//y=-40*128;
				//x=-21*128;
				shiftEntities();
			} catch (Exception e) {
				System.out.println("[SEVERE] Could not find file res/maps/Level_3.txt");
			}
			break;
		}
		//System.exit(1);
		System.out.println("[INFO] Done Loading Map - Level:" +Global.level);
	}
	public void update(){
		
	}
	public void paint(Graphics g){
		if(Global.level>0){
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1024,640);
			for(int i=0;i<data.length;i++){
				for(int j=0;j<data[0].length;j++){
					if((i*128+y>-128)&&(i*128+y<Global.frameHeight)&&(j*128+x>-128)&&(j*128+x<Global.frameWidth)){
						if(data[i][j].equals("1")){
							g.drawImage(tile,(int)(j*128+x),(int)(i*128+y),null);
						}
						else if(data[i][j].equals("3")){//entrance to next level;
							g.drawImage(entrance,(int)(j*128+x),(int)(i*128+y),null);
						}
						else if(data[i][j].equals("4")){
							g.drawImage(exit,(int)(j*128+x),(int)(i*128+y),null);
						}
					}
				}
			}
		}else{
			g.drawImage(menuBack[(int)animationCounter],0,0,null);
			g.drawImage(title, 0, 0, null);
			animationCounter+=.075;
			animationCounter=animationCounter%menuBack.length;
		}
	}
	public String[][] mapLoader(String filename) throws IOException{//Going to make this return a SparseMatrix when we go to states
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
		//Shoving it in a 2D array && loading in entities
		char[] data5=data4.toCharArray();
		String[][] map = new String[64][64];
		for(int i = 0;i<map.length;i++){
			for(int j=0;j<map[0].length;j++){
				map[i][j]=""+data5[j+i*65];
				System.out.print(map[i][j]);
				if(map[i][j].equals("2"))
					Global.walls.add(new Wall(j*128,i*128));
				else if(map[i][j].equals("3"))
					Global.portal=(new Portal(j*128,i*128));
				else if(map[i][j].equals("4")){
					x=-(j*128)+(4*128);
					y=-i*128+(2*128);
				}
				else if(map[i][j].equals("1")&&Math.random()<.02&&Entity.distance(j*128, i*128, Global.frameWidth/2,Global.frameHeight/2)>640)
					Global.enemies.add(new Crawler(j*128,i*128,(int)(255*Math.round(Math.random())),(int)(255*Math.round(Math.random())),(int)(255*Math.round(Math.random()))));
				else if(map[i][j].equals("1")&&Math.random()<.01&&Entity.distance(j*128, i*128, Global.frameWidth/2,Global.frameHeight/2)>640)
					Global.spawners.add(new Spawner(j*128,i*128,(int)(255*Math.round(Math.random())),(int)(255*Math.round(Math.random())),(int)(255*Math.round(Math.random()))));
			}
			System.out.println("");
		}
		return map;
	}
	private void shiftEntities(){
		for(int i=0;i<Global.projectiles.size();i++){
			Global.projectiles.get(i).y+=y;
			Global.projectiles.get(i).x+=x;
		}
		for(int i=0;i<Global.enemies.size();i++){
			Global.enemies.get(i).y+=y;
			Global.enemies.get(i).x+=x;
		}
		for(int i=0;i<Global.walls.size();i++){
			Global.walls.get(i).y+=y;
			Global.walls.get(i).x+=x;
		}
		for(int i=0;i<Global.spawners.size();i++){
			Global.spawners.get(i).y+=y;
			Global.spawners.get(i).x+=x;
		}
		Global.portal.x+=x;
		Global.portal.y+=y;
		Global.portal.update();
	}
	private void clearEntities(){
		Global.projectiles.clear();
		Global.enemies.clear();
		Global.walls.clear();
		Global.spawners.clear();
	}
	public void mouseClicked(MouseEvent e) {

	}
	public void mouseEntered(MouseEvent e) {

	}
	public void mouseExited(MouseEvent e) {

	}
	public void mousePressed(MouseEvent e) {
		Point mouse = Global.frame.getMousePosition();
		if(Global.level==0&&mouse.x>129&&mouse.x<385&&mouse.y>262&&mouse.y<390){
			Global.loading=true;
			try {
				Camera.playSound("res/sound/Menu/Start.wav");
			} catch (UnsupportedAudioFileException | IOException
					| LineUnavailableException e1) {
				e1.printStackTrace();
			}
			Global.level++;
			Canvas.map = new Map(Global.level);
			Global.loading=false;
		}
		if(Global.level==0&&mouse.x>129&&mouse.x<385&&mouse.y>422&&mouse.y<550){
			System.out.println("[INFO] Game is Closing...");
			System.exit(0);
		}
	}
	public void mouseReleased(MouseEvent e) {

	}
}
