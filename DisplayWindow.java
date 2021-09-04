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
		
		graphics.setColor(Color.GREEN);
		graphics.fillRect(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2);
		graphics.setColor(Color.CYAN);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight() / 2);
		
		//iterate through every vertical strip on the screen
		
		for (int i = 0; i < 800; i++)
		{
		double angle = ((double)i / canvas.getWidth() * game.FOV + game.dir - game.FOV / 2);
			
		angle %= Math.PI * 2;
		if (angle < 0) {
			angle += Math.PI * 2;
		}
		
		//graphics.drawLine((int)(game.xPos / 64d * 50), (int)(game.yPos / 64d * 50), (int)(game.xPos / 64d * 50 + Math.cos(angle) * 500), (int)(game.yPos / 64d * 50 + Math.sin(angle) * 500));
		
		//calculate the horizontal wall ray collisions
		boolean facingUp = (angle < 0 || angle > Math.PI);
		boolean facingLeft = (angle > Math.PI / 2 && angle < 3 * Math.PI / 2);

		double currentWallY = (int)game.yPos;
		if (facingUp) {
			currentWallY -= 0.001;
		} else {
			currentWallY += 1;
		}
		
		double currentWallX = game.xPos + (game.yPos - currentWallY) / Math.tan(-angle);
		
		double deltaY = 1.0;
		if (facingUp) {
			deltaY = -1.0;
		}
		
		double deltaX = 1 / Math.tan(angle);
		if (facingUp) {
			deltaX = 1 / Math.tan(-angle);
		}
		
		graphics.setColor(Color.BLUE);
		//graphics.fillRect(currentWallX / 64 * 50, currentWallY / 64 * 50, 50, 50);
		
		double distance = 10000;
		
		while((currentWallX > 0 && currentWallX < game.worldWidth) && (currentWallY > 0 && currentWallY < game.worldHeight))
		{
			if (game.world[(int)currentWallY][(int)currentWallX] == 1) {
				//graphics.drawLine((int)(game.xPos / 64d * 50), (int)(game.yPos / 64d * 50), (int)(currentWallX / 64d * 50), (int)(currentWallY / 64d * 50));
				distance = Math.sqrt(Math.pow(currentWallX - game.xPos, 2) + Math.pow(currentWallY - game.yPos, 2));
				break;
			}
			currentWallX += deltaX;
			currentWallY += deltaY;
			
			//graphics.fillRect(currentWallX / 64 * 50, currentWallY / 64 * 50, 50, 50);
		}
		
		double currentWallX2 = (int)game.xPos;
		if (facingLeft) {
			currentWallX2 -= 0.001;
			deltaX = -1;
		} else {
			currentWallX2 += 1;
			deltaX = 1;
		}
		
		double currentWallY2 = game.yPos + (game.xPos - currentWallX2) * Math.tan(-angle);
		
		if (facingLeft) {
			deltaY = Math.tan(-angle);
		} else {
			deltaY = Math.tan(angle);
		}
		
		boolean vertHit = false;
		
		while((currentWallX2 > 0 && currentWallX2 < game.worldWidth) && (currentWallY2 > 0 && currentWallY2 < game.worldHeight))
		{
			if (game.world[(int)currentWallY2][(int)currentWallX2] == 1) {
				double dist = Math.sqrt(Math.pow(currentWallX2 - game.xPos, 2) + Math.pow(currentWallY2 - game.yPos, 2));
				if (dist < distance) {
					//graphics.drawLine((int)(game.xPos / 64d * 50), (int)(game.yPos / 64d * 50), (int)(currentWallX2 / 64d * 50), (int)(currentWallY2 / 64d * 50));				
					vertHit = true;
					distance = Math.sqrt(Math.pow(currentWallX2 - game.xPos, 2) + Math.pow(currentWallY2 - game.yPos, 2));
					break;
				}
			}
			currentWallX2 += deltaX;
			currentWallY2 += deltaY;
			
			//graphics.fillRect(currentWallX2 / 64 * 50, currentWallY2 / 64 * 50, 50, 50);
		}
		
		distance = distance * Math.cos(angle - game.dir);
		
		graphics.setColor(Color.DARK_GRAY);
		if (vertHit) {
			graphics.setColor(Color.GRAY);
		}
		
		int lineHeight = (int)(1 / distance * 200);
		
			if (distance < 1000) {
				graphics.fillRect(i, (int)((canvas.getHeight() - lineHeight) / 2), 1, lineHeight);
				//graphics.drawLine(i, (int)((canvas.getHeight() - lineHeight) / 2), i, (int)((canvas.getHeight() - lineHeight) / 2 + lineHeight));
			}
		}
		
		graphics.setColor(Color.WHITE);
		int fps = (int)(1 / elapsedTime);
		graphics.drawString("FPS: " + fps, 5, 15);
		graphics.drawString("X Pos: " + game.xPos, 5, 30);
		graphics.drawString("Y Pos: " + game.yPos, 5, 45);
		
		for (int x = 0; x < game.worldWidth; x++)
		{
			for (int y = 0; y < game.worldHeight; y++)
			{
				if (game.world[y][x] == 0) {
					graphics.setColor(Color.GRAY);
				} else {
					graphics.setColor(Color.RED);
				}
				graphics.fillRect(x * 10, y * 10, 10, 10);
			}
		}
		
		graphics.setColor(Color.BLUE);
		for (int x = 0; x < game.worldWidth; x++)
		{
			graphics.drawLine(x * 10, 0, x * 10, 10 * game.worldHeight);
		}
		for (int y = 0; y < game.worldHeight; y++)
		{
			graphics.drawLine(0, y * 10, 10 * game.worldWidth, y * 10);
		}
		
		graphics.setColor(Color.YELLOW);
		graphics.fillOval((int)(game.xPos * 10 - 5), (int)(game.yPos * 10 - 5), 10, 10);
		graphics.drawLine((int)(game.xPos * 10), (int)(game.yPos * 10), (int)(game.xPos * 10 + Math.cos(game.dir - game.FOV / 2) * 50), (int)(game.yPos * 10 + Math.sin(game.dir - game.FOV / 2) * 50));
		graphics.drawLine((int)(game.xPos * 10), (int)(game.yPos * 10), (int)(game.xPos * 10 + Math.cos(game.dir + game.FOV / 2) * 50), (int)(game.yPos * 10 + Math.sin(game.dir + game.FOV / 2) * 50));
		
		graphics.dispose();
		bufferStrat.show();
	}
	

	// Horizontal run snipped,
	// basically the same as vertical run

	//if (dist)
	//drawRay(xHit, yHit);
	//}
}