package com.victorcaldas.plugins.folsomiaCounter.view;

import javax.swing.JFrame;

import com.victorcaldas.plugins.folsomiaCounter.controller.FCounterController;

/**
 * @author Victor Caldas
 *
 */
public class FCounterFrame extends JFrame {
	
	private FCounterPanel basePanel;
	
	/**
	 * Create a FCounterFrame object passing a reference to the FCounterController for use by the FCounterFrame object
	 * @param baseController The reference to the FCounterController object for MVC
	 */
	public FCounterFrame(FCounterController baseController) {
	
		basePanel = new FCounterPanel(baseController);
		setupFrame();
	}
	
	/**
	 * Sets the content pane, size and makes the frame visible
	 */
	private void setupFrame() {
		this.setContentPane(basePanel);
		this.setSize(500,500);
		this.setVisible(true);
	
		
	}

}
