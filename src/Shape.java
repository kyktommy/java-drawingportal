import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public abstract class Shape {
	protected Color fillColor;
	protected Color strokeColor;
	protected int x;
	protected int y;
	protected int strokeWidth;
	protected boolean isSelected;
	
	protected void move(Point from, Point to) {
		this.x += to.x - from.x;
		this.y += to.y - from.y;
	}
	
	public void scale(float ratio) {}
	
	protected abstract void draw(Graphics g);
	
	public Shape() {
		isSelected = false;
	}
	
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
	
	public void pressed(int x, int y) {
		
	}
	
	public void dragged(int x, int y) {
		
	}
	
	public boolean existInArea(int x1, int y1, int x2, int y2) {
		return false;
	}

	public String toString() {
		return this.getClass().getName() + " x: " + x + "y: " + y;
	}
}
