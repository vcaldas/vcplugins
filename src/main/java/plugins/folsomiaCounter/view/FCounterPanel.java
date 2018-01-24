package com.victorcaldas.plugins.folsomiaCounter.view;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.victorcaldas.plugins.folsomiaCounter.controller.FCounterController;
import com.victorcaldas.plugins.folsomiaCounter.model.Whatsit;
import com.victorcaldas.plugins.folsomiaCounter.model.Widget;

public class FCounterPanel extends Panel {
	
	private FCounterController baseController;
	
	private JLabel nameLabel;
	private JLabel descriptionLabel;
	private JLabel weightLabel;
	private JLabel sizeLabel;
	
	private JTextField nameField;
	private JTextField descriptionField;
	private JTextField weightField;
	private JTextField sizeField;
	
	private JButton createWhatsitButton;
	private JButton createWidgetButton;
	
	private SpringLayout baseLayout;
	
	
	
	public FCounterPanel(FCounterController baseController) {
		
		this.baseController = baseController;
		
		createWhatsitButton = new JButton("Create a Whatsit");
		createWidgetButton = new JButton("Create a Widget");
		
		nameLabel = new JLabel("Type the name of the Whatsit");
		descriptionLabel = new JLabel("Descrivve the widget");
		weightLabel = new JLabel("Enter the weight for the Widget");
		sizeLabel = new JLabel("Enter the size of the Whatsit");
		
		nameField = new JTextField(15);
		descriptionField = new JTextField(15);
		weightField = new JTextField(15);
		sizeField =  new JTextField(15);
		
		baseLayout = new SpringLayout();
		
		
		
		setupPanel();
		setupLayout();
		setupListeners();
		
		
		}



	


	private void setupLayout() {
		baseLayout.putConstraint(SpringLayout.NORTH, createWidgetButton, 0, SpringLayout.NORTH, createWhatsitButton);
		baseLayout.putConstraint(SpringLayout.WEST, createWidgetButton, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, createWhatsitButton, -10, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, createWhatsitButton, -10, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, sizeField, 31, SpringLayout.SOUTH, nameField);
		baseLayout.putConstraint(SpringLayout.NORTH, sizeLabel, 5, SpringLayout.NORTH, sizeField);
		baseLayout.putConstraint(SpringLayout.WEST, sizeLabel, 0, SpringLayout.WEST, descriptionLabel);
		baseLayout.putConstraint(SpringLayout.EAST, weightField, -46, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.EAST, nameField, -46, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, descriptionField, -5, SpringLayout.NORTH, descriptionLabel);
		baseLayout.putConstraint(SpringLayout.EAST, descriptionField, 0, SpringLayout.EAST, nameField);
		baseLayout.putConstraint(SpringLayout.NORTH, weightField, -5, SpringLayout.NORTH, weightLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, weightLabel, 46, SpringLayout.SOUTH, descriptionLabel);
		baseLayout.putConstraint(SpringLayout.EAST, sizeField, -46, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, nameField, -5, SpringLayout.NORTH, nameLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, nameLabel, 59, SpringLayout.SOUTH, weightLabel);
		baseLayout.putConstraint(SpringLayout.WEST, nameLabel, 0, SpringLayout.WEST, descriptionLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, 79, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, weightLabel, 0, SpringLayout.WEST, descriptionLabel);
		baseLayout.putConstraint(SpringLayout.EAST, descriptionLabel, 0, SpringLayout.EAST, createWidgetButton);
		
	}



	private void setupPanel() {
		this.setSize(500,500);
		this.setLayout(baseLayout);
		
		this.add(createWhatsitButton);
		this.add(createWidgetButton);
		
		this.add(descriptionField);
		this.add(nameField);
		this.add(weightField);
		this.add(sizeField);
		
		this.add(descriptionLabel);
		this.add(nameLabel);
		this.add(sizeLabel);
		this.add(weightLabel);
		
	}
	
	private boolean checkInteger(String currentInput) {
		boolean isInteger = false;
		
		try {
			Integer.parseInt(currentInput);
			isInteger = true;
		}
		catch(NumberFormatException currentException) {
			JOptionPane.showMessageDialog(this, "Please type an integer in the size field");
		}
		return isInteger;
	}
	
	private boolean checkDouble(String currentInput) {
		boolean isDouble = false;
		
		try {
			Double.parseDouble(currentInput);
			isDouble = true;
		}
		catch(NumberFormatException currentException) {
			JOptionPane.showMessageDialog(this, "Please type an double in the size field");
		}
		return isDouble;
	}
	
	
	private void setupListeners() {
		
		createWidgetButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent click) {
				String description = descriptionField.getText();
				double currentWeight = 0.0;
				
				if(checkDouble(sizeField.getText())) {
					currentWeight = Double.parseDouble(weightField.getText());
					
					if(baseController.getAppFactory().makeWidget(currentWeight, description)) {
						JOptionPane.showMessageDialog(baseController.getAppFrame(),  "Congrats, you made a widget!");
					}
					
					else {
						JOptionPane.showMessageDialog(baseController.getAppFrame(),  "Sorry, not enough resources to make a widget!");
												
					}
//					Widget tempWidget = new Widget(currentWeight, description);
//					baseController.getAppFactory().getWidgetList().add(tempWidget);
				}
				
				
			}
			
		});
		
		createWhatsitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent click) {
				String name = nameField.getText();
				int size = 0;
				
				if(checkInteger(sizeField.getText())) {
					size = Integer.parseInt(sizeField.getText());
					
					if(baseController.getAppFactory().makeWhatsit(size, name)) {
						JOptionPane.showMessageDialog(baseController.getAppFrame(),  "Congrats, you made a whatsit!");
					}
					
					else {
						JOptionPane.showMessageDialog(baseController.getAppFrame(),  "Sorry, not enough resources to make a whatsit!");
												
					}
					
					
//					baseController.getAppFactory().getWhatsitList().add(new Whatsit(size, name));
					
				}
				
				
				
			}
			
		});
		
	}

	
	
	
	

}
