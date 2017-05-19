import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * <code>HuffmanEncoding</code> contains methods for Huffman encoding and
 * decoding
 * 
 * @version 1.0 
 * @author Renee Ti Chou
 *
 */
public class HuffmanEncoding {
	private Node[] freqTable;
	private HuffmanTree huffTree;

	/**
	 * The constructor initializes <code>freqTable</code>
	 */
	public HuffmanEncoding() {
		freqTable = new Node[27];
	}

	/**
	 * The method reads in the frequency table file by line and store the values
	 * into a node array to be passed to create <code>huffTree</code>.
	 * <p>
	 * Only the first 27 informative (not blank) lines in the input file will be
	 * read in and stored in <code>freqTable</code>
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void makeFreqTable(String filePath) throws IOException {
		String line;
		String[] params; // to store single letter and frequency
		char ch;
		int freq, lineNum; // keep track of line being read

		freq = -1;
		lineNum = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			line = br.readLine();
			/* only read up to 27 lines */
			while (line != null && lineNum != freqTable.length) {

				/* skip blank line */
				if (line.equals("")) {
					line = br.readLine();
					continue;
				}

				/* check the line read in is in correct format */
				params = line.split(",");

				if (params.length != 2) {
					System.err.println("Frequency table: parameters less or more than 2 in line \"" + line + "\"");
					System.exit(3);
				}
				if (params[0].length() != 1) {
					System.err.println("Frequency table: character not a singel letter in line \"" + line + "\"");
					System.exit(3);
				}
				try {
					freq = Integer.parseInt(params[1]);

				}
				catch (NumberFormatException nfe) {
					System.err.println(
							"Frequency table: frequency not an integer or contains space in line \"" + line + "\"");
					System.exit(3);
				}
				if (freq < 0) {
					System.err.println("Frequency table: freq < 0 in line " + line);
					System.exit(3);
				}

				/*
				 * check and store the values in freqTable. The separator will
				 * be put into freqTable[0], A-Z into freqTable[1-27]
				 */
				ch = Character.toUpperCase(params[0].charAt(0));
				// allows for flexibility of lower case letters

				if (ch >= 65 && ch <= 90) {
					if (freqTable[ch - 64] != null) {
						System.err.println("Duplates of \"" + ch + "\" in frequencey table.");
						System.exit(3);
					}
					freqTable[ch - 64] = new Node(Character.toString(ch), freq);
				}
				else {
					if (freqTable[0] != null) {
						System.err.println("More than one sperator in frequency table.");
						System.exit(3);
					}
					freqTable[0] = new Node(params[0], freq);
				}
				lineNum++;
				line = br.readLine();
			} // end while

			/*
			 * print in the output file the warning message when more
			 * informative lines are presented after the 27th line in the input
			 */
			while (line != null) {
				if (!line.equals("")) {
					System.out.println("!!Warning: only the first 27 lines in frequency table "
							+ "are used to construct Huffman tree. ");
					System.out.println();
					break;
				}
				line = br.readLine();
			} // end while
		}
		catch (IOException ioe) {
			throw ioe;
		}
	}

	/**
	 * This method echoes the input frequency table by printing out
	 * <code>freqTable</code>
	 */
	public void printFreqTable() {
		String n = System.getProperty("line.separator"); // instead of println()
		System.out.println("+--------------+-----------+");
		System.out.println("| Character    | Frequency |");
		System.out.println("+--------------+-----------+");
		if (freqTable[0] != null)
			System.out.printf("| %-13s|%10d |" + n, freqTable[0].getData() + "(separater)", freqTable[0].getFreq());
		for (int i = 1; i < freqTable.length; i++) {
			if (freqTable[i] == null)
				continue;
			System.out.printf("| %-13s|%10d |" + n, freqTable[i].getData(), freqTable[i].getFreq());
		}
		System.out.println("+--------------+-----------+");
	}

	/**
	 * pass the <code>freqTable</code> to <code>HuffmanTree</code> constroctor
	 * to make the tree
	 */
	public void makeHuffmanTree() {
		huffTree = new HuffmanTree(freqTable);
		huffTree.makeTree();
	}

	/**
	 * print the Huffman tree
	 */
	public void printHuffmanTree() {
		huffTree.printTree();
	}

	/**
	 * print <code>codeTable</code> in <code>hufftree</code> with annotaion of
	 * data from <code>freqTable</code>
	 */
	public void printCodeTable() {
		String n = System.getProperty("line.separator");
		String[] codeTable = huffTree.getCodeTable();
		System.out.println("+-----------+-------------+");
		System.out.println("| Character | Code        |");
		System.out.println("+-----------+-------------+");
		for (int i = 1; i < freqTable.length; i++) {
			if (freqTable[i] != null)
				System.out.printf("| %-10s|%12s |" + n, freqTable[i].getData(), codeTable[i]);
		}
		if (freqTable[0] != null)
			System.out.printf("| %-10s|%12s |" + n, freqTable[0].getData(), codeTable[0]);
		System.out.println("+-----------+-------------+");
	}

	/**
	 * Encode the message file and print out the result
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void encodeFile(String filePath) throws IOException {
		String line, codeString, n;
		char ch, separater;
		String[] codeTable;
		int count; // count number of characters being encoded

		n = System.getProperty("line.separator");
		separater = 0;
		codeTable = huffTree.getCodeTable();

		/* get the separator if there is one */
		if (freqTable[0] != null)
			separater = freqTable[0].getData().charAt(0);

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			line = br.readLine();
			while (line != null) {

				if (line.equals("")) { // skip blank line
					line = br.readLine();
					continue;
				}

				codeString = "";
				count = 0;
				System.out.println("-----------------------------------------------------------+");
				System.out.println("# Input: " + line + n); // echo the input

				/*
				 * encode the characters in line and add the result to
				 * codeString
				 */
				for (int i = 0; i < line.length(); i++) {
					ch = line.charAt(i);
					ch = Character.toUpperCase(ch);
					if (ch >= 65 && ch <= 90 && codeTable[ch - 64] != null) {
						codeString += codeTable[ch - 64];
						count++;
					}
					else if (ch == separater && codeTable[0] != null) {
						codeString += codeTable[0];
						count++;
					}
				} // end for loop
				System.out.println("# Output: " + codeString + n);
				System.out.print("(" + count + " characters in " + codeString.length() + " bits, ");
				System.out.printf("%.2f bits/character.)" + n, (double) codeString.length() / count);
				line = br.readLine();
			}
		}
		catch (IOException ioe) {
			throw ioe;
		}
	}

	/**
	 * decode the code file using the tree in <code>huffTree</code> and print
	 * out the result
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void decodeFile(String filePath) throws IOException {
		String line, message, n;
		Node p; // node pointer
		char ch; // 0's and 1's in the code string
		int error;

		n = System.getProperty("line.separator");
		error = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			line = br.readLine();
			while (line != null) {

				if (line.equals("")) { // skip blank line
					line = br.readLine();
					continue;
				}

				message = "";
				p = huffTree.getRoot();
				System.out.println("-----------------------------------------------------------+");
				System.out.println("# Input: " + line + n); // echo the input
				line = line.replaceAll(" ", ""); // remove any space in the line

				/*
				 * decode the string and add the result to message
				 */
				for (int i = 0; i < line.length(); i++) {
					ch = line.charAt(i);
					if (ch == '0' && p.getLeft() != null)
						p = p.getLeft();
					else if (ch == '1' && p.getRight() != null)
						p = p.getRight();
					else if (ch != '0' || ch != '1') { // error checking
						error = 1;
					}
					if (p.getLeft() == null && p.getRight() == null) {
						message += p.getData();
						p = huffTree.getRoot();
					}
				} // end for loop

				/* check error */
				if (error == 1) {
					System.out.println("# Output: " + message + n);
					System.out.println("!!Wrong format: Should contain only 0's and 1's." + n);
					error = 0;
					line = br.readLine();
					continue;
				}

				System.out.println("# Output: " + message + n);
				System.out.print("(" + message.length() + " characters in " + line.length() + " bits, ");
				System.out.printf("%.2f bits/character.)" + n, (double) line.length() / message.length());
				line = br.readLine();
			}
		}
		catch (IOException ioe) {
			throw ioe;
		}
	} // end decodeFile method
} // end class
