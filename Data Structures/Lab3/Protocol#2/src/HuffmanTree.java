/**
 * <code>HuffmanTree</code> uses the frequency table passed in to build the
 * Huffman tree and generate the corresponding code table without annotation.
 * Users should annotate it by the frequency table used to build the tree.
 * 
 * @version 1.0
 * @author Renee Ti Chou
 *
 */
public class HuffmanTree {
	private Node root;
	private Node[] freqTable;
	private PriorityQueue pq;
	private String[] codeTable;

	/**
	 * The constructor initializes the priority queue and code table according
	 * to the parameter of frequency table
	 * 
	 * @param freqTable
	 */
	public HuffmanTree(Node[] freqTable) {
		root = null;
		this.freqTable = freqTable;
		pq = new PriorityQueue(freqTable.length);
		codeTable = new String[freqTable.length];
	}

	/**
	 * This method make the Huffman tree using <code>freqTable</code> and
	 * generate the code table
	 */
	public void makeTree() {
		Node node1, node2, node;

		/* insert the nodes of freqTable to the priority queue */
		for (int i = 0; i < freqTable.length; i++) {
			if (freqTable[i] != null)
				pq.insert(freqTable[i]);
		}

		/* build the Huffman tree */
		while (pq.getSize() >= 2) {
			node1 = pq.delete();
			node2 = pq.delete();
			node = new Node(node1.getData() + node2.getData(), node1.getFreq() + node2.getFreq());
			node.setLeft(node1);
			node.setRight(node2);
			pq.insert(node);
		}
		if (!pq.isEmpty())
			root = pq.delete();

		makeCodeTable();
	}

	/**
	 * 
	 * @return <b>String[]</b> codeTable
	 */
	public String[] getCodeTable() {
		return codeTable;
	}

	/**
	 * 
	 * @return <b>Node</b> root
	 */
	public Node getRoot() {
		return root;
	}

	/**
	 * This method prints the tree in preorder.
	 */
	public void printTree() {
		System.out.println("+-----------------------------+-----------+");
		System.out.println("| Node_data                   | Frequency |");
		System.out.println("+-----------------------------+-----------+");
		preTrav(root);
		System.out.println("+-----------------------------+-----------+");
	}

	/**
	 * This private method generate the code table without annotation. It does
	 * it by traversing from the leaves to the root.
	 */
	private void makeCodeTable() {
		Node node;

		/*
		 * Use the node pointers of freqTable to find the leaves and traverse to
		 * the root
		 */
		for (int i = 0; i < freqTable.length; i++) {
			node = freqTable[i];
			if (node != null) {
				codeTable[i] = "";
				while (node != root) {
					if (node == node.getParent().getLeft())
						codeTable[i] = "0" + codeTable[i];
					else
						codeTable[i] = "1" + codeTable[i];
					node = node.getParent();
				} // end while
			} // end if
		} // end for loop
	}

	/**
	 * This private method traverses the tree in preorder.
	 * 
	 * @param root
	 */
	private void preTrav(Node root) {
		if (root == null) // stopping case
			return;
		System.out.printf("| %-28s|%10d |", root.getData(), root.getFreq());
		System.out.println();
		preTrav(root.getLeft());
		preTrav(root.getRight());
	}
} // end class
