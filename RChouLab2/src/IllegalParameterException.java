/**
 * Unchecked exception thrown to indicate illegal parameter is passed in.
 * 
 * @version 1.0 2017-03-21
 * @author Renee Ti Chou
 */
public class IllegalParameterException extends RuntimeException {
	/**
	 * The serialVersionUID is generated by Eclipse.
	 */
	private static final long serialVersionUID = 3622294185146630231L;

	private String message;

	/**
	 * The constructor initializes message.
	 * 
	 * @param message
	 */
	public IllegalParameterException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
