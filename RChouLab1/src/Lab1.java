import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Lab1 is a program converting an expression into a set of machine
 * instructions, which would evaluate the expression if actually executed.
 * <p>
 * The instructions include addition (AD), subtraction (SB), multiplication
 * (ML), division (DV), and exponentiation (EP). The accepted operands for an
 * expression are single lowercase or uppercase letters. Space(s) incorporated
 * in the expression will be automatically ignored.
 * 
 * @version 1.0 2017-02-28
 * @author Renee Ti Chou
 */
public class Lab1 {

	private LStack stack;
	private int n; // Indicate the number portion of TEMPn variables.
	private int error; // Indicate types of error.
	private BufferedReader buf;
	private PrintWriter pw;

	/**
	 * The constructor initializes a new stack, a number indicating TEMPn
	 * variables, and an error type.
	 */
	public Lab1() {
		stack = new LStack();
		n = 1;
		error = 0;
	}

	/**
	 * The main method of the program, which reads in the input, converts the
	 * expressions, and write the results to the output.
	 * 
	 * @param args
	 *            Two program arguments [input file path] and [out file path].
	 */
	public static void main(String[] args) {
		Lab1 Lab;
		String line; // The line read from the input file.
		int i; // Indicate the position of the character in the String line.
		Character chr;

		Lab = new Lab1();

		/* Check for the command line arguments. Adapted from: Project0. */
		if (args.length != 2) {
			System.out.println("Usage: java Lab1 [input file path]" + " [output file path]");
			System.exit(1);
		}

		try {
			/* Open the input file and the output file. */
			Lab.buf = new BufferedReader(new FileReader(args[0]));
			Lab.pw = new PrintWriter(new FileWriter(args[1]));

			/*
			 * Read the input file by line, convert the expressions, and write
			 * the results to the output file.
			 */
			line = Lab.buf.readLine();
			while (line != null) {
				Lab.pw.println(line); // Print out the input expression.
				for (i = 0; i < line.length(); i++) {
					chr = line.charAt(i);

					/* Check if the character is an uppercase letter. */
					if ((int) chr >= 65 && (int) chr <= 90)
						Lab.pushChar(chr);

					/* Check if the character is a lowercase letter. */
					else if ((int) chr >= 97 && (int) chr <= 122) {
						chr = Character.toUpperCase(chr);
						Lab.pushChar(chr);
					}
					else if (chr == '+')
						Lab.execution("AD");
					else if (chr == '-')
						Lab.execution("SB");
					else if (chr == '*')
						Lab.execution("ML");
					else if (chr == '/')
						Lab.execution("DV");
					else if (chr == '$')
						Lab.execution("EP");
					else if (chr == ' ' || chr == '\t') {
						continue;
					}
					else {
						Lab.error = 2;
						break;
					}
					if (Lab.error == 1)
						break;
				} // End for loop.

				/*
				 * Handle possible errors, empty the stack, and reset the
				 * parameters for the next iteration.
				 */
				if (Lab.error == 1)
					Lab.pw.println("Insufficient operand(s) for: " + line.charAt(i));
				else if (Lab.error == 2)
					Lab.pw.println("Invalid symbol: " + line.charAt(i));
				else if (Lab.stack.size() >= 2) {
					String a = Lab.stack.pop();
					String b = Lab.stack.pop();
					Lab.pw.println("Insufficient operator for: " + b + " and " + a);
				}
				else if (Lab.stack.size() == 1)
					Lab.pw.println("Conversion completed!");
				else // Lab.stack.size() == 0
					Lab.pw.println("(blank line)");

				Lab.stack.cleanUp();
				Lab.n = 1;
				Lab.error = 0;
				Lab.pw.println();
				line = Lab.buf.readLine();
			} // End while loop.
			Lab.buf.close();
			Lab.pw.close();
		}
		catch (IOException ioe) {
			System.err.println(ioe.toString());
			System.exit(2);
		}
		return;
	}

	/**
	 * The private method converts the expression when encountering operators.
	 * It prints out the instructions and stores a TMEPn variable back to the
	 * stack. It sets error to 1 when there is not enough operands.
	 * 
	 * @param op
	 *            A String value specifying the kind of operations.
	 */
	private void execution(String op) {
		String EX = null; // The operand to be executed.
		String LD = null; // The operand to be loaded.

		if (stack.isEmpty() != true)
			EX = stack.pop();
		if (stack.isEmpty() != true)
			LD = stack.pop();
		if (EX == null || LD == null) {
			error = 1;
		}
		else { // (EX != null && LD != null)
			pw.println("LD" + "  " + LD);
			pw.println(op + "  " + EX);
			pw.println("ST" + "  " + "TEMP" + n);
			stack.push("TEMP" + n);
			n++;
		}
		return;
	}

	/**
	 * The private method converts the character into String type, and pushes it
	 * back onto the stack.
	 * 
	 * @param item
	 */
	private void pushChar(Character item) {
		String strgItem;

		strgItem = item.toString();
		stack.push(strgItem);
		return;
	}
}
