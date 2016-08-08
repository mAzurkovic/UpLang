import java.io.*;
import java.util.*;

public class up {

	// Public variables and usages.
	public static String upMessage = "<UpLang> Message: ";

	//**********************************************//
	//				Initializer Method				//
	//**********************************************//
	public static void main(String[] args) {
		String ret = getFile(args[0]); // Lexical analysis gets called from within getFile() method
		List<String> lexed = new ArrayList<String>();
		lexed = lex(ret);
		parser(lexed);
	}

	private static void parser(List<String> tokenValues) {
		for (int i = 0; i < tokenValues.size(); i++) {
			if (tokenValues.get(i) == "PRINT") {
				System.out.println(tokenValues.get(i + 1));
			}
		}		
	}

	//**********************************************//
	//				Lexical Analysis 				//
	//**********************************************//
	private static List<String> lex(String line) {
		String tok = new String();
		String string = new String();
		boolean findString = false;
		boolean quote = false;
		List<String> tokens = new ArrayList<String>();

		for (int i = 0; i < line.length(); i++) {
			char currTok = line.charAt(i);

			if (currTok == ' ') {
				if (!findString) { currTok = Character.MIN_VALUE; }
				else { currTok = ' '; }
			}

			tok += currTok;

			if (tok.equals("PRINT")) {
				tokens.add("PRINT");
				tok = "";
			} 

			if (currTok == '"') {
				if (!findString) { findString = true; }
				else if (findString) {
					tokens.add("STRING:" + string + "\"");
					findString = false;
					string = "";
					tok = "";
				}
				
			} else if (findString) {
				string += tok;
				tok = "";
			}
		}
		return tokens;
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
            	file += line;
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
        return file;
	}

}