import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MyFileReader {
	
	
	//String Tokenizer
	
	public static void main(String [] args) {
		System.out.print("Enter the name of a file: ");
		
		Scanner sc = new Scanner(System.in);
		String fileName = sc.nextLine();
		
		System.out.println("filename = " + fileName);
		sc.close();
		
		try {
			//Character by character
			FileReader fr = new FileReader(fileName);
			
			//Line by line
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			while(line != null) {
				System.out.println(line);
				line = br.readLine();
			}
			
			System.out.println();
			
			br.close();
			fr.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("fnfe: " + fnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
}
