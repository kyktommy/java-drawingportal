import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class FontShape extends Shape{
	protected int fontSize;
	protected String str;
	
	public FontShape(int x, int y, String str, int fontSize, Color strokeColor, Color fillColor){
		this.x = x;
		this.y = y;
		this.str = str;
		this.fontSize = fontSize;
		this.strokeColor = strokeColor;
		this.fillColor = fillColor;
	}
	
	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public void draw(Graphics g) {
		Graphics2D gg = (Graphics2D)g.create();
		Font font = new Font("Helvetica", Font.PLAIN, this.fontSize);
		gg.setFont(font);
		gg.drawString(str, x, y);
	}
}
