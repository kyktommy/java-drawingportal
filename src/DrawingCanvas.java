import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JColorChooser;
import javax.swing.JLabel;

public class DrawingCanvas extends JLabel implements MouseListener,
		MouseMotionListener {

	int width;
	int height;
	public Collection<Shape> shapes;
	Shape drawingShape;
	Shape ruler;
	Shape rulerText;
	RectSelection selectionShape;
	ShapeState currentShapeState;
	DrawState currentDrawState;
	Shape latestFocusShape;
	
	//ShapePopupMenu shapePopupMenu;

	public DrawingCanvas() {
		super();
		
		this.width = 1000;
		this.height = 1000;
		this.shapes = new ArrayList<Shape>();
		//this.shapePopupMenu = new ShapePopupMenu(this);

		this.currentShapeState = ShapeState.circle;
		this.currentDrawState = DrawState.draw;

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.setPreferredSize(new Dimension(width, height));
	}
	
	public RenderedImage renderPNG() {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		draw(g2d);
		g2d.dispose();
		return bufferedImage;
	}

	public void addShape(Shape shape) {
		shapes.add(shape);
	}

	public void removeShape(Shape shape) {
		shapes.remove(shape);
	}

	public void initShape() {
		// clear useless shape
		drawingShape = null;
		ruler = null;
		rulerText = null;
	}
	
	public void draw(Graphics g) {
		for (Shape shape : shapes) {
			shape.draw(g);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.draw(g);
		// drawing on top
		// draw when it is dragging
		if (ruler != null)
			ruler.draw(g);
		if (rulerText != null)
			rulerText.draw(g);
		if (this.selectionShape != null) {
			this.selectionShape.draw(g);
		}
		// repaint this
		this.repaint();
	}

	public void selectShape(Shape shape) {
		if (!shape.isSelected()) {
			shape.setSelected(true);
		}
		// else {
		// shape.setSelected(false);
		// }
	}

	public void deselectAll() {
		for (Shape shape : shapes) {
			shape.setSelected(false);
		}
	}

	public void setShapeState(ShapeState shapeState) {
		this.currentShapeState = shapeState;
	}

	public ShapeState getShapeState() {
		return this.currentShapeState;
	}

	public void setDrawState(DrawState drawState) {
		this.currentDrawState = drawState;
	}

	public DrawState getDrawState() {
		return this.currentDrawState;
	}

	public void setShapeColor(Color fillColor) {
		for (Shape shape : this.shapes) {
			if (shape.isSelected()) {
				shape.fillColor = fillColor;
			}
		}
	}

	public Shape findShape(Point pt) {
		// find the shapes are in bound of the Point(s)
		Shape result = null;
		for (Shape shape : shapes) {

			int x = shape.x;
			int y = shape.y;

			if (shape instanceof Circle) {
				int r = ((Circle) shape).r;
				//System.out.println("x: " + x + "y: " + y + "r " + r);

				if (!(pt.x > x + r || // right
					pt.y < y - r || // top
					pt.x < x - r || // left
					pt.y > y + r)) { // bottom

					result = shape;
				}
			} else if (shape instanceof Rectangle) {
				int height = ((Rectangle) shape).height;
				int width = ((Rectangle) shape).width;

				if (pt.x > x + width ||
					pt.y < y || 
					pt.x < x || 
					pt.y > y + height) {
					continue;
				} else {
					result = shape;
				}
			}
		}
		return result;
	}

	public Collection<Shape> findShapes(Point fromOld, Point toOld) {
		//swap point
		Point from = new Point(Math.min(fromOld.x, toOld.x), Math.min(fromOld.y, toOld.y));
		Point to = new Point(Math.max(fromOld.x, toOld.x), Math.max(fromOld.y, toOld.y));

		this.deselectAll();
		
		// find the shapes are in bound of the Point(s)
		Collection<Shape> shapesInBound = new ArrayList<Shape>();
		for (Shape shape : shapes) {
			if(shape.existInArea(from.x, from.y, to.x, to.y)) {
				this.selectShape(shape);
				shapesInBound.add(shape);
			}
		}
		return shapesInBound;
	}

	// press
	int beginX = 0;
	int beginY = 0;
	// release
	int endX = 0;
	int endY = 0;
	// drag
	int dragX = 0;
	int dragY = 0;

	// press to move
	Point from;
	Point previous;

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int ex = e.getX();
		int ey = e.getY();
		beginX = ex;
		beginY = ey;
		
		//if left click
		if (e.getButton() == MouseEvent.BUTTON1) {
			//consider the draw state
			if (this.currentDrawState == DrawState.draw) {
				
				//create shape
				if(this.currentShapeState == ShapeState.circle) {
					drawingShape = new Circle(ex, ey, 0, Color.red, Color.blue);
				} else if(this.currentShapeState == ShapeState.rectangle) {
					drawingShape = new Rectangle(ex, ey, 0, 0, Color.red, Color.blue);
				}
				
				//add new shape
				this.drawingShape.pressed(ex, ey);
				this.shapes.add(drawingShape);
				
			} else if (this.currentDrawState == DrawState.select) {
				
				this.selectionShape = new RectSelection(ex, ey, 0, 0, null, null);
				this.selectionShape.pressed(ex, ey);
				
				// find shape and highlight it
//				Shape shape = this.findShape(new Point(ex, ey));
//				if (shape != null) {
//					// focus shape for move or something
//					this.latestFocusShape = shape;
//					this.selectShape(shape);
//
//					// focus & going to move
//					if (shape.isSelected()) {
//						from = new Point(ex, ey);
//						this.currentDrawState = DrawState.focus;
//					}
//
//					// System.out.println(shape);
//					// System.out.println("ex: " + ex + " ey: " + ey);
//				} else {
//					// if select nothing, deselect all
//					this.deselectAll();
//				}
			} 
		} 
		
		//right click, color plate
		if(e.isPopupTrigger()) {
			//this.shapePopupMenu.show(e.getComponent(), ex, ey);
			JColorChooser colorChooser = new JColorChooser();
			Color colorchose = colorChooser.showDialog(this, "choose Color", Color.white);
			this.changeShapeColor(colorchose);
		}
	}
	
	public void changeShapeColor(Color colorchose) {
		for(Shape shape : shapes) {
			if(shape.isSelected()) {
				shape.fillColor = colorchose;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		endX = e.getX();
		endY = e.getY();
		
		if (this.currentDrawState == DrawState.draw) {

			if (currentShapeState == ShapeState.circle) {	

			} else if (currentShapeState == ShapeState.rectangle) {
				
			}

			// init
			this.initXY();
			this.initShape();
		}
		else if (this.currentDrawState == DrawState.select) {
			this.selectionShape = null;
		}
		
		else if (this.currentDrawState == DrawState.focus) {
			from = null;
			previous = null;
			this.currentDrawState = DrawState.select;
			this.setCursor(Cursor.getDefaultCursor());
		}
		
	}

	public void initXY() {
		beginX = 0;
		beginY = 0;
		dragX = 0;
		dragY = 0;
		endX = 0;
		endY = 0;
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		int ex = e.getX();
		int ey = e.getY();

		if (this.currentDrawState == DrawState.draw) {

			Color fillColor = Color.blue;
			Color strokeColor = Color.red;

			if (currentShapeState == ShapeState.circle) {
				endX = e.getX();
				endY = e.getY();
				this.drawingShape.dragged(ex, ey);
				
				ruler = new Line(beginX, beginY, endX, endY, Color.black);
//				int fontX = beginX + r / 2;
//				int fontY = beginY + r / 2;
//				rulerText = new FontShape(fontX, fontY, "test", 24,
//						strokeColor, fillColor);

			} else if (currentShapeState == ShapeState.rectangle) {
				dragX = e.getX();
				dragY = e.getY();
				
				this.drawingShape.dragged(ex, ey);
				// TODO:
				// ruler = new Line(beginX, beginY, endX, endY, Color.black);
			}
			// System.out.println("drag " + endX + " " + endY);
		} 
		else if (this.currentDrawState == DrawState.select) {
			Collection<Shape> shapesInBound = this.findShapes(new Point(ex, ey), new Point(beginX, beginY));
			for(Shape shape : shapesInBound) {
				this.selectShape(shape);
			}
			this.selectionShape.dragged(ex, ey);
		}
		else if (this.currentDrawState == DrawState.focus) {
			// move all selected
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			if (previous == null) {
				previous = from;
			}
			for (Shape shape : this.shapes) {
				if (shape.isSelected()) {
					Point to = new Point(ex, ey);
					shape.move(previous, to);
				}
			}
			previous.setLocation(ex, ey);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// detect exist shape

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
