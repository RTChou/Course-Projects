import java.util.NoSuchElementException;

/**
 * LStack is the source code for the Lab 1 programming assignment.
 * <p>
 * It is implemented by a linked-structure, which only stores data of String
 * type. It contains standard stack methods <i>push</i>, <i>pop</i>, and
 * <i>isEmpty</i>, as well as other methods <i>size</i> and <i>cleanUp</i>.
 * <p>
 * All of the methods except for <i>cleanUp</i> have constant time cost O(1).
 * The cost of time to <i>cleanUp</i> is O(n).
 * 
 * @version 1.0 2017-02-28
 * @author Renee Ti Chou
 */

public class LStack {
	private int size;
	private SNode top;

	/**
	 * The private class instantiate a stack node that is going to contain the
	 * information of items being put onto the stack.
	 */
	private class SNode {
		private String stringData = null;
		private SNode next;
	}

	/**
	 * The constructor initializes the top reference and the size of the stack.
	 */
	public LStack() {
		top = null;
		size = 0;
	}

	/**
	 * The method checks if the stack is empty.
	 * 
	 * @return true if stack is empty; false if stack is not empty.
	 */
	public boolean isEmpty() {
		return (top == null);
	}

	/**
	 * 
	 * @return size of the stack
	 */
	public int size() {
		return size;
	}

	/**
	 * The method push the String item onto the stack, and increases the stack
	 * size by one.
	 * 
	 * @param item
	 */
	public void push(String item) {
		SNode temp = new SNode(); // Create a temporary node to store the data.
		temp.stringData = item;
		temp.next = top;
		top = temp;
		size++;
		return;
	}

	/**
	 * The method pop out a String item from the stack in a LIFO manner. It
	 * throws unchecked RuntimeException for preventable errors. The stack size
	 * is decreased by one.
	 * 
	 * @return the String value of the deleted item.
	 */
	public String pop() {
		String item;
		SNode temp = top; // Create a temporary reference to the deleted item.
		if (isEmpty())
			throw new NoSuchElementException("Stack underflow");
		else
			item = top.stringData; // Retrieve the value of the deleted item.
		top = top.next;
		temp.next = null; // Disconnect the deleted item.
		size--;
		return item;
	}

	/**
	 * The method cleans up all of the items in the stack.
	 */
	public void cleanUp() {
		while (isEmpty() != true) {
			pop();
		}
		return;
	}
}
