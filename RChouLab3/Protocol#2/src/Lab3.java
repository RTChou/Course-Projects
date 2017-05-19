import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * <code>Lab3</code> is a program of Huffman Encoding.
 * <p>
 * It reads in a frequency table file, a message file, and a code file, and then
 * builds a Huffman tree to encode the message strings and decode the code
 * strings. It will print out the results in one output file, along with the
 * tree in preorder and the code table it generates.
 * 
 * @version 1.0
 * @author Renee Ti Chou
 */
public class Lab3 {

	/**
	 * The main method of the program
	 * 
	 * @param args
	 *            [frequency table file path] [message file path] [code file
	 *            path] [output file path]
	 */
	public static void main(String[] args) {
		final PrintStream systemOut = System.out;
		HuffmanEncoding huff = new HuffmanEncoding();
		// which addresses the input files

		/* Check for command line arguments. */
		if (args.length != 4) {
			System.err.println("Usage: java Lab3 [frequency table file path] [message file path]"
					+ "[code file path] [output file path]");
			System.exit(1);
		}

		/*
		 * Passing arguments to corresponding methods and formatting the output
		 */
		try (PrintStream ps = new PrintStream(new FileOutputStream(args[3]), true)) {
			System.setOut(ps); // redirect the standard output

			huff.makeFreqTable(args[0]);
			huff.makeHuffmanTree();

			System.out.println("## Your input frequency table is: ");
			huff.printFreqTable();
			System.out.println();
			System.out.println(
					"========================================================================================");
			System.out.println();

			System.out.println("## The Huffman tree in preorder is: ");
			huff.printHuffmanTree();
			System.out.println();
			System.out.println(
					"========================================================================================");
			System.out.println();

			System.out.println("## The code table is: ");
			huff.printCodeTable();
			System.out.println();
			System.out.println(
					"========================================================================================");
			System.out.println();

			/* encode the file */
			System.out.println("## The results of encoding the file \"" + args[1] + "\" are: ");
			System.out.println();
			huff.encodeFile(args[1]);
			System.out.println(
					"========================================================================================");
			System.out.println();

			/* decode the file */
			System.out.println("## The results of decoding the file \"" + args[2] + "\" are: ");
			System.out.println();
			huff.decodeFile(args[2]);

			System.setOut(systemOut);
		}
		catch (IOException ioe) {
			System.err.println(ioe.toString());
			System.exit(2);
		}
	} // end main
} // end class
