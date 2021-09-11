import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Input implements KeyListener
{
	private boolean[] pressed;
	
	public Input()
	{
		pressed = new boolean[255];
	}
	
	public boolean isPressed(int keyCode)
	{
		return pressed[keyCode];
	}
	
	public void keyPressed(KeyEvent e)
	{
		pressed[e.getKeyCode()] = true;
	}
	
	public void keyReleased(KeyEvent e)
	{
		pressed[e.getKeyCode()] = false;
	}
	
	public void keyTyped(KeyEvent e)
	{
	}
}