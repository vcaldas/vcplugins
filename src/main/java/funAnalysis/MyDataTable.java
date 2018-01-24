package funAnalysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JTable;

public class MyDataTable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File file;
	private JTable table;
	String imageFolder = "/Users/caldas/Documents/Projects/PN Foraging/Data/Images";
	private String[][] data;
    final String[] columnNames = {"path", "plate", 	"harvest", 	"picture_folder", 	"treatment", "picture_number", 	"square", 	"side", 	"sporeCount", "Area",  "NSpores", "Auto_Spores", "Auto_length", };
    String csvFile = "/Users/caldas/Documents/Projects/PN Foraging/Data/SPORE_COUNTS.csv";
    String csvFileOut = "/Users/caldas/Documents/Projects/PN Foraging/Data/SPORE_COUNTS_out.csv";
    
    
	public MyDataTable() {
		this.file = new File(csvFile);
		
		populate();
		
	}

	public static void main(String[] args) {
			MyDataTable data = new MyDataTable();
			System.out.println(data.getPath(2));

	}
	
	public String getPath(int i) {
		return table.getValueAt(i, 0).toString();
	
	}
	
	public String getNSpores(int i) {
		return table.getValueAt(i, 8).toString();
	
	}
	
	public String getLength(int i) {
		return table.getValueAt(i, 12).toString();
	
	}

	private void populate() {
        
        // this gives you a 2-dimensional array of strings
        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;
        
        

        try{
            inputStream = new Scanner(file);
            
            while(inputStream.hasNext()){
                String line= inputStream.next();
                List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
                lines.add(items);
            }

            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
       String[][] mydata = new String[lines.size()-1][lines.get(0).size() + 1];
    	
       for(int i = 1; i< lines.size(); i++) {
    	   	for(int j = 0; j< lines.get(i).size(); j++) {
    	   		mydata[i-1][j] = lines.get(i).get(j);
    	   	}
       }
              
        
       
       for(int i=0; i < mydata.length; i++) {
			//System.out.println(i + "    " +  mydata.length);
			mydata[i][0] = getPath(mydata[i]);
			
		}
       
       this.data = mydata;	

       this.table = new JTable(data, columnNames);
       
	}
	
	private String getPath(String[] line) {
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
			return new File(f, "Image_"+path + ".jpg");
			
		}
		if(new File(f, "Image_"+path + ".jpeg").exists()) {
			return new File(f, "Image_"+path + ".jpeg");
			
		}
		else if(new File(f, "Image_"+path + ".tif").exists()) {
			return new File(f, "Image_"+path + ".tif");
			
		}
		else if(new File(f, "Image_"+path + ".tiff").exists()) {
			return new File(f, "Image_"+path + ".tiff");
			
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

	public JTable getTable() {
		return table;
	}

	public void saveTable() throws Exception{
		saveTable(csvFileOut);
	}
	
	public void saveTable(String path) throws Exception{
		
		  BufferedWriter bfw = new BufferedWriter(new FileWriter(path));
		
		  for(int i = 0 ; i < table.getColumnCount() ; i++)
			  
		  {
			
		    bfw.write(table.getColumnName(i));
		    bfw.write(",");
		  }
		 
		  for (int i = 0 ; i < table.getRowCount(); i++)
		  {
		    bfw.newLine();
		    for(int j = 0 ; j < table.getColumnCount();j++)
		    {
		    //	System.out.println(table.getValueAt(i,j));
		      bfw.write((String)(table.getValueAt(i,j)));
		      bfw.write(",");;
		    }
		  
		   
		
		  }
		  bfw.close();
	}

	public String getAutoSpores(int selectedRow) {
		return table.getValueAt(selectedRow, 11).toString();
	}

	public int getRowCount() {
		return table.getRowCount();
	}

	public int getSelectedRow() {
		// TODO Auto-generated method stub
		return table.getSelectedRow();
	}
	
	
	
}
