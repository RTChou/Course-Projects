/**
 * Solves the problem using a recursive approach.
 * <p>
 * It contains two methods: <i>recTower</i> and <i>recTime</i>.
 * 
 * @version 1.0 2017-03-21
 * @author Renee Ti Chou
 */
public class RecHanoi {
	/**
	 * The recursive method which prints out steps of the Hanoi tower of the
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
	 */
	public void recTower(int n, char poleA, char poleB, char poleC) {
		if (n == 0) // stopping case
			return;

		else {
			recTower(n - 1, poleA, poleC, poleB); // recursive call
			System.out.println("Move disk " + n + " from tower " + poleA + " to tower " + poleB);
			recTower(n - 1, poleC, poleB, poleA); // recursive call
		}
	}

	/**
	 * This method prints out steps of the Hanoi tower and returns the elapsed
	 * time of the run as well.
	 * 
	 * @param n
	 *            number of disks
	 * @return time in nanoseconds
	 */
	public long recTime(int n) {
		long time1, time2;

		time1 = System.nanoTime();
		recTower(n, 'A', 'B', 'C');
		time2 = System.nanoTime();

		return (time2 - time1);
	}
}
