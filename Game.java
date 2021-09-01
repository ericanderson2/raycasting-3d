import java.lang.Math;
import java.awt.event.KeyEvent;

public class Game
{
	private DisplayWindow window;
	private Input input;
	private double timeSinceLastMove = 0;
	
	public int[][] world = {{1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
							{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
							{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							{1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1}};
	
	public int worldWidth = 16;
	public int worldHeight = 9;
	public int gridSize = 64;
	public int xPos = 96;
	public int yPos = 224;
	public double dir = 1.047198;
	public double FOV = Math.PI / 2;
	public double maximumSpeed = 0.005;
	
	public Game(int width, int height)
	{
		input = new Input();
		window = new DisplayWindow(width, height, input);
	}
	
	public void update(double elapsedTime)
	{
		timeSinceLastMove -= elapsedTime;
		
		if (input.isPressed(KeyEvent.VK_D)) {
			dir += Math.PI * 0.5 * elapsedTime;
		}
		if (input.isPressed(KeyEvent.VK_A)) {
			dir -= Math.PI * 0.5 * elapsedTime;
		}
		
		dir %= 2 * Math.PI;
		if (dir < 0) {
			dir += 2 * Math.PI;
		}
		
		//movement with WASD
		if (timeSinceLastMove <= 0) {
			if (input.isPressed(KeyEvent.VK_W)) {
				
				if (dir >= Math.PI / 4 && dir < 3 * Math.PI / 4) {
					yPos += 1;
				} else if (dir >= 3 * Math.PI / 4 && dir < 5 * Math.PI / 4) {
					xPos -= 1;
				} else if (dir >= 5 * Math.PI / 4 && dir < 7 * Math.PI / 4) {
					yPos -= 1;
				} else {
					xPos += 1;
				}
				
				timeSinceLastMove = maximumSpeed;
			}
			xPos = Math.min(xPos, worldWidth * gridSize - gridSize / 2);
			xPos = Math.max(xPos, gridSize / 2);
			
			yPos = Math.min(yPos, worldHeight * gridSize - gridSize / 2);
			yPos = Math.max(yPos, gridSize / 2);
		}
		
		
	}

	public void draw(double elapsedTime)
	{
		window.draw(this, elapsedTime);
	}
}