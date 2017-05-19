import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * <code>Lab2</code> is a program which generates three versions of solution to
 * the Tower of Hanoi problem, with tower size starting from 1 to the assigned
 * number. It prints out the movement steps along with time spent in each tower
 * size. All three versions will be printed into one single output file.
 * <p>
 * The first solution is of recursive version, the second one is of iterative
 * version which involves a data structure to move the disks behind the scene,
 * and the third one is another iterative solution which is directly converted
 * from the recursive one with some minor modifications.
 * <p>
 * The program also provides an option to generate the table of times with tower
 * size from 1 to the assigned number. The output file format is of comma
 * separated values for convenience purposes, and there will be steps printed
 * out to the console.
 * 
 * @version 1.0 2017-03-21
 * @author Renee Ti Chou
 */
public class Lab2 {

	RecHanoi rec;
	IterHanoi iter;
	IterHanoiFromRec iter2;

	/**
	 * The constructor initializes the three different solutions.
	 */
	public Lab2() {
		rec = new RecHanoi();
		iter = new IterHanoi();
		iter2 = new IterHanoiFromRec();
	}

	/**
	 * The main method of the program, which prints the output of the three
	 * solutions into a single file.
	 * 
	 * @param args
	 *            two arguments [number of disks] and [output file path].
	 */
	public static void main(String[] args) {
		Lab2 lab;
		int numDisks;
		PrintStream ps;
		final PrintStream systemOut;
		long recTime, iterTime, iter2Time;

		lab = new Lab2();
		numDisks = 0;
		systemOut = System.out; // Hold the "standard" output stream
		recTime = 0;
		iterTime = 0;
		iter2Time = 0;

		/* Check for the command line arguments. */
		if (args.length != 2) {
			System.err.println("Usage: java Lab2 [number of disks] [output file path]");
			System.exit(1);
		}
		try {
			numDisks = Integer.parseInt(args[0]);
			if (numDisks < 1) {
				System.err.println("The number of disks sould be greater than 0");
				System.exit(1);
			}
		}
		catch (NumberFormatException nfe) {
			System.err.println("The number of disks should be an integer");
			System.exit(1);
		}

		/*
		 * Open the output file and print out the steps of the three solutions
		 */
		try {
			ps = new PrintStream(new FileOutputStream(args[1]), true);
			System.setOut(ps); // Redirect the output stream

			for (int i = 1; i <= numDisks; i++) {
				System.out.println("n = " + i);
				System.out.println();

				System.out.println("The recursive tower: ");
				recTime = lab.rec.recTime(i);
				System.out.println("It takes " + recTime + " nanoseconds to move " + i + " disks");
				System.out.println();

				System.out.println("The iterative tower: ");
				iterTime = lab.iter.iterTime(i);
				System.out.println("It takes " + iterTime + " nanoseconds to move " + i + " disks");
				System.out.println();

				System.out.println("The iterative tower 2: ");
				iter2Time = lab.iter2.iter2Time(i);
				System.out.println("It takes " + iter2Time + " nanoseconds to move " + i + " disks");
				System.out.println();

				System.out.println("------------------- END " + "n = " + i + " --------------------");
				System.out.println();
			} // end for loop
			ps.close();
			System.setOut(systemOut); // Set back to "standard" output stream
		}
		catch (IOException ioe) {
			System.err.println(ioe.toString());
			System.exit(2);
		}

		lab.printTimeTable(); // Can be commented out if not to be used

		return;
	} // end main method

	/**
	 * The private method asks for output file path, and generates a table of
	 * time for each tower size with the three solutions.
	 * <p>
	 * This is only an optional method for creating a file with CSV format for
	 * analysis purposes.
	 * 
	 * @param n
	 *            number of disks
	 */
	private void printTimeTable() {
		BufferedReader br;
		String read;
		int numDisks = 0;
		String outputPath;
		PrintStream ps;

		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Generate table of time with tower size from 1 to any number? (y/n)");

			while (true) { // if not y nor n, keep reading in the input

				read = br.readLine();
				if (read.equals("y")) {
					System.out.println("Please enter output file path for table of time: ");
					outputPath = br.readLine();

					System.out.println("Please enter the number of disks: ");
					try { // error handling
						numDisks = Integer.parseInt(br.readLine());
					}
					catch (NumberFormatException nfe) {
						System.err.println("The number of disks should be an integer");
						System.exit(3);
					}
					br.close();

					/* Open the output stream */
					ps = new PrintStream(new FileOutputStream(outputPath), true);
					for (int i = 1; i <= numDisks; i++) {
						ps.println("Rec," + i + "," + rec.recTime(i));
						ps.println("Iter," + i + "," + iter.iterTime(i));
						ps.println("Iter2," + i + "," + iter2.iter2Time(i));
					}
					ps.close();
					return;
				}

				else if (read.equals("n")) {
					br.close();
					return;
				}
			} // end while
		}
		catch (IOException ioe) {
			System.err.println(ioe.toString());
			System.exit(3);
		}
	} // end printTimeTable
} // end class
