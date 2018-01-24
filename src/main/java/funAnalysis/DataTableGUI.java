package funAnalysis;

import java.io.File;
import javax.swing.JTable;

/*
 * SimpleTableDemo.java requires no other files.
 */
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
 
public class DataTableGUI extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean DEBUG = false;
	private JTable table;



 
    public DataTableGUI(MyDataTable data) {
        super(new GridLayout(1,1));
        

        this.table = data.getTable();
        this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        this.table.setFillsViewportHeight(true);
        
        
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
     * @param data 
     */
    private static void createAndShowGUI(MyDataTable data) {
    		
//    	
//    		DataTable table = new DataTable(csvFile);
//    		System.out.println(table.data[0].toString());

    	
        //Create and set up the window.
        JFrame frame = new JFrame("DataTable");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
 
        //Create and set up the content pane.
        DataTableGUI newContentPane = new DataTableGUI(data);
        
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
        
        
    }
 
    public static void main(String[] args) {
    	
    		MyDataTable data = new MyDataTable();
    		
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(data);
            }
        });
    }


	
	

	private static File getExistingImage(File folder, String string) {
		File path_1 = new File(folder.getAbsolutePath() + File.separator + "Image_" + string + ".jpg");
		File path_2 = new File(folder.getAbsolutePath() + File.separator + "Image_" + string + ".tif");
		
		if(path_1.exists()) {
			return path_1;
		}
		else if (path_2.exists()) {
			return path_2;
		}
		
		return new File("");
	}

	private static File getExistingFolder(File dataFolder, String plateN) {
		
		File path_1 = new File(dataFolder.getAbsoluteFile() + File.separator + "plate " + plateN);
		File path_2 = new File(dataFolder.getAbsoluteFile() + File.separator + plateN);
		
		if(path_1.exists()) {
			return path_1;
		}
		else if (path_2.exists()) {
			return path_2;
		}
		
		return new File("");
	}
	

	
	

	
	public JTable getTable() {
		return this.table;
	}
		}