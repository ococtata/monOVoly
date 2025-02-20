package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	public static List<String> readFile(String filePath){
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line;
			try {
				while((line = br.readLine()) != null) {
					lines.add(line);
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lines;
	}
	
	public static void appendToFile(String filePath, String line) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write(line);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeToFile(String filePath, String data) {
        try {
        	BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        	bw.write(data);
        	bw.close();
        } 
        catch (IOException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
    }
	
	public static void writeLinesToFile(String filePath, List<String> lines) {
        try {
        	BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
            
            bw.close();
        } catch (IOException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
    }
}
