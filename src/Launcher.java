/**
 * Launcher runs the game in a thread.
 * @author Eric Anderson
 * @version 1.0
 */
public class Launcher {
  public static void main(String[] args) {
    new Thread(new MainLoop(new Game(800, 450))).start();
  }
}