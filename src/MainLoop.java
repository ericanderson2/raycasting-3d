/**
 * MainLoop updates and renders the game while it is running.
 * @author Eric Anderson
 * @version 1.0
 */
public class MainLoop implements Runnable {
  private Game game;
  private boolean running;
  
  /**
   * Constructor for MainLoop.
   * @param game  the game object that stores world and player information
   */
  public MainLoop(Game game) {
    this.game = game;
  }
  
  /**
   * Comprises the main game loop.
   */
  @Override
  public void run() {
    running = true;
    long lastTime = System.currentTimeMillis();
	long currentTime;

    while(running) {	
      currentTime = System.currentTimeMillis();
      double elapsedTime = (currentTime - lastTime) / 1000d;
      lastTime = currentTime;

	  update(elapsedTime);
	  draw(elapsedTime);
    }
  }
  
  /**
   * Updates the game object.
   * @param elapsedTime  the time since the last frame was drawn, in seconds
   */
  private void update(double elapsedTime) {
    game.update(elapsedTime);
  }

  /**
   * Draws the current game state to the screen.
   * @param elapsedTime  the time since the last frame was drawn, in seconds
   */
  private void draw(double elapsedTime) {
	game.draw(elapsedTime);
  }
}