import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * <code>Program1</code> contains the main entry to conduct the program of AVL
 * trees. It reads in two files containing sorted integer numbers, makes two AVL
 * trees, merges them into one tree, and generates the result to an output file
 * along with inputs and trace logs.
 * 
 * @version 1.0
 * @author Renee Ti Chou
 *
 */
public class Program1 {

	/**
	 * The main method of <code>Program1</code>.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader br;
		PrintStream ps;
		String inputFilePath1, inputFilePath2, outputFilePath;
		int[] array1, array2;
		AVLTree tree1, tree2, tree, testTree;

		try {

			/*
			 * Get input and output files information, and convert inputs into
			 * integer arrays.
			 */
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("File path for first sorted input: ");
			inputFilePath1 = br.readLine();
			array1 = createIntegerArray(inputFilePath1); // convert to array

			System.out.println("File path for second sorted input: ");
			inputFilePath2 = br.readLine();
			array2 = createIntegerArray(inputFilePath2); // convert to array

			System.out.println("File path for output: ");
			outputFilePath = br.readLine();
			ps = new PrintStream(new FileOutputStream(outputFilePath));

			br.close(); // finish getting information from user
			System.setOut(ps); // redirect the standard output

			/* Echo inputs */
			System.out.println("==== Input #1 =================================");
			System.out.println();
			for (int i = 0; i < array1.length; i++)
				System.out.print(array1[i] + " ");
			System.out.println();
			System.out.println();

			System.out.println("==== Input #2 =================================");
			System.out.println();
			for (int j = 0; j < array2.length; j++)
				System.out.print(array2[j] + " ");
			System.out.println();
			System.out.println();

			/* Conduct the tasks and create trace logs */
			System.out.println("==== Trace Logs ===============================");
			System.out.println();

			System.out.println("-- 1. Converting sorted array into AVL tree");
			System.out.println();
			System.out.println("Input #1");
			System.out.println("--------");
			tree1 = new AVLTree(array1);
			System.out.println();
			System.out.println("Input #2");
			System.out.println("--------");
			tree2 = new AVLTree(array2);
			System.out.println();
			System.out.println();

			System.out.println("-- 2. Converting AVL tree to sorted array");
			System.out.println();
			System.out.println("Input #1");
			System.out.println("--------");
			tree1.convertToSortedArray();
			System.out.println();
			System.out.println("Input #2");
			System.out.println("--------");
			tree2.convertToSortedArray();
			System.out.println();
			System.out.println();

			System.out.println("-- 3. Merging two AVL trees");
			System.out.println();
			tree = tree1.mergeWith(tree2);
			System.out.println();
			System.out.println();

			System.out.println("-- 4. Additional tests");
			System.out.println();
			testTree = new AVLTree();
			testTree.insert(6);
			testTree.insert(2);
			testTree.insert(5);
			testTree.printTree();
			System.out.println();

			/* Generate the output result */
			System.out.println("==== Output ===================================");
			System.out.println();
			System.out.println("-- Result of merging Input #1 and Input #2");
			System.out.println();
			tree.printTree();
			System.out.println();

//			/* Code for analysis */
//			System.out.println("==== Data for Analysis ========================");
//			System.out.println();
//			System.out.println("-- Result of type 1 merge (O(mlg(m+n)))");
//			System.out.println();
//			tree1 = new AVLTree(array1);
//			tree2 = new AVLTree(array2);
//			System.out.println();
//			System.out.println("## Merging data ##");
//			tree1.type1Merge(tree2);
//			System.out.println();
//
//			System.out.println("-- Result of type 2 merge (O(nlg(m+n)))");
//			System.out.println();
//			tree1 = new AVLTree(array1);
//			tree2 = new AVLTree(array2);
//			System.out.println();
//			System.out.println("## Merging data ##");
//			tree1.type2Merge(tree2);
//			System.out.println();
//
//			System.out.println("-- Result of type 3 merge (O(m+n))");
//			System.out.println();
//			tree1 = new AVLTree(array1);
//			tree2 = new AVLTree(array2);
//			System.out.println();
//			System.out.println("## Merging data ##");
//			tree1.type3Merge(tree2);
//			System.out.println();

			ps.close(); // finish writing output
		}
		catch (IOException ioe) {
			System.err.println(ioe.toString());
			System.exit(1);
		}
	} // end main method

	/**
	 * This private static method reads in the input file and stores the numbers
	 * into an integer array.
	 * 
	 * @param filePath
	 *            the input file path
	 * @return an integer array
	 */
	private static int[] createIntegerArray(String filePath) {
		String integerString, line, stringNums[];
		int[] intArray;

		/* Initialization */
		intArray = null;
		integerString = "";

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) { // auto-close

			/* Read all lines and combine them into one string */
			line = br.readLine();
			while (line != null) {
				integerString = integerString + line + " ";
				line = br.readLine();
			}

			/* Create a string array from integerString */
			stringNums = integerString.trim().split("\\s+"); // trim and split

			/* Convert the string array to an integer array */
			intArray = new int[stringNums.length];
			for (int i = 0; i < stringNums.length; i++)
				intArray[i] = Integer.parseInt(stringNums[i]);

		}
		catch (IOException ioe) {
			System.err.println(ioe.toString());
			System.exit(1);
		}
		return intArray;

	} // end createIntegerArray method
} // end Program1 class
