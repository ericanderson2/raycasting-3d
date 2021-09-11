import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class DisplayWindow extends JFrame
{
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
		
		double distanceToProjection = (canvas.getWidth() / 2) / Math.tan(game.FOV / 2);
		
		//iterate through every vertical strip on the screen	
		for (int i = 0; i < 800; i++)
		{
		double angle = ((double)i / canvas.getWidth() * game.FOV + game.dir - game.FOV / 2);
			
		angle %= Math.PI * 2;
		if (angle < 0) {
			angle += Math.PI * 2;
		}
		
		//graphics.drawLine((int)(game.xPos / 64d * 50), (int)(game.yPos / 64d * 50), (int)(game.xPos / 64d * 50 + Math.cos(angle) * 500), (int)(game.yPos / 64d * 50 + Math.sin(angle) * 500));
		
		
		int offset = 0;
		int tileID = 0;
		
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
			if (game.world[(int)currentWallY][(int)currentWallX] != 0) {
				//graphics.drawLine((int)(game.xPos / 64d * 50), (int)(game.yPos / 64d * 50), (int)(currentWallX / 64d * 50), (int)(currentWallY / 64d * 50));
				distance = Math.sqrt(Math.pow(currentWallX - game.xPos, 2) + Math.pow(currentWallY - game.yPos, 2));
				offset = (int)((currentWallX - (int)currentWallX) * 15);
				tileID = game.world[(int)currentWallY][(int)currentWallX];
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
			if (game.world[(int)currentWallY2][(int)currentWallX2] != 0) {
				double dist = Math.sqrt(Math.pow(currentWallX2 - game.xPos, 2) + Math.pow(currentWallY2 - game.yPos, 2));
				if (dist < distance) {
					//graphics.drawLine((int)(game.xPos / 64d * 50), (int)(game.yPos / 64d * 50), (int)(currentWallX2 / 64d * 50), (int)(currentWallY2 / 64d * 50));				
					vertHit = true;
					distance = Math.sqrt(Math.pow(currentWallX2 - game.xPos, 2) + Math.pow(currentWallY2 - game.yPos, 2));
					offset = (int)((currentWallY2 - (int)currentWallY2) * 15);
					tileID = game.world[(int)currentWallY2][(int)currentWallX2];
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
		
		if (distance < 1000) {
			int lineHeight = (int)(1 / distance * distanceToProjection);
			int[] slice = tiles[tileID][offset];
			
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
			//graphics.fillRect(i, (int)((canvas.getHeight() - lineHeight) / 2), 1, lineHeight);
		}
		
		}
		
		for (int x = 0; x < game.worldWidth; x++)
		{
			for (int y = 0; y < game.worldHeight; y++)
			{
				if (game.world[y][x] == 0) {
					graphics.setColor(Color.LIGHT_GRAY);
				} else {
					graphics.setColor(Color.BLACK);
				}
				graphics.fillRect(x * 10, y * 10, 10, 10);
			}
		}
		
		graphics.setColor(Color.DARK_GRAY);
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
		
		
		graphics.setColor(Color.WHITE);
		int fps = (int)(1 / elapsedTime);
		
		graphics.setFont(new Font("SansSerif", Font.PLAIN, 18));
		graphics.drawString("FPS: " + fps, 710, 20);
		graphics.drawString("Use WS to move and AD to look around.", 250, 380);
		
		
		graphics.dispose();
		bufferStrat.show();
	}
	

	// Horizontal run snipped,
	// basically the same as vertical run

	//if (dist)
	//drawRay(xHit, yHit);
	//}
}