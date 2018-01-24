package sandbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class adf {
	static String[][] data;
	
	public static void main(String[] args) {
        String fileName= "/Users/caldas/Documents/Projects/PN Foraging/Data/SPORE_COUNTS.csv";
        
        data = getData(fileName);
        
        System.out.println(data[1][0]);
    }

	
	public static String[][] getData(String fileName){
		File file= new File(fileName);
        
        // this gives you a 2-dimensional array of strings
        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;
        
        

        try{
            inputStream = new Scanner(file);
            
            while(inputStream.hasNext()){
                String line= inputStream.next();
                List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
//                String[] values = line.split(",");
                
              //  System.out.println(items);
               lines.add(items);
            }

            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
       String[][] mydata = new String[lines.size()][lines.get(0).size() + 1];
    	
       for(int i = 0; i< lines.size(); i++) {
    	   	for(int j = 0; j< lines.get(i).size(); j++) {
    	   		mydata[i][j] = lines.get(i).get(j);
    	   	}
       }
       
       System.out.println(lines.size());
       System.out.println(lines.get(0).size());
       
       return mydata;
	}

}
