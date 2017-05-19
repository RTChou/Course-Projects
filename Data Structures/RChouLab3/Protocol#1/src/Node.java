/**
 * This class is the building block for <code>HuffmanTree</code> and
 * <code>PriorityQueue</code>, which contains the priority scheme.
 * 
 * @version 1.0 
 * @author Renee Ti Chou
 *
 */
public class Node implements Comparable<Node> {
	private String data;
	private int freq;
	private Node parent, left, right; // node pointers

	/**
	 * Create a new node.
	 * 
	 * @param data
	 * @param freq
	 */
	public Node(String data, int freq) {
		this.data = data;
		this.freq = freq;
		left = null;
		right = null;
	}

	/**
	 * 
	 * @return <b>String</b> data
	 */
	public String getData() {
		return data;
	}

	/**
	 * 
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 
	 * @return <b>int</b> freq
	 */
	public int getFreq() {
		return freq;
	}

	/**
	 * 
	 * @param freq
	 */
	public void setFreq(int freq) {
		this.freq = freq;
	}

	/**
	 * 
	 * @return <b>Node</b> left
	 */
	public Node getLeft() {
		return left;
	}

	/**
	 * 
	 * @param left
	 */
	public void setLeft(Node left) {
		this.left = left;
		left.parent = this;
	}

	/**
	 * 
	 * @return <b>Node</b> right
	 */
	public Node getRight() {
		return right;
	}

	/**
	 * 
	 * @param right
	 */
	public void setRight(Node right) {
		this.right = right;
		right.parent = this;
	}

	/**
	 * 
	 * @return <b>Node</b> parent
	 */
	public Node getParent() {
		return parent;
	}

	@Override
	public int compareTo(Node node) {
		if (freq < node.getFreq())
			return -1;

		else if (freq == node.getFreq()) {

			if (data.length() < node.getData().length())
				return -1;

			else if (data.length() == node.getData().length()) {

				if (data.charAt(0) < node.getData().charAt(0))
					return -1;
			}

		}
		return 1;
	}

} // end class
