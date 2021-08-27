import java.lang.Math;

public class Game
{
	private DisplayWindow window;
	public int[][] world = {{0, 1, 1, 0, 0},
							{1, 0, 1, 1, 1},
							{1, 0, 0, 0, 1},
							{1, 0, 0, 1, 0},
							{1, 0, 1, 1, 0}};
	public int x = 2;
	public int y = 2;
	public float direction = 0;
	public float fieldOfView = (float)(Math.PI / 2);
	
	public Game(int width, int height)
	{
		window = new DisplayWindow(width, height);
	}
	
	public void update(double elapsedTime)
	{
	
	}
	
	public void draw(double elapsedTime)
	{
		window.draw(this, elapsedTime);
	}
}