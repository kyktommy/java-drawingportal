import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JLabel;

	public class ColorPlate extends JLabel implements MouseMotionListener {
		int width = 100;
		int height = 100;
		Collection<Shape> shapes;
		DrawingCanvas canvas;
		
		public ColorPlate(DrawingCanvas canvas) {
			super("ColorPlate");
			shapes = new ArrayList<Shape>();
			this.setPreferredSize(new Dimension(width, height));
			this.canvas = canvas;
			//many color rect
			for(int i = 0; i < width; ++i) {
				float brightness = 1.0f;
				for(int j = 0; j < height; ++j) {
					int RGB = Color.HSBtoRGB((float)i/width, 1.0f, brightness);
					Color color = new Color(RGB);
					Shape shape = new Rectangle(i, j, 2, 2, Color.blue, color);
					shape.setStrokeWidth(0);
					this.shapes.add(shape);
					brightness = 1.0f - (float)j/height;
				}
			}
			this.addMouseMotionListener(this);
			this.setOpaque(false);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for(Shape shape : shapes) {
				shape.draw(g);
			}
		}

		public void findColor(int x, int y) {
			for(Shape shape : shapes) {
				//find shape
				if(shape.getX() == x && shape.getY() == y) {
					//System.out.println(shape.getFillColor().toString());
					this.canvas.setShapeColor(shape.getFillColor());
				    break;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			this.findColor(e.getX(), e.getY());
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}