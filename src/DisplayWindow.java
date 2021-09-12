import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * DisplayWindow represents the window and graphics that the user sees.
 * @author Eric Anderson
 * @version 1.0
 */
public class DisplayWindow extends JFrame {
  private Canvas canvas;
  private Color[] palette = {null, Color.RED, Color.WHITE, new Color(150, 75, 0)};
  private int[][] brick = {{2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2},
							 {1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2},
							 {1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2},
							 {1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2},
							 {1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2},
							 {2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2},
							 {1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2},
							 {1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2},
							 {1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2},
							 {1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2},
							 {2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2},
							 {1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2},
							 {1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2},
							 {1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2},
							 {1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 2}};
  private int[][] fence = {{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
							 {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
							 {0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 3, 3, 0, 0, 0},
							 {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
							 {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
							 {0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 3, 3, 0, 0, 0},
							 {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
							 {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
							 {0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 3, 3, 0, 0, 0},
							 {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
							 {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
							 {0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 3, 3, 0, 0, 0},
							 {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
							 {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
							 {0, 0, 0, 0, 3, 3, 0, 0, 0, 0, 3, 3, 0, 0, 0}};	
  private int[][][] tiles = {null, brick, fence};
	
  /**
   * Constructor for DisplayWindow.
   * @param width  the width of the window
   * @param height  the height of the window
   * @param input  the object that will record user input
   */
  public DisplayWindow(int width, int height, Input input) {
    setTitle("Raycasting 3D");
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	
	canvas = new Canvas();
	canvas.setPreferredSize(new Dimension(width, height));
	canvas.setFocusable(false);
	add(canvas);
	addKeyListener(input);
	pack();
	
    canvas.createBufferStrategy(2);
    setVisible(true);
  }
	
  /**
   * Displays the current game state on the screen
   * @param game  the game object with information about the world and player
   * @param elapsedTime  the time since the last frame was drawn in seconds
   */
  public void draw(Game game, double elapsedTime) {
    BufferStrategy bufferStrat = canvas.getBufferStrategy();
    Graphics graphics = bufferStrat.getDrawGraphics();

    //color the floor and ceiling
    graphics.setColor(Color.GREEN);
    graphics.fillRect(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2);
    graphics.setColor(Color.CYAN);
    graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight() / 2);

    double distanceToProjection = (canvas.getWidth() / 2) / Math.tan(game.FOV / 2);
    
	for (int i = 0; i < canvas.getWidth(); i++) {
	  //calculate ray angle based on column
      double angle = ((double)i / canvas.getWidth() * game.FOV + game.dir - game.FOV / 2);
      angle %= Math.PI * 2;
      if (angle < 0) {
        angle += Math.PI * 2;
      }
	  
	  double distance = 10000;
      int offset = 0;
      int tileID = 0;
	  
	  //calculate starting position and step for horizontal wall checks
      boolean facingUp = (angle < 0 || angle > Math.PI);
      double currentWallY = (facingUp) ? (int)game.yPos - 0.001 : (int)game.yPos + 1;
      double currentWallX = game.xPos + (game.yPos - currentWallY) / Math.tan(-angle);
      double deltaY = (facingUp) ? -1.0 : 1.0;
      double deltaX = (facingUp) ? 1 / Math.tan(-angle) : 1 / Math.tan(angle);
	  
	  //check for horizontal wall collisions
      while((currentWallX > 0 && currentWallX < game.WORLD_WIDTH) && (currentWallY > 0 && currentWallY < game.WORLD_HEIGHT)) {
        if (game.world[(int)currentWallY][(int)currentWallX] != 0) {
          distance = Math.sqrt(Math.pow(currentWallX - game.xPos, 2) + Math.pow(currentWallY - game.yPos, 2));
          offset = (int)((currentWallX - (int)currentWallX) * 15);
          tileID = game.world[(int)currentWallY][(int)currentWallX];
          break;
        }
        currentWallX += deltaX;
        currentWallY += deltaY;
      }
      
	  //calculate starting position and step for vertical wall checks
      boolean facingLeft = (angle > Math.PI / 2 && angle < 3 * Math.PI / 2);
      currentWallX = (facingLeft) ? (int)game.xPos - 0.001 : (int)game.xPos + 1;
      currentWallY = game.yPos + (game.xPos - currentWallX) * Math.tan(-angle);
      deltaX = (facingLeft) ? -1 : 1;
      deltaY = (facingLeft) ? Math.tan(-angle) : Math.tan(angle);
      
	  boolean vertHit = false;
      //check for vertical wall collisions
	  while((currentWallX > 0 && currentWallX < game.WORLD_WIDTH) && (currentWallY > 0 && currentWallY < game.WORLD_HEIGHT)) {
        if (game.world[(int)currentWallY][(int)currentWallX] != 0) {
          double newDist = Math.sqrt(Math.pow(currentWallX - game.xPos, 2) + Math.pow(currentWallY - game.yPos, 2));
	      if (newDist < distance) {
            vertHit = true;
            distance = newDist;
            offset = (int)((currentWallY - (int)currentWallY) * 15);
            tileID = game.world[(int)currentWallY][(int)currentWallX];
            break;
          }
        }
        currentWallX += deltaX;
        currentWallY += deltaY;
      }	
      
	  distance = distance * Math.cos(angle - game.dir);
      
	  if (distance < 10000) {
        int lineHeight = (int)(1 / distance * distanceToProjection);
        int[] slice = tiles[tileID][offset];
        
		//draw the pixel column in parts according to its texture
        for (int j = 0; j < 15; j++) {
          Color color = palette[slice[j]];
          if (color != null) {
            if (vertHit) {
              color = color.darker();
            }
            graphics.setColor(color);
            graphics.fillRect(i, (int)((canvas.getHeight() - lineHeight) / 2 + j * (lineHeight / 15d)), 1, (int)(lineHeight / 15d + 1));
          }
        }
      }
    }
	
	//draw minimap
    for (int x = 0; x < game.WORLD_WIDTH; x++) {
      for (int y = 0; y < game.WORLD_HEIGHT; y++) {
        if (game.world[y][x] == 0) {
         graphics.setColor(Color.LIGHT_GRAY);
        } else {
          graphics.setColor(Color.BLACK);
        }
        graphics.fillRect(x * 10, y * 10, 10, 10);
	  }
    }
    graphics.setColor(Color.DARK_GRAY);
    for (int x = 0; x < game.WORLD_WIDTH; x++) {
      graphics.drawLine(x * 10, 0, x * 10, 10 * game.WORLD_HEIGHT);
    }
    for (int y = 0; y < game.WORLD_HEIGHT; y++) {
      graphics.drawLine(0, y * 10, 10 * game.WORLD_WIDTH, y * 10);
    }
    
	//draw player on minimap
	graphics.setColor(Color.YELLOW);
    graphics.fillOval((int)(game.xPos * 10 - 5), (int)(game.yPos * 10 - 5), 10, 10);
    graphics.drawLine((int)(game.xPos * 10), (int)(game.yPos * 10), (int)(game.xPos * 10 + Math.cos(game.dir - game.FOV / 2) * 50), (int)(game.yPos * 10 + Math.sin(game.dir - game.FOV / 2) * 50));
    graphics.drawLine((int)(game.xPos * 10), (int)(game.yPos * 10), (int)(game.xPos * 10 + Math.cos(game.dir + game.FOV / 2) * 50), (int)(game.yPos * 10 + Math.sin(game.dir + game.FOV / 2) * 50));
    
	//screen text
	graphics.setColor(Color.WHITE);
	graphics.setFont(new Font("SansSerif", Font.PLAIN, 18));
    int fps = (int)(1 / elapsedTime);
    graphics.drawString("FPS: " + fps, canvas.getWidth() - 90, 20);
    graphics.drawString("Use WS to move and AD to look around.", canvas.getWidth() / 2 - 150, canvas.getHeight() - 70);

    graphics.dispose();
    bufferStrat.show();
  }
}