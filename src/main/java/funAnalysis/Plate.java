package funAnalysis;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.ui.ExtensionFileFilter;

public class Plate {
	
	private File file;
	private String name;
	private String path;
	private File[] images;
	private final FileNameExtensionFilter filter =
	        new FileNameExtensionFilter("Compressed files",
	            "jpg", "jpeg", "tif", "tiff");
	
	public static void main(String[] args) {
		
		Plate plate = new Plate(new File("/Users/caldas/Documents/Projects/PN Foraging/Scripts/20170818/plate 38"));
		
		for(int i=0; i< plate.images.length; i++) {
			System.out.println(plate.images[i]);
		}
		
	}
	
	public Plate(File file) {
		this.file = file;
		this.name = file.getName();
		this.path = file.getAbsolutePath();
		
		images = file.listFiles(new FileFilter() {
		    public boolean accept(File f) {
		        return filter.accept(file);
		    }
		});
	}
	
	public String getName() {
		return name;
	}	
}
