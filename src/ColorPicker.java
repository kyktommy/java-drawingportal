import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;


public class ColorPicker extends JDialog {
	
	ColorPlate colorPlate;
	static Color fillColor;
	
	public ColorPicker(DrawingCanvas canvas){
		super();
		this.colorPlate = new ColorPlate(canvas);
		this.add(colorPlate);
		this.setPreferredSize(new Dimension(200, 200));
		this.pack();
	}
	
}