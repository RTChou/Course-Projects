/**
 * The <code>AVLTree</code> class implement AVL trees. AVL trees are balanced
 * binary search trees. The heights of the children of each node in the tree can
 * only be differ by at most 1. When the rule is violated, the rotation method
 * would apply, modifying the subtree to make the tree to become balanced again.
 * 
 * @version 1.0
 * @author Renee Ti Chou
 * 
 */
public class AVLTree {
	private int treeSize, indexOfArray, count;
	private Node root;
	private boolean trace = true; // switch for trace logs
	private boolean collectData = false;

	/**
	 * This private <code>Node</code> class is the building block of the AVL
	 * tree, in which the null nodes are created to represent the end of the
	 * tree. The null node has height of -1, so the leaf nodes would have height
	 * of 0.
	 *
	 */
	private class Node {
		private int data, height, balanceFactor;
		private Node left, right;
		private boolean isNull;

		/**
		 * Constructor for null nodes.
		 */
		private Node() {
			isNull = true;
			height = -1;
		}

		/**
		 * Constructor for general nodes.
		 * 
		 * @param data
		 *            an integer number
		 */
		private Node(int data) {
			this.data = data;
			left = new Node();
			right = new Node();
		}
	} // end Node class

	/**
	 * This constructor initializes <code>AVLTree</code>.
	 * 
	 */
	public AVLTree() {
		if (trace == true) {
			this.trace = true;
			System.out.println("#Trace: AVL Tree initialized.");
		}
	}

	/**
	 * The constructor builds an AVL tree from a sorted array that is passed in
	 * as an argument.
	 * 
	 * @param array
	 *            the sorted array
	 */
	public AVLTree(int[] array) {
		count = 0;

		root = buildTree(0, array.length - 1, array);

		if (trace == true) {
			this.trace = true;
			System.out.println("#Trace: AVL tree built from an array.");
			System.out.println("#Trace: Inorder printing of AVL tree--");
			printTree();
		}

		if (collectData == true) {
			System.out.println("Number of buildTree() calls: " + count);
			System.out.println("Number of Node objects allocated: " + treeSize);
		}
	} // end constructor

	/**
	 * This private method contains the algorithm for building an AVL tree from
	 * a sorted array recursively. The time complexity is O(n).
	 * 
	 * @param start
	 * @param end
	 * @param array
	 *            the sorted array
	 * @return the root node
	 */
	private Node buildTree(int start, int end, int[] array) {
		count++;

		int mid = (start + end) / 2; // integer division
		Node root = new Node(array[mid]);
		treeSize++;
		if (start == end) // stopping case
			return root;
		if (start <= mid - 1)
			root.left = buildTree(start, mid - 1, array); // recursive call
		root.right = buildTree(mid + 1, end, array); // recursive call
		updateInfo(root); // update the height of node
		return root;
	}

	/**
	 * This private method updates the height and balance factor information of
	 * the node.
	 * 
	 * @param node
	 */
	private void updateInfo(Node node) {
		node.height = Math.max(node.left.height, node.right.height) + 1;
		node.balanceFactor = node.right.height - node.left.height;
	}

	/**
	 * This method converts the AVL tree into a sorted integer array through
	 * inorder traversal.
	 * 
	 * @return the sorted array
	 */
	public int[] convertToSortedArray() {
		count = 0;

		if (trace == true)
			System.out.println("#Trace: AVL tree converted to sorted array.");

		int[] sortedArray = new int[treeSize];
		indexOfArray = 0;
		inTraverse(root, sortedArray);

		if (collectData == true) {
			System.out.println("Number of inTraverse() calls: " + count);
			System.out.println("Size of array allocated: " + treeSize);
		}

		if (trace == true) { // this trace prints out the sorted array
			System.out.print("#Trace: Sorted array-- ");
			for (int i = 0; i < sortedArray.length; i++)
				System.out.print(sortedArray[i] + " ");
			System.out.println();
		}
		return sortedArray;
	} // end
		// convertToSortedArray

	/**
	 * This private method convert the AVL tree into a sorted array recursively
	 * through inorder traversal.
	 * 
	 * @param root
	 * @param array
	 */
	private void inTraverse(Node root, int[] array) {
		if (root.isNull == true) // stopping case
			return;

		count++;

		inTraverse(root.left, array); // recursive call
		array[indexOfArray] = root.data;
		indexOfArray++;
		inTraverse(root.right, array); // recursive call
	}

	/**
	 * This method insert a new node into the AVL tree. When the balance of the
	 * tree is disturbed, it will re-balance the tree.
	 * 
	 * @param data
	 *            an integer value
	 */
	public void insert(int data) {
		count = 0;

		Node temp = new Node(data);

		if (trace == true)
			System.out.println("#Trace: Inserting " + data + " into AVL tree...");

		if (treeSize == 0) { // inserted as root
			root = temp;
			treeSize++;
			if (trace == true)
				System.out.println("#Trace: " + data + " inserted as root.");
		}
		else
			root = recursiveInsert(temp, root);
	} // end insert

	/**
	 * This private method inserts an integer number into the AVL tree
	 * recursively, and examines if the tree is balanced after insertion.
	 * 
	 * @param temp
	 *            the node being inserted
	 * @param root
	 *            the root of the subtree
	 * @return the root node
	 */
	private Node recursiveInsert(Node temp, Node root) {

		if (temp.data >= root.data) {
			count++;

			if (trace == true)
				System.out.print("#Trace: " + temp.data + " >= " + root.data + ", ");

			if (root.right.isNull != true) { // the node has right child
				if (trace == true)
					System.out.println("go down to right child.");
				root.right = recursiveInsert(temp, root.right); // recursive-call
			}
			else {
				root.right = temp; // attach node
				treeSize++;
				if (trace == true)
					System.out.println("attached as right child of " + root.data + ".");
			}
		}
		else {
			count++;

			if (trace == true)
				System.out.print("#Trace: " + temp.data + " < " + root.data + ", ");

			if (root.left.isNull != true) { // the node has left child
				if (trace == true)
					System.out.println("go down to left child.");
				root.left = recursiveInsert(temp, root.left); // recursive call
			}
			else {
				root.left = temp; // attach node
				treeSize++;
				if (trace == true)
					System.out.println("attached as left child of " + root.data + ".");
			}
		}
		updateInfo(root);

		/* examine the balance factor of the node and re-balance the tree */
		// Left-left case
		if (root.balanceFactor == -2 && root.left.balanceFactor == -1) {
			if (trace == true)
				System.out.println("#Trace: LL case occurred on node containing " + root.data + ".");
			root = rightRotate(root);
		}
		// Right-right case
		else if (root.balanceFactor == 2 && root.right.balanceFactor == 1) {
			if (trace == true)
				System.out.println("#Trace: RR case occurred on node containing " + root.data + ".");
			root = leftRotate(root);
		}
		// Left-right case
		else if (root.balanceFactor == -2 && root.left.balanceFactor == 1) {
			if (trace == true)
				System.out.println("#Trace: LR case occurred on node containing " + root.data + ".");
			root.left = leftRotate(root.left);
			root = rightRotate(root);
		}
		// Right-left case
		else if (root.balanceFactor == 2 && root.right.balanceFactor == -1) {
			if (trace == true)
				System.out.println("#Trace: RL case occurred on node containing " + root.data + ".");
			root.right = rightRotate(root.right);
			root = leftRotate(root);
		}
		return root;
	} // end recursiveInsert

	/**
	 * This private method rotates the node in the AVL tree with a left
	 * rotation.
	 * 
	 * @param root
	 *            the node being rotated
	 * @return the new root node generated after rotation
	 */
	private Node leftRotate(Node root) {
		if (trace == true)
			System.out.println("#Trace: Left rotation applied to node containing " + root.data + ".");
		Node temp;
		temp = root.right;
		root.right = temp.left;
		temp.left = root;
		updateInfo(root);
		updateInfo(temp);
		root = temp;
		return root;
	}

	/**
	 * This private method rotates the node in the AVL tree with a right
	 * rotation.
	 * 
	 * @param root
	 *            the node being rotated
	 * @return the new root node generated after rotation
	 */
	private Node rightRotate(Node root) {
		if (trace == true)
			System.out.println("#Trace: Right rotation applied to node containing " + root.data + ".");
		Node temp;
		temp = root.left;
		root.left = temp.right;
		temp.right = root;
		updateInfo(root);
		updateInfo(temp);
		root = temp;
		return root;
	}

	/**
	 * This method merges two AVL trees together to become one AVL tree. It will
	 * choose the most efficient algorithm within the three to optimize the
	 * performance.
	 * 
	 * @param tree
	 *            the AVL tree being merged with
	 * @return the merged AVL tree
	 */
	public AVLTree mergeWith(AVLTree tree) {
		double m, n, k;
		AVLTree mergedTree;
		m = this.treeSize;
		n = tree.treeSize;
		k = (m + n) / (Math.log(m + n) / Math.log(2)); // (m+n)/lg(m+n)

		if (trace == true)
			System.out.println("#Trace: Merging two AVL trees, where m = " + (int) m + " and n = " + (int) n + "...");

		// O(mlg(m+n)) performance is min
		if (m < k && m < n) {
			if (trace == true)
				System.out.println("#Trace: O(mlg(m+n)) merging applied.");

			mergedTree = type1Merge(tree);

			if (trace == true)
				mergedTree.printTree();
		}

		// O(nlg(m+n)) performance is min
		else if (n < k && n < m) {
			if (trace == true)
				System.out.println("#Trace: O(nlg(m+n)) merging applied.");

			mergedTree = type2Merge(tree);

			if (trace == true)
				mergedTree.printTree();
		}

		// O(m+n) performance is min or other equivalent cases
		else {
			if (trace == true)
				System.out.println("#Trace: O(m+n) merging applied.");

			mergedTree = type3Merge(tree);
		}
		return mergedTree;
	} // end mergeWith

	/*
	 * The algorithms for merging two trees are separated for analysis purposes.
	 * The followings are the three algorithms.
	 */

	/**
	 * The protected method contains the merging algorithm having time
	 * complexity O(mlg(m+n)).
	 * 
	 * @param tree
	 *            the AVL tree being merged
	 * @return the merged AVL tree
	 */
	protected AVLTree type1Merge(AVLTree tree) {

		int auxCount, numComparisons, spaceCount;
		numComparisons = 0;
		spaceCount = 0;

		int[] auxArray = this.convertToSortedArray();

		for (int i = 0; i < auxArray.length; i++) {
			tree.insert(auxArray[i]);
			auxCount = tree.getCount();
			numComparisons += auxCount;
			spaceCount++;
		}
		if (collectData == true) {
			System.out.println("Number of comparisons: " + numComparisons);
			System.out.println("Number of Node objects allocated: " + spaceCount);
		}
		return tree;
	}

	/**
	 * The protected method contains the merging algorithm having time
	 * complexity O(nlg(m+n)).
	 * 
	 * @param tree
	 *            the AVL tree being merged
	 * @return the merged AVL tree
	 */
	protected AVLTree type2Merge(AVLTree tree) {

		int auxCount, numComparisons, spaceCount;
		numComparisons = 0;
		spaceCount = 0;

		int[] auxArray = tree.convertToSortedArray();

		for (int i = 0; i < auxArray.length; i++) {
			this.insert(auxArray[i]);
			auxCount = this.getCount();
			numComparisons += auxCount;
			spaceCount++;
		}
		if (collectData == true) {
			System.out.println("Number of comparisons: " + numComparisons);
			System.out.println("Number of Node objects allocated: " + spaceCount);
		}
		return this;
	}

	/**
	 * The protected method contains the merging algorithm having time
	 * complexity O(m+n).
	 * 
	 * @param tree
	 *            the AVL tree being merged
	 * @return the merged AVL tree
	 */
	protected AVLTree type3Merge(AVLTree tree) {
		int[] auxArray1 = this.convertToSortedArray();
		int[] auxArray2 = tree.convertToSortedArray();
		int[] auxArray = mergeArrays(auxArray1, auxArray2);
		return new AVLTree(auxArray);
	}

	/**
	 * This private method merges two sorted array together and creates a new
	 * sorted array. The time complexity is O(m+n).
	 * 
	 * @param array1
	 *            a sorted array
	 * @param array2
	 *            a sorted array
	 * @return the mergedArray
	 */
	private int[] mergeArrays(int[] array1, int[] array2) {
		int[] mergedArray = new int[array1.length + array2.length];
		int j, k, mergeCount;
		j = 0;
		k = 0;
		mergeCount = 0;
		if (trace == true)
			System.out.println("#Trace: Two arrays merged.");

		for (int i = 0; i < mergedArray.length; i++) {
			if (j >= array1.length) { // no element left in array1
				mergedArray[i] = array2[k];
				k++;
				mergeCount++;
			}
			else if (k >= array2.length) { // no element left in array2
				mergedArray[i] = array1[j];
				j++;
				mergeCount++;
			}
			else if (array1[j] < array2[k] && j < array1.length) {
				mergedArray[i] = array1[j];
				j++;
				mergeCount++;
			}
			else if (array1[j] >= array2[k] && k < array2.length) {
				mergedArray[i] = array2[k];
				k++;
				mergeCount++;
			}
		} // end for loop
		if (collectData == true) {
			System.out.println("Number of elements put into new array: " + mergeCount);
			System.out.println("Size of array allocated: " + mergedArray.length);
		}
		return mergedArray;
	} // end mergeArrays

	/**
	 * This method prints out the AVL tree through inorder traversal.
	 */
	public void printTree() {
		System.out.println("+-----------+--------+----------------+");
		System.out.println("| Node Data | Height | Balance Factor |");
		System.out.println("+-----------+--------+----------------+");
		printInTrav(root);
		System.out.println("+-----------+--------+----------------+");
	}

	/**
	 * This private method traverses through the AVL tree recursively in an
	 * inorder way, and prints out the information of each node.
	 * 
	 * @param root
	 *            the root node of the tree
	 */
	private void printInTrav(Node root) {
		if (root.isNull == true) // stopping case
			return;
		printInTrav(root.left); // recursive call
		System.out.printf("|%10d |%7d |%15d |", root.data, root.height, root.balanceFactor);
		System.out.println();
		printInTrav(root.right); // recursive call
	}

	/**
	 * The getter for the private field <code>count</code>.
	 * 
	 * @return the value of <code>count</code>
	 */
	public int getCount() {
		return count;
	}

} // end AVLTree class
