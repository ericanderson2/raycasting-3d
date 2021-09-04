import java.lang.Math;
import java.awt.event.KeyEvent;

public class Game
{
	private DisplayWindow window;
	private Input input;
	private double speed = 5;
	private double halfPlayerWidth = 0.25;
	
	public int[][] world = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
							{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
							{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
							{1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1},
							{1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1},
							{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
							{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
	
	public int worldWidth = 16;
	public int worldHeight = 9;
	
	public double xPos = 10;
	public double yPos = 3;
	public double dir = 1.047198;
	public double FOV = Math.PI / 3;
	
	public Game(int width, int height)
	{
		input = new Input();
		window = new DisplayWindow(width, height, input);
	}
	
	public void update(double elapsedTime)
	{
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
		
		double oldX = xPos;
		double oldY = yPos;
		
		//movement with WASD
		if (input.isPressed(KeyEvent.VK_W)) {
			xPos += Math.cos(dir) * elapsedTime * speed;
			yPos += Math.sin(dir) * elapsedTime * speed;
		} else if (input.isPressed(KeyEvent.VK_S)) {
			xPos -= Math.cos(dir) * elapsedTime * speed;
			yPos -= Math.sin(dir) * elapsedTime * speed;
		}
		
		//prevent the player from running into walls
		if (world[(int)yPos][(int)(xPos + halfPlayerWidth)] != 0 ||
			world[(int)yPos][(int)(xPos - halfPlayerWidth)] != 0) {
				xPos = oldX;
		}
		if (world[(int)(yPos + halfPlayerWidth)][(int)xPos] != 0 ||
			world[(int)(yPos - halfPlayerWidth)][(int)xPos] != 0) {
				yPos = oldY;
		}	
		
		//limit the player to the bounds of the world
		xPos = Math.min(xPos, worldWidth - halfPlayerWidth);
		xPos = Math.max(xPos, halfPlayerWidth);
		
		yPos = Math.min(yPos, worldHeight - halfPlayerWidth);
		yPos = Math.max(yPos, halfPlayerWidth);
	}

	public void draw(double elapsedTime)
	{
		window.draw(this, elapsedTime);
	}
}