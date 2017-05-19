/*
 * Renee Ti Chou
 */
/**
 * <code>Quicksort2</code> contains two types of sorts: quicksort and insertion
 * sort.
 * <p>
 * The quicksort selects the first element of the partition as the pivot, and is
 * performed until the size of the partition is smaller than or equal to 100
 * elements. Then the insertion sort takes over the partition and finishes the
 * sorting.
 * <p>
 * The code except for <code>insertionSort()</code> is borrowed from <a href=
 * "http://www.geeksforgeeks.org/iterative-quick-sort/">http://www.geeksforgeeks.org/iterative-quick-sort/</a>,
 * and is contributed by Rajat Mishra.
 */
public class Quicksort2 {

	/**
	 * Exchange the two elements in an array.
	 * 
	 * @param arr
	 *            the int array
	 * @param i
	 *            the index of the element
	 * @param j
	 *            the index of the other element
	 */
	private void swap(int arr[], int i, int j) {
		int t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}

	/**
	 * Select the first element as pivot and partition the array.
	 * 
	 * @param arr
	 *            the int array
	 * @param dn
	 *            the index of the first element of the partition
	 * @param up
	 *            the index of the last element of the partition
	 * @return the index of the pivot
	 */
	private int partition(int arr[], int dn, int up) {
		int x = arr[dn]; // pivot
		int i = (up + 1);

		for (int j = up; j > dn; j--) {
			if (arr[j] >= x) {
				i--;
				swap(arr, i, j); // swap arr[i] and arr[j]
			}
		} // end for loop
		swap(arr, i - 1, dn); // swap arr[i+1] and arr[h]
		return (i - 1);
	}

	/**
	 * This method sorts the input array in ascending order.
	 * 
	 * @param arr
	 *            the int array to be sorted
	 * @return the elapsed time for sorting
	 */
	public long sort(int arr[]) {
		int n, top, h, l;
		int stack[];
		long time1, time2;

		n = arr.length;
		stack = new int[n]; // create auxiliary stack
		top = -1; // initialize top of stack

		time1 = System.nanoTime();

		/*
		 * Use insertion sort when the size of array is smaller than or equal to
		 * 100
		 */
		if (n <= 100) {
			insertionSort(arr, 0, (n - 1));
			time2 = System.nanoTime();
			return (time2 - time1);
		}

		time1 = System.nanoTime();

		/* push initial values in the stack */
		stack[++top] = 0;
		stack[++top] = (n - 1);

		/* Do partition iteratively */
		while (top >= 0) {
			// pop h and l
			h = stack[top--];
			l = stack[top--];

			// set pivot element at it's proper position
			int p = partition(arr, l, h);

			// If there are more than 100 elements on left side of pivot,
			// then push left side to stack
			if (p - 100 > l) {
				stack[++top] = l;
				stack[++top] = p - 1;
			}
			else
				insertionSort(arr, l, p - 1);

			// If there are more than 100 elements on right side of pivot,
			// then push right side to stack
			if (p + 100 < h) {
				stack[++top] = p + 1;
				stack[++top] = h;
			}
			else
				insertionSort(arr, p + 1, h);
		} // end while
		time2 = System.nanoTime();
		return (time2 - time1);
	}

	/**
	 * This private method sorts the array in ascending order using the strategy
	 * of insertion sort.
	 * 
	 * @param arr
	 *            the int array
	 * @param l
	 *            the index of the first element of the partition
	 * @param h
	 *            the index of the last element of the partition
	 */
	private void insertionSort(int[] arr, int l, int h) {

		/* Pass through each element and insert it at the head of the list */
		for (int i = (l + 1); i < (h + 1); i++) {
			int j = i;
			while ((j - 1) >= l) {
				if (arr[j] < arr[(j - 1)]) {
					swap(arr, j, (j - 1));
					j--;
				}
				else
					break;
			} // end while
		} // end for loop
	}
} // end class