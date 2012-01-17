import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class App extends JFrame implements ActionListener {
	
	Container cpane;
	DrawingCanvas canvas;
	
	JMenuBar menuBar;
	JMenu mainMenu;
	JMenuItem menuItem1;
	
	JButton btnCircle;
	JButton btnRect;
	
	JButton btnDraw;
	JButton btnSelect;
	
	JLabel lbDrawState;
	JLabel lbShapeState;
	
	JButton btnRenderPNG;
	
	public App(){
		super("God Draw App");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cpane = this.getContentPane();
		
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.GRAY);
		mainMenu = new JMenu("main");
		mainMenu.setBackground(Color.GRAY);
		menuItem1 = new JMenuItem("hi");
		mainMenu.add(menuItem1);
		menuBar.add(mainMenu);
		this.setJMenuBar(menuBar);
		
		JButton btn = new JButton("hi man");
		JToolBar toolBar = new JToolBar("tool");
		toolBar.add(btn);
		cpane.add(toolBar, BorderLayout.NORTH);
		
		canvas  = new DrawingCanvas();
		cpane.add(canvas, BorderLayout.CENTER);
		
		//button shape state
		Container shapeStatePane = new Container();
		shapeStatePane.setLayout(new GridLayout(0, 2));
		
		btnCircle = new JButton("circle");
		btnRect = new JButton("Rect");
		btnCircle.addActionListener(this);
		btnRect.addActionListener(this);
		
		shapeStatePane.add(btnCircle);
		shapeStatePane.add(btnRect);
		
		
		//button draw state
		Container drawStatePane = new Container();
		drawStatePane.setLayout(new GridLayout(0, 2));
		
		btnDraw = new JButton("draw");
		btnSelect = new JButton("select");
		btnDraw.addActionListener(this);
		btnSelect.addActionListener(this);
		
		drawStatePane.add(btnDraw);
		drawStatePane.add(btnSelect);
		
		//show current state
		Container statePane = new Container();
		statePane.setLayout(new GridLayout(0, 2));
		this.lbShapeState = new JLabel(this.canvas.getShapeState().toString(), SwingConstants.CENTER);
		this.lbDrawState = new JLabel(this.canvas.getDrawState().toString(), SwingConstants.CENTER);
		statePane.add(this.lbShapeState);
		statePane.add(this.lbDrawState);
		
		//button to render
		Container renderPane = new Container();
		renderPane.setLayout(new GridLayout(0,1));
		this.btnRenderPNG = new JButton("jpg");
		this.btnRenderPNG.addActionListener(this);
		renderPane.add(this.btnRenderPNG);
		
		//other components pane
		Container choicePane = new Container();
		choicePane.setLayout(new GridLayout(0,1));
		
		choicePane.add(shapeStatePane, 0);
		choicePane.add(drawStatePane, 1);
		choicePane.add(statePane, 2);
		choicePane.add(renderPane, 3);
		
		//
		cpane.add(choicePane, BorderLayout.SOUTH);
		
		
		//test 
		
		Circle circle = new Circle(100, 100, 100, Color.blue, Color.red);
		Rectangle rect = new Rectangle(500, 500, 50, 50, Color.red, Color.blue);
		canvas.addShape(circle);
		canvas.addShape(rect);
		
		//transform
		//rect.scale(0.5f);
		
		//end test
		
		
		this.setVisible(true);
		this.pack();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(
			new Runnable() {
				public void run() {
					App app = new App();
				}
			}
		);
	}
	
	public void updateState() {
		this.lbShapeState.setText(this.canvas.getShapeState().toString());
		this.lbDrawState.setText(this.canvas.getDrawState().toString());
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == this.btnCircle) {
			this.canvas.setShapeState(ShapeState.circle);
		} 
		else if(obj == this.btnRect) {
			this.canvas.setShapeState(ShapeState.rectangle);
		}
		else if(obj == this.btnDraw) {
			this.canvas.setDrawState(DrawState.draw);
		}
		else if(obj == this.btnSelect) {
			this.canvas.setDrawState(DrawState.select);
		}
		else if(obj == this.btnRenderPNG) {
			renderPNG();
		}
		this.updateState();
	}
	
	public void renderPNG() {
		try {
			JFileChooser chooser = new JFileChooser("~");
			int result = chooser.showDialog(this, JFileChooser.APPROVE_SELECTION);
			if(result != JFileChooser.APPROVE_OPTION) return;
			File file = new File(chooser.getSelectedFile().getAbsolutePath());
			RenderedImage output = this.canvas.renderPNG();
			ImageIO.write(output, "png", file);
		}
		catch (IOException ex) {
			
		}
	}

}
