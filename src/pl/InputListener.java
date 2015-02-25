package pl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputListener implements KeyListener {
	public InputListener() {
	}
	public void keyPressed(KeyEvent e) {
		Global.pressedKeys|=(1<<e.getKeyCode()-64);
	}
	public void keyReleased(KeyEvent e) {
		Global.pressedKeys&=~(1<<e.getKeyChar()-64);
	}
	public void keyTyped(KeyEvent e) {
		
	}
	public static boolean isPressed(char key){
		return ((Global.pressedKeys&(1<<(int)key))!=0);
	}

}
