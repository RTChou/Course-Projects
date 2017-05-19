/**
 * This class uses a binary heap structure to implement the priority queue.
 * <p>
 * It contains standard methods <i>insert</i>, <i>delete</i>, and
 * <i>isEmpty</i>, as well as the method <i>getSize</i>.
 * 
 * @version 1.0
 * @author Renee Ti Chou
 *
 */
public class PriorityQueue {
	private Node[] nodes; // represent the heap structure
	private int rear;
	private int size;

	/**
	 * Create a new priority queue.
	 * 
	 * @param spaceSize
	 */
	public PriorityQueue(int spaceSize) {
		nodes = new Node[spaceSize + 1];
		rear = 0;
		this.size = 0;
	}

	/**
	 * This method insert the node into the end of the heap and reorganizes the
	 * heap structure according to the priority scheme.
	 * 
	 * @param item
	 * @throws RuntimeException
	 */
	public void insert(Node item) throws RuntimeException {
		int child, parent;

		/* check for overflow */
		if (rear == nodes.length - 1)
			throw new RuntimeException("Queue Overflow");

		/* insert the first item at nodes[1] */
		if (rear == 0) { // rear == null
			nodes[1] = item;
			rear = 1;
			size++;
			return;
		}

		/*
		 * Insert item which is not the first one and reorganize the heap
		 * structure. Items with smaller values have higher priority.
		 */
		rear++; // update rear
		nodes[rear] = item;
		child = rear;
		while (child > 1) {
			parent = child / 2;
			if (nodes[parent].compareTo(nodes[child]) == 1) {
				exchange(parent, child);
				child /= 2;
			}
			else
				break;
		} // end while
		size++;
	}

	/**
	 * This method delete the item with the highest priority, put the node at
	 * the end of the tree to the root, and reorganize the structure according
	 * to the priority scheme.
	 * 
	 * @return item
	 * @throws RuntimeException
	 */
	public Node delete() throws RuntimeException {
		Node item;
		int parent, child;

		if (isEmpty())
			throw new RuntimeException("Queue Underflow");

		item = nodes[1];
		nodes[1] = nodes[rear];
		nodes[rear] = null;
		rear--;
		size--;

		/* reorganize the heap */
		parent = 1;
		while (parent <= rear / 2) {
			child = parent * 2;
			if (nodes[child + 1] != null) { // check right child

				if (nodes[child].compareTo(nodes[child + 1]) == 1) {
					// if right child has higher priority than left child

					if (nodes[parent].compareTo(nodes[child + 1]) == -1)
						break;
					else {
						exchange(parent, child + 1);
						parent = child + 1;
						continue;
					}
				}

			}

			/*
			 * when there is no right child or left child smaller than right
			 * child
			 */
			if (nodes[parent].compareTo(nodes[child]) == -1)
				break;
			else {
				exchange(parent, child);
				parent = child;
			}
		} // end while
		return item;
	} // end delete

	/**
	 * 
	 * @return <code>true</code> if the priority queue is empty;
	 *         <code>false</code> if not.
	 */
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * 
	 * @return <b>int</b> size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * This private method exchange two nodes in the heap tree.
	 * 
	 * @param node1
	 * @param node2
	 */
	private void exchange(int node1, int node2) {
		nodes[0] = nodes[node1]; // nodes[0] as temp pointer
		nodes[node1] = nodes[node2];
		nodes[node2] = nodes[0];
		nodes[0] = null;
	}
} // end class
