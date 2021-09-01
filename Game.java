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
	public double dir = 0.0;
	public double FOV = Math.PI / 2;
	public double maximumSpeed = 0.002;
	
	public Game(int width, int height)
	{
		input = new Input();
		window = new DisplayWindow(width, height, input);
	}
	
	public void update(double elapsedTime)
	{
		timeSinceLastMove -= elapsedTime;
		
		if (input.isPressed(KeyEvent.VK_E)) {
			dir += Math.PI * 0.5 * elapsedTime;
		}
		if (input.isPressed(KeyEvent.VK_Q)) {
			dir -= Math.PI * 0.5 * elapsedTime;
		}
		
		//movement with WASD
		if (timeSinceLastMove <= 0) {
			if (input.isPressed(KeyEvent.VK_W)) {
				yPos -= 1;
				timeSinceLastMove = maximumSpeed;
			}
			if (input.isPressed(KeyEvent.VK_S)) {
				yPos += 1;
				timeSinceLastMove = maximumSpeed;
			}
			if (input.isPressed(KeyEvent.VK_A)) {
				xPos -= 1;
				timeSinceLastMove = maximumSpeed;
			}
			if (input.isPressed(KeyEvent.VK_D)) {
				xPos += 1;
				timeSinceLastMove = maximumSpeed;
			}
		}
	}
	
	public void draw(double elapsedTime)
	{
		window.draw(this, elapsedTime);
	}
}