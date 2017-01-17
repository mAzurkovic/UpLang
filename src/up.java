import java.io.*;
import java.util.*;
import java.util.Scanner;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

public class up {

	// Public variables and usages.
	public static String upMessage = "<UpLang> Message: ";
	// The dictionary that holds variable information (key = variable name, value = value)
/**
 * ============================================================================
 *                                INITIALIZE
 * ============================================================================
 */
	public static void main(String[] args) throws Exception {
		//String ret = getFile(args[0]); // Lexical analysis gets called from within getFile() method
		List<String> fileRead = new ArrayList<String>();
		List<String> lexed = new ArrayList<String>();

		// Init objects
		Lexer lexer = new Lexer();
		Parser parser = new Parser();

		fileRead = getFile(args[0]);
		lexed = lexer.lex(fileRead);
	//	parser(lexed);
		parser.parse(lexed);
	}

/**
 * ============================================================================
 *                            GRAB FILE CONTENTS
 * ============================================================================
 */
	private static List<String> getFile(String fileName) {
		String file = new String();
		String line = null;
		List<String> fileContents = new ArrayList<String>();
		try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	// file += line;
            	fileContents.add(line);
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
        return fileContents;
	}

}
