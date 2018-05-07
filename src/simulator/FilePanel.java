package simulator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FilePanel {

	public Main main;
	
	public FilePanel(Main main){
		this.main = main;
		
	}
	
	private int countLines(File file){
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			int lines = 0;
			try {
				while (reader.readLine() != null) lines++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				reader.close();
				return lines;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	
	
	
	public Code getCode(){
		
		JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter f = new FileNameExtensionFilter("MATLAB-file (.m) ", "m", "matlab");
		fc.setFileFilter(f);
		
		int returnVal = fc.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
        	
			File file = fc.getSelectedFile();
            System.out.println("FilePanel:\tOpening: " + file.getName());
            Code code = new Code(countLines(file));
            
            try{
            	
            	BufferedReader br = new BufferedReader(new FileReader(file));
     			String line;
     
    			while ((line = br.readLine()) != null) {
    				
    				code.add(line);
    				
    			}
    			br.close();
     	
    		}catch (IOException e) {
    			e.printStackTrace();
    		} 
            return code;
            
        }else {
        	System.out.println("FilePanel:\tOpen command cancelled by user.");
        }
		
		
		return null;
		
		
	}
	
	
	
	
	/*
	public static void main(String[] args) {
		System.out.println(new FilePanel().getCode().toString());
	}
	 */
	
	

}