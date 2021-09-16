import java.lang.Math;
import java.awt.event.KeyEvent;

/**
 * Game holds information about the world and player.
 * @author Eric Anderson
 * @version 1.0
 */
public class Game {
  private DisplayWindow window;
  private Input input;
  
  private static final double ACCELERATION = 30;
  private static final double MAX_SPEED = 4;
  private static final double FRICTION = 8;
  private static final double ANGULAR_SPEED = 0.65;
  private static final double HALF_PLAYER_WIDTH = 0.25;
  public static final double FOV = Math.PI / 3;
  public static final int WORLD_WIDTH = 16;
  public static final int WORLD_HEIGHT = 16;
  
  private double speed = 0;
  public double xPos = 8;
  public double yPos = 8;
  public double dir = Math.PI;
  public int[][] world = {{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                          {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                          {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                          {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                          {2, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2},
                          {2, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2},
                          {2, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 2},
                          {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 2},
                          {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                          {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                          {2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                          {2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                          {2, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 2},
                          {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                          {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                          {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}};
  public int ifCase = 0;
  public int insideCase = 0;

  /**
   * Constructor for game.
   * @param width  the width of the window
   * @param height  the height of the window
   */
  public Game(int width, int height) {
    input = new Input();
    window = new DisplayWindow(width, height, input);
  }
  
  /**
   * Updates the player based on key input.
   * @param elapsedTime  the time since the last frame was drawn, in seconds
   */
  public void update(double elapsedTime) {
    //looking from side to side
	if (input.isPressed(KeyEvent.VK_D)) {
      dir += Math.PI * ANGULAR_SPEED * elapsedTime;
    }
    if (input.isPressed(KeyEvent.VK_A)) {
      dir -= Math.PI * ANGULAR_SPEED * elapsedTime;
    }
	
    dir %= 2 * Math.PI;
    if (dir < 0) {
      dir += 2 * Math.PI;
    }
    
	double oldX = xPos;
    double oldY = yPos;
		
    //movement with W and S
    if (input.isPressed(KeyEvent.VK_W)) {
      speed += elapsedTime * ACCELERATION;
    } else if (input.isPressed(KeyEvent.VK_S)) {
      speed -= elapsedTime * ACCELERATION;
    }

    speed = Math.max(speed, -MAX_SPEED);
    speed = Math.min(speed, MAX_SPEED);

    xPos += Math.cos(dir) * speed * elapsedTime;
    yPos += Math.sin(dir) * speed * elapsedTime;

    if (speed > 0) {
      speed -= FRICTION * elapsedTime;
      speed = Math.max(0, speed);
    } else {
      speed += FRICTION * elapsedTime;
      speed = Math.min(0, speed);
    }

    //prevent the player from running into walls
	//bug: the player get 'stuck' on corners. this code is the culprit.
    //ifCase = 0;
    if (world[(int)yPos][(int)(xPos + HALF_PLAYER_WIDTH)] != 0 ||
        world[(int)yPos][(int)(xPos - HALF_PLAYER_WIDTH)] != 0) {
	  xPos = oldX;
	  ifCase = 1;
    }
    if (world[(int)(yPos + HALF_PLAYER_WIDTH)][(int)xPos] != 0 ||
        world[(int)(yPos - HALF_PLAYER_WIDTH)][(int)xPos] != 0) {
	  yPos = oldY;
	  ifCase = 2;
    }
	/*if (world[(int)(yPos + HALF_PLAYER_WIDTH)][(int)(xPos + HALF_PLAYER_WIDTH)] != 0 ||
        world[(int)(yPos - HALF_PLAYER_WIDTH)][(int)(xPos - HALF_PLAYER_WIDTH)] != 0) {
      xPos = oldX;
	  yPos = oldY;
	  ifCase = 3;
    }*/
    if (world[(int)(yPos + HALF_PLAYER_WIDTH)][(int)(xPos + HALF_PLAYER_WIDTH)] != 0) {
    	if (1 == ifCase) {
          xPos = Math.min(xPos, oldX);
          insideCase = 1;}
    	else if (2 == ifCase) {
    	  yPos = Math.min(yPos,  oldY);
    	  insideCase = 2;}
    	  //ifCase = 4;
        }
    else if (world[(int)(yPos - HALF_PLAYER_WIDTH)][(int)(xPos - HALF_PLAYER_WIDTH)] != 0) {
          //xPos = Math.max(xPos, oldX);
    	  //yPos = Math.max(yPos,  oldY);
    	if (1 == ifCase) {
    	  xPos = Math.max(Math.round(xPos) + HALF_PLAYER_WIDTH, xPos);
    	  //xPos = (int)(xPos - HALF_PLAYER_WIDTH) + HALF_PLAYER_WIDTH;
    	  insideCase = 1;}
    	if (2 == ifCase) {
  	      yPos = Math.max(Math.round(yPos) + HALF_PLAYER_WIDTH,  yPos);
  	      //yPos = (int)(yPos - HALF_PLAYER_WIDTH) + HALF_PLAYER_WIDTH;
  	      insideCase = 2;}
    	  //ifCase = 5;
        }
    else if (world[(int)(yPos + HALF_PLAYER_WIDTH)][(int)(xPos - HALF_PLAYER_WIDTH)] != 0) {
    	if (1 == ifCase) {
          xPos = Math.max(xPos, oldX);
          insideCase = 1;}
    	else if (2 == ifCase) {
    	  yPos = Math.min(yPos,  oldY);
    	  insideCase = 2;}
    	  //ifCase = 6;
        }
    else if (world[(int)(yPos - HALF_PLAYER_WIDTH)][(int)(xPos + HALF_PLAYER_WIDTH)] != 0) {
    	if (1 == ifCase) {
          xPos = Math.min(xPos, oldX);
          insideCase = 1;}
    	else if (2 == ifCase) {
    	  yPos = Math.max(yPos,  oldY);
    	  insideCase = 2;}
    	  //ifCase = 7;
        }

    //limit the player to the bounds of the world
    xPos = Math.min(xPos, WORLD_WIDTH - HALF_PLAYER_WIDTH);
    xPos = Math.max(xPos, HALF_PLAYER_WIDTH);
    yPos = Math.min(yPos, WORLD_HEIGHT - HALF_PLAYER_WIDTH);
    yPos = Math.max(yPos, HALF_PLAYER_WIDTH);
  }
  
  /**
   * Renders the current state of the game to the screen.
   * @param elapsedTime  the time since the last frame was drawn, in seconds
   */
  public void draw(double elapsedTime) {
    window.draw(this, elapsedTime);
  }
}