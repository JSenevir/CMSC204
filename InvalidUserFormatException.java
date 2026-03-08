/**
 * A custom exception for invalid file format
 */
public class InvalidUserFormatException extends Exception {
	/**
	 * No parameter constructor
	 */
	public InvalidUserFormatException() {
		super("Format error");
	}
	
	/**
	 * Parameterized constructor
	 * @param message the exception message
	 */
	public InvalidUserFormatException(String message) {
		super(message);
	}
}
