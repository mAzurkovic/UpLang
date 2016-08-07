import java.io.*;
import java.util.*;

public class up {

	// Public variables and usages.
	public static List<String> tokens = new ArrayList<String>();
	public static String upMessage = "<UpLang> Message: ";

	//**********************************************//
	//				Initializer Method				//
	//**********************************************//
	public static void main(String[] args) {
		getFile(args[0]); // Lexical analysis gets called from within getFile() method
	}

	private static void parser(List<String> tokenValues) {
		tokenValues = new ArrayList<String>();
		int i = 0; // Counter for iteration
		while (i < tokenValues.size()) {
			System.out.println(i);
		}		
	}

	//**********************************************//
	//				Lexical Analysis 				//
	//**********************************************//
	private static void lex(String line) {
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
		}
		tokens.add("STRING: " + string + "\"");
		parser(tokens);
	}

	//**********************************************//
	//		 	  Retreving File Contents 	     	//
	//**********************************************//
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