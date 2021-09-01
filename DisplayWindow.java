import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class DisplayWindow extends JFrame
{
	private Canvas canvas;
	
	public DisplayWindow(int width, int height, Input input)
	{
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
	
	public void draw(Game game, double elapsedTime)
	{
		BufferStrategy bufferStrat = canvas.getBufferStrategy();
		Graphics graphics = bufferStrat.getDrawGraphics();
		
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		//show map
		for (int x = 0; x < game.worldWidth; x++)
		{
			for (int y = 0; y < game.worldHeight; y++)
			{
				if (game.world[y][x] == 0) {
					graphics.setColor(Color.GRAY);
				} else {
					graphics.setColor(Color.RED);
				}
				graphics.fillRect(x * 50, y * 50, 50, 50);
			}
		}
		
		graphics.setColor(Color.BLUE);
		for (int x = 0; x < game.worldWidth; x++)
		{
			graphics.drawLine(x * 50, 0, x * 50, 450);
		}
		for (int y = 0; y < game.worldHeight; y++)
		{
			graphics.drawLine(0, y * 50, 800, y * 50);
		}
		
		
		graphics.setColor(Color.YELLOW);
		graphics.fillOval((int)(game.xPos / 64d * 50 - 15), (int)(game.yPos / 64d * 50 - 15), 30, 30);
		
		//iterate through every vertical strip on the screen
		int i = 0;
		double angle = ((double)i / canvas.getWidth() * game.FOV + game.dir - game.FOV / 2);
			
		angle %= Math.PI * 2;
		if (angle < 0) {
			angle += Math.PI * 2;
		}
		
		graphics.drawLine((int)(game.xPos / 64d * 50), (int)(game.yPos / 64d * 50), (int)(game.xPos / 64d * 50 + Math.cos(angle) * 500), (int)(game.yPos / 64d * 50 + Math.sin(angle) * 500));
	
		boolean facingUp = (angle < 0 || angle > Math.PI);
		
		
		graphics.setColor(Color.WHITE);
		int fps = (int)(1 / elapsedTime);
		graphics.drawString("FPS: " + fps, 5, 15);
		graphics.drawString("X Pos: " + game.xPos, 5, 30);
		graphics.drawString("Y Pos: " + game.yPos, 5, 45);
		graphics.drawString("Angle: " + angle, 5, 60);
		graphics.drawString("Facing Up: " + facingUp, 5, 75);
		
		graphics.dispose();
		bufferStrat.show();
	}
	

	// Horizontal run snipped,
	// basically the same as vertical run

	//if (dist)
	//drawRay(xHit, yHit);
	//}
}