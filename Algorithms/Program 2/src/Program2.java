import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * <code>Program2</code> implements an online auction system, and contains the
 * main entry of the program.
 * <p>
 * It will prompt for the number of the bidding agents, and the output is a
 * trace log of the auction process.
 * <p>
 * It will also prompt to ask whether to generate empirical data of CSV format,
 * where there will be 100 test runs for each of the 6 values of <i>n</i>.
 * 
 * @version 1.0
 * @author Renee Ti Chou
 *
 */
public class Program2 {
	private boolean trace; // switch for trace logs

	/**
	 * The setter for the private field <code>trace</code>.
	 * 
	 * @param trace
	 *            <code>true</code> to turn on trace; <code>false</code> to turn
	 *            it off
	 */
	public void setTrace(boolean trace) {
		this.trace = trace;
	}

	/**
	 * This method performs the online auction. It will generate n distinct
	 * random values for bids, with n provided as input. This simulates the bids
	 * in the order of the random appearance of agents. During the auction
	 * process, the highest bid so far will be maintained as b*.
	 * 
	 * @param n
	 *            the number of bidding agents
	 * @return the number of times b* is updated
	 */
	public int randomizedAuction(int n) {
		int count; // count b* update times
		long bStar, bids[];
		double max; // for limiting the range of random numbers

		count = 0;
		bStar = 0;
		bids = new long[n];
		max = Math.pow(n, 3);

		/* Generate n distinct random values in the range between 1 and n^3 */
		for (int i = 0; i < bids.length; i++)
			bids[i] = (long) (Math.random() * max + 1);

		/* Generate trace logs */
		if (trace == true) {
			System.out.println("================= Trace Log =================");
			System.out.println();
			System.out.println("# Number of bidding agents: ");
			System.out.println("---------------------------");
			System.out.println(n);
			System.out.println();
			System.out.println("# The bids in order are: ");
			System.out.println("------------------------");

			for (int i = 0; i < bids.length; i++)
				System.out.print(bids[i] + " ");

			System.out.println();
			System.out.println();
			System.out.println("# The process of auction: ");
			System.out.println("-------------------------");
			System.out.println("b* initialized as 0");
		} // end code for trace logs

		/*
		 * The bidding process. The bids are read in from the bids array in turn
		 * to compare with b*.
		 */
		for (int i = 0; i < bids.length; i++) {

			if (trace == true) {
				System.out.println();
				System.out.println("bid = " + bids[i] + "...");
			}

			if (bids[i] > bStar) {
				bStar = bids[i];
				count++;

				if (trace == true) {
					System.out.println(bids[i] + " > b*");
					System.out.println("b* updated");
				}
			} // end if

			else {
				if (trace == true) {
					System.out.println(bids[i] + " < b*");
					System.out.println("No update");
				}
			} // end else

			/* Brief summary for this run. */
			if (trace == true) {
				System.out.println("Update times: " + count);
				System.out.println("b* is now: " + bStar);
			}
		} // end for

		return count; // return total update times
	} // end randomizedAuction

	/**
	 * The main method of <code>Program2</code>.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Program2 program2;
		BufferedReader br;
		PrintStream ps1;
		String line;
		int n; // number of agents
		final PrintStream systemOut;

		program2 = new Program2();
		systemOut = System.out; // hold the standard output stream

		/* Check for command line argument */
		if (args.length != 1) {
			System.err.println("Usage: java Program2 [trace file path]");
			System.exit(1);
		}

		try {
			/*
			 * Get the number of bidding agents, perform the auction, and output
			 * the trace to the file.
			 */
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please enter number of bidding agents: ");
			n = Integer.parseInt(br.readLine()); // get the number of agents

			ps1 = new PrintStream(new FileOutputStream(args[0]));
			System.setOut(ps1); // redirect the output stream

			program2.setTrace(true); // turn on trace
			program2.randomizedAuction(n); // perform auction

			System.setOut(systemOut); // redirect stdout back
			ps1.close();

			/*
			 * Generate empirical data through 100 test runs for each of the 6
			 * values of n. The output format is CSV.
			 */
			System.out.println("Generate empirical data? [y/n]");

			while (true) { // if not y nor n, keep reading the input
				line = br.readLine();

				if (line.equals("y")) {
					int numArray[], sumArray[], testRuns, updateTimes;
					String filePath;
					PrintStream ps2;

					/* Set parameters for testing */
					numArray = new int[6]; // to store the 6 values of n
					sumArray = new int[6];
					testRuns = 100;

					System.out.println("Please enter output file path");
					filePath = br.readLine();

					ps2 = new PrintStream(new FileOutputStream(filePath));
					System.setOut(ps2);
					program2.setTrace(false);

					/*
					 * Create the values of n and output the header line of the
					 * data
					 */
					System.out.print("n,");

					numArray[0] = 10; // start from 10
					System.out.print(numArray[0] + ",");

					// The next value of n is 5 times the previous value of n
					for (int i = 1; i < numArray.length; i++) {
						numArray[i] = numArray[i - 1] * 5;
						System.out.print(numArray[i] + ",");
					}
					System.out.println();

					/*
					 * Generate 100 test runs. In each run the auction is
					 * performed on each value of n.
					 */
					for (int i = 1; i <= testRuns; i++) {
						System.out.print("Test " + i + ",");

						// run on each value of n
						for (int j = 0; j < numArray.length; j++) {
							updateTimes = program2.randomizedAuction(numArray[j]);
							System.out.print(updateTimes + ","); // output data

							sumArray[j] += updateTimes;
							// to sum the update times of all test runs of each
							// value of n for average calculation
						}
						System.out.println();

					} // end outer for loop

					/* Calculate average for each value of n */
					System.out.print("Average,");
					for (int i = 0; i < sumArray.length; i++)
						System.out.format("%.2f,", sumArray[i] / (double) testRuns);
					System.out.println();

					/* Calculate theoretical values of upper bound */
					System.out.print("Upper Bound,");
					for (int i = 0; i < numArray.length; i++)
						// (1 + ln n)
						System.out.format("%.2f,", 1 + Math.log(numArray[i]));
					System.out.println();

					/* Calculate theoretical values of lower bound */
					System.out.print("Lower Bound,");
					for (int i = 0; i < numArray.length; i++)
						// ln (n + 1)
						System.out.format("%.2f,", Math.log(numArray[i] + 1));
					System.out.println();

					/* Finish the program */
					System.setOut(systemOut);
					ps2.close();
					br.close();
					return;
				} // end if equals "y"

				else if (line.equals("n")) {
					br.close();
					return;
				}

			} // end while
		} // end try
		catch (IOException ioe) {
			System.err.println(ioe.toString());
			System.exit(2);
		}
	} // end main method
} // end class
