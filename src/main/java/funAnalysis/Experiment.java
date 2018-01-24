package funAnalysis;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

public class Experiment {

	private static File ROOT;
	private static Plate[] plates;
	

	
	public Experiment() {
		
		File folder = new File("/Users/caldas/Documents/Projects/PN Foraging/Scripts/20170818");
		
		setROOT(folder);		
		
		
		File[] myplates = folder.listFiles(new FileFilter() {
		    public boolean accept(File f) {
		        return f.isDirectory();
		    }
		});
		
		setPlates(myplates);
		
		System.out.println("Folders count: " + myplates.length);
		
	}
	
	public static void main(String[] args) {
		Experiment exp = new Experiment();
		
		for(int i=0; i<  plates.length; i++) {
			System.out.println(plates[i].getName());
		}
			
	}

	
	
	
	public File getDirectory(String destination, String username) {
        System.out.println("called get directory");
        // currently not working, is not calling the username or destination 
        //set the user directory from the destinarion and the logged user name
        File directory = new File(destination, username);
        //check if the location exists
        if (!directory.exists()) {
            //let's try to create it
            try {
                directory.mkdir();
            } catch (SecurityException secEx) {
                //handle the exception
                secEx.printStackTrace(System.out);
                directory = null;
            }
        }
        return directory;
    }
	

	public static File getROOT() {
		return ROOT;
	}



	public Plate[] getPlates() {
		return plates;
	}



	public static void setROOT(File root) {
		ROOT = root;
	}



	public void setPlates(File[] myplates) {
		plates = new Plate[myplates.length];
		
		for(int i = 0; i< myplates.length; i++) {
			plates[i] = new Plate(myplates[i]);
		}
	}

	public String getPath() {
		return ROOT.getAbsolutePath();
	}
	
	

}
