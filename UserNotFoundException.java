/**
 * A custom exception for entering an invalid username
 */
public class UserNotFoundException extends Exception {
	/**
	 * No parameter exception
	 */
	public UserNotFoundException(){
		super("A user with that username cannot be found");
	}
	
	/**
	 * Parameterized exception
	 * @param message the exception message
	 */
	public UserNotFoundException(String message) {
		super(message);
	}
}
