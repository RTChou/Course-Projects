/**
 * This class is converted from the recursive method directly, and it uses array
 * structures to simulate the stack that supports recursion.
 * <p>
 * It contains two methods: <i>iterTower2</i> and <i>iter2Time</i>.
 *
 * @version 1.0 2017-03-21
 * @author Renee Ti Chou
 */
public class IterHanoiFromRec {
	private long time1, time2;

	/**
	 * The iterative method which prints out steps of the Hanoi tower of the
	 * assigned size.
	 * 
	 * @param n
	 *            number of disks
	 * @param poleA
	 *            initiation pole
	 * @param poleB
	 *            destination pole
	 * @param poleC
	 *            auxiliary pole
	 * @throws IllegalParameterException
	 *             if n less than 1
	 */
	public void iterTower2(int n, char poleA, char poleB, char poleC) throws IllegalParameterException {
		if (n < 1)
			throw new IllegalParameterException("n less than 1");

		int[] disks; // the int array and char arrays simulate the stack
		char[] polesA, polesB, polesC;

		boolean right; // indicate the right child in binary tree structure
		int disk, ref;
		char auxPole;

		disks = new int[n];
		polesA = new char[n];
		polesB = new char[n];
		polesC = new char[n];
		right = false;

		time1 = System.nanoTime(); // include the timing of the root
		ref = n - 1;

		disks[ref] = n; // put in the information of root into the mimic stack
		polesA[ref] = poleA;
		polesB[ref] = poleB;
		polesC[ref] = poleC;

		disk = n - 1; // go to the left child

		/* inorder traversal of the binary tree */
		while (ref != n || disk != 0) {
			/*
			 * go along the left child branch and put them in the mimic stack
			 * until there is no left child
			 */
			while (disk != 0) {
				ref--;
				if (right == false && ref < n - 1) { // n - 1 because the root
														// is out of loop
					polesA[ref] = polesA[ref + 1];
					polesB[ref] = polesC[ref + 1];
					polesC[ref] = polesB[ref + 1];
				}
				else {
					auxPole = polesA[ref];
					polesA[ref] = polesC[ref];
					polesC[ref] = auxPole;
					right = false;
				}
				disks[ref] = disk; // push in the number of the disk
				disk--;
			}

			/*
			 * if the mimic stack is not empty, pop out the values of the node
			 */
			if (ref != n) {
				System.out.print("Move disk " + disks[ref]);
				System.out.println(" from tower " + polesA[ref] + " to tower " + polesB[ref]);
				disk = disks[ref] - 1; // set right child value to disk
				right = true;
				ref++;
			}
		} // end while

		time2 = System.nanoTime();
	} // end iterTower2

	/**
	 * This method prints out steps of the Hanoi tower and returns the elapsed
	 * time of the run as well.
	 * 
	 * @param n
	 *            number of disks
	 * @return time in nanoseconds
	 */
	public long iter2Time(int n) {
		iterTower2(n, 'A', 'B', 'C');
		return (time2 - time1);
	}
}
