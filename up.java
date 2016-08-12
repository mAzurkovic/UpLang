import java.io.*;
import java.util.*;
import java.util.Scanner;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

public class up {

	// Public variables and usages.
	public static String upMessage = "<UpLang> Message: ";
	// The dictionary that holds variable information (key = variable name, value = value)
	public static Map<String, String> varMap = new HashMap<String, String>();

/**
 * ============================================================================
 *                                INITIALIZE
 * ============================================================================
 */
	public static void main(String[] args) throws Exception {
		//String ret = getFile(args[0]); // Lexical analysis gets called from within getFile() method
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
		String varName = new String();
		String varValue = new String();

		for (int i = 0; i < tokenValues.size(); i++) {
			// Print function:
			currString = tokenValues.get(i);
			if (tokenValues.get(i) == "PRINT") {
				String value = tokenValues.get(i + 1);
				// Check if the value that needs to be printed is a number/expression, let
				// expression handeler do the job...
				if (value.contains("EXPR") || value.contains("[0-9]+")) { continue; }
				// Define start and end indexes of value that needs to be printed
				int startIndex = value.indexOf(":") + 1;
				int endIndex = value.length();
				printAns = value.substring(startIndex, endIndex);
				// Execute intended PRINT command
				System.out.println(printAns.replaceAll("\"", ""));
			}

			// Expression and number handling
			if (tokenValues.get(i).contains("EXPR")) {
				int startIndex = currString.indexOf(":") + 1;
				int endIndex = currString.length();
				expr = currString.substring(startIndex, endIndex);
				if (tokenValues.get(i - 1) == "PRINT") {
					System.out.println(engine.eval(expr));
				}
				expr = "";
			}

			if (currString.contains("VARIABLE")) {
				varName = currString.substring(9, currString.length());
				varValue = tokenValues.get(i + 2);

				// Parse and format variable data types
				if (varValue.contains("NUM:")) {
					varMap.put(varName, varValue.substring(4, varValue.length()));	
				} else if (varValue.contains("EXPR:")) {
					// HashMap needs to have String obj. passed, turn computed value to string, and then back
					String shorten = varValue.substring(5, varValue.length());
					Object ans = engine.eval(shorten);
					String stringAns = "\"" + ans + "\"";
					varMap.put(varName, stringAns.replaceAll("\"", ""));	
				} else if (varValue.contains("STRING:")) {
					varMap.put(varName, varValue.substring(7, varValue.length()));	
				} else { // ****
					varMap.put(varName, varValue);	
				}
			}
		}

		System.out.println(varMap);
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
		String variable = new String();

		char currChar;

		boolean findString = false;
		boolean findNum = false;
		boolean isExpression = false;
		boolean isVar = false;

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

				if (tok.matches("VAR")) {
					isVar = true;
				} else if (tok.matches("=")) {
					isVar = false;
					tokens.add("VARIABLE:" + variable);
					tokens.add("EQL");
					variable = "";
					tok = "";
				}

				if (isVar) {
					variable += tok;
					tok = "";
					if (variable.matches("VAR")) { variable = ""; }
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
		System.out.println(tokens);
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