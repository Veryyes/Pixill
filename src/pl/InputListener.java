package pl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputListener implements KeyListener {
	public InputListener() {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Global.pressedKeys|=(1<<e.getKeyCode()-64);
		//System.out.println(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Global.pressedKeys&=~(1<<e.getKeyChar()-64);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	public static boolean isPressed(char key){
		return ((Global.pressedKeys&(1<<(int)key))!=0);
	}

}
