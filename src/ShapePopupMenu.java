import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


public class ShapePopupMenu extends JPopupMenu implements ActionListener{

	ColorPlate plate;

	public ShapePopupMenu(DrawingCanvas canvas) {
		super("");
		
		plate = new ColorPlate(canvas);
		this.add(plate);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
