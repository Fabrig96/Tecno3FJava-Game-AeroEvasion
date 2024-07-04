package com.tecno3f.game;

import java.awt.*;

/**
 * Esta clase sirve para representar objeto Nave
 * @author FabrizioAG
 */
public class Nave {
	int x, y;
	int[] xPoints, yPoints;
	int height;
	int panelHeight;

	public Nave(int x, int y) {
		this.x = x;
		this.y = y;
		this.xPoints = new int[] { x, x + 50, x };
		this.yPoints = new int[] { y, y + 25, y + 50 };
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.lightGray);
		g.fillPolygon(xPoints, yPoints, 3);
	}

	public void move(int dy) {

		y += dy;
		for (int i = 0; i < yPoints.length; i++) {
			yPoints[i] += dy;
		}
	}

	public void keepWithinBounds(int screenHeight) {
		if (y < 0) {
			y = 0;
		}
		if (y + 50 > screenHeight) {
			y = screenHeight - 50;
		}
		xPoints[0] = x;
		xPoints[1] = x + 50;
		xPoints[2] = x;
		yPoints[0] = y;
		yPoints[1] = y + 25;
		yPoints[2] = y + 50;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 50, 50);
	}
}
