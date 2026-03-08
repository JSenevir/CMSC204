/**
 * A custom exception for invalid passwords
 */
public class InvalidPasswordException extends Exception {
	/**
	 * No parameter constructor
	 */
	public InvalidPasswordException() {
		super("Incorrect password");
	}
	
	/**
	 * Parameterized constructor
	 * @param message the exception message
	 */
	public InvalidPasswordException(String message) {
		super(message);
	}
}
