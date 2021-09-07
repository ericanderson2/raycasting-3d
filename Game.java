import java.lang.Math;
import java.awt.event.KeyEvent;

public class Game
{
	private DisplayWindow window;
	private Input input;
	
	private double speed = 0;
	private double acceleration = 30;
	private double maxSpeed = 4;
	private double friction = 8;
	
	private double angularSpeed = 0.65;
	
	private double halfPlayerWidth = 0.25;
	
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
	
	public int worldWidth = 16;
	public int worldHeight = 16;
	
	public double xPos = 8;
	public double yPos = 8;
	public double dir = Math.PI;
	public double FOV = Math.PI / 3;
	
	public Game(int width, int height)
	{
		input = new Input();
		window = new DisplayWindow(width, height, input);
	}
	
	public void update(double elapsedTime)
	{
		if (input.isPressed(KeyEvent.VK_D)) {
			dir += Math.PI * angularSpeed * elapsedTime;
		}
		if (input.isPressed(KeyEvent.VK_A)) {
			dir -= Math.PI * angularSpeed * elapsedTime;
		}
		
		dir %= 2 * Math.PI;
		if (dir < 0) {
			dir += 2 * Math.PI;
		}
		
		double oldX = xPos;
		double oldY = yPos;
		
		//movement with WASD
		if (input.isPressed(KeyEvent.VK_W)) {
			speed += elapsedTime * acceleration;
		} else if (input.isPressed(KeyEvent.VK_S)) {
			speed -= elapsedTime * acceleration;
		}
		
		speed = Math.max(speed, -maxSpeed);
		speed = Math.min(speed, maxSpeed);
		
		xPos += Math.cos(dir) * speed * elapsedTime;
		yPos += Math.sin(dir) * speed * elapsedTime;
		
		if (speed > 0) {
			speed -= friction * elapsedTime;
			speed = Math.max(0, speed);
		} else {
			speed += friction * elapsedTime;
			speed = Math.min(0, speed);
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