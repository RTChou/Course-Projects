/**
 * The <code>JobPlanner</code> class contains two algorithms for planning the
 * jobs for n weeks. The correct one is implemented as
 * <code>OptimalJobPlanning()</code>, and the incorrect one is implemented as
 * <code>flawedJobPlanning()</code>. Both of these methods would print out the
 * value of the optimal plan they calculate and return the actual plan as a
 * String array.
 * 
 * @version 1.0
 * @author Renee Ti Chou
 *
 */
public class JobPlanner {
	private boolean traceOn; // switch for trace log

	/**
	 * Constructor for <code>JobPlanner</code> class.
	 */
	public JobPlanner() {
	}

	/**
	 * Constructor for <code>JobPlanner</code> class.
	 * 
	 * @param trace
	 *            true to turn on; false to turn off
	 */
	public JobPlanner(boolean trace) {
		traceOn = trace;
	}

	/**
	 * This method implement the correct algorithm, which performs dynamic
	 * programming with the recursive formula values[i] = max(low[i] +
	 * values[i-1], high[i] + values[i-2]) when i >= 2; max(low[1], high[1])
	 * when i = 1; 0 when i = 0.
	 * 
	 * @param low
	 *            integer array of low stress jobs
	 * @param high
	 *            integer array of high stress jobs
	 * @param n
	 *            the number of weeks for the plan
	 * @return the actual plan as a String array
	 */
	public String[] OptimalJobPlanning(int low[], int high[], int n) {
		/* Initialization */
		int values[] = new int[n + 1]; // to store maximum values of the plan
		String jobs[] = new String[n + 1]; // to store information for
											// constructing the actual plan
		String plan[] = new String[n + 1];

		tracePrint(low, "l", 1); // print low[]
		tracePrint(high, "h", 1); // print high[]
		tracePrint("let v[0.." + n + "] and j[0.." + n + "] be new arrays");

		/* Calculate the value of each entry in values[] */
		// when i = 0
		tracePrint("i = 0");
		values[0] = 0;
		tracePrint("v[0] = 0");

		// when i = 1, choose either low stress or high stress job
		tracePrint("i = 1");
		if (high[1] > low[1]) {
			values[1] = high[1];
			jobs[1] = "H";
			tracePrint(
					"max(l[1], h[1]) = max(" + low[1] + ", " + high[1] + ") ==> v[1] = " + high[1] + ", j[1] = \"H\"");
		}
		else { // if equal, chose low stress job
			values[1] = low[1];
			jobs[1] = "L";
			tracePrint(
					"max(l[1], h[1]) = max(" + low[1] + ", " + high[1] + ") ==> v[1] = " + low[1] + ", j[1] = \"L\"");
		}

		// when i >= 2
		for (int i = 2; i <= n; i++) {
			tracePrint("i = " + i);
			int lowValue = low[i] + values[i - 1];
			int highValue = high[i] + values[i - 2];

			if (highValue > lowValue) {
				values[i] = highValue;
				jobs[i] = "H";
				tracePrint("max(l[" + i + "] + v[" + (i - 1) + "], h[" + i + "] + v[" + (i - 2) + "]) = max(" + lowValue
						+ ", " + highValue + ") ==> v[" + i + "] = " + highValue + ", j[" + i + "] = \"H\"");
			}
			else { // if equal, chose low stress job
				values[i] = lowValue;
				jobs[i] = "L";
				tracePrint("max(l[" + i + "] + v[" + (i - 1) + "], h[" + i + "] + v[" + (i - 2) + "]) = max(" + lowValue
						+ ", " + highValue + ") ==> v[" + i + "] = " + lowValue + ", j[" + i + "] = \"L\"");
			}
		} // end for
		tracePrint(values, "v", 0); // print values[]
		tracePrint(jobs, "j", 0); // print jobs[]
		tracePrint();

		/* Brief summary of the optimal plan and its value */
		System.out.println("# The optimal plan is:");
		System.out.println();
		printOptimalPlan(jobs, n, plan); // retrieve information from jobs[] and
											// store it in plan[]
		printArray(plan, "plan", 1); // print plan[]
		System.out.println();
		System.out.println("# The maximum value is: " + values[n]);
		System.out.println();
		return plan;
	}

	/**
	 * This method recursively retrieves the information in jobs array generated
	 * by the <code>OptimalJobPlanning()</code> method, and stores it in the
	 * plan array.
	 * 
	 * @param jobs
	 *            an String array
	 * @param index
	 *            the index of week
	 * @param plan
	 *            an String array
	 */
	private void printOptimalPlan(String[] jobs, int index, String[] plan) {
		if (index == 0) { // base case
			tracePrint("i = 0, return");
			tracePrint();
			return;
		}
		if (index == 1) { // base case
			if (jobs[index].equals("H")) {
				tracePrint("j[1] = \"H\", return");
				plan[index] = "H";
			}
			else {
				tracePrint("j[1] = \"L\", return");
				plan[index] = "L";
			}
			tracePrint();
			return;
		}
		if (jobs[index].equals("H")) {
			tracePrint("j[" + index + "] = \"H\" ==> find job at j[" + (index - 2) + "]");
			printOptimalPlan(jobs, index - 2, plan); // recursive call
			plan[index - 1] = "NA";
			plan[index] = "H";
		}
		else {
			tracePrint("j[" + index + "] = \"L\" ==> find job at j[" + (index - 1) + "]");
			printOptimalPlan(jobs, index - 1, plan); // recursive call
			plan[index] = "L";
		}
	} // end method

	/**
	 * This method implements the flawed algorithm, which is essentially a
	 * greedy algorithm.
	 * 
	 * @param low
	 *            integer array of low stress jobs
	 * @param high
	 *            integer array of high stress jobs
	 * @param n
	 *            the number of weeks for the plan
	 * @return the actual plan as a String array
	 */
	public String[] flawedJobPlanning(int low[], int high[], int n) {
		int maxValue = 0; // to add on the best value so far in each iteration
		String[] plan = new String[n + 1];

		System.out.println("# The optimal plan is:");
		System.out.println();
		tracePrint(low, "l", 1); // print low[]
		tracePrint(high, "h", 1); // print high[]

		int i = 1;
		while (i <= n) {
			if (i < n) {
				tracePrint("i = " + i);
				if (high[i + 1] > low[i] + low[i + 1]) {
					maxValue += high[i + 1];
					plan[i] = "NA";
					plan[i + 1] = "H";
					tracePrint("h[" + (i + 1) + "] > l[" + i + "] + l[" + (i + 1) + "] ==> choose no job in week " + i
							+ ", high stress job in week " + (i + 1));

					if ((i + 2) <= n) // if not the loop will terminate
						tracePrint("continue with iteration " + (i + 2));

					i += 2;
					continue;
				}
				else {
					maxValue += low[i];
					plan[i] = "L";
					tracePrint("h[" + (i + 1) + "] <= l[" + i + "] + l[" + (i + 1)
							+ "] ==> choose low stress job in week " + i);
					tracePrint("continue with iteration " + (i + 1)); // since i < n
					i++;
					continue;
				}
			} // end if
			else {
				tracePrint("i = " + i + " = n");
				maxValue += low[i];
				plan[i] = "L";
				tracePrint("find job for the last week ==> choose low stress job in week " + i);
				break;
			}
		} // end while
		tracePrint();

		/* Brief summary of the optimal plan and its value */
		printArray(plan, "plan", 1);
		System.out.println();
		System.out.println("# The maximum value is: " + maxValue);
		System.out.println();
		return plan;
	} // end flawed planning method

	/* Section of private methods */

	/**
	 * This private method prints the trace log in the format of putting
	 * "Trace:" in front of the string to be printed.
	 * 
	 * @param str
	 *            the string to be printed out
	 */
	private void tracePrint(String str) {
		if (traceOn == true)
			System.out.println("Trace: " + str);
	}

	/**
	 * This private method prints the integer array in the format of trace log.
	 * 
	 * @param array
	 *            the integer array
	 * @param name
	 *            the name of the array
	 * @param start
	 *            the index of the array starts at
	 */
	private void tracePrint(int[] array, String name, int start) {
		if (traceOn == true) {
			System.out.print("Trace: ");
			printArray(array, name, start);
		}
	}

	/**
	 * This private method prints the String array in the format of trace log.
	 * 
	 * @param array
	 *            the String array
	 * @param name
	 *            the name of the array
	 * @param start
	 *            the index of the array starts at
	 */
	private void tracePrint(String[] array, String name, int start) {
		if (traceOn == true) {
			System.out.print("Trace: ");
			printArray(array, name, start);
		}
	}

	/**
	 * This private method prints out a blank line if the trace is turned on.
	 */
	private void tracePrint() {
		if (traceOn == true)
			System.out.println();
	}

	/**
	 * This private method prints out the integer Array in one line.
	 * 
	 * @param array
	 *            the integer array
	 * @param name
	 *            the name of the array
	 * @param start
	 *            the index of the array starts at
	 */
	private void printArray(int[] array, String name, int start) {
		System.out.print(name + "[");
		for (int i = start; i < array.length - 1; i++)
			System.out.print(array[i] + ", ");
		System.out.println(array[array.length - 1] + " ] (array starts at " + start + ")");
	}

	/**
	 * This private method prints out the String array in one line.
	 * 
	 * @param array
	 *            the String array
	 * @param name
	 *            the name of the array
	 * @param start
	 *            the index of the array starts at
	 */
	private void printArray(String[] array, String name, int start) {
		System.out.print(name + "[");
		for (int i = start; i < array.length - 1; i++)
			System.out.print(array[i] + ", ");
		System.out.println(array[array.length - 1] + " ] (array starts at " + start + ")");
	}

} // end class
