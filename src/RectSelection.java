import java.awt.Color;


public class RectSelection extends Rectangle{

	public RectSelection(int x, int y, int width, int height,
			Color strokeColor, Color fillColor) {
		super(x, y, width, height, strokeColor, fillColor);
		this.strokeColor = null;
		this.strokeWidth = 0;
		this.fillColor = null;
		this.isSelected = true;
	}
	
}
