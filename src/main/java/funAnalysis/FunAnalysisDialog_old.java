package funAnalysis;/*
 * SimpleTableSelectionDemo.java requires no other files.
 */
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.imagej.ImageJ;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
 
/** 
 * SimpleTableSelectionDemo is just like SimpleTableDemo, 
 * except that it detects selections, printing information
 * about the current selection to standard output.
 */
public class FunAnalysisDialog_old extends JPanel implements ItemListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean DEBUG = false;
    private boolean ALLOW_COLUMN_SELECTION = false;
    private boolean ALLOW_ROW_SELECTION = true;
    private JTable table;
    private JTextField txtnotActiveNow;
    String csvFile = "/Users/caldas/Documents/Projects/PN Foraging/Data/SPORE_COUNTS.csv";
    String imageFolder = "/Users/caldas/Documents/Projects/PN Foraging/Data/Images";
    

    final String[] columnNames = {"path", "plate", 	"harvest", 	"picture_folder", 	"treatment", "picture_number", 	"square", 	"side", 	"sporeCount", "Area",  "NSpores", "Auto_Spores", "Auto_length", };
    private JCheckBox chckbxAnalysisMode;;
    
 
    public FunAnalysisDialog_old() {
        super(new GridLayout(1,0));
        
        

 
        final Object[][] data  = getData();
        addImagePaths(data);
   
 
        
        table = new JTable(data, columnNames);
        
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
 
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (ALLOW_ROW_SELECTION) { // true by default
            ListSelectionModel rowSM = table.getSelectionModel();
            rowSM.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    //Ignore extra messages.
                    if (e.getValueIsAdjusting()) return;
 
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    if (lsm.isSelectionEmpty()) {
                        System.out.println("No rows are selected.");
                    } else {
                        int selectedRow = lsm.getMinSelectionIndex();
                        
                        System.out.println("Row " + selectedRow
                                           + " is now selected.");
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
                        int selectedCol = lsm.getMinSelectionIndex();
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
 
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Add the scroll pane to this panel.
        add(scrollPane);
        
        JPanel panel = new JPanel();
        add(panel);
        SpringLayout sl_panel = new SpringLayout();
        panel.setLayout(sl_panel);
        
        JButton btnLoaf = new JButton("Load");
        sl_panel.putConstraint(SpringLayout.NORTH, btnLoaf, 10, SpringLayout.NORTH, panel);
        sl_panel.putConstraint(SpringLayout.WEST, btnLoaf, 10, SpringLayout.WEST, panel);
        panel.add(btnLoaf);
        
        txtnotActiveNow = new JTextField();
        txtnotActiveNow.setText("\"Not active now\"");
        sl_panel.putConstraint(SpringLayout.NORTH, txtnotActiveNow, 10, SpringLayout.NORTH, panel);
        sl_panel.putConstraint(SpringLayout.WEST, txtnotActiveNow, 6, SpringLayout.EAST, btnLoaf);
        sl_panel.putConstraint(SpringLayout.EAST, txtnotActiveNow, 236, SpringLayout.EAST, btnLoaf);
        panel.add(txtnotActiveNow);
        txtnotActiveNow.setColumns(10);
        
        JButton btnClose = new JButton("Close");
        sl_panel.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, panel);
        sl_panel.putConstraint(SpringLayout.EAST, btnClose, -10, SpringLayout.EAST, panel);
        panel.add(btnClose);
        
        JButton btnSave = new JButton("Save");
        sl_panel.putConstraint(SpringLayout.NORTH, btnSave, 0, SpringLayout.NORTH, btnClose);
        sl_panel.putConstraint(SpringLayout.EAST, btnSave, -6, SpringLayout.WEST, btnClose);
        panel.add(btnSave);
        
        JCheckBox chckbxAutosave = new JCheckBox("Autosave");
        sl_panel.putConstraint(SpringLayout.NORTH, chckbxAutosave, 1, SpringLayout.NORTH, btnClose);
        sl_panel.putConstraint(SpringLayout.EAST, chckbxAutosave, -6, SpringLayout.WEST, btnSave);
        panel.add(chckbxAutosave);
        
        chckbxAnalysisMode = new JCheckBox("Analysis mode");
        chckbxAnalysisMode.addItemListener(this);
        sl_panel.putConstraint(SpringLayout.NORTH, chckbxAnalysisMode, 6, SpringLayout.SOUTH, btnLoaf);
        sl_panel.putConstraint(SpringLayout.WEST, chckbxAnalysisMode, 0, SpringLayout.WEST, btnLoaf);
        panel.add(chckbxAnalysisMode);
        
        JButton button = new JButton(">");
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		nextLine("+");
        	}
        });
        sl_panel.putConstraint(SpringLayout.WEST, button, 0, SpringLayout.WEST, txtnotActiveNow);
        panel.add(button);
        
        JButton button_1 = new JButton("<");
        button_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		nextLine("-");
        	}
        });
        sl_panel.putConstraint(SpringLayout.NORTH, button, 0, SpringLayout.NORTH, button_1);
        sl_panel.putConstraint(SpringLayout.NORTH, button_1, 35, SpringLayout.SOUTH, chckbxAnalysisMode);
        sl_panel.putConstraint(SpringLayout.WEST, button_1, 0, SpringLayout.WEST, btnLoaf);
        panel.add(button_1);
        
        JLabel lblImageSelector = new JLabel("Image Selector");
        sl_panel.putConstraint(SpringLayout.WEST, lblImageSelector, 0, SpringLayout.WEST, btnLoaf);
        sl_panel.putConstraint(SpringLayout.SOUTH, lblImageSelector, -6, SpringLayout.NORTH, button_1);
        panel.add(lblImageSelector);
    }
 

   

	private void addImagePaths(Object[][] data) {
		for(int i=0; i < data.length; i++) {
			System.out.println(i + "    " +  data.length);
			data[i][0] = getPath(data[i]);
			
		}
	}




	private String getPath(Object[] line) {
		File f = new File(imageFolder);
				
		if(f.exists()) {
			//Check for subdirectory
			f = new File(f, line[3].toString().replace("-", ""));
			// get next level
			f = getPathWithPlate(f, line[1].toString());
			
			if (f ==null){ // plate folder does not exist
				return "File does not exist";
			}
			else { // Plate folder do exist and its correct!
				f = getPathWithFileExtension(f, line[5].toString());
				if(f == null) {
					return "File does not exist";
				}
				else {
					return f.getAbsolutePath();
				}
				
				
			}
			
		}
			
		return "File does not exist";
			
		}
		
	private File getPathWithFileExtension(File f, String path) {
		
		if(new File(f, "Image_"+path + ".jpg").exists()) {
			return new File(f, path);
			
		}
		if(new File(f, "Image_"+path + ".jpeg").exists()) {
			return new File(f, path);
			
		}
		else if(new File(f, "Image_"+path + ".tif").exists()) {
			return new File(f, path);
			
		}
		else if(new File(f, "Image_"+path + ".tiff").exists()) {
			return new File(f, path);
			
		}
		else {
			return null;
			
		}
	}




	private File getPathWithPlate(File f, String path) {
		
			if(new File(f, path).exists()) {
				return new File(f, path);
				
			}
			else if(new File(f, "Plate "+ path).exists()) {
				return new File(f, "Plate "+ path);
				
			}
			return null;
}




	protected void nextLine(String string) {
		int size = table.getRowCount();
		int current = table.getSelectedRow();
		
		if(string == "+") {
			if(current < size) {
				current = current + 1;
				table.setRowSelectionInterval(current, current);
			}
			
		}
		else if( string == "-") {
			if(current >0) {
				current = current -1;
				table.setRowSelectionInterval(current, current);				
			}
			
		}
		
		if(chckbxAnalysisMode.isSelected()) {
			openImage(table.getRowCount());
		}
		
		
	}




	private void openImage(int rowCount) {
		//get path to image
		
		
	}




	private String[][] getData() {
	        File file= new File(csvFile);

	        // this gives you a 2-dimensional array of strings
	        // this gives you a 2-dimensional array of strings
	        List<List<String>> lines = new ArrayList<>();
	        Scanner inputStream;
	        
	        try{
	            inputStream = new Scanner(file);
	            
	            while(inputStream.hasNext()){
	                String line= inputStream.next();
	                List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
//	                String[] values = line.split(",");
	                
	               // System.out.println(items);
	               lines.add(items);
	            }

	            inputStream.close();
	        }catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	        
	        String[][] mydata = new String[lines.size()-1][lines.get(0).size()];
	    	
	        for(int i = 1; i< lines.size(); i++) {
	     	   	for(int j = 0; j< lines.get(i).size(); j++) {
	     	   		mydata[i-1][j] = lines.get(i).get(j);
	     	   	}
	        }
	        
	            
	        return mydata;
	}


	private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        // System.out.println("Value of data: ");
        for (int i=1; i < numRows; i++) {
           // System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
             //   System.out.print("  " + model.getValueAt(i, j));
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
        JFrame frame = new JFrame("SimpleTableSelectionDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        FunAnalysisDialog_old newContentPane = new FunAnalysisDialog_old();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    
    /** Listens to the check boxes. */
    public void itemStateChanged(ItemEvent e) {
        
        Object source = e.getItemSelectable();
 
        if (source == chckbxAnalysisMode) {
            System.out.println("Analysis mode " + chckbxAnalysisMode.isSelected());
        }
//        } else if (source == glassesButton) {
//            index = 1;
//            c = 'g';
//        } else if (source == hairButton) {
//            index = 2;
//            c = 'h';
//        } else if (source == teethButton) {
//            index = 3;
//            c = 't';
//        }
 
//        //Now that we know which button was pushed, find out
//        //whether it was selected or deselected.
//        if (e.getStateChange() == ItemEvent.DESELECTED) {
//            c = '-';
//        }
// 
//        //Apply the change to the string.
//        choices.setCharAt(index, c);
// 
//        updatePicture();
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
    }
}