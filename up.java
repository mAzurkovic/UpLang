import java.io.*;
import java.util.*;
import java.util.Scanner;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

public class up {

	// Public variables and usages.
	public static String upMessage = "<UpLang> Message: ";

/**
 * ============================================================================
 *                                INITIALIZE
 * ============================================================================
 */
	public static void main(String[] args) throws Exception {
		//String ret = getFile(args[0]); // Lexical analysis gets called from within getFile() method
		// lexed = lex(ret);
		// String content = new Scanner(new File(args[0])).useDelimiter("\\Z").next();

		List<String> fileRead = new ArrayList<String>();
		List<String> lexed = new ArrayList<String>();

		fileRead = getFile(args[0]);
		lexed = lex(fileRead);
		parser(lexed);
	}

/**
 * ============================================================================
 *                               	 PARSER
 * ============================================================================
 */
	private static void parser(List<String> tokenValues) throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");

		String printAns = new String();
		String expr = new String();
		String currString = new String();

		for (int i = 0; i < tokenValues.size(); i++) {
			// Print function:
			currString = tokenValues.get(i);
			if (tokenValues.get(i) == "PRINT") {
				String value = tokenValues.get(i + 1);
				// Define start and end indexes of value that needs to be printed
				int startIndex = value.indexOf(":") + 1;
				int endIndex = value.length();
				printAns = value.substring(startIndex, endIndex);
				// Execute intended PRINT command
				System.out.println(printAns.replaceAll("\"", ""));
			}

			if (tokenValues.get(i).contains("EXPR")) {
				int startIndex = currString.indexOf(":") + 1;
				int endIndex = currString.length();
				expr = currString.substring(startIndex, endIndex);
				
				System.out.println(engine.eval(expr));
				expr = "";
			}
		}
	}

/**
 * ============================================================================
 *                            	LEXICAL ANALYSIS
 * ============================================================================
 */
	private static List<String> lex(List<String> fileContents) {
		// String line
		String currLine = new String();
		String tok = new String();
		String string = new String();
		String expression = new String();

		char currChar;

		boolean findString = false;
		boolean findNum = false;
		boolean isExpression = false;

		List<String> tokens = new ArrayList<String>();


		for (int n = 0; n < fileContents.size(); n++) {
			currLine = fileContents.get(n);
			// Reset token value at start of a new line of code
			tok = "";
			isExpression = false;
			for (int i = 0; i < currLine.length(); i++) {
				currChar = currLine.charAt(i);
				tok += currChar;

				if (tok.matches(" ")) {
					// Remove spaces
					if (findString) { tok = " "; }
					// However do not remove if spaces are a part of string
					else if (!findString) { tok = ""; }
				} else if (tok.equals("PRINT")) {
					tokens.add("PRINT");
					tok = "";
				} else if (tok.equals("\"")) {
					if (!findString) { findString = true; }
					else if (findString) {
						findString = false;
						tokens.add("STRING:" + string + "\"");
						string = "";
					}
				} else if (findString) {
					string += tok;
					tok = "";
				} else if (tok.matches("[0-9]+")) {
					expression += tok;
					tok = "";
				} else if (tok.matches("[/*+-]")) {
					isExpression = true;
					expression += tok;
					tok = "";
				}

			}

			if (isExpression && expression.length() > 0) {
				// System.out.println("EXPR:" + expression);
				tokens.add("EXPR:" + expression);
				expression = "";
			} else if (!isExpression && expression.length() > 0) {
				// System.out.println("NUM:" + expression);
				tokens.add("NUM:" + expression);
				expression = "";
			}

		}
		// System.out.println(tokens);
		return tokens;
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
