import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class DisplayWindow extends JFrame
{
	private Canvas canvas;
	
	public DisplayWindow(int width, int height)
	{
		setTitle("Raycasting 3D");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setFocusable(false);
		add(canvas);
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
		
		graphics.setColor(Color.WHITE);
		int fps = (int)(1 / elapsedTime);
		graphics.drawString("FPS: " + fps, 5, 15);
		
		for (int x = 0; x < 5; x++)
		{
			for (int y = 0; y < 5; y++)
			{
				if (game.world[y][x] == 0) {
					graphics.setColor(Color.GRAY);
				} else {
					graphics.setColor(Color.RED);
				}
				graphics.fillRect(x * 40 + 600, y * 40, 40, 40);
			}
		}
		
		graphics.setColor(Color.YELLOW);
		graphics.fillRect(game.x * 40 + 600 + 10, game.y * 40 + 10, 20, 20);
		
		graphics.dispose();
		bufferStrat.show();
	}
}