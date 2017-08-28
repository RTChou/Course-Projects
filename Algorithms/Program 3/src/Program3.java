import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * <code>Program3</code>implements the two algorithm for calculating the optimal
 * plan of jobs of n weeks. One of them utilizes dynamic programming, and the
 * other is a greedy algorithm.
 * <p>
 * The program reads in a file of specific format containing series of revenues
 * of low stress jobs and high stress jobs for every week, runs the
 * implementations of the two algorithms, outputs the actual plans and values,
 * and compares the difference between the two results.
 * 
 * @version 1.0
 * @author Renee Ti Chou
 *
 */
public class Program3 {
	/**
	 * The main method of <code>Program3</code>, which checks the format of
	 * input file, read in two lines of the input data in each iteration,
	 * performs the two job planning algorithms, and output the result.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JobPlanner jp;
		BufferedReader br;
		PrintStream ps;
		String line1, line2; // to read two lines each time
		String[] plan1, plan2;
		int[] low, high; // low stress and high stress jobs
		int n; // number of weeks
		final PrintStream systemOut;

		jp = new JobPlanner(true); // true to turn on trace
		systemOut = System.out; // hold the standard output stream

		/* Check for command line arguments */
		if (args.length != 2) {
			System.err.println("Usage: java Program3 [input file path][output file path]");
			System.exit(1);
		}

		/* Address input file and generate output */
		try {
			br = new BufferedReader(new FileReader(args[0]));
			ps = new PrintStream(new FileOutputStream(args[1]));
			System.setOut(ps); // redirect the standard output

			int input = 1;
			// read in two lines each time
			while ((line1 = br.readLine()) != null && (line2 = br.readLine()) != null) {
				low = null; // initialize
				high = null;
				n = 0;

				/* Address input data */
				if (line1.startsWith("L") && line2.startsWith("H")) {
					// echo input
					System.out.println("========== Input " + input + " ==============");
					System.out.println();
					System.out.println(line1);
					System.out.println(line2);
					System.out.println();

					// store the input data into integer arrays
					low = convertToIntArray(line1.trim().split("\\s+"));
					high = convertToIntArray(line2.trim().split("\\s+"));
					if (low.length != high.length) {
						System.out.println(
								"Error: The numbers of low stress and high stress jobs are not equal for input "
										+ input);
						System.out.println("\n");
						input++;
						continue;
					}
					n = low.length - 1; // assign value of number of weeks
				} // end if
				else if (line1.trim().isEmpty()) // to skip trailing blank lines
					break;
				else { // error handling
					System.setOut(systemOut);
					System.err.println("Invalid format. Please refer to README.txt");
					System.exit(2);
				}

				/* Perform the job planning algorithms and output the results */
				System.out.println("========== Output " + input + " =============");
				System.out.println();
				System.out.println("1. Optimal Job Planning Algorithm");
				System.out.println("---------------------------------");
				System.out.println();
				plan1 = jp.OptimalJobPlanning(low, high, n);
				System.out.println();
				System.out.println("2. Flawed Job Planning Algorithm");
				System.out.println("--------------------------------");
				System.out.println();
				plan2 = jp.flawedJobPlanning(low, high, n);
				// examine where the flawed algorithm breaks down
				checkPlan(plan1, plan2);
				System.out.println("\n");
				input++; // continue reading the next two series of job data
			} // end while

			/* Finish the program */
			System.setOut(systemOut);
			ps.close();
			br.close();
		} // end try
		catch (IOException ioe) {
			System.err.println(ioe.toString());
			System.exit(3);
		}
	} // end main

	/**
	 * This private method converts a String array into an integer array.
	 * 
	 * @param stringArray
	 *            the String array
	 * @return an integer array
	 */
	private static int[] convertToIntArray(String[] stringArray) {
		int len = stringArray.length;
		int[] intArray = new int[len];
		for (int i = 1; i < len; i++)
			intArray[i] = Integer.parseInt(stringArray[i]);
		return intArray;
	}

	/**
	 * This private checks in which week the flawed algorithm breaks down and
	 * prints out the result.
	 * 
	 * @param correctPlan
	 * @param flawedPlan
	 */
	private static void checkPlan(String[] correctPlan, String[] flawedPlan) {
		for (int i = 1; i < correctPlan.length; i++) {
			if (!correctPlan[i].equals(flawedPlan[i])) {
				System.out.println("# The flawed algorithm breaks down in week " + i);
				return;
			}
		} // end for
		System.out.println("# The plan is the same as the correct one");
	}
} // end class
