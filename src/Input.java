import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * Input records key input from the user.
 * @author Eric Anderson
 * @version 1.0
 */
public class Input implements KeyListener {
  private boolean[] pressed;
  
  public Input() {
    pressed = new boolean[255];
  }
  
  /**
   * Returns whether a key is currently held down.
   * @param keyCode  the key code associated with the key being checked
   * @return true if the key is pressed, false if not
   */
  public boolean isPressed(int keyCode) {
    return pressed[keyCode];
  }
  
  /**
   * Adds a key to the array of currently pressed keys
   * @param e  the KEY_PRESSED event
   */
  public void keyPressed(KeyEvent e) {
    pressed[e.getKeyCode()] = true;
  }
  
  /**
   * Removes a key from the array of currently pressed keys
   * @param e  the KEY_RELEASED event
   */
  public void keyReleased(KeyEvent e) {
	pressed[e.getKeyCode()] = false;
  }

  public void keyTyped(KeyEvent e) {}
}