import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Line extends Shape{
	public int getToX() {
		return toX;
	}

	public void setToX(int toX) {
		this.toX = toX;
	}

	public int getToY() {
		return toY;
	}

	public void setToY(int toY) {
		this.toY = toY;
	}

	protected int toX;
	protected int toY;
	
	public Line(int x, int y, int toX, int toY, Color fillColor){
		this.x = x;
		this.y = y;
		this.toX = toX;
		this.toY = toY;
		this.fillColor = fillColor;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D gg = (Graphics2D)g.create();
		gg.setColor(this.fillColor);
		gg.drawLine(x, y, toX, toY);
	}
	
}
