import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Rectangle extends Shape{
	protected int width;
	protected int height;
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle(int x, int y, int width, int height, Color strokeColor, Color fillColor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.strokeColor = strokeColor;
		this.fillColor = fillColor;
		this.strokeWidth = 5;
	}
	
	public void scale(float ratio) {
		this.width = (int)(this.width*ratio);
		this.height = (int)(this.height*ratio);
	}
	
	int beginX;
	int beginY;
	
	public void pressed(int x, int y) {
		//init
		this.x = beginX = x;
		this.y = beginY = y;
		this.width = 1;
		this.height = 1;
	}
	
	public void dragged(int x, int y) {
		
		int tempX = -1;
		int tempY = -1;

		int w = x - beginX;
		int h = y - beginY;
		
		if (w < 0) {
			tempX = x;
		}
		if (h < 0) {
			tempY = y;
		}
		
		this.height = Math.abs(h);
		this.width = Math.abs(w);
		this.x = (tempX == -1) ? beginX : tempX;
		this.y = (tempY == -1) ? beginY : tempY;
	}
	
	public boolean existInArea(int x1, int y1, int x2, int y2) {
		if(x1 < x+width && x2 > x && y1 < y+height && y2 > y) {
			return true;
		} else {
			return false;
		}
	}
	
	public void draw(Graphics g) {
		Graphics2D gg = (Graphics2D)g.create();
		if(this.fillColor != null) {
			gg.setColor(this.fillColor);
			gg.fillRect(x, y, width-1, height-1);
		}
		if(this.strokeWidth != 0) {
			gg.setStroke(new BasicStroke(this.strokeWidth));
			gg.setColor(this.strokeColor);
			gg.drawRect(x, y, width-1, height-1);
		}
		if(this.isSelected) {
			BasicStroke stroke = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 4, new float[] {5f}, 0f);
			gg.setColor(Color.black);
			gg.setStroke(stroke);
			gg.drawRect(x-1, y-1, width+2, height+2);
		}
	}
}
