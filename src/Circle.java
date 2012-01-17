import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;


public class Circle extends Shape{
	protected int r;

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public Circle(int cx, int cy, int r, Color strokeColor, Color fillColor) {
		this.x = cx;
		this.y = cy;
		this.r = r;
		this.strokeColor = strokeColor;
		this.fillColor = fillColor;
		this.strokeWidth = 5;
	}
	
	public void scale(float ratio) {
		this.r = (int)(r * ratio);
	}
	
	public boolean existIn(int x1, int y1, int x2, int y2) {
		return false;
	}
	
	public void pressed(int x, int y) {
		//init
		this.x = x;
		this.y = y;
		this.r = 0;
	}
	
	public void dragged(int x, int y) {
		this.r = (int)Math.round(Math.hypot(this.x-x, this.y-y));
	}
	
	public boolean existInArea(int x1, int y1, int x2, int y2) {
		if(x1 < x+r && x2 > x-r && y1 < y+r && y2 > y-r) {
			return true;
		} else {
			return false;
		}
	}
	
	public void draw(Graphics g) {
		Graphics2D gg = (Graphics2D)g.create();
		gg.setStroke(new BasicStroke(this.strokeWidth));
		gg.setColor(this.fillColor);
		gg.fillOval(x-r, y-r, 2*r, 2*r);
		gg.setColor(this.strokeColor);
		gg.drawOval(x-r, y-r, 2*r, 2*r);
		if(this.isSelected) {
			BasicStroke stroke = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 4, new float[] {5f}, 0f);
			gg.setColor(Color.black);
			gg.setStroke(stroke);
			int newR = r + 2;
			gg.drawOval(x-newR, y-newR, 2*newR, 2*newR);
		}
	}
}
