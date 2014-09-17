// DisplayImage.java
// 
// -Illustrates how to:
//	* create a JFrame with a menubar
//	* define ActionListeners
//	
// by Dave Small
// class DisplayDrunkenWalk by Raymond Clark

import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.Random;
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

class DisplayDrunkenWalk {
	BufferedImage drunkenBufferedImage;
	Random rand;
	
	int bufferedImageWidth;
	int bufferedImageHeight;
	int positionX;
	int positionY;
	int stepsTaken;
	
	//=======================================================
	// constructor
	
	DisplayDrunkenWalk() {
		// --------------------------------------------------
		// setup the buffered image's width and height
		// setup pixel's position to the center of the buffered image
		
		bufferedImageWidth = bufferedImageHeight = 401;
		positionX = positionY = (401 / 2);
		
		drunkenBufferedImage = new BufferedImage(bufferedImageWidth,bufferedImageHeight,BufferedImage.TYPE_INT_ARGB);
		
		rand = new Random();
		
		//clear the buffered image 
		clearBufferedImage();
	}
	
	public void clearBufferedImage() {
		
		// clears the buffered image by setting the pixels to a cream color
		
		for(int x = 0; x < drunkenBufferedImage.getWidth(); x++) {
			for(int y = 0; y < drunkenBufferedImage.getHeight(); y++) {
				drunkenBufferedImage.setRGB(x, y, 0xFFFFFFEE);
			}
		}
	}
	
	int returnStepsTaken() {
		
		// setup the JOptionPane so user can input a value and return that value
		
		String stepsTakenString = JOptionPane.showInputDialog("Input the number of steps for the drunk to take");
		
		stepsTaken = Integer.parseInt(stepsTakenString);
		
		return stepsTaken;
	}

	public void mooreInfiniteWalk() {
		
		// infinite walk using moore neighborhood
		
		for(int i = 0; i < stepsTaken; i++) {
		positionX += 1 - rand.nextInt(3);
		positionY += 1 - rand.nextInt(3);
			if(positionX < bufferedImageWidth - 1 && positionX > 0) {
				if (positionY < bufferedImageHeight - 1 && positionY > 0) {
					
					// set pixel color to black to show path

					drunkenBufferedImage.setRGB(positionX,positionY,0xFF000000);
				}
			}
		}
		if(positionX < bufferedImageWidth - 1 && positionX > 0) {
			if (positionY < bufferedImageHeight - 1 && positionY > 0) {
				
				// set pixel color to red to show last step

				drunkenBufferedImage.setRGB(positionX,positionY,0xFFFF0000);
			}
		}
	}

	public void mooreBoundedWalk() {
		
		// bounded walk using moore neighborhood
		
		for(int i = 0; i < stepsTaken; i++) {
			positionX += 1 - rand.nextInt(3);
			positionY += 1 - rand.nextInt(3);
			
			if(positionX > bufferedImageWidth - 1) {
				positionX = bufferedImageWidth - 1;
			}
			if(positionY > bufferedImageHeight - 1) {
				positionY = bufferedImageHeight - 1;
			}
			if(positionX < 0) {
				positionX = 0;
			}
			if(positionY < 0) {
				positionY = 0;
			}
			// set pixel color to black to show path
			
			drunkenBufferedImage.setRGB(positionX,positionY,0xFF000000);
		}
		// set pixel color to red to show last step
		
		drunkenBufferedImage.setRGB(positionX, positionY, 0xFFFF0000);
	}

	public void mooreToroidalWalk() {	
		
		// toroid walk using moore neighborhood
		
		for (int i = 0; i < stepsTaken; i++) {
			positionX += 1 - rand.nextInt(3);
			positionY += 1 - rand.nextInt(3);
			
			if(positionX > bufferedImageWidth - 1) {
				positionX = 0;
			}
			if(positionX < 0) {
				positionX = bufferedImageWidth - 1;
			}
			if(positionY > bufferedImageHeight - 1) {
				positionY = 0;
			}
			if(positionY < 0) {
				positionY = bufferedImageHeight - 1;
			}
			// set pixel color to black to show path

			drunkenBufferedImage.setRGB(positionX,positionY,0xFF000000);
			
		}
		// set pixel color to red to show last step

		drunkenBufferedImage.setRGB(positionX, positionY, 0xFFFF0000);

	}
	
	public void vonInfiniteWalk() {
		
		// infinite walk using von Neumann neighborhood
		// utilizes same method as moore except for the path taken
		
		int moveAlongAxis = 0;
		for(int i = 0; i < stepsTaken; i++) {
			moveAlongAxis = rand.nextInt(2);
			if(moveAlongAxis == 0) {
				positionX += 1 - rand.nextInt(3);
				positionY += 0;
			}
			if(moveAlongAxis == 1) {
				positionY += 1 - rand.nextInt(3);
				positionX += 0;
			}
			if(positionX < bufferedImageWidth - 1 && positionX > 0) {
				if (positionY < bufferedImageHeight - 1 && positionY > 0) {
					drunkenBufferedImage.setRGB(positionX,positionY,0xFF000000);
				}
			}
		}
		if(positionX < bufferedImageWidth - 1 && positionX > 0) {
			if (positionY < bufferedImageHeight - 1 && positionY > 0) {
				drunkenBufferedImage.setRGB(positionX,positionY,0xFFFF0000);
			}
		}
	}
	
	public void vonBoundedWalk() {
		
		// bounded walk using von Neumann neighborhood
		
		int moveAlongAxis = 0;
		for(int i = 0; i < stepsTaken; i++) {
			moveAlongAxis = rand.nextInt(2);
			if(moveAlongAxis == 0) {
				positionX += 1 - rand.nextInt(3);
				positionY += 0;
			}
			if(moveAlongAxis == 1) {
				positionY += 1 - rand.nextInt(3);
				positionX += 0;
			}			
			if(positionX > bufferedImageWidth - 1) {
				positionX = bufferedImageWidth - 1;
			}
			if(positionY > bufferedImageHeight - 1) {
				positionY = bufferedImageHeight - 1;
			}
			if(positionX < 0) {
				positionX = 0;
			}
			if(positionY < 0) {
				positionY = 0;
			}
			drunkenBufferedImage.setRGB(positionX,positionY,0xFF000000);
		}
		drunkenBufferedImage.setRGB(positionX, positionY, 0xFFFF0000);
	}
	
	public void vonToroidalWalk() {	
		
		// toroidal walk using von Neumann neighborhood
		
		int moveAlongAxis = 0;
		for(int i = 0; i < stepsTaken; i++) {
			moveAlongAxis = rand.nextInt(2);
			if(moveAlongAxis == 0) {
				positionX += 1 - rand.nextInt(3);
				positionY += 0;
			}
			if(moveAlongAxis == 1) {
				positionY += 1 - rand.nextInt(3);
				positionX += 0;
			}			
			if(positionX > bufferedImageWidth - 1) {
				positionX = 0;
			}
			if(positionX < 0) {
				positionX = bufferedImageWidth - 1;
			}
			if(positionY > bufferedImageHeight - 1) {
				positionY = 0;
			}
			if(positionY < 0) {
				positionY = bufferedImageHeight - 1;
			}
			
			drunkenBufferedImage.setRGB(positionX,positionY,0xFF000000);
			
		}
		drunkenBufferedImage.setRGB(positionX, positionY, 0xFFFF0000);
	}
	
}

//############################################################################

class ImageFrame extends JFrame {
	
	//=======================================================
	// constructor
	
	public ImageFrame(int width, int height) {
		
		// --------------------------------------------------
		// setup the frame's attributes
		
		this.setTitle("CAP 3027 - HW01 - Raymond Clark");
		this.setSize(width, height);
		
		addMenu();
	}


private void addMenu() {
	//--------------------------------------------------------
	// setup the frame's menu bar
		
	// === File menu
	
	JMenu fileMenu = new JMenu("File");
	
	// moore menu
	
	JMenu mooreMenu = new JMenu("Moore");
	
	// von Neumann menu
	
	JMenu vonNeumannMenu = new JMenu("von Neumann");
	
	JMenuItem mooreInfinitePlaneItem = new JMenuItem("Drunken walk on an infinite plane");
	mooreInfinitePlaneItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			DisplayDrunkenWalk displayDrunkenWalk = new DisplayDrunkenWalk();
			displayDrunkenWalk.returnStepsTaken();
			//ADD INFINITE METHOD HERE
			displayDrunkenWalk.mooreInfiniteWalk();
			displayBufferedImage(displayDrunkenWalk.drunkenBufferedImage);
		}
	});
	
	JMenuItem mooreBoundedPlaneItem = new JMenuItem("Drunken walk on a bounded plane");
	mooreBoundedPlaneItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			DisplayDrunkenWalk displayDrunkenWalk = new DisplayDrunkenWalk();
			displayDrunkenWalk.returnStepsTaken();
			//ADD BOUNDED METHOD HERE
			displayDrunkenWalk.mooreBoundedWalk();
			displayBufferedImage(displayDrunkenWalk.drunkenBufferedImage);
		}
	});
	
	JMenuItem mooreToroidalPlaneItem = new JMenuItem("Drunken walk on a toroidal plane");
	mooreToroidalPlaneItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			DisplayDrunkenWalk displayDrunkenWalk = new DisplayDrunkenWalk();
			displayDrunkenWalk.returnStepsTaken();
			//ADD TOROIDAL METHOD HERE
			displayDrunkenWalk.mooreToroidalWalk();
			displayBufferedImage(displayDrunkenWalk.drunkenBufferedImage);
		}
	});
	
	JMenuItem vonInfinitePlaneItem = new JMenuItem("Drunken walk on an infinite plane");
	vonInfinitePlaneItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			DisplayDrunkenWalk displayDrunkenWalk = new DisplayDrunkenWalk();
			displayDrunkenWalk.returnStepsTaken();
			//ADD INFINITE METHOD HERE
			displayDrunkenWalk.vonInfiniteWalk();
			displayBufferedImage(displayDrunkenWalk.drunkenBufferedImage);
		}
	});
	
	JMenuItem vonBoundedPlaneItem = new JMenuItem("Drunken walk on a bounded plane");
	vonBoundedPlaneItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			DisplayDrunkenWalk displayDrunkenWalk = new DisplayDrunkenWalk();
			displayDrunkenWalk.returnStepsTaken();
			//ADD BOUNDED METHOD HERE
			displayDrunkenWalk.vonBoundedWalk();
			displayBufferedImage(displayDrunkenWalk.drunkenBufferedImage);
		}
	});
	
	JMenuItem vonToroidalPlaneItem = new JMenuItem("Drunken walk on a toroidal plane");
	vonToroidalPlaneItem.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			DisplayDrunkenWalk displayDrunkenWalk = new DisplayDrunkenWalk();
			displayDrunkenWalk.returnStepsTaken();
			//ADD TOROIDAL METHOD HERE
			displayDrunkenWalk.vonToroidalWalk();
			displayBufferedImage(displayDrunkenWalk.drunkenBufferedImage);
		}
	});
	
	// add options to menu items
	
	mooreMenu.add(mooreInfinitePlaneItem);
	mooreMenu.add(mooreBoundedPlaneItem);
	mooreMenu.add(mooreToroidalPlaneItem);
	vonNeumannMenu.add(vonInfinitePlaneItem);
	vonNeumannMenu.add(vonBoundedPlaneItem);
	vonNeumannMenu.add(vonToroidalPlaneItem);
	
	JMenuItem exitItem = new JMenuItem("Exit");
	exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		fileMenu.add(exitItem);
		
		// === attach menus to a menu bar
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(mooreMenu);
		menuBar.add(vonNeumannMenu);
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
