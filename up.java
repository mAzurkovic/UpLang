import java.io.*;
import java.util.*;

public class up {

	public static void main(String[] args) {
		getFile(args[0]);
	}

	public static String upMessage = "<UpLang> Message: ";

	private static ArrayList<String> lex(String line) {
		List<String> tokens = new ArrayList<String>();
		String tok = new String();
		String string = new String();
		boolean spaces = true;

		for (int i = 0; i < line.length(); i++) {
			char currTok = line.charAt(i);

			if (currTok == ' ') {
				if (spaces) { currTok = Character.MIN_VALUE; }
				else { currTok = ' '; }
			}

			if (currTok == '"') { 
				string += tok;
				spaces = false;
				tok = "";
			}

			if (tok.equals("PRINT")) {
				tokens.add("PRINT");
				tok = "";
			}

			tok += currTok;
			System.out.println(tok);
			spaces = true;
		}
		// System.out.println(line);
		tokens.add("STRING: " + string + "\"");
		System.out.println(tokens);
		return null;
	}

	private static String getFile(String fileName) {
		String file = new String();
		String line = null;
		try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	//file += line;
            	// Replace all spaces in program file
            	//String lineSpacless = line.replaceAll(" ", "");
            	lex(line);
            }   
            // Always close files.
            bufferedReader.close();
        }

        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + fileName + "'");                
        }

        catch(IOException ex) {
            System.out.println(
                "Error reading file '" + fileName + "'");      
        }
        return null;
	}

}