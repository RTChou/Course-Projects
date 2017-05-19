/**
 * Uses the array structure to simulate the tower, moves disks actually, and
 * solves the problem iteratively.
 * <p>
 * It contains two methods: <i>iterTower</i> and <i>iterTime</i>.
 * 
 * @version 1.0 2017-03-21
 * @author Renee Ti Chou
 */
public class IterHanoi {
	private int[] A, B, C; // represent the three poles
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
	public void iterTower(int n, char poleA, char poleB, char poleC) throws IllegalParameterException {
		if (n < 1)
			throw new IllegalParameterException("n less than 1");

		double numMovements, cycleMovements;

		A = new int[n + 3];
		B = new int[n + 3];
		C = new int[n + 3];

		for (int i = 1; i <= n; i++) // put in the disks to poleA
			A[i] = i;

		A[0] = 1; // reference to the top of the pole
		B[0] = n + 1;
		C[0] = n + 1;

		A[n + 1] = -1; // indicate the pole is bottom of the pole
		B[n + 1] = -1;
		C[n + 1] = -1;

		A[n + 2] = poleA; // store the name of the pole
		B[n + 2] = poleB;
		C[n + 2] = poleC;

		numMovements = Math.pow(2, n) - 1;
		cycleMovements = numMovements / 3; // to reduce the number of iteration
		if (n % 2 == 1) {
			time1 = System.nanoTime();

			for (int i = 1; i <= cycleMovements; i++) {
				moveBetweenAB();
				moveBetweenAC();
				moveBetweenBC();
			}
			if (numMovements % 3 == 1)
				moveBetweenAB();
			else if (numMovements % 3 == 2) {
				moveBetweenAB();
				moveBetweenAC();
			}
			time2 = System.nanoTime();
		}
		else {
			time1 = System.nanoTime();

			for (int i = 1; i <= cycleMovements; i++) {
				moveBetweenAC();
				moveBetweenAB();
				moveBetweenBC();
			}
			if (numMovements % 3 == 1)
				moveBetweenAC();
			else if (numMovements % 3 == 2) {
				moveBetweenAC();
				moveBetweenAB();
			}
			time2 = System.nanoTime();
		}
	} // end iterTower

	/*
	 * A set of three private methods containing conditions to decide the
	 * movement direction. A[0], B[0], C[0] are references to the top disks of
	 * each pole.
	 */
	private void moveBetweenAB() {
		if ((A[A[0]] < B[B[0]] || B[B[0]] == -1) && A[A[0]] != -1) {
			move(A, B); // the actual move
		} // AB
		else if ((B[B[0]] < A[A[0]] || A[A[0]] == -1) && B[B[0]] != -1) {
			move(B, A);
		} // BA
	}

	private void moveBetweenAC() {
		if ((A[A[0]] < C[C[0]] || C[C[0]] == -1) && A[A[0]] != -1) {
			move(A, C);
		} // AC
		else if ((C[C[0]] < A[A[0]] || A[A[0]] == -1) && C[C[0]] != -1) {
			move(C, A);
		} // CA
	}

	private void moveBetweenBC() {
		if ((B[B[0]] < C[C[0]] || C[C[0]] == -1) && B[B[0]] != -1) {
			move(B, C);
		} // BC
		else if ((C[C[0]] < B[B[0]] || B[B[0]] == -1) && C[C[0]] != -1) {
			move(C, B);
		} // CB
	}

	/**
	 * This private method actually moves the disks behind the scene, and print
	 * out the step.
	 * 
	 * @param start
	 *            the int array to move form
	 * @param end
	 *            the int array to move to
	 */
	private void move(int[] start, int[] end) {
		int j = end[0]; // retrieve the top reference
		int i = start[0]; // retrieve the top reference
		System.out.print("Move disk " + start[i] + " from tower " + (char) start[start.length - 1]);
		System.out.println(" to tower " + (char) end[end.length - 1]);
		j--;
		end[j] = start[i];
		start[i] = 0;
		i++;
		end[0] = j;
		start[0] = i;
	}

	/**
	 * This method prints out steps of the Hanoi tower and returns the elapsed
	 * time of the run as well.
	 * 
	 * @param n
	 *            number of disks
	 * @return time in nanoseconds
	 */
	public long iterTime(int n) {
		iterTower(n, 'A', 'B', 'C');
		return (time2 - time1);
	}
}
