package funAnalysis;


	/*
	 * SimpleTableDemo.java requires no other files.
	 */
	 
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.JScrollPane;
	import javax.swing.JTable;
	import java.awt.Dimension;
	import java.awt.GridLayout;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
import java.util.Random;

import ij.plugin.filter.MaximumFinder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.imagej.ImageJ;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.SpringLayout;
	 
import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.measure.ResultsTable;

import java.awt.*;
import ij.plugin.*;
import ij.plugin.filter.Analyzer;

	public class FunAnalysisPlugin extends JPanel implements ItemListener{
	    /**
		 * 
		 */
		private boolean AUTOSAVE = false;
		private boolean AUTOLOAD = false;
	    private boolean ALLOW_COLUMN_SELECTION = false;
	    private boolean ALLOW_ROW_SELECTION = true;
		private static final long serialVersionUID = 1L;
		private boolean DEBUG = false;
		private JTextField textFieldNSpores;
		private JTextField textField_Lenght;
	    private MyDataTable dataTable = new MyDataTable();
	    private JTextField textImagePath;
	    private int  selectedCol;
	    private ListSelectionModel lsm;
	    private JTextField textFieldAutoSpores;
	    private JCheckBox chckbxAutoSave;
	    private JCheckBox chckbxAutoLoad;
	    private JTable table;
	    public ImagePlus imp;
	    public ResultsTable currentTable;
	    
		public FunAnalysisPlugin() {
	        super(new GridLayout(1,0));
	 
	        
	        
	 
	        table = dataTable.getTable();
	        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	        table.setFillsViewportHeight(true);
	        
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        if (ALLOW_ROW_SELECTION) { // true by default
	            ListSelectionModel rowSM = table.getSelectionModel();
	            rowSM.addListSelectionListener(new ListSelectionListener() {
	                public void valueChanged(ListSelectionEvent e) {
	                    //Ignore extra messages.
	                    if (e.getValueIsAdjusting()) return;
	 
	                    lsm = (ListSelectionModel)e.getSource();
	                    if (lsm.isSelectionEmpty()) {
	                        System.out.println("No rows are selected.");
	                    } else {
	                        updateSelection();
	                    }
	                }
	            });
	        } else {
	            table.setRowSelectionAllowed(false);
	        }
	 
	        if (ALLOW_COLUMN_SELECTION) { // false by default
	            if (ALLOW_ROW_SELECTION) {
	                //We allow both row and column selection, which
	                //implies that we *really* want to allow individual
	                //cell selection.
	                table.setCellSelectionEnabled(true);
	            }
	            table.setColumnSelectionAllowed(true);
	            ListSelectionModel colSM =
	                table.getColumnModel().getSelectionModel();
	            colSM.addListSelectionListener(new ListSelectionListener() {
	                public void valueChanged(ListSelectionEvent e) {
	                    //Ignore extra messages.
	                    if (e.getValueIsAdjusting()) return;
	 
	                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	                    if (lsm.isSelectionEmpty()) {
	                        System.out.println("No columns are selected.");
	                    } else {
	                        selectedCol = lsm.getMinSelectionIndex();
	                        textImagePath.setText(dataTable.getPath(selectedCol));
	                        System.out.println("Column " + selectedCol
	                                           + " is now selected.");
	                    }
	                }
	            });
	        }
	 
	        if (DEBUG) {
	            table.addMouseListener(new MouseAdapter() {
	                public void mouseClicked(MouseEvent e) {
	                    printDebugData(table);
	                }
	            });
	        }
	 
	        if (DEBUG) {
	            table.addMouseListener(new MouseAdapter() {
	                public void mouseClicked(MouseEvent e) {
	                    printDebugData(table);
	                }
	            });
	        }
	 
	        //Create the scroll pane and add the table to it.
	        JScrollPane scrollPane = new JScrollPane(table);
	 
	        //Add the scroll pane to this panel.
	        add(scrollPane);
	        
	        JPanel panel = new JPanel();
	        add(panel);
	        SpringLayout sl_panel = new SpringLayout();
	        panel.setLayout(sl_panel);
	        
	        JLabel lblCurrentImage = new JLabel("Current image:");
	        sl_panel.putConstraint(SpringLayout.NORTH, lblCurrentImage, 6, SpringLayout.NORTH, panel);
	        sl_panel.putConstraint(SpringLayout.WEST, lblCurrentImage, 6, SpringLayout.WEST, panel);
	        sl_panel.putConstraint(SpringLayout.EAST, lblCurrentImage, 107, SpringLayout.WEST, panel);
	        panel.add(lblCurrentImage);
	        
	        textImagePath = new JTextField();
	        sl_panel.putConstraint(SpringLayout.NORTH, textImagePath, 28, SpringLayout.NORTH, panel);
	        sl_panel.putConstraint(SpringLayout.WEST, textImagePath, 6, SpringLayout.WEST, panel);
	        sl_panel.putConstraint(SpringLayout.EAST, textImagePath, 295, SpringLayout.WEST, panel);
	        panel.add(textImagePath);
	        textImagePath.setColumns(15);
	        
	        JLabel lblNspores = new JLabel("NSpores");
	        sl_panel.putConstraint(SpringLayout.NORTH, lblNspores, 65, SpringLayout.NORTH, panel);
	        sl_panel.putConstraint(SpringLayout.WEST, lblNspores, 6, SpringLayout.WEST, panel);
	        sl_panel.putConstraint(SpringLayout.EAST, lblNspores, 67, SpringLayout.WEST, panel);
	        panel.add(lblNspores);
	        
	        textFieldNSpores = new JTextField();
	        sl_panel.putConstraint(SpringLayout.NORTH, textFieldNSpores, 6, SpringLayout.SOUTH, textImagePath);
	        sl_panel.putConstraint(SpringLayout.WEST, textFieldNSpores, 6, SpringLayout.EAST, lblNspores);
	        panel.add(textFieldNSpores);
	        textFieldNSpores.setColumns(10);
	        
	        JLabel lblLength = new JLabel("Length");
	        sl_panel.putConstraint(SpringLayout.WEST, lblLength, 0, SpringLayout.WEST, lblCurrentImage);
	        sl_panel.putConstraint(SpringLayout.EAST, lblLength, 0, SpringLayout.EAST, lblNspores);
	        panel.add(lblLength);
	        
	        textField_Lenght = new JTextField();
	        sl_panel.putConstraint(SpringLayout.WEST, textField_Lenght, 6, SpringLayout.EAST, lblLength);
	        sl_panel.putConstraint(SpringLayout.EAST, textField_Lenght, -153, SpringLayout.EAST, panel);
	        sl_panel.putConstraint(SpringLayout.NORTH, lblLength, 5, SpringLayout.NORTH, textField_Lenght);
	        sl_panel.putConstraint(SpringLayout.NORTH, textField_Lenght, 6, SpringLayout.SOUTH, textFieldNSpores);
	        sl_panel.putConstraint(SpringLayout.EAST, textFieldNSpores, 0, SpringLayout.EAST, textField_Lenght);
	        panel.add(textField_Lenght);
	        textField_Lenght.setColumns(10);
	        
	        JButton button = new JButton(">>");
	        button.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		changeLine("+");
	        	}
	        });
	        sl_panel.putConstraint(SpringLayout.WEST, button, 133, SpringLayout.WEST, panel);
	        panel.add(button);
	        
	        JButton button_1 = new JButton("<<");
	        button_1.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		changeLine("-");
	        	}
	        });
	        sl_panel.putConstraint(SpringLayout.WEST, button_1, 10, SpringLayout.WEST, panel);
	        sl_panel.putConstraint(SpringLayout.EAST, button_1, -6, SpringLayout.WEST, button);
	        sl_panel.putConstraint(SpringLayout.NORTH, button, 0, SpringLayout.NORTH, button_1);
	        panel.add(button_1);
	        
	        chckbxAutoLoad = new JCheckBox("Auto load");
	        sl_panel.putConstraint(SpringLayout.NORTH, chckbxAutoLoad, 18, SpringLayout.SOUTH, button);
	        sl_panel.putConstraint(SpringLayout.WEST, chckbxAutoLoad, 20, SpringLayout.WEST, panel);
	        sl_panel.putConstraint(SpringLayout.EAST, chckbxAutoLoad, 0, SpringLayout.EAST, textFieldNSpores);
	        panel.add(chckbxAutoLoad);
	        
	        JButton btnLoad = new JButton("Load Image");
	        btnLoad.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		openImage();
	        	}
	        });
	        sl_panel.putConstraint(SpringLayout.NORTH, btnLoad, -1, SpringLayout.NORTH, chckbxAutoLoad);
	        sl_panel.putConstraint(SpringLayout.WEST, btnLoad, 26, SpringLayout.EAST, chckbxAutoLoad);
	        sl_panel.putConstraint(SpringLayout.EAST, btnLoad, -10, SpringLayout.EAST, panel);
	        panel.add(btnLoad);
	        
	        JButton btnClose = new JButton("Close");
	        sl_panel.putConstraint(SpringLayout.NORTH, btnClose, 397, SpringLayout.NORTH, panel);
	        sl_panel.putConstraint(SpringLayout.WEST, btnClose, 178, SpringLayout.WEST, panel);
	        sl_panel.putConstraint(SpringLayout.EAST, btnClose, 295, SpringLayout.WEST, panel);
	        panel.add(btnClose);
	        
	        JButton btn_MeasureBoth = new JButton("Calculate Both");
	        btn_MeasureBoth.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		CalculateBoth();
	        	}
	        });
	        sl_panel.putConstraint(SpringLayout.NORTH, button_1, 1, SpringLayout.SOUTH, btn_MeasureBoth);
	        sl_panel.putConstraint(SpringLayout.WEST, btn_MeasureBoth, 98, SpringLayout.WEST, panel);
	        sl_panel.putConstraint(SpringLayout.EAST, btn_MeasureBoth, -10, SpringLayout.EAST, panel);
	        panel.add(btn_MeasureBoth);
	        
	        JButton btnSaveTable = new JButton("Save Table");
	        sl_panel.putConstraint(SpringLayout.WEST, btnSaveTable, 0, SpringLayout.WEST, btnLoad);
	        sl_panel.putConstraint(SpringLayout.EAST, btnSaveTable, 291, SpringLayout.WEST, panel);
	        btnSaveTable.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		try {
						dataTable.saveTable();
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
	        	}
	        });
	        panel.add(btnSaveTable);
	        
	        JLabel lblSpores = new JLabel("Spores");
	        sl_panel.putConstraint(SpringLayout.WEST, lblSpores, 0, SpringLayout.WEST, lblCurrentImage);
	        panel.add(lblSpores);
	        
	        textFieldAutoSpores = new JTextField();
	        sl_panel.putConstraint(SpringLayout.NORTH, btn_MeasureBoth, 6, SpringLayout.SOUTH, textFieldAutoSpores);
	        sl_panel.putConstraint(SpringLayout.WEST, textFieldAutoSpores, 25, SpringLayout.EAST, lblSpores);
	        sl_panel.putConstraint(SpringLayout.EAST, textFieldAutoSpores, -153, SpringLayout.EAST, panel);
	        sl_panel.putConstraint(SpringLayout.NORTH, lblSpores, 5, SpringLayout.NORTH, textFieldAutoSpores);
	        sl_panel.putConstraint(SpringLayout.NORTH, textFieldAutoSpores, 4, SpringLayout.SOUTH, textField_Lenght);
	        panel.add(textFieldAutoSpores);
	        textFieldAutoSpores.setColumns(10);
	        
	        JButton btnMeasure = new JButton("Measure");
	        btnMeasure.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		GIM();
	        	}
	        });
	        sl_panel.putConstraint(SpringLayout.EAST, button, 0, SpringLayout.EAST, btnMeasure);
	        sl_panel.putConstraint(SpringLayout.WEST, btnMeasure, 6, SpringLayout.EAST, textField_Lenght);
	        sl_panel.putConstraint(SpringLayout.SOUTH, btnMeasure, 0, SpringLayout.SOUTH, textField_Lenght);
	        panel.add(btnMeasure);
	        
	        JButton btnCount = new JButton("Count");
	        btnCount.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		CountSpores();
	        	}
	        });
	        sl_panel.putConstraint(SpringLayout.WEST, btnCount, 6, SpringLayout.EAST, textFieldAutoSpores);
	        sl_panel.putConstraint(SpringLayout.SOUTH, btnCount, 0, SpringLayout.SOUTH, textFieldAutoSpores);
	        panel.add(btnCount);
	        
	        chckbxAutoSave = new JCheckBox("Auto Save");
	        chckbxAutoSave.addItemListener(this);
	        sl_panel.putConstraint(SpringLayout.NORTH, btnSaveTable, -1, SpringLayout.NORTH, chckbxAutoSave);
	        sl_panel.putConstraint(SpringLayout.NORTH, chckbxAutoSave, 21, SpringLayout.SOUTH, chckbxAutoLoad);
	        sl_panel.putConstraint(SpringLayout.WEST, chckbxAutoSave, 0, SpringLayout.WEST, chckbxAutoLoad);
	        chckbxAutoLoad.addItemListener(this);
	        panel.add(chckbxAutoSave);
	        
	        JButton btnCropArea = new JButton("Crop area");
	        btnCropArea.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		ImageProcessor ip = imp.getProcessor();
		    		Rectangle r = ip.getRoi();
		    		IJ.run(imp, "Crop", "");
	        		
	        		
	        	}
	        });
	        sl_panel.putConstraint(SpringLayout.WEST, btnCropArea, 6, SpringLayout.EAST, textFieldNSpores);
	        sl_panel.putConstraint(SpringLayout.SOUTH, btnCropArea, 0, SpringLayout.SOUTH, textFieldNSpores);
	        panel.add(btnCropArea);
	    }
	 
	    protected void CalculateBoth() {
	    		CountSpores();
	    		GIM();
		}

		protected void CountSpores() {
	    		System.out.println("Count on " + textImagePath.getText());
	    		
	    		
		}

		protected void GIM() {
	    		System.out.println("Calculate on " + textImagePath.getText());
	    		ImageProcessor ip = imp.getProcessor();
	    		Rectangle r = ip.getRoi();
	    		IJ.run(imp, "8-bit", "");
	    		
	    		double x = r.getMinX();
	    		double y = r.getMinY();
	    		double w = r.getWidth();
	    		double h = r.getHeight();
	    		
	    		
	    		double x1,x2,y1,y2;
	    		// Get the coordinates of the sides.
	    		for(int i = 0; i<1; i++){
	    			Random rand = new Random();
		    		
		    		double direction = rand.nextDouble();
		    		
	    			if(direction > 0.5){
	    				//sort the y value. x0 is defined
	    				// horizontal line
	    				 x1 = x;
	    				 x2 = x + w;
	    				
	    				 y1 = y + (new Random()).nextDouble()* h;
	    				 y2 = y + (new Random()).nextDouble()* h;
	    				//Overlay.drawLine(x1, y1, x2, y2)
	    			
	    				
	    			}
	    			else {
	    				//sort the x value. x0 is defined
	    				//vertical line
	    				 x1 = x + (new Random()).nextDouble()* w;
	    				 x2 = x + (new Random()).nextDouble()* w;
	    				 y1 = y;
	    				 y2 = y +  h;
	    				//Overlay.drawLine(x1, y1, x2, y2)
	    			}
	    			
	    			Roi roi = new Line(x1, y1, x2, y2);
	    			// create empty table
	    			ResultsTable rt = new ResultsTable();
	    			
	    			Overlay currentLine = new Overlay(roi);
	    			imp.setOverlay(currentLine);
	    			ProfilePlot profiler = new ProfilePlot(imp);
	    			double[] profile = profiler.getProfile();
	    			ImageStatistics stats = roi.getStatistics();
	    			System.out.println("Dev " + stats.stdDev);
	    			System.out.println("Length " + profile.length);	  
	    			
	    			
	    			
	    			 int[] max = MaximumFinder.findMaxima(profile, 3*stats.stdDev, true);
	    			 System.out.println("Number of crosses " + max.length);
	    			 
	    			 for(int k = 0 ; k < max.length; k++) {
	    				 System.out.println("Maxima " + max[k] + "  intensity " + profile[max[k]]);
	    			 }
	    			 IJ.run(imp, "Plot Profile", "");
	    			 
//	    			for (int j = 0; j < profile.length; j++) {
//	    				rt.setValue("line" + i, j, profile[j]);}
//	    			// show the profiles
//	    			rt.show("line profiles");

	    			
	    		    		   

	    
	    			
	    		}

	    		System.out.println(r.getWidth());
	    		
	    		
		}

		protected void changeLine(String string) {
	    		int size = dataTable.getRowCount();
			int current = dataTable.getSelectedRow();
			
			if(string == "+") {
				if(current < size) {
					current = current + 1;
 					lsm.setSelectionInterval(current,  current);
				}
				
			}
			else if( string == "-") {
				if(current > 0) {
					current = current -1;
					lsm.setSelectionInterval(current,  current);			
				}
				
			}
				
			
			
		}

		protected void openImage() {
			imp = WindowManager.getCurrentImage();
			
			if (imp!=null) {
				imp.close();
				
				imp = IJ.openImage(textImagePath.getText());
	    			imp.show();
			}
			else {
				imp = IJ.openImage(textImagePath.getText());
	    		
				imp.show();
			}
	    		
			
		}

		protected void updateSelection() {
	    		int selectedRow = lsm.getMinSelectionIndex();
            textImagePath.setText(dataTable.getPath(selectedRow));
            textField_Lenght.setText(dataTable.getLength(selectedRow));
            textFieldNSpores.setText(dataTable.getNSpores(selectedRow));
            textFieldAutoSpores.setText(dataTable.getAutoSpores(selectedRow));
            
            System.out.println("Row " + selectedRow
                               + " is now selected.");
            
            if(AUTOLOAD) {
            		openImage();
            }
            
            
		}



		private void printDebugData(JTable table) {
	        int numRows = table.getRowCount();
	        int numCols = table.getColumnCount();
	        javax.swing.table.TableModel model = table.getModel();
	 
	        System.out.println("Value of data: ");
	        for (int i=0; i < numRows; i++) {
	            System.out.print("    row " + i + ":");
	            for (int j=0; j < numCols; j++) {
	                System.out.print("  " + model.getValueAt(i, j));
	            }
	            System.out.println();
	        }
	        System.out.println("--------------------------");
	    }
	 
	    /**
	     * Create the GUI and show it.  For thread safety,
	     * this method should be invoked from the
	     * event-dispatching thread.
	     */
	    private static void createAndShowGUI() {
	        //Create and set up the window.
	        JFrame frame = new JFrame("SimpleTableDemo");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	        //Create and set up the content pane.
	        FunAnalysisPlugin newContentPane = new FunAnalysisPlugin();
	        newContentPane.setOpaque(true); //content panes must be opaque
	        frame.setContentPane(newContentPane);
	 
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }
	 
	    public static void main(String[] args) {

	            
	            
	            try {
	    			final ImageJ ij = new ImageJ();
	    			ij.launch(args);
	    	        //Schedule a job for the event-dispatching thread:
	    	        //creating and showing this application's GUI.
	    	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	    	            public void run() {
	    	                createAndShowGUI();
	    	            }
	    	        });
	    		}
	    		catch (final Exception e) {
	    			e.printStackTrace();
	    		}
	        
	    	
	    	
	    	
	    	
	    	
//	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//	            public void run() {
//	                createAndShowGUI();
//	            }
//	        });
	    	


	    	
	    	
	    }

		@Override
		public void itemStateChanged(ItemEvent e) {
			  Object source = e.getItemSelectable();
			  
		        if (source == chckbxAutoLoad) {
		        	AUTOLOAD = chckbxAutoLoad.isSelected();
		            System.out.println("Analysis mode " + chckbxAutoLoad.isSelected());
		          }
		        if (source == chckbxAutoSave) {
		        	AUTOSAVE = chckbxAutoSave.isSelected();
		            System.out.println("Analysis mode " + chckbxAutoSave.isSelected());
		          }
		}
	}