// DisplayImage.java
// 
// -Illustrates how to:
//	* create a JFrame with a menubar
//	* define ActionListeners
//	* define a color gradient using interpolation between color extremes
//	
// by Dave Small
// class DisplayGradient by Raymond Clark


import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.awt.image.BufferedImage;


public class DisplayImage {
	private static final int WIDTH = 400;
	private static final int HEIGHT = 400;
	
	public static void main(String[] args) {
		JFrame frame = new ImageFrame(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

//############################################################################

class DisplayGradient {
	BufferedImage gradientBufferedImage; //declare the buffered image
	int heightInput; // user value for height of the image
	int red = 0xFFFF0000; // red
	int blue = 0xFF0000FF; // blue
	int black = 0xFF000000; // black
	int green = 0xFF00FF00; // green
	int colorMask = 0x000000FF; // used to mask values as shown in class
	int alphaConstant = 0xFF; // alpha channel remains constant throughout interpolation
	int aVal, bVal; // initial and final color for interpolation
	float alphaInterval, redInterval, greenInterval, blueInterval; // steps to be used between color values (interpolation)
	int a, r, g, b; // initial colors that intervals get added to ==> transitions to final color
	float aFloat, rFloat, gFloat, bFloat; // float values of a,r,g,b to avoid roundoff error
	int colorToFill; // synthesized color after a,r,g,b are generated
	String p = "Enter the height";
	
	// constructor
	
	public DisplayGradient() {
		heightInput = 1; // initial value for the height
		getHeight(p); //retrieve the height of the buffered image
		gradientBufferedImage = new BufferedImage(heightInput, heightInput, BufferedImage.TYPE_INT_ARGB); //initialize the image

	}
	
	// returns user input for image dimensions
	
	int getHeight(String a) {
		String heightString = JOptionPane.showInputDialog(a); // shows a pane for input
		if(heightString == null) {
			System.exit(0);
		}
		// exception handling if user enters anything but an integer
		try {
		heightInput = Integer.parseInt(heightString);  // parse the string for the integer
		}
		catch(Exception e) {
			String x = e.toString();
			System.out.println(x);
			getHeight("Warning: Input has to be an Integer!");
		}
		
		return heightInput;
	}
	
	// performs the interpolation of colors along left edge,
	// right edge, and in between edges
	
	void gradientMethod(int height) {	
		// extract colors and interpolate between initial and final colors
		//--- interpolate 3 color channels
		
		a = alphaConstant;
		
		// interpolate between blue and green along the left edge
		
		// red channel
		
		aVal = (blue >>> 16) & colorMask;
	    bVal = (green >>> 16) & colorMask;
	    redInterval = (float)(bVal - aVal) / height;
		
	    // green channel
	    
		aVal = (blue >>> 8) & colorMask;
		bVal = (green >>> 8) & colorMask;
		greenInterval = (float)(bVal - aVal) / height;
		
		// blue channel
		
		aVal = (blue) & colorMask;
		bVal = (green) & colorMask;
		blueInterval = (float)(bVal - aVal) / height;
		
		// extract and set initial color for each color channel (blue in this case)
		
		r = (blue >>> 16) & colorMask;
		g = (blue >>> 8) & colorMask;
		b = (blue) & colorMask;
		
		// convert r, g, b to floats to avoid roundoff error
		
		aFloat = (float) a;
		rFloat = (float) r;
		gFloat = (float) g;
		bFloat = (float) b;
		
		// loop through height and add r,g,b steps after each iteration
		
		for (int i = 0; i < height; i++) {
			rFloat += redInterval;
			gFloat += greenInterval;
			bFloat += blueInterval;
			
			// convert the float values back to int
			
			floatToInt();
			
			// synthesize the channels into one intARGB
			
			synthesizeColors();				
			
			// display the color gradient along the left edge

			setRGB(0, i);
			
		}
		
		// repeat the process, but along the right edge using red as initial and black as final colors
		
		aVal = (red >>> 16) & colorMask;
		bVal = (black >>> 16) & colorMask;
		redInterval = (float)(bVal - aVal) / height;
		
		aVal = (red >>> 8) & colorMask;
		bVal = (black >>> 8) & colorMask;
		greenInterval = (float)(bVal - aVal) / height;
		
		aVal = (red) & colorMask;
		bVal = (black) & colorMask;
		blueInterval = (float)(bVal - aVal) / height;
		
		r = (red >>> 16) & colorMask;
		g = (red >>> 8) & colorMask;
		b = (red) & colorMask;
		
		aFloat = (float) a;
		rFloat = (float) r;
		gFloat = (float) g;
		bFloat = (float) b;
		
		for (int i = 0; i < height; i++) {
			rFloat += redInterval;
			gFloat += greenInterval;
			bFloat += blueInterval;
			
			floatToInt();
			
			synthesizeColors();				
			
			// display gradient along right edge
			
			setRGB(height - 1, i);
		}
		
		// repeat the process, but for the colors in between the left and right edges
		
		for (int i = 0; i < height; i++) {
			
			// retrieve left edge and right edge colors
			
			int colorLeft = gradientBufferedImage.getRGB(0, i);
			int colorRight = gradientBufferedImage.getRGB(height - 1, i);
			
			aVal = (colorLeft >>> 16) & colorMask;
			bVal = (colorRight >>> 16) & colorMask;
			redInterval = (float)(bVal - aVal) / height;
			
			aVal = (colorLeft >>> 8) & colorMask;
			bVal = (colorRight >>> 8) & colorMask;
			greenInterval = (float)(bVal - aVal) / height;
			
			aVal = (colorLeft) & colorMask;
			bVal = (colorRight) & colorMask;
			blueInterval = (float)(bVal - aVal) / height;
			
			r = (colorLeft >>> 16) & colorMask;
			g = (colorLeft >>> 8) & colorMask;
			b = (colorLeft) & colorMask;
			
			aFloat = (float) a;
			rFloat = (float) r;
			gFloat = (float) g;
			bFloat = (float) b;
			
			for(int j = 0; j < height; j++) {
				rFloat += redInterval;
				gFloat += greenInterval;
				bFloat += blueInterval;
				
				floatToInt();
				
				synthesizeColors();		
				
				// fill colors in between left and right edges
				
				setRGB(j,i);
			}
		}
	}
	
	// converts float values to int values to be used for synthesis
	
	void floatToInt() {
		r = (int) rFloat;
		g = (int) gFloat;
		b = (int) bFloat;
	}
	
	// synthesizes the channels into an intARGB
	
	void synthesizeColors() {
		colorToFill = (a << 24) | (r << 16) | (g << 8) | (b);
	}
	
	// sets RGB values for bufferedImage
	
	void setRGB(int num1, int num2) {
		
		gradientBufferedImage.setRGB(num1, num2, colorToFill);

	}
}

//############################################################################

class ImageFrame extends JFrame {
	private BufferedImage image = null;
	
	public ImageFrame(int width, int height) {
		
		this.setTitle("CAP 3027 2014 - HW02 - Raymond Clark");
		this.setSize(width, height);
		
		addMenu();
		
	}


private void addMenu() {
	JMenu fileMenu = new JMenu("File");
	
	// bilinear gradient menu item + method to display buffered image
	
	JMenuItem bilinearItem = new JMenuItem("Bilinear Gradient");
	bilinearItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
			
			// new instance of DisplayGradient class
			
			DisplayGradient displayGradient = new DisplayGradient();
			
			// call method that interpolates between and displays colors
			
			displayGradient.gradientMethod(displayGradient.heightInput);
			
			// actually display the colors on the buffered image
			
			displayBufferedImage(displayGradient.gradientBufferedImage);
		}
	});
	
	// add bilinear item to the menu
	
	fileMenu.add(bilinearItem);
	
	JMenuItem exitItem = new JMenuItem("Exit");
	exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		fileMenu.add(exitItem);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
}


//--------------------------------------------------------------
//Display BufferedImage

	private void displayBufferedImage(BufferedImage image) {
		
		// There are many ways to display a BufferedImage; for now we'll...

		this.setContentPane(new JScrollPane(new JLabel(new ImageIcon(image))));
		
		// one problem with this technique is that if this method is
		// called more than once, it does NOT reuse the existing
		// JScrollPane, JLabel, or ImageIcon.
		
		// JFrames are a type of container.  Anytime a container's
		// subcomponents are modified (added to or removed from the
		// container, or layout-related information changed) after the
		// container has been displayed, one should call the validate() 
		// method--which causes the container to lay out its subcomponents 
		// again.
		
		this.validate();
	}
}