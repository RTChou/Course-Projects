import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * <code>Lab4</code> is a program for sorting integers, which reads in the file
 * to be sorted, and generates a sorted file, in which each value will be
 * printed in one separate line.
 * <p>
 * There are five sorts to be chosen for this sorting program -- 1: Quicksort1;
 * 2: Quicksort2; 3: Quicksort3; 4: Quicksort4; 5: Heapsort.
 * 
 * @version 1.0 2017-05-02
 * @author Renee Ti Chou
 *
 */
public class Lab4 {

	/**
	 * The main entry of the program, which reads in the file, creates an
	 * integer array, sorts the array, and prints the result to the output file.
	 * 
	 * @param args
	 *            [input file path] [output file path]
	 */
	public static void main(String[] args) {
		BufferedReader br1, br2;
		PrintStream ps;
		String stringNums[];
		int nums[]; // the integer array to be sorted
		String line, str, read;
		long time;
		final PrintStream systemOut;

		str = "";
		stringNums = null;
		time = 0L;
		systemOut = System.out;

		/* Check arguments */
		if (args.length != 2) {
			System.err.println("Usage: java Lab4 [input file path] [output file path]");
			System.exit(1);
		}

		/* Read in the file, sort, and print out the result */
		try {
			br1 = new BufferedReader(new FileReader(args[0]));
			ps = new PrintStream(new FileOutputStream(args[1]), true);

			System.setOut(ps); // redirect the standard output

			/* Read all lines and combine to one string */
			line = br1.readLine();
			while (line != null) {
				str = str + line + " ";
				line = br1.readLine();
			}

			/* Echo input */
			System.out.println("Input data: ");
			System.out.println(str);
			System.out.println();

			/* Create string array */
			try {
				stringNums = str.trim().split("\\s+"); // trim and split
			}
			catch (NullPointerException npe) {
				System.err.println("No values in file. ");
				System.exit(2);
			}

			/* Convert to integer array */
			nums = new int[stringNums.length];
			for (int i = 0; i < stringNums.length; i++) {
				try {
					nums[i] = Integer.parseInt(stringNums[i]);
				}
				catch (NumberFormatException nfe) {
					System.setOut(systemOut);
					System.err.println("Input file contains non-integer(s). ");
					System.exit(2);
				}
			}
			br1.close(); // finish reading in file

			/* Prompt the user for types of sort */
			br2 = new BufferedReader(new InputStreamReader(System.in));
			System.setOut(systemOut); // redirect the output stream
			System.out.println("Please enter sorting type #: ");
			System.out.println("(1: Quicksort1; 2: Quicksort2; 3: Quicksort3; 4: Quicksort4; 5: Heapsort)");

			read = br2.readLine();
			br2.close(); // finish reading console input

			System.setOut(ps); // redirect the output stream

			/* Sort the array */
			if (read.equals("1")) {
				Quicksort1 qs1 = new Quicksort1();
				time = qs1.sort(nums);
				System.out.print("Quicksort1 ");
			}
			else if (read.equals("2")) {
				Quicksort2 qs2 = new Quicksort2();
				time = qs2.sort(nums);
				System.out.print("Quicksort2 ");
			}
			else if (read.equals("3")) {
				Quicksort3 qs3 = new Quicksort3();
				time = qs3.sort(nums);
				System.out.print("Quicksort3 ");
			}
			else if (read.equals("4")) {
				Quicksort4 qs4 = new Quicksort4();
				time = qs4.sort(nums);
				System.out.print("Quicksort4 ");
			}
			else if (read.equals("5")) {
				Heapsort hp = new Heapsort();
				time = hp.sort(nums);
				System.out.print("Heapsort ");
			}
			else {
				System.setOut(systemOut); // redirect the output stream
				System.err.println("Invalid input.");
				System.exit(3);
			}

			/* Print out the result and elapsed time for sorting */
			System.out.println("result: ");
			for (int i = 0; i < nums.length; i++) // print the elements in array
				System.out.println(nums[i]);
			System.out.println();
			System.out.println("Number of elements: " + nums.length);
			System.out.println();
			System.out.println("Sorting time: " + time + " (ns)");
			ps.close();
		}
		catch (IOException ioe) {
			System.err.println(ioe.toString());
			System.exit(4);
		}
	} // end main method
} // end class