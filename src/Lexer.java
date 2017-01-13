import java.io.*;
import java.util.*;
import java.util.Scanner;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

/**
 * ============================================================================
 *                              LEXER CLASS
 * ============================================================================
 */

public class Lexer {

  public static List<String> lex(List<String> fileContents) {
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

}
